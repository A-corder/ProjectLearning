package com.example.youtubeapi;

import java.io.*;
import java.net.*;

public class ServerDriver {
    public static void main(String[] args) throws Exception {
        // サーバーを別スレッドで起動
        Thread serverThread = new Thread(() -> {
            Server.main(new String[]{});
        });
        serverThread.start();

        // サーバーの起動を待つ
        Thread.sleep(1000);

        // 疑似クライアント作動
        testClientConnection();

        // サーバーを停止
        System.exit(0);
    }

    private static void testClientConnection() {
        try (Socket socket = new Socket("localhost", 10001);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            System.out.println("クライアント: サーバーに接続しました。");

            // テストケース1: 有効なリクエストを送信
            sendRequest(writer, "10", "プログラミング", "1");
            printResponse(reader);

            writer.flush();

            System.out.println("クライアント: テスト完了");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendRequest(BufferedWriter writer, String time, String searchWord, String condition) throws IOException {
        writer.write(time);
        writer.newLine();
        writer.write(searchWord);
        writer.newLine();
        writer.write(condition);
        writer.newLine();
        writer.flush();
        System.out.println("クライアント: リクエスト送信 - 時間: " + time + ", 検索ワード: " + searchWord + ", 条件: " + condition);
    }

    private static void printResponse(BufferedReader reader) throws IOException {
        String line;
        System.out.println("サーバーからの応答:");
        while (!(line = reader.readLine()).equals("END")) {
            System.out.println(line);
        }
        System.out.println("応答終了");
    }
}