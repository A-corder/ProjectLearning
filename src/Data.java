package com.example.youtubeapi;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeScopes;
import com.google.api.services.youtube.model.Playlist;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemSnippet;
import com.google.api.services.youtube.model.PlaylistSnippet;
import com.google.api.services.youtube.model.PlaylistStatus;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;

public class Data {

    private static final String APPLICATION_NAME = "youtube-api-example";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Arrays.asList(YouTubeScopes.YOUTUBE);
    private static final String CREDENTIALS_FILE_PATH = "/client_secrets.json";

    public static List<List<Object>> processSearchWord(String searchWord) {
        List<List<Object>> allvideo = new ArrayList<>();
        try {
            YouTube youtubeService = getService();

            // 動画IDを取得するための検索リクエスト
            YouTube.Search.List searchRequest = youtubeService.search()
                    .list("id")
                    .setQ(searchWord)
                    .setType("video")
                    .setMaxResults(100L); // 必要に応じて数を調整
            List<String> videoIds = searchRequest.execute()
                    .getItems()
                    .stream()
                    .map(result -> result.getId().getVideoId())
                    .collect(Collectors.toList());

            // 動画詳細情報を取得するリクエスト
            YouTube.Videos.List videosRequest = youtubeService.videos()
                    .list("snippet,contentDetails,statistics")
                    .setId(String.join(",", videoIds));
            VideoListResponse videoResponse = videosRequest.execute();
            ArrayList<Video> videoList = (ArrayList<Video>) videoResponse.getItems();
            

            // 動画の詳細情報を保存
            for (Video video : videoList) {
                ArrayList<Object> videoData = new ArrayList<>();
                String title = video.getSnippet().getTitle();
                String duration = video.getContentDetails().getDuration();
                BigInteger viewCount = video.getStatistics().getViewCount();
                String videoId = video.getId();

                // 時間を秒単位に変換
                Duration duration1 = Duration.parse(duration);
                int seconds = (int) duration1.getSeconds();

                // 型を変換
                int counts = viewCount.intValue();
                

                // Listに入れ込む
                videoData.add(title);
                videoData.add(seconds);
                videoData.add(counts);
                videoData.add(videoId);
                
                allvideo.add(videoData);
                
                System.out.println(videoData);
                
                
            }

        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
        return allvideo;
    }
    
    public static String getPlaylistURL(ArrayList<ArrayList<Object>> sortedData) throws InterruptedException {
    	
    	YouTube youtubeService;
    	String playListURL = "https://www.youtube.com/watch?v=-wb2PAx6aEs&list=PLoE8WdcNzsqBXGhZidcrqONRLwfCn2Ybs";
    	
    	
		try {
			youtubeService = getService();
			
			// プレイリストを作成
	        String playlistId = createPlaylist(youtubeService, "New Playlist", "A playlist created using the YouTube Data API");
	        System.out.println("Created Playlist ID: " + playlistId);
	        playListURL = "Playlist URL: https://www.youtube.com/playlist?list=" + playlistId;
	        System.out.println(playListURL);
	        
	        // プレイリストに動画を入れ込む
	        for (List<Object> video :sortedData) {
	        	String videoId = String.valueOf(video.get(3));
	        	addVideoToPlaylist(youtubeService, playlistId, videoId);
	        }
	        
	        
		} catch (GeneralSecurityException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
    	
    	return playListURL;
    }

    private static YouTube getService() throws GeneralSecurityException, IOException {
        Credential credential = authorize();
        return new YouTube.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private static Credential authorize() throws IOException {
        InputStream in = Data.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = null;
		try {
			flow = new GoogleAuthorizationCodeFlow.Builder(
			        GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, clientSecrets, SCOPES)
			        .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
			        .setAccessType("offline")
			        .build();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    private static String createPlaylist(YouTube youtubeService, String title, String description) throws IOException {
        PlaylistSnippet playlistSnippet = new PlaylistSnippet();
        playlistSnippet.setTitle(title);
        playlistSnippet.setDescription(description);

        PlaylistStatus playlistStatus = new PlaylistStatus();
        playlistStatus.setPrivacyStatus("public");

        Playlist youTubePlaylist = new Playlist();
        youTubePlaylist.setSnippet(playlistSnippet);
        youTubePlaylist.setStatus(playlistStatus);

        YouTube.Playlists.Insert playlistInsertCommand = youtubeService.playlists().insert("snippet,status", youTubePlaylist);
        Playlist playlistInserted = playlistInsertCommand.execute();
        return playlistInserted.getId();
    }
    
    private static void addVideoToPlaylist(YouTube youtubeService, String playlistId, String videoId) throws IOException {
        ResourceId resourceId = new ResourceId();
        resourceId.setKind("youtube#video");
        resourceId.setVideoId(videoId);

        PlaylistItemSnippet playlistItemSnippet = new PlaylistItemSnippet();
        playlistItemSnippet.setPlaylistId(playlistId);
        playlistItemSnippet.setResourceId(resourceId);

        PlaylistItem playlistItem = new PlaylistItem();
        playlistItem.setSnippet(playlistItemSnippet);

        YouTube.PlaylistItems.Insert request = youtubeService.playlistItems()
                .insert("snippet", playlistItem);

        int maxRetries = 5;
        int attempt = 0;
        boolean success = false;

        while (attempt < maxRetries && !success) {
            try {
                PlaylistItem response = request.execute();
                System.out.println("Added video to playlist: " + response.getSnippet().getTitle());
                success = true;
            } catch (GoogleJsonResponseException e) {
                if (e.getStatusCode() == 409) {
                    System.out.println("Conflict error, retrying...");
                    attempt++;
                    try {
                        Thread.sleep(1000 * attempt); // 再試行前に待機する（指数バックオフ）
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                } else {
                    throw e;
                }
            }
        }

        if (!success) {
            throw new IOException("Failed to add video to playlist after " + maxRetries + " attempts");
        }
    }


    public static void main(String[] args) throws InterruptedException {
        ArrayList<ArrayList<Object>> videoData = processSearchWord("米津玄師");
        String playListURL = getPlaylistURL(videoData);
        System.out.println("Playlist URL: " + playListURL);
        for (List<Object> video : videoData) {
            System.out.println("Title: " + video.get(0));
            System.out.println("Duration: " + video.get(1) + " seconds");
            System.out.println("View Count: " + video.get(2));
            System.out.println("Video ID: " + video.get(3));
            System.out.println();
        }
    }
}

