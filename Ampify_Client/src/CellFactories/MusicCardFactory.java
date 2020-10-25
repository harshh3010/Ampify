package CellFactories;

import controllers.MusicCardController;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javafx.scene.control.ListCell;
import model.Song;


public class MusicCardFactory implements Callback<ListView<Song>, ListCell<Song>> {

    @Override
    public ListCell<Song> call(ListView<Song> songListView) {
        return new MusicCardController();
    }
}
