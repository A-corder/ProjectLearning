import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

public class ClientTest {
    
    String IP; // IPアドレス
    int port; // ポート番号
    
    // サーバーに送る用のデータたち
    int time; // 時間
    String keyword; // 検索ワード
    int condition; // 最適化条件
    UserStub user;
    
    //ユーザークラスから送られてくるフラグ
    boolean Regenerate; //再生成であるかどうかを判断するフラグ

    static boolean check = false; // sendOperationメソッドを最初に呼び出すタイミング調整用フラグ
    
    Socket socket = null; // ソケット
    BufferedWriter out = null; // 出力バッファ
    BufferedReader in = null; // 入力バッファ

    // コンストラクタ
    ClientTest(String IP, int port) {
        this.IP = IP;
        this.port = port;
    }
    
    //Userインスタンスをセット
    public void setUser(UserStub user) {
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
    public void fromUser(String time, String keyword, int condition, boolean Regenerate) {
        this.time = Integer.parseInt(time);
        this.keyword = keyword;
        this.condition = condition;
        this.Regenerate = Regenerate;

        if (Regenerate) {
            try (Socket socket = setConnect(this.IP, this.port);
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                sendOperation(out, in);
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        } else {
            check = true; // sendOperation()呼び出しフラグ
        }
        
        System.out.println("時間:" + this.time);
        System.out.println("検索ワード:" + this.keyword);
        System.out.println("最適化条件:" + this.condition);
    }
    
    // 情報をサーバーに送る
    public void sendOperation(BufferedWriter out, BufferedReader in) {
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

            // サーバからのレスポンスを待つ
            String responseLine;
            while ((responseLine = in.readLine()) != null) {
                this.user.fromClient(responseLine);
                if (responseLine.equals("END")) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    
    //ウィンドウが閉じられたことをサーバーに送る
    public void sendENDmessage(BufferedWriter out) {
    	try {
    		out.write("WindowsClosed" + "\n");
    	}catch(Exception e) {
    		System.out.println(e.toString());
    	}

    }

    public boolean checkFlag() {
        System.out.println("checkするよ");
        return check;
    }

    public boolean initializeCheckFlag() {
    	System.out.println("initializeCheckFlag呼び出し.checkをfalseにするよ");
    	check = false;
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
        
        ClientTest client = new ClientTest(localIP, 10000);
        UserStub user = new UserStub(client);
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
                    client.sendOperation(out, in);
                    break;
                }
            }
            
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}

class Receiver extends Thread {
    private UserStub user;
    private InputStreamReader sisr; // 受信データ用文字ストリーム
    private BufferedReader br; // 文字ストリーム用のバッファ

    // 内部クラスReceiverのコンストラクタ
    Receiver(Socket socket, UserStub user) {
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