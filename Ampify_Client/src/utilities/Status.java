/*
Enum for different responses received from the server
*/

package utilities;

public enum  Status {
    SUCCESS("0"),
    FAILED("1"),
    ALREADY_EXIST("3"),
    NOT_OWNER("4"),
    NO_SUCH_USER_EXIST("5"),
    ALREADY_SENT("6"),
    ALREADY_LIKED("7"),

    ;

    Status(String s){
        s.toString();
    }
}
