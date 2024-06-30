// クライアントリクエストを表すクラス
public class ClientRequest {
    private int desiredDuration; // リクエストされた再生時間
    private String searchKeyword; // 検索キーワード
    private String playlistAlgorithm; // プレイリスト生成アルゴリズム

    // コンストラクタ
    public ClientRequest(int desiredDuration, String searchKeyword, String playlistAlgorithm) {
        this.desiredDuration = desiredDuration;
        this.searchKeyword = searchKeyword;
        this.playlistAlgorithm = playlistAlgorithm;
    }

    // 各フィールドのゲッター
    public int getDesiredDuration() {
        return desiredDuration;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public String getPlaylistAlgorithm() {
        return playlistAlgorithm;
    }
}