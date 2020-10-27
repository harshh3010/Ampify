package CellFactories;

import controllers.InvitationCellController;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import model.Notification;

public class InvitationCellFactory implements Callback<ListView<Notification>, ListCell<Notification>> {
    @Override
    public ListCell<Notification> call(ListView<Notification> notificationListView) {
        return new InvitationCellController();
    }
}
