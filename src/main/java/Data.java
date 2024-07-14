	package com.example.youtubeapi;


	import java.io.FileNotFoundException;
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
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeScopes;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;

	public class Data {

	    private static final String APPLICATION_NAME = "youtube-api-example";
	    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	    private static final List<String> SCOPES = Arrays.asList(YouTubeScopes.YOUTUBE);
	    private static final String CREDENTIALS_FILE_PATH = "src/main/resources/client_secret.json";

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



	    private static YouTube getService() throws GeneralSecurityException, IOException {
	        Credential credential = authorize();
	        return new YouTube.Builder(
	                GoogleNetHttpTransport.newTrustedTransport(),
	                JSON_FACTORY,
	                credential)
	                .setApplicationName(APPLICATION_NAME)
	                .build();
	    }

	    private static Credential authorize() throws IOException, GeneralSecurityException {
    		// ファイルのパスを修正し、クラスローダーからの絶対パスを使用
    		InputStream in = Data.class.getClassLoader().getResourceAsStream("client_secret.json");
    		if (in == null) {
        	    // ファイルが見つからない場合のエラーメッセージ
        	    throw new FileNotFoundException("クライアントシークレットファイルが見つかりません: " + CREDENTIALS_FILE_PATH);
    		}
    		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

   		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
        	    GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, clientSecrets, SCOPES)
        	    .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
         	    .setAccessType("offline")
        	    .build();

    		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8080).build();
    		return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
	    }

	}

