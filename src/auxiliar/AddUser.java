
package auxiliar;

import exceptions.ApplicationException;
import exceptions.DatabaseException;
import java.io.IOException;

public class AddUser extends Action {

    @Override
    public void doSomething() throws IOException, DatabaseException, ApplicationException {
        view.addUser();
    }

    @Override
    public String toString() {
        return "Add User";
    }
}