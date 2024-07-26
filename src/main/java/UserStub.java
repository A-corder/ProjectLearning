public class UserStub {
	
		private ClientTest client;

		private boolean flag = false; 
		
		public UserStub(ClientTest client) {
			this.client = client;
		}

		//メッセージをクライアントから受け取る
		public void fromClient(String msg) {
			System.out.println("fromClientが受信 : "+msg +"");
		}
		
		public boolean checkFlag() {
			return flag;
		}

}
