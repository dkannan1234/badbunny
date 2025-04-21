import java.util.LinkedList;

public class Song {
    LinkedList<String> artistNames;
    String trackName;

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
