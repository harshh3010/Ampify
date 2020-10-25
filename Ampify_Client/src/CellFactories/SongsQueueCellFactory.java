package CellFactories;

import controllers.SongsQueueCellController;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import model.Song;

public class SongsQueueCellFactory implements Callback<ListView<Song>, ListCell<Song>> {
    @Override
    public ListCell<Song> call(ListView<Song> songListView) {
        return new SongsQueueCellController();
    }
}
