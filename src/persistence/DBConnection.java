
package persistence;

import model.Usuarios;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class DBConnection {

    private DBConnection() {}
    private static DBConnection instance;
    public static DBConnection getInstance() {
        if (instance == null)
            instance = new DBConnection();
        return instance;
    }

    public Usuarios getUserById(int id) {
        Session session = getSession();
        Usuarios user = (Usuarios) session.get(Usuarios.class, id);

        session.close();
        return user;
    }
    public Usuarios validateLogin(String matricula, String password) {
        Session session = getSession();

        session.close();
        throw new RuntimeException("Login incorrect");
    }
    public void addUser(String nombre, String apellidos, String dni, String matricula, String pass, Integer tipoUsuario) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();

        Usuarios user = new Usuarios(nombre, apellidos, dni, matricula, pass, tipoUsuario);
        session.save(user);
        tx.commit();

        session.close();
    }

    private Session getSession() {
        return HibernateUtil.createSessionFactory().openSession();
    }
}