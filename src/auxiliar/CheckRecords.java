
package auxiliar;

import static auxiliar.Action.view;

public class CheckRecords extends Action {
    
    @Override
    public void doSomething() {
        view.listAllRecords();
    }

    @Override
    public String toString() {
        return "Check all Records";
    }
}