
package auxiliar;

import exceptions.MyException;
import java.io.IOException;

public class DeleteRecord extends Action {

    @Override
    public void doSomething() throws IOException, MyException {
        view.removeRecord();
    }

    @Override
    public String toString() {
        return "Remove Record";
    }
}