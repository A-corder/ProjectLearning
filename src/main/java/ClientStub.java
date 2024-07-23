public class ClientStub {
    public void fromUser(String viewTime, String searchWord, int condition, boolean regenerate) {
        System.out.println("fromUser呼び出し成功.以下に設定したパラメータを表示");
        System.out.println("viewTime: " + viewTime);
        System.out.println("searchWord: " + searchWord);
        System.out.println("condition: " + condition);
        System.out.println("regenerate: " + regenerate);
    }
}