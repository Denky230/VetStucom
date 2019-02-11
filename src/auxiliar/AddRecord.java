
package auxiliar;

import exceptions.MyException;
import java.io.IOException;

public class AddRecord extends Action {

    @Override
    public void doSomething() throws IOException, MyException {
        view.addRecord();
    }

    @Override
    public String toString() {
        return "Add Record";
    }
}