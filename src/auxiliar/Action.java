
package auxiliar;

import exceptions.MyException;
import java.io.IOException;
import view.ViewHandler;

public abstract class Action {
    
    protected static ViewHandler view = ViewHandler.getInstance();
    
    public abstract void doSomething() throws IOException, MyException;
    @Override public abstract String toString();
}