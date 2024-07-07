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
            
            // sortクラスのメソッドを呼び出して、ソートされたデータを取得する
            ArrayList<ArrayList<Object>> sortedData = sort.processAndSort(videoData, time, optimizationCondition);


            // プレイリストデータを生成する（デモ用）
            String playlistUrl = "http://example.com/playlist";
            int totalTime = 3600; // 例として合計時間を秒で設定
            String[] songTitles = {"Song 1", "Song 2", "Song 3"};
            String[] songUrls = {"http://example.com/song1", "http://example.com/song2", "http://example.com/song3"};

	        // 合計時間を分:秒に変換
            String totalTimeFormatted = formatTime(totalTime)
;
            // プレイリストデータをクライアントに送信する
            writer.write(playlistUrl);
            writer.newLine();
            writer.write(totalTimeFormatted);
            writer.newLine();
            for (int i = 0; i < songTitles.length; i++) {
                writer.write(songTitles[i]);
                writer.newLine();
                writer.write(songUrls[i]);
                writer.newLine();
            }
            
            // ソートされたデータをクライアントに送信する
            /*for (ArrayList<Object> video : sortedData) {
                writer.write("Title: " + video.get(0));
                writer.newLine();
                writer.write("Duration: " + formatTime((int) video.get(1)));
                writer.newLine();
                writer.write("View Count: " + video.get(2));
                writer.newLine();
                writer.write("Video URL: https://www.youtube.com/watch?v=" + video.get(3));
                writer.newLine();
            }*/

            writer.flush();

            // 接続を閉じる
            reader.close();
            writer.close();
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            semaphore.release();
        }
    }
    private String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%d:%02d", minutes, seconds);
    }
}