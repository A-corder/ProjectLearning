import java.io.Serializable;

public class ClientData implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int desiredPlaylistDuration;
    private String searchKeyword;
    private int selectedAlgorithm;

    public ClientData(int desiredPlaylistDuration, String searchKeyword, int selectedAlgorithm) {
        this.desiredPlaylistDuration = desiredPlaylistDuration;
        this.searchKeyword = searchKeyword;
        this.selectedAlgorithm = selectedAlgorithm;
    }

    public int getDesiredPlaylistDuration() {
        return desiredPlaylistDuration;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public int getSelectedAlgorithm() {
        return selectedAlgorithm;
    }

    @Override
    public String toString() {
        return "ClientData{" +
                "プレイリスト時間=" + desiredPlaylistDuration +
                ", 検索キーワード='" + searchKeyword + '\'' +
                ", 検索アルゴリズム=" + selectedAlgorithm +
                '}';
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> branch 'master' of https://github.com/A-corder/ProjectLearning.git
