package controllers;

import CellFactories.InvitationCellFactory;
import Services.AmpifyServices;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import model.Notification;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class InvitationsScreenController implements Initializable {

    public JFXListView<Notification> invitationsListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadInvitations();
    }

    private void loadInvitations() {
        try {
            List<Notification> notificationList = AmpifyServices.getMyNotifications();
            invitationsListView.setItems(FXCollections.observableArrayList(notificationList));
            invitationsListView.setCellFactory(new InvitationCellFactory());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void onReloadClicked(ActionEvent actionEvent) {

        loadInvitations();
    }
}
