package controllers;

import Services.AmpifyServices;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import model.Notification;
import utilities.Status;

import java.io.IOException;

public class InvitationCellController extends ListCell<Notification> {

    @FXML
    private Label nameLabel, statusLabel, senderIDLabel;
    @FXML
    private Button menuButton;
    public InvitationCellController() {

        loadFXML();
    }

    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/invitationCell.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
   Function to setup the menu to be displayed on click of menuButton
    */
    private void setUpMenuButton(Notification notification) {

        // Creating the context menu
        ContextMenu contextMenu = new ContextMenu();

        // Creating items to be displayed in the menu
        MenuItem item1 = new MenuItem("Accept");
        MenuItem item2 = new MenuItem("Decline");

        // Setting action events for menu items
        item1.setOnAction(actionEvent -> {

            try {
                String result = AmpifyServices.confirmNotification(notification.getId());
                if (result.equals(String.valueOf(Status.SUCCESS))) {
                    statusLabel.setText("Accepted");
                    statusLabel.setVisible(true);
                    menuButton.setVisible(false);
                } else {
                    // DISPLAY ERROR
                    Alert alert=new Alert(Alert.AlertType.ERROR,"Failed to join playlist!", ButtonType.OK);
                    alert.showAndWait();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        });

        // On clicking add to playlist button
        item2.setOnAction(actionEvent -> {

            try {
                String result = AmpifyServices.deleteNotification(notification.getId());
                if (result.equals(String.valueOf(Status.SUCCESS))) {
                    statusLabel.setText("Declined");
                    statusLabel.setVisible(true);
                    menuButton.setVisible(false);
                } else {
                    // DISPLAY ERROR
                    Alert alert=new Alert(Alert.AlertType.ERROR,"Failed to decline request.", ButtonType.OK);
                    alert.showAndWait();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();

            }

        });

        // Adding the items in menu
        contextMenu.getItems().add(item1);
        contextMenu.getItems().add(item2);

        // Displaying the menu on button click
        menuButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY)
                contextMenu.show(menuButton, mouseEvent.getScreenX(), mouseEvent.getScreenY());
        });

    }

    @Override
    protected void updateItem(Notification notification, boolean b) {
        super.updateItem(notification, b);

        if (b) {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        } else {

            // Displaying the name of playlist
            nameLabel.setText(notification.getPlaylistName());
            statusLabel.setVisible(false);
            senderIDLabel.setText(notification.getSender());
            setUpMenuButton(notification);

            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }
}
