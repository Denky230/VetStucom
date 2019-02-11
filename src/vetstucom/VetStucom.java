
package vetstucom;

import exceptions.CancelOperationException;
import exceptions.MyException;
import java.io.IOException;
import java.util.logging.Level;
import view.ViewHandler;

public class VetStucom {

    public static void main(String[] args) {

        // Disable Hibernate error spam
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

        ViewHandler view = ViewHandler.getInstance();

        try {
            while (true) {
                view.menu();    
            }

        } catch (CancelOperationException e) {
        } catch (MyException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}