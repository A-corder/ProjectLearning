import java.util.Scanner;
public class ClientDriver {

	public static void main(String[] args)throws Exception {
		String IP = "localhost";
		int port = 10000;
		
		System.out.println("テスト用サーバーに接続します。");
		ClientTest test = new ClientTest(IP,port);
		UserStub user = test.user;
		
		Scanner s = new Scanner(System.in);
		int c;
		boolean r = false;
		
		System.out.println("再生時間・キーワード・検索条件を入力します");
		while(true) {
			
			System.out.printf("再生時間を入力（Integer）:");
			String t = s.next();
			System.out.printf("キーワードを入力（String）:");
			String k = s.next();
			System.out.printf("検索条件を入力（Integer）:");
			c = s.nextInt();
			System.out.println("fromUserから以上の三値を入力します。");
			test.fromUser(t, k, c, r);
			
			System.out.printf("\n");
		}
	}

}
