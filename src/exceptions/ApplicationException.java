
package exceptions;

import java.util.Arrays;
import java.util.List;

public class ApplicationException extends MyException {

    public enum Errors {
        INVALID_NUM_ARGUMENTS,
        OPERATION_CANCELLED
    }
    
    public ApplicationException(int code) {
        super(code);
    }

    @Override
    public List<String> initErrorMessages() {
        List<String> messages = Arrays.asList(
                "Invalid number of arguments",
                "Operation cancelled!"
        );
        
        return messages;
    }
}