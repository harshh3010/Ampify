package utilities;

public enum NotificationType {

    SEND("1"),
    GET_NOTIFICATIONS("2"),
    CONFIRM_NOTIFICATION("3"),
    DELETE_NOTIFICATION("4"),
    ;
    NotificationType(String s) {
        s.toString();
    }
}
