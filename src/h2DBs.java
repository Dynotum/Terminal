import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class h2DBs {

    private static String db_url;
    private static String db_name;
    private static String db_user;
    private static String db_password;
    private static Statement connection;

    private h2DBs() {
        db_url = "jdbc:h2:file:";
        db_name = "./h2DB/data";
        //TODO properties file for username & passwords
        db_user = "sa";
        db_password = "sa";

        /* Creation of an instance of the connection statement*/
        connection = setConnection();
    }

    /* Private method charge to set the connection statement*/
    private Statement setConnection() {
        Connection con = null;

        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection(db_url + db_name, db_user, db_password); //"jdbc:h2:file:./h2DB/data","sa","sa"

            //Creation of the Statement object
            Statement state = con.createStatement();
            return (Statement) state;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }

    /* Private inner class responsible for instantiating the single instance of the singleton */
    private static class h2DBsManagerHolder {
        private final static h2DBs instance = new h2DBs();
    }

    public static h2DBs getInstance() {
        try {
            return h2DBsManagerHolder.instance;
        } catch (ExceptionInInitializerError e) {
            System.out.println(e.toString());
        }
        return null;
    }

    public static Statement getStatement() {
        return connection;
    }
}

/*
    public Connection connect() {
        Connection con = null;

        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection(db_url + db_name, db_user, db_password); //"jdbc:h2:file:./h2DB/data","sa","sa"
            System.out.println("Online....");
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }

        return con;
    }

    public ResultSet consultar() {
        Connection con = connect();
        ResultSet resultSet = null;

        try {
            PreparedStatement ps = con.prepareStatement("select * from persons");
            resultSet = ps.executeQuery();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return resultSet;
    }

    public static void main(String[] args) {
        h2DBs con = new h2DBs();
        ResultSet rs = con.consultar();

        try {
            while (rs.next()) {
                System.out.printf("%s", rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
}*/

