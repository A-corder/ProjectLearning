package com.example.youtubeapi;

import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeRequestInitializer;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;

public class Data {

    private static final String APPLICATION_NAME = "youtube-api-example";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String API_KEY = "AIzaSyC4IFYlzBbU9JJxiRebO3g0nhTl5eIt8Oo"; // 作成したAPIキーをここに入力

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
            List<Video> videoList = videoResponse.getItems();

            // 動画の詳細情報を保存
            for (Video video : videoList) {
                List<Object> videoData = new ArrayList<>();
                String title = video.getSnippet().getTitle();
                String duration = video.getContentDetails().getDuration();
                BigInteger viewCount = video.getStatistics().getViewCount();
                String videoId = video.getId();
                
                // 時間を秒単位に変換
                Duration duration1 = Duration.parse(duration);
                int seconds = (int) duration1.getSeconds();
                
                //型を変換
                int counts = viewCount.intValue();

                videoData.add((String) title);
                videoData.add((int) seconds);
                videoData.add((int) counts);
                videoData.add((String) videoId);
                allvideo.add(videoData);
            }
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
        return allvideo;
    }

    private static YouTube getService() throws GeneralSecurityException, IOException {
        return new YouTube.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                null)
                .setApplicationName(APPLICATION_NAME)
                .setYouTubeRequestInitializer(new YouTubeRequestInitializer(API_KEY))
                .build();
    }

    public static void main(String[] args) {
        List<List<Object>> videoData = processSearchWord("test");
        for (List<Object> video : videoData) {
            System.out.println("Title: " + video.get(0));
            System.out.println("Duration: " + video.get(1));
            System.out.println("View Count: " + video.get(2));
            System.out.println("Video ID: " + video.get(3));
            System.out.println();
        }
    }
}
