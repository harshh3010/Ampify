package utilities;

import com.jfoenix.controls.JFXListView;
import javafx.scene.layout.Pane;
import model.Song;

public class HomeScreenWidgets {

    public static HomeScreenDisplays currentDisplayPage = HomeScreenDisplays.MAIN_PAGE;
    public static Pane displayPane;
    public static Pane bottomPane;
    public static JFXListView<Song> nowPlayingList;
}
