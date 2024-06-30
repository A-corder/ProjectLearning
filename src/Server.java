import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

// メインのサーバークラス
public class Server {
    private static final int PORT = 10001; // このサーバーが待ち受けるポート番号
    private static final int MAX_CLIENTS = 100; // 最大クライアント数
    private static final String DATA_HOST = "localhost"; // Dataサーバーのホスト名
    private static final int DATA_PORT = 10002; // Dataサーバーのポート番号
    private static final String PLAYLIST_HOST = "localhost"; // Playlistサーバーのホスト名
    private static final int PLAYLIST_PORT = 10003; // Playlistサーバーのポート番号

    // クライアントリクエストのリストを保持
    private List<ClientRequest> clientRequests = new ArrayList<>();

    // サーバースタート
    public static void main(String[] args) {
        new Server().start();
    }

    // サーバーの開始メソッド
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("サーバがポートリスニング中 " + PORT);

            // 常にクライアントからの接続を待ち受け
            while (true) {
                Socket clientSocket = serverSocket.accept(); // クライアント接続を受け入れ
                System.out.println("新しいクライアントが接続された！: " + clientSocket.getInetAddress().getHostAddress());

                // クライアントからのデータを処理
                handleClient(clientSocket);
            }
        } catch (IOException e) {
            System.out.println("サーバの例外処理です: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // クライアントの処理メソッド
    private void handleClient(Socket clientSocket) {
        try (
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            // クライアントからデータを受信
            int duration = Integer.parseInt(reader.readLine());
            String keyword = reader.readLine();
            String algorithm = reader.readLine();

            // リクエストをリストに保存
            synchronized (clientRequests) {
                if (clientRequests.size() < MAX_CLIENTS) {
                    ClientRequest request = new ClientRequest(duration, keyword, algorithm);
                    clientRequests.add(request);
                    writer.println("リクエストの受信と格納を完了しました");

                    // Dataサーバーに楽曲検索キーワードを送信
                    sendToDataServer(request.getSearchKeyword());

                    // Playlistサーバーからプレイリスト生成情報を受信
                    List<String> playlistResult = receiveFromPlaylistServer(request.getDesiredDuration(), request.getPlaylistAlgorithm());
                    
                    // プレイリスト情報をクライアントに送信
                    for (String line : playlistResult) {
                        writer.println(line);
                    }

                } else {
                    // クライアント数が上限を超えた場合の処理
                    writer.println("サーバはお腹いっぱいです。再接続をお願いいたします。");
                }
            }

            // クライアントソケットを閉じる
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("クライアント処理例外: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Dataサーバーにキーワードを送信するメソッド
    private void sendToDataServer(String keyword) {
        try (
            Socket socket = new Socket(DATA_HOST, DATA_PORT);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)
        ) {
            writer.println(keyword);
        } catch (IOException e) {
            System.out.println("データクラスとの接続例外処理: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Playlistサーバーからプレイリストを受信するメソッド
    private List<String> receiveFromPlaylistServer(int duration, String algorithm) {
        List<String> playlistResult = new ArrayList<>();
        try (
            Socket socket = new Socket(PLAYLIST_HOST, PLAYLIST_PORT);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            // プレイリスト生成情報を送信
            writer.println(duration);
            writer.println(algorithm);

            // プレイリスト生成結果を受信
            String line;
            while ((line = reader.readLine()) != null) {
                playlistResult.add(line);
            }
        } catch (IOException e) {
            System.out.println("プレイリストクラスとの接続処理例外処理: " + e.getMessage());
            e.printStackTrace();
        }
        return playlistResult;
    }
}