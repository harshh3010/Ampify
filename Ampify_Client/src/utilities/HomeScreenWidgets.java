package utilities;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.Song;

public class HomeScreenWidgets {

    public static HomeScreenDisplays currentDisplayPage = HomeScreenDisplays.MAIN_PAGE;
    public static Pane displayPane;
    public static Pane bottomPane;
    public static JFXListView<Song> nowPlayingList;
    public static JFXTextField searchBar;
    public static ProgressIndicator loadingIndicator;
    public static AnchorPane parentPane;

    public static void showProgressIndicator(){
        loadingIndicator.setVisible(true);
        parentPane.setDisable(true);
    }

    public static void hideProgressIndicator(){
        loadingIndicator.setVisible(false);
        parentPane.setDisable(false);
    }
}
