
package exceptions;

import java.util.List;

public abstract class MyException extends Exception {

    private final List<String> errorMessages = initErrorMessages();
    private final String value;
    private final int code;

    public MyException(int code, String value) {
        this.code = code;
        this.value = value;
    }
    public MyException(int code) {
        this(code, "");
    }
    public MyException() {
        this(0, "");
    }

    @Override
    public String getMessage() {
        // Get message corresponding to error
        String errorMessage = errorMessages.get(code);
        // Format message with input value (if none was input, nothing happens)
        errorMessage = String.format(errorMessage, value);

        return ">>> "+errorMessage+" <<<";
    }

    /**
     * Define error messages supported by this Exception.
     * @return error messages
     */
    public abstract List<String> initErrorMessages();
}