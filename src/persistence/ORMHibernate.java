
package persistence;

import exceptions.DatabaseException;
import java.util.List;
import model.Expedientes;
import model.Usuarios;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ORMHibernate {

    private ORMHibernate() {}
    private static ORMHibernate instance;
    public static ORMHibernate getInstance() {
        if (instance == null)
            instance = new ORMHibernate();
        return instance;
    }

    /* --- USER --- */

    public Usuarios validateLogin(String matricula, String password) throws DatabaseException {
        Usuarios user = getUserByMatricula(matricula);
        if (user == null || !user.getPass().equals(password)) {
            throw new DatabaseException(DatabaseException.Errors.LOGIN_INCORRECT.ordinal());
        }
        
        return user;
    }

    public List<Usuarios> getAllUsers() {
        Session session = getSession();
        
        String query = "FROM Usuarios";
        Query q = session.createQuery(query);
        List<Usuarios> users = q.list();
        
        session.close();
        return users;
    }
    private Usuarios getUserByMatricula(String matricula) {
        Session session = getSession();

        String query = "FROM Usuarios WHERE matricula = '"+matricula+"'";
        Query q = session.createQuery(query);
        Usuarios user = (Usuarios) q.uniqueResult();
        
        session.close();
        return user;
    }
    
    public void addUser(Usuarios user) throws DatabaseException {
        // Make sure User is valid
        validateUser(user);
        // Register User
        insertUser(user);
    }
    private void validateUser(Usuarios user) throws DatabaseException {
        // Make sure User is not already registered
        if (getUserByMatricula(user.getMatricula()) != null) {
            throw new DatabaseException(DatabaseException.Errors.USER_ALREADY_REGISTERED.ordinal());
        }
    }
    private void insertUser(Usuarios user) {
        insertItem(user);
    }

    public void updateUser(Usuarios user) {
        
    }
    
    public void deleteUser(Usuarios user) {
        deleteItem(user);
    }

    /* --- RECORD --- */
    
    public List<Expedientes> getAllRecords() {
        return selectAllByClass(Expedientes.class);
    }
    
    public void addRecord(Expedientes record) {
        // Make sure Record is valid
        validateRecord(record);
        // Register Record
        insertRecord(record);
    }
    private void validateRecord(Expedientes record) {
        
    }
    private void insertRecord(Expedientes record) {
        insertItem(record);
    }
    
    public void updateRecord(Expedientes record) {
        
    }
    
    public void deleteRecord(Expedientes record) {
        deleteItem(record);
    }

    /* --- DATABASE --- */
    
    private <T extends Object> List<T> selectAllByClass(Class type) {
        Session session = getSession();
        String query = "FROM " + type.getSimpleName();
        Query q = session.createQuery(query);
        List<T> items = q.list();
        
        session.close();
        return items;
    }
    private void insertItem(Object item) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        session.save(item);
        tx.commit();
        session.close();
    }
    private void deleteItem(Object item) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        session.delete(item);
        tx.commit();
        session.close();
    }
    
    private Session getSession() {
        return HibernateUtil.createSessionFactory().openSession();
    }
}