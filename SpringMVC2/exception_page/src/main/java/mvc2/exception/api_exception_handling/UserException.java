package mvc2.exception.api_exception_handling;

public class UserException extends Exception{
    public UserException(){ super(); }
    public UserException(String message){ super(message);}
    public UserException(String message, Throwable cause) { super(message,cause);}
    public UserException(Throwable cause) { super(cause);}
}