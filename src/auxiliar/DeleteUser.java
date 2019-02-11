
package auxiliar;

import exceptions.MyException;
import java.io.IOException;

public class DeleteUser extends Action {

    @Override
    public void doSomething() throws IOException, MyException {
        view.removeUser();
    }

    @Override
    public String toString() {
        return "Delete User";
    }
}