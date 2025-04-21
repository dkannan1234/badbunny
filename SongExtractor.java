import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SongExtractor {
    public static void main(String[] args) {
        String csvFile = "dataset.csv";
        String line;
        String cvsSplitBy = ",";

        int artistIndex = -1;
        int trackNameIndex = -1;

        List<Song> songs = new ArrayList<>();
        HashMap<Song, List<Song>> adjacencyList = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            if ((line = br.readLine()) != null) {
                String[] headers = line.split(cvsSplitBy, -1);
                for (int i = 0; i < headers.length; i++) {
                    if (headers[i].trim().equalsIgnoreCase("artists")) {
                        artistIndex = i;
                    } else if (headers[i].trim().equalsIgnoreCase("track_name")) {
                        trackNameIndex = i;
                    }
                }

                if (artistIndex == -1 || trackNameIndex == -1) {
                    System.out.println("Required columns not found.");
                    return;
                }
            }

            while ((line = br.readLine()) != null) {
                String[] columns = line.split(cvsSplitBy, -1);
                if (columns.length > Math.max(artistIndex, trackNameIndex)) {
                    String rawArtists = columns[artistIndex].replaceAll("[\\[\\]\"]", "");
                    String trackName = columns[trackNameIndex].trim();
                    LinkedList<String> artistNames = new LinkedList<>(Arrays.asList(rawArtists.split(",\\s*")));

                    Song song = new Song(artistNames, trackName);
                    songs.add(song);
                    adjacencyList.put(song, new ArrayList<>());
                }
            }

            //After printing size of list/adjacency list: 114,000 which is correct
            System.out.println("size of adjacency list: " + adjacencyList.size());

            System.out.println("size of list of songs: " + songs.size());

            for (int i = 0; i < songs.size(); i++) {
                for (int j = i + 1; j < songs.size(); j++) {
                    //System.out.println("in the for looop");
                    Song s1 = songs.get(i);
                    Song s2 = songs.get(j);
                    if (s1.checkSharesArtist(s2)) {
                        //System.out.println("checking this");
                        adjacencyList.get(s1).add(s2);
                        adjacencyList.get(s2).add(s1);
                    }
                }
            }

            System.out.println("outside of for loop");


            for (Song song : adjacencyList.keySet()) {
                System.out.print(song.getTrackName() + " -> ");
                for (Song neighbor : adjacencyList.get(song)) {
                    System.out.print(neighbor.getTrackName() + ", ");
                }
                System.out.println();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}