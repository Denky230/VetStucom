
package exceptions;

import java.util.List;

public class CancelOperationException extends MyException {

    public CancelOperationException() {}

    @Override
    public List<String> initErrorMessages() { return null; }
}