import java.io.*;
import java.util.*;

public class SongExtractor {
    public static void main(String[] args) {
        String csvFile = "dataset.csv";
        String saveFile = "adjacencyList.ser";

        HashMap<Song, List<Song>> adjacencyList = null;
        File file = new File(saveFile);

        if (file.exists()) {
            adjacencyList = loadAdjacencyList(saveFile);
        } else {
            adjacencyList = buildAdjacencyListFromCSV(csvFile);
            saveAdjacencyList(adjacencyList, saveFile);
        }

        System.out.println("Finished preparing adjacency list!");

        Scanner scanner = new Scanner(System.in);

        BFSFinder bfsFinder = new BFSFinder(adjacencyList);


        System.out.print("Enter the artist you want to connect to: ");
        String targetArtist = scanner.nextLine();

        List<String> path = bfsFinder.findConnection("Bad Bunny", targetArtist);

        if (path != null) {
            System.out.println("\nConnection path found:");
            for (String step : path) {
                System.out.println(step);
            }
        } else {
            System.out.println("\nNo connection path found.");
        }

    }
    // ================== Helper methods ==================

    public static HashMap<Song, List<Song>> buildAdjacencyListFromCSV(String csvFile) {
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
                    return null;
                }
            }

            while ((line = br.readLine()) != null) {
                String[] columns = line.split(cvsSplitBy, -1);
                if (columns.length > Math.max(artistIndex, trackNameIndex)) {
                    //String rawArtists = columns[artistIndex].replaceAll("[\\[\\]\"]", "");
                    String trackName = columns[trackNameIndex].trim();
                    //LinkedList<String> artistNames = new LinkedList<>(Arrays.asList(rawArtists.split(",\\s*")));

                    String rawArtists = columns[artistIndex].replaceAll("[\\[\\]\"]", "");
                    LinkedList<String> artistNames = new LinkedList<>(Arrays.asList(rawArtists.split(";\\s*")));


                    Song song = new Song(artistNames, trackName);
                    songs.add(song);
                    adjacencyList.put(song, new ArrayList<>());
                }
            }

            System.out.println("Size of adjacency list (initial): " + adjacencyList.size());
            System.out.println("Size of list of songs: " + songs.size());

            for (int i = 0; i < songs.size(); i++) {
                for (int j = i + 1; j < songs.size(); j++) {
                    Song s1 = songs.get(i);
                    Song s2 = songs.get(j);
                    if (s1.checkSharesArtist(s2)) {
                        adjacencyList.get(s1).add(s2);
                        adjacencyList.get(s2).add(s1);
                    }
                }
            }

            System.out.println("Finished building adjacency list (connections made)");

            // Uncomment this if you want to print the entire adjacency list:
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

        return adjacencyList;
    }

    public static void saveAdjacencyList(HashMap<Song, List<Song>> adjacencyList, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(adjacencyList);
            System.out.println("Adjacency list saved to file: " + filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HashMap<Song, List<Song>> loadAdjacencyList(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            HashMap<Song, List<Song>> adjacencyList = (HashMap<Song, List<Song>>) ois.readObject();
            System.out.println("Adjacency list loaded from file: " + filename);
            return adjacencyList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
