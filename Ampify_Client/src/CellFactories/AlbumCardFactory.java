package CellFactories;

import controllers.AlbumCardController;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import model.Album;

public class AlbumCardFactory implements Callback<ListView<Album>, ListCell<Album>> {
    @Override
    public ListCell<Album> call(ListView<Album> albumListView) {

        return new AlbumCardController();
    }
}
