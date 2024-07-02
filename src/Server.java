import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final int PORT = 10001;
    private static final String IP_ADDRESS = "127.0.0.1"; // 仮置きのIPアドレス
    private static List<ClientData> clientDataList = new ArrayList<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());
                
                // クライアントハンドリングの処理を別スレッドで実行
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try (ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                 ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream())) {

                // クライアントデータを受信
                ClientData clientData = (ClientData) input.readObject();
                synchronized (clientDataList) {
                    if (clientDataList.size() < 100) {
                        clientDataList.add(clientData);
                        output.writeObject("Data received and stored successfully");
                    } else {
                        output.writeObject("Server storage is full");
                    }
                }

                System.out.println("Received data: " + clientData);

            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Client handler exception: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}