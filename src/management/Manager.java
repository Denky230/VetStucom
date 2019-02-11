
package management;

import auxiliar.Action;
import auxiliar.AddRecord;
import auxiliar.AddUser;
import auxiliar.CheckRecords;
import auxiliar.CheckUsers;
import auxiliar.DeleteRecord;
import auxiliar.DeleteUser;
import constants.UserType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import model.Usuarios;

public class Manager {

    private Usuarios loggedUser;
    private HashMap<UserType, LinkedHashSet<Action>> userActions;
    
    private Manager() {
        initUserActions();
    }
    private static Manager instance;
    public static Manager getInstance() {
        if (instance == null)
            instance = new Manager();
        return instance;
    }
    
    private void initUserActions() {
        this.userActions = new HashMap();
        
        // ADMIN
        LinkedHashSet<Action> adminActions = new LinkedHashSet<>();
        adminActions.add(new CheckUsers());
        adminActions.add(new AddUser());
        adminActions.add(new DeleteUser());
        adminActions.add(new CheckRecords());
        adminActions.add(new AddRecord());
        adminActions.add(new DeleteRecord());
        userActions.put(UserType.ADMIN, adminActions);
        
        // VETERINARIAN
        LinkedHashSet<Action> vetActions = new LinkedHashSet<>();
        vetActions.add(new CheckRecords());
        vetActions.add(new AddRecord());
        vetActions.add(new DeleteRecord());
        userActions.put(UserType.VETERINARIAN, vetActions);
        
        // AUXILIAR
        LinkedHashSet<Action> auxActions = new LinkedHashSet<>();
        auxActions.add(new CheckRecords());
        userActions.put(UserType.AUXILIAR, auxActions);
    }
    
    public List<Action> getUserActionsByUserType(UserType type) {
        // From HashSet to List
        List<Action> actions = new ArrayList<>();
        actions.addAll(userActions.get(type));

        return actions;
    }
    
    public Usuarios getLoggedUser() {
        return this.loggedUser;
    }
    public void setLoggedUser(Usuarios user) {
        this.loggedUser = user;
    }
}