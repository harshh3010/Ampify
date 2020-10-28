package CellFactories;

import controllers.PlaylistCellController;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import model.Playlist;

public class PlaylistCellFactory implements Callback<ListView<Playlist>, ListCell<Playlist>> {

    @Override
    public ListCell<Playlist> call(ListView<Playlist> playlistListView) {

        return new PlaylistCellController();
    }
}
