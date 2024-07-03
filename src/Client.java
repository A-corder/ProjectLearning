import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Client{
    
    String IPa;
    int port;
    static int time = -1;//時間
    static String keyword;//検索ワード
    static int condition = -1;//最適化条件
    ArrayList<String> list = new ArrayList<String>();
    
    Client(String IPa, int port){
        this.IPa = IPa;
        this.port = 10001;
    }
    
    //IPアドレスとポート番号を使ってソケットを接続
    public Socket setConnect(String IPa, int port){
        
    	Socket socket = null;
        try{
            socket = new Socket(IPa, port);
            return socket;
        }catch(Exception e){
            System.out.println(e.toString());
            return null;
        }
        
    }
    public void userReceive(String time, String keyword, int condition) {
    	Client.time = Integer.parseInt(time);
    	Client.keyword = keyword;
    	Client.condition = condition;
    	
    	System.out.println("時間:"+Client.time);
    	System.out.println("検索ワード:"+Client.keyword);
    	System.out.println("最適化条件:"+Client.condition);
    }
    
    public void sendOperation(BufferedWriter out){
        
        try{
        	out.write(Client.time);
        	out.write(Client.keyword);
        	out.write(Client.condition);
        }catch(Exception e){
            System.out.println(e.toString());
        }
        
    }
    
    //URL・曲名を受け取る
    ArrayList<String> getMessage(BufferedReader in){
        
    	int i = 0;
    	
    	try {
    		// プレイリストURL→総再生時間→動画タイトル・URLの順でリストに追加していく
    		while(list.add(in.readLine())) {
    			System.out.println("message : "+list.get(i)+"");
    			i++;
    		}
    	}catch(Exception e) {
    		System.out.println(e.toString());
    	}
    	
    	return list;
    }
    
	public static void main(String[] args) {
		
		//サーバープログラム用のソケット・入出力バッファ
		Socket socket = null;
		BufferedWriter out = null;
		BufferedReader in = null;
		Client client = new Client("aaa", 10001);
		
		try {
			//ソケットの接続試行。IPa、portは別で指定
			socket = client.setConnect("IP",10001);
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			while(true) {
				//Userから条件の入力があるまで待機
				while(Client.time<0||Client.keyword.equals(null)||Client.condition<0) ;
				
				//サーバー側へユーザープログラムの入力を送信
				client.sendOperation(out);
			
				//最終的にサーバー側からURLを受け取る処理。ユーザープログラムに投げる
				client.getMessage(in);
				
				Client.time = -1;
				Client.keyword = null;
				Client.condition = -1;
			}
			
		}catch(Exception e) {
			System.out.println(e.toString());
		}
	}
}