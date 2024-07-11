package com.example.youtubeapi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Server {
    private static final int PORT = 10001;
    private static final int MAX_CONNECTIONS = 100;
    static final Semaphore semaphore = new Semaphore(MAX_CONNECTIONS);

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("サーバーがポート " + PORT + " で待機しています");

            while (true) {
                Socket socket = serverSocket.accept();
                
                if (semaphore.tryAcquire()) {
                    System.out.println("新しいクライアントが接続しました");
                    new ServerThread(socket, semaphore).start(); // セマフォを渡す
                } else {
                    System.out.println("最大接続数に達しました。新しいクライアントを拒否します。");
                    rejectClient(socket);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void rejectClient(Socket socket) {
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            writer.write("サーバーが忙しいです。後でもう一度お試しください。");
            writer.newLine();
            writer.flush();
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

class ServerThread extends Thread {
    private Socket socket;
    private Semaphore semaphore;

    public ServerThread(Socket socket, Semaphore semaphore) {
        this.socket = socket;
        this.semaphore = semaphore;
    }

    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            // クライアントからのデータを受け取る
            int time = Integer.parseInt(reader.readLine());
            String searchWord = reader.readLine();
            int optimizationCondition = Integer.parseInt(reader.readLine());

            System.out.println("クライアントから受信しました:");
            System.out.println("時間: " + time);
            System.out.println("検索ワード: " + searchWord);
            System.out.println("最適化条件: " + optimizationCondition);
            
            // Dataクラスのメソッドを呼び出して、検索結果を取得する
            List<List<Object>> videoData = Data.processSearchWord(searchWord);
            
            // Playlistクラスのメソッドを呼び出して、ソートされたデータを取得する
            ArrayList<ArrayList<Object>> sortedData = Playlist.processAndSort(videoData, time, optimizationCondition);
            
            // プレイリスト総時間を計算
            int totalTime = calculateTotalTime(sortedData);
            
            // 合計時間を分:秒に変換
            String totalTimeFormatted = formatTime(totalTime);
            
            // プレイリストURLを取得
            String playlistUrl = Data.getPlaylistURL(sortedData);
            System.out.println("プレイリストのURL: " + playlistUrl);

            // プレイリストデータをクライアントに送信する
            writer.write(playlistUrl);
            writer.newLine();
            writer.write(totalTimeFormatted);
            writer.newLine();
            // ソートされたデータをクライアントに送信する
            for (ArrayList<Object> video : sortedData) {
                writer.write("タイトル: " + video.get(0));
                writer.newLine();
                writer.write("URL: https://www.youtube.com/watch?v=" + video.get(3));
                writer.newLine();
            }
            
            writer.write("END");
            writer.newLine();

            writer.flush();

            // 接続を閉じる
            reader.close();
            writer.close();
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            System.out.println("スレッドが中断されました");
            ex.printStackTrace();
            Thread.currentThread().interrupt(); // スレッドの中断ステータスを再設定
        } finally {
            semaphore.release();
        }
    }
    
    private int calculateTotalTime(ArrayList<ArrayList<Object>> playlist) {
        int totalTime = 0;
        for (ArrayList<Object> video : playlist) {
            totalTime += (int) video.get(1);
        }
        return totalTime;
    }
    
    private String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%d:%02d", minutes, seconds);
    }
}