package com.example.youtubeapi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    
    String IP; // IPアドレス
    int port; // ポート番号
    
    // サーバーに送る用のデータたち
    int time; // 時間
    String keyword; // 検索ワード
    int condition; // 最適化条件
    User user;
    
    static boolean check = false; // メソッド呼び出しタイミング調整用
    
    // コンストラクタ
    Client(String IP, int port) {
        this.IP = IP;
        this.port = port;
    }
    
    //Userインスタンスをセット
    public void setUser(User user) {
        this.user = user;
    }
    
    // IPアドレスとポート番号を使ってソケットを接続
    public Socket setConnect(String IPa, int port) {
        Socket socket = null;
        try {
            socket = new Socket(IPa, port);
            return socket;
        } catch (Exception e) {
            System.out.println(e.toString());
            if (this.user != null) {
                this.user.fromClient("error");
            }
            return null;
        }
    }
    
    // ユーザーからのメッセージを受け取る
    public void fromUser(String time, String keyword, int condition) {
        this.time = Integer.parseInt(time);
        this.keyword = keyword;
        this.condition = condition;
        check = true; // sendOperation()呼び出しフラグ
        
        System.out.println("時間:" + this.time);
        System.out.println("検索ワード:" + this.keyword);
        System.out.println("最適化条件:" + this.condition);
    }
    
    // 情報をサーバーに送る
    public void sendOperation(BufferedWriter out) {
        try {
            System.out.println("sendOperationはいってるよ");
            out.write(String.valueOf(this.time) + "\n");
            out.write(this.keyword + "\n");
            out.write(String.valueOf(this.condition) + "\n");
            check = false; // 呼び出しフラグを折る
            out.flush();
            
            System.out.println("時間送信:" + this.time);
            System.out.println("検索ワード送信:" + this.keyword);
            System.out.println("最適化条件送信:" + this.condition);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    
    public boolean checkFlag() {
        System.out.println("checkするよ");
        return check;
    }
    
    public static void main(String[] args) {
        
        // サーバープログラム用のソケット・入出力バッファ
        Socket socket = null;
        BufferedWriter out = null;
        BufferedReader in = null;
        
        boolean check = false;
        
        String localIP = null;
        try {
            InetAddress addr = InetAddress.getLocalHost();
            localIP = addr.getHostAddress();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        
        Client client = new Client(localIP, 10001);
        User user = new User(client);
        client.setUser(user);
        
        try {
            System.out.println("接続なう");
            // ソケットの接続試行。IPa、portは別で指定
            socket = client.setConnect(client.IP, client.port);
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Receiver receiver = new Receiver(socket, user);
            receiver.start();
            
            while (true) {
                // System.out.println("ループなう");
                if (client.checkFlag() == true) {
                    System.out.println("sendOperation呼び出すよ");
                    // サーバー側へユーザープログラムの入力を送信
                    client.sendOperation(out);
                    break;
                }
            }
            
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}

class Receiver extends Thread {
    private User user;
    private InputStreamReader sisr; // 受信データ用文字ストリーム
    private BufferedReader br; // 文字ストリーム用のバッファ

    // 内部クラスReceiverのコンストラクタ
    Receiver(Socket socket, User user) {
        try {
            this.user = user;
            sisr = new InputStreamReader(socket.getInputStream()); // 受信したバイトデータを文字ストリームに
            br = new BufferedReader(sisr); // 文字ストリームをバッファリングする
        } catch (IOException e) {
            System.err.println("データ受信時にエラーが発生しました(C): " + e);
        }
    }
    
    // 内部クラス Receiverのメソッド
    public void run() {
    	int count = 0;
        try {
            while (true) { // データを受信し続ける
            	System.out.println("受信開始"+count);
                String inputLine = br.readLine(); // 受信データを一行分読み込む
                if (inputLine != null) { // データを受信したら
                	System.out.println("fromClient呼び出し");
                	count++;
                    this.user.fromClient(inputLine); // ユーザークラスに書き込む
                }
            }
        } catch (IOException e) {
            System.err.println("データ受信時にエラーが発生しました(R): " + e);
        }
    }
}

