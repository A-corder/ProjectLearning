public class UserDriver {
    public static void main(String[] args) {
        // ClientStub を使用して User オブジェクトを作成
        ClientStub clientStub = new ClientStub();
        UserTest test = new UserTest(clientStub);

        // fromClient メソッドのテスト
        System.out.println("fromClient呼び出し");
        test.fromClient("Test URL");
        test.fromClient("10:00");
        test.fromClient("Song 1");
        test.fromClient("Song1 URL");
        test.fromClient("END");

        // checkFlag メソッドのテスト
        System.out.println("checkFlag呼び出し");
        System.out.println("Flagの状態: " + test.checkFlag() + "\n");

        // checkError メソッドのテスト
        System.out.println("checkError呼び出し");
        System.out.println("errorFlagの状態: " + test.checkError() + "\n");

        
    }
}