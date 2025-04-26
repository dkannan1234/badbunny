import java.util.LinkedList;
import java.io.Serializable;

public class Song implements Serializable {
    LinkedList<String> artistNames;
    String trackName;
    private static final long serialVersionUID = 1L;

    public Song(LinkedList<String> artistNames, String trackName){
        this.artistNames = artistNames;
        this.trackName = trackName;
    }

    public String getTrackName(){
        return trackName;
    }

    public LinkedList<String> getArtistNames(){
        return artistNames;
    }

    public boolean checkSharesArtist(Song other){
        LinkedList<String> otherSongArtistNames = other.getArtistNames();
        for (String artist: artistNames){
            if (otherSongArtistNames.contains(artist)) {
                return true;
            }
        }
        return false;
    }



}
