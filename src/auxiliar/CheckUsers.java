
package auxiliar;

public class CheckUsers extends Action {

    @Override
    public void doSomething() {
        view.listAllUsers();
    }

    @Override
    public String toString() {
        return "Check all Users";
    }
}