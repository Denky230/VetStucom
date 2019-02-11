
package exceptions;

import java.util.Arrays;
import java.util.List;

public class DatabaseException extends MyException {

    public enum Errors {
        LOGIN_INCORRECT,
        USER_ALREADY_REGISTERED
    }
    
    public DatabaseException(int code) {
        super(code);
    }

    @Override
    public List<String> initErrorMessages() {
        List<String> messages = Arrays.asList(
                "Wrong credentials",
                "User already registered"
        );
        
        return messages;
    }
}