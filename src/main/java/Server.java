package com.example.youtubeapi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeScopes;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemSnippet;
import com.google.api.services.youtube.model.PlaylistSnippet;
import com.google.api.services.youtube.model.PlaylistStatus;
import com.google.api.services.youtube.model.ResourceId;

public class Server {
    private static final int PORT = 10001;
    private static final int MAX_CONNECTIONS = 100;
    static final Semaphore semaphore = new Semaphore(MAX_CONNECTIONS);

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("サーバーがポート " + PORT + " で待機しています");

            while (true) {
                Socket socket = serverSocket.accept();
                
                if (semaphore.tryAcquire()) {
                    System.out.println("新しいクライアントが接続しました");
                    new ServerThread(socket, semaphore).start(); // セマフォを渡す
                } else {
                    System.out.println("最大接続数に達しました。新しいクライアントを拒否します。");
                    rejectClient(socket);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void rejectClient(Socket socket) {
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            writer.write("サーバーが忙しいです。後でもう一度お試しください。");
            writer.newLine();
            writer.flush();
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

class ServerThread extends Thread {
    private Socket socket;
    private Semaphore semaphore;
    private YouTube youtubeService;

    public ServerThread(Socket socket, Semaphore semaphore) {
        this.socket = socket;
        this.semaphore = semaphore;
        try {
            this.youtubeService = YouTubeService.getService();
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            while (true) {
                try {
                    // クライアントからのデータを受け取る
                    String line = reader.readLine();
                    if (line == null || line.equalsIgnoreCase("終了")) {
                        System.out.println("クライアントが接続を切断しました");
                        break;
                    }
                    int timeInMinutes = Integer.parseInt(line);
                    String searchWord = reader.readLine();
                    int optimizationCondition = Integer.parseInt(reader.readLine());

                    // 分単位の時間を秒単位に変換
                    int timeInSeconds = timeInMinutes * 60;

                    System.out.println("クライアントから受信しました:");
                    System.out.println("時間 (秒): " + timeInSeconds);
                    System.out.println("検索ワード: " + searchWord);
                    System.out.println("最適化条件: " + optimizationCondition);
                    
                    // Dataクラスのメソッドを呼び出して、検索結果を取得する
                    List<List<Object>> videoData = Data.processSearchWord(searchWord);
                    
                    // PlaylistUtilsクラスのメソッドを呼び出して、ソートされたデータを取得する
                    ArrayList<ArrayList<Object>> sortedData = PlaylistUtils.processAndSort(videoData, timeInSeconds, optimizationCondition);
                    
                    // プレイリスト総時間を計算
                    int totalTime = calculateTotalTime(sortedData);
                    
                    // 合計時間を分:秒に変換
                    String totalTimeFormatted = formatTime(totalTime);

                    // プレイリストを作成し、URLを取得
                    String playlistUrl = createAndPopulatePlaylist("My Java Playlist", "This is a playlist created with Java!", sortedData);
                    System.out.println("プレイリストのURL: " + playlistUrl);

                    // プレイリストデータをクライアントに送信する
                    writer.write(playlistUrl);
                    writer.newLine();
                    writer.write(totalTimeFormatted);
                    writer.newLine();
                    // ソートされたデータをクライアントに送信する
                    for (ArrayList<Object> video : sortedData) {
                        writer.write("タイトル: " + video.get(0));
                        writer.newLine();
                        writer.write("URL: https://www.youtube.com/watch?v=" + video.get(2));
                        writer.newLine();
                    }
                    
                    writer.write("END");
                    writer.newLine();

                    writer.flush();
                } catch (IOException ex) {
                    System.out.println("クライアントとの接続でエラーが発生しました: " + ex.getMessage());
                    break;
                }
            }

            // 接続を閉じる
            reader.close();
            writer.close();
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    private int calculateTotalTime(ArrayList<ArrayList<Object>> playlist) {
        int totalTime = 0;
        for (ArrayList<Object> video : playlist) {
            totalTime += (int) video.get(1);
        }
        return totalTime;
    }

    private String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    private String createAndPopulatePlaylist(String title, String description, ArrayList<ArrayList<Object>> videos) throws IOException {
        // プレイリストを作成
        Playlist playlist = new Playlist();
        PlaylistSnippet snippet = new PlaylistSnippet();
        snippet.setTitle(title);
        snippet.setDescription(description);
        playlist.setSnippet(snippet);
        playlist.setStatus(new PlaylistStatus().setPrivacyStatus("public"));

        YouTube.Playlists.Insert playlistInsertCommand = youtubeService.playlists()
                .insert("snippet,status", playlist);
        Playlist playlistResponse = playlistInsertCommand.execute();
        String playlistId = playlistResponse.getId();

        // プレイリストに動画を追加
        for (ArrayList<Object> video : videos) {
            PlaylistItem playlistItem = new PlaylistItem();
            PlaylistItemSnippet playlistItemSnippet = new PlaylistItemSnippet();
            playlistItemSnippet.setPlaylistId(playlistId);
            playlistItemSnippet.setResourceId(new ResourceId().setKind("youtube#video").setVideoId((String) video.get(2)));
            playlistItem.setSnippet(playlistItemSnippet);

            YouTube.PlaylistItems.Insert playlistItemsInsertCommand = youtubeService.playlistItems()
                    .insert("snippet", playlistItem);
            playlistItemsInsertCommand.execute();
        }

        return "https://www.youtube.com/playlist?list=" + playlistId;
    }
}

class YouTubeService {
    private static final String CLIENT_SECRETS = "client_secret.json"; // ファイル名のみを指定
    private static final List<String> SCOPES = Collections.singletonList(YouTubeScopes.YOUTUBE_FORCE_SSL);
    private static final String APPLICATION_NAME = "YouTube Playlist Creator";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    public static Credential authorize(final NetHttpTransport httpTransport) throws IOException {
        // クラスローダーを使用してリソースを取得
        ClassLoader classLoader = YouTubeService.class.getClassLoader();
        InputStream in = classLoader.getResourceAsStream(CLIENT_SECRETS);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CLIENT_SECRETS);
        }

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8080).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public static YouTube getService() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Credential credential = authorize(httpTransport);
        return new YouTube.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}