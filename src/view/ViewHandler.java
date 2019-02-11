
package view;

import auxiliar.Action;
import constants.UserType;
import exceptions.ApplicationException;
import exceptions.CancelOperationException;
import exceptions.DatabaseException;
import exceptions.MyException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import management.Manager;
import model.Expedientes;
import model.Usuarios;
import persistence.ORMHibernate;
import utils.Reader;

public class ViewHandler {
    
    private final Reader reader;
    private final Manager manager;
    private final ORMHibernate db;
    
    private ViewHandler() {
        reader = Reader.getInstance();
        manager = Manager.getInstance();
        db = ORMHibernate.getInstance();
    }
    private static ViewHandler instance;
    public static ViewHandler getInstance() {
        if (instance == null)
            instance = new ViewHandler();
        return instance;
    }
    
    public void menu() throws IOException, MyException {
        // Check if User is logged in
        Usuarios loggedUser = manager.getLoggedUser();
        
        try {
            if (loggedUser == null) {
                // Show login menu
                showLoginMenu();
            } else {
                // Show user menu
                showMenuByUser(loggedUser);
            }
        } catch (DatabaseException | ApplicationException e) {
            System.out.println(e.getMessage());
        }
    }
    private void showLoginMenu() throws IOException, MyException {
        // Build login menu action list
        List<String> actions = new ArrayList<>();
        actions.add("Login");
        
        // Show menu
        String action = requestPickFromList(actions, String.class);

        switch (action) {
            case "Login":
                login();
                break;
        }
    }
    private void showMenuByUser(Usuarios user) throws IOException, MyException {
        try {
            UserType type = UserType.values()[user.getTipoUsuario()];
            List<Action> actions = manager.getUserActionsByUserType(type);
            Action action = requestPickFromList(actions, Action.class);
            action.doSomething();
            
        } catch (CancelOperationException e) {
            manager.setLoggedUser(null);
        }
    }

    private void login() throws IOException, MyException {
        Usuarios user = validateUser();
        // If everything went ok...
        manager.setLoggedUser(user);
        // Welcome User
        System.out.println(
                "Login successful\n" +
                "Welcome, " + user.getMatricula()
        );
        // Show User possible actions
        showMenuByUser(user);
    }
    private Usuarios validateUser() throws IOException, DatabaseException, ApplicationException {
        // Request credentials
        String[] credentials = requestUserInput("User-sama, gimme login plox", 2);
        String login = credentials[0];
        String password = credentials[1];

        // Try to login w/ credentials
        Usuarios user = db.validateLogin(login, password);
        return user;
    }
    
    /* --- USER --- */
    
    public void listAllUsers() {
        // Get every User from database
        List<Usuarios> users = db.getAllUsers();

        String header = "*** USERS ***";
        listUsers(users, header, true);
    }
    private void listUsers(List<Usuarios> users, String header, boolean indexed) {
        listItems(users, header, indexed);
    }

    public void addUser() throws IOException, DatabaseException, ApplicationException {
        // Request User data
        String[] userData = requestUserInput("User-sama, please enter: name, surname, DNI, username and password", 5);

        // Add User to database
        Usuarios user = fillUser(userData);
        db.addUser(user);

        // If everything went ok...
        String username = user.getMatricula();
        System.out.println(username + " successfully added!");
    }
    private Usuarios fillUser(String[] data) {
        String name = data[0];
        String surname = data[1];
        String DNI = data[2];
        String matricula = data[3];
        String password = data[4];
        int userType = 0;
        Date date = new Date();
        HashSet expedienteses = new HashSet();
        
        Usuarios user = new Usuarios(name, surname, DNI, matricula, password, userType, date, expedienteses);
        return user;
    }

    // TO DO
    public void editUser() throws IOException, ApplicationException, CancelOperationException {
        // Request User to edit
        Usuarios user = requestPickFromAllUsers();

        // Print User editable fields
        // Request field to edit
    }

    public void removeUser() throws IOException, ApplicationException, CancelOperationException {
        // Request User to remove
        Usuarios user = requestPickFromAllUsers();
        
        // Remove User from database
        db.deleteUser(user);
        
        String username = user.getMatricula();
        System.out.println(username + " was successfully deleted!");
    }
    
    /* --- RECORD --- */
    
    public void listAllRecords() {
        // Get every Record from database
        List<Expedientes> records = db.getAllRecords();
 
        String header = "*** RECORDS ***";
        listRecords(records, header, true);
    }
    private void listRecords(List<Expedientes> records, String header, boolean indexed) {
        listItems(records, header, indexed);
    }
    
    public void addRecord() throws IOException, ApplicationException {
        // Request Record data
        String[] recordData = requestUserInput("User-sama, please enter: name, surname, DNI, CP, telephone and number of pets", 6);

        // Add Record to database
        Expedientes record = fillRecord(recordData);
        db.addRecord(record);

        // If everything went ok...
        String name = record.getNombre();
        System.out.println("New record for "+name+" successfully added!");
    }
    private Expedientes fillRecord(String[] data) {
        Usuarios user = manager.getLoggedUser();
        String name = data[0];
        String surname = data[1];
        String DNI = data[2];
        String CP = data[3];
        Date date = new Date();
        String telephone = data[4];
        int petsNumber = Integer.parseInt(data[5]);
        
        Expedientes record = new Expedientes(user, name, surname, DNI, CP, date, telephone, petsNumber);
        return record;
    }
    
    // TO DO
    public void editRecord() throws IOException, ApplicationException, CancelOperationException {
        // Request User to edit
        Expedientes record = requestPickFromAllRecords();

        // Print Record editable fields
        // Request field to edit
    }
    
    public void removeRecord() throws IOException, ApplicationException, CancelOperationException {
        // Request Record to remove
        Expedientes record = requestPickFromAllRecords();
        
        // Remove Record from database
        db.deleteRecord(record);
        
        String DNI = record.getDni();
        System.out.println(DNI + "'s record was successfully removed!");
    }
    
    
    private <T extends Object> T requestPickFromList(List items, Class<T> type) throws IOException, CancelOperationException {
        // Request item choice by index
        String header = "Please, enter index (1, 2, 3...) or 0 to exit";
        listItems(items, header, true);
        // Make sure index input is inside range
        int index = reader.nextInt(0, items.size());

        // 0 = Cancel operation
        if (index == 0) {
            throw new CancelOperationException();
        }
        
        // Cast chosen item to given class
        T item = type.cast(items.get(index - 1));
        return item;
    }
    private Usuarios requestPickFromUsers(List<Usuarios> users) throws IOException, ApplicationException, CancelOperationException {
        Usuarios user = requestPickFromList(users, Usuarios.class);
        return user;
    }
    private Usuarios requestPickFromAllUsers() throws IOException, ApplicationException, CancelOperationException {
        // Get every User from database
        List<Usuarios> users = db.getAllUsers();
        Usuarios user = requestPickFromUsers(users);
        return user;
    }
    private Expedientes requestPickFromRecords(List<Expedientes> records) throws IOException, ApplicationException, CancelOperationException {
        Expedientes record = requestPickFromList(records, Expedientes.class);
        return record;
    }
    private Expedientes requestPickFromAllRecords() throws IOException, ApplicationException, CancelOperationException {
        // Get every Record from database
        List<Expedientes> records = db.getAllRecords();
        Expedientes record = requestPickFromRecords(records);
        return record;
    }
    private String[] requestUserInput(String request, int expectedArguments) throws IOException, ApplicationException {
        System.out.println(request);
        String[] input = reader.nextLine().split(" ");
        
        if (input.length < expectedArguments) {
            throw new ApplicationException(ApplicationException.Errors.INVALID_NUM_ARGUMENTS.ordinal());
        }
        
        return input;
    }
    private void listItems(List items, String header, boolean indexed) {
        if (!items.isEmpty()) {
            System.out.println(header);

            if (indexed) {
                // Sout items preceded by index (1- item, 2- item, 3- item, etc.)
                for (int i = 0; i < items.size(); i++) {
                    System.out.println(i + 1 + " - " + items.get(i));
                }
            } else {
                // Sout just items (item, item, item, etc.)
                for (Object item : items) {
                    System.out.println(item);
                }
            }

        } else {
            System.out.println("Nothing to see here :(");
        }
    }
}