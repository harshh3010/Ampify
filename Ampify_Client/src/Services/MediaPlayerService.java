package Services;

import model.Song;

import java.util.LinkedList;
import java.util.Queue;

public class MediaPlayerService {

    public static Song previousSong;
    public static Song currentSong;

    public static Queue<Song> currentPlaylist = new LinkedList<>();

}
