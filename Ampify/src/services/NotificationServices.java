package services;

import model.Notification;
import serverClasses.Main;
import serverClasses.requests.NotificationRequest;
import utilities.DatabaseConstants;
import utilities.Status;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationServices {

    /**
     * *for sending notification to the user whom our client wants to add as member of a
     * particular playlist
     */
    public static String sendingNotification(NotificationRequest notificationRequest) {
        //for checking if such receiver exists in our db or not
        //i.e whether this particular email id is a USER for our app AMPIFY
        //if not we throw back a message to send it to someone who is registered on AMPIFY
        String query = "SELECT * FROM user_auth" +
                " WHERE email=\"" + notificationRequest.getReceiver() + "\";";
        try {
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);
            ResultSet resultset = preparedStatement.executeQuery();
            if (resultset.next()) {
                //if receiver exists we need to check that whether he has received any prior notification
                //to become member of this particular playlist
                //if yes no need to send again!
                query = " SELECT * FROM " + DatabaseConstants.NOTIFICATION_TABLE +
                        " WHERE " + DatabaseConstants.NOTIFICATION_COL_PLAYLIST_ID + "=\"" + notificationRequest.getPlaylistID() + "\"" +
                        " AND " + DatabaseConstants.NOTIFICATION_COL_RECEIVER + "=\"" + notificationRequest.getReceiver() + "\"";
                try {
                    PreparedStatement preparedStatement1 = Main.connection.prepareStatement(query);
                    ResultSet resultSet = preparedStatement1.executeQuery();
                    if (resultSet.next())
                        return String.valueOf(Status.ALREADY_SENT);//we return corr string
                    else {
                        //if earlier not sent we will send it now
                        query = "INSERT INTO " + DatabaseConstants.NOTIFICATION_TABLE +
                                "(" + DatabaseConstants.NOTIFICATION_COL_SENDER +
                                "," + DatabaseConstants.NOTIFICATION_COL_RECEIVER +
                                "," + DatabaseConstants.NOTIFICATION_COL_PLAYLIST_ID +
                                ") values(?,?,?);";
                        try {
                            preparedStatement = Main.connection.prepareStatement(query);
                            preparedStatement.setString(1, notificationRequest.getSender());
                            preparedStatement.setString(2, notificationRequest.getReceiver());
                            preparedStatement.setInt(3, notificationRequest.getPlaylistID());
                            preparedStatement.executeUpdate();
                            return String.valueOf(Status.SUCCESS);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        return String.valueOf(Status.FAILED);

                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return String.valueOf(Status.FAILED);
            } else
                return String.valueOf(Status.NO_SUCH_USER_EXIST);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return String.valueOf(Status.FAILED);
    }

    /**
     * for returning back list of notifications that this particular client has received
     * @param notificationRequest
     * @return
     */
    public static List<Notification> gettingNotification(NotificationRequest notificationRequest) {

        String query = "SELECT notification.sender,notification.playlistID,playlist.playlist_name " +
                "FROM notification " +
                " INNER JOIN playlist ON notification.playlistID=playlist.id" +
                " WHERE notification.receiver =\"" + notificationRequest.getReceiver() + "\" " +
                ";";

        Notification notification;
        List<Notification> notificationList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                notification = new Notification();
                notification.setSender(resultSet.getString(DatabaseConstants.NOTIFICATION_COL_SENDER));
                notification.setPlaylistName(resultSet.getString(3));
                notification.setId(resultSet.getInt(2));

                notificationList.add(notification);
            }
            return notificationList;
        } catch (SQLException e) {
            //displaying error if occured *_*
            e.printStackTrace();
        }
        return notificationList;
    }

    /**
     * here we confirm the notification received !!
     * if already a member corr msg given
     * @param notificationRequest
     * @return
     */
    public static String confirmNotification(NotificationRequest notificationRequest) {
        /**
         * thru this query we first check if aready this user is member of this playlist or not
         */
        String query1 = " SELECT * FROM " + DatabaseConstants.PLAYLIST_MEMBER_TABLE +
                " WHERE " + DatabaseConstants.PLAYLIST_MEMBER_COL_PLAYLIST_ID + "=\"" + notificationRequest.getPlaylistID() + "\"" +
                " AND " + DatabaseConstants.PLAYLIST_MEMBER_COL_MEMBEREMAIL + "=\"" + notificationRequest.getReceiver() + "\"";
        try {
            PreparedStatement preparedStatement1 = Main.connection.prepareStatement(query1);
            ResultSet resultSet = preparedStatement1.executeQuery();
            if (resultSet.next())
                return String.valueOf(Status.ALREADY_EXIST);
            else {
                //if not a member then only we confirm this notification
                String query = "INSERT INTO " + DatabaseConstants.PLAYLIST_MEMBER_TABLE +
                        "(" + DatabaseConstants.PLAYLIST_MEMBER_COL_PLAYLIST_ID +
                        "," + DatabaseConstants.PLAYLIST_MEMBER_COL_MEMBEREMAIL +
                        ") values(?,?);";
                try {
                    PreparedStatement preparedStatement = Main.connection.prepareStatement(query);
                    preparedStatement.setInt(1, notificationRequest.getPlaylistID());
                    preparedStatement.setString(2, notificationRequest.getReceiver());

                    preparedStatement.executeUpdate();
                    //DELETE THAT PARTICULAR NOTIFICATION ALSO because now he is a member!!
                    query = "DELETE notification" +
                            " FROM notification" +
                            " WHERE notification.playlistID=\"" + notificationRequest.getPlaylistID() + "\"" +
                            " AND notification.receiver=\"" + notificationRequest.getReceiver() + "\";";
                    try {
                        preparedStatement = Main.connection.prepareStatement(query);
                        preparedStatement.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return String.valueOf(Status.SUCCESS);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return String.valueOf(Status.FAILED);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return String.valueOf(Status.FAILED);
    }

    /**
     * function is to delete a notification which a user (receiver) dont wish to be present in the app anymore!
     */
    public static String deleteNotification(NotificationRequest notificationRequest) {
        String query = "DELETE notification" +
                " FROM notification" +
                " WHERE notification.playlistID=\"" + notificationRequest.getPlaylistID() + "\"" +
                " AND notification.receiver=\"" + notificationRequest.getReceiver() + "\";";
        try {
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            return String.valueOf(Status.SUCCESS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return String.valueOf(Status.FAILED);
    }

}
