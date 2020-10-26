package utilities;

public enum  Status {
    SUCCESS("0"),
    FAILED("1"),
    ALREADY_EXIST("3"),
    NOT_OWNER("4"),
    ;

    Status(String s){
        s.toString();
    }
}
