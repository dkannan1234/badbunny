import java.util.*;

public class BFSFinder {
    private HashMap<Song, List<Song>> adjacencyList;

    public BFSFinder(HashMap<Song, List<Song>> adjacencyList) {
        this.adjacencyList = adjacencyList;
    }

    public List<String> findConnection(String startArtist, String targetArtist) {
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        HashMap<String, String> parentMap = new HashMap<>();

        queue.add(startArtist);
        visited.add(startArtist);
        parentMap.put(startArtist, null);

        while (!queue.isEmpty()) {
            String currentArtist = queue.poll();
            //System.out.println("Visiting artist: " + currentArtist);

            if (currentArtist.equalsIgnoreCase(targetArtist)) {
                return buildPath(currentArtist, parentMap);
            }

            for (Song song : adjacencyList.keySet()) {
                if (song.getArtistNames().contains(currentArtist)) {
                    for (String neighborArtist : song.getArtistNames()) {
                        if (!neighborArtist.equalsIgnoreCase(currentArtist) && !visited.contains(neighborArtist)) {
                            visited.add(neighborArtist);
                            queue.add(neighborArtist);
                            parentMap.put(neighborArtist, currentArtist);
                            //System.out.println("  Found neighbor artist: " + neighborArtist);
                        }
                    }
                }
            }
        }

        System.out.println("No connection found between " + startArtist + " and " + targetArtist);
        return null;
    }

    private List<String> buildPath(String endArtist, HashMap<String, String> parentMap) {
        List<String> path = new LinkedList<>();
        String current = endArtist;

        while (parentMap.get(current) != null) {
            String parent = parentMap.get(current);

            // Find the song connecting parent -> current
            Song connectingSong = findConnectingSong(parent, current);

            if (connectingSong != null) {
                // Build a nice string like "Artist1, Artist2, Artist3"
                StringBuilder artistsString = new StringBuilder();
                List<String> artists = connectingSong.getArtistNames();
                for (int i = 0; i < artists.size(); i++) {
                    artistsString.append(artists.get(i));
                    if (i < artists.size() - 1) {
                        artistsString.append(", ");
                    }
                }

                // Print cleanly
                String step = connectingSong.getTrackName() + " by " + artistsString.toString();
                path.add(0, step);
            } else {
                path.add(0, parent + " --> " + current); // fallback if no connecting song
            }

            current = parent;
        }

        path.add(0, current); // starting artist (no song needed)
        return path;
    }


    private Song findConnectingSong(String artist1, String artist2) {
        for (Song song : adjacencyList.keySet()) {
            List<String> artists = song.getArtistNames();
            if (artists.contains(artist1) && artists.contains(artist2)) {
                return song;
            }
        }
        return null;
    }
}
