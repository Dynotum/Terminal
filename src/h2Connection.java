import java.sql.*;

public class h2Connection {

    public Connection connect() {
        Connection con = null;

        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection("jdbc:h2:file:./h2DB/data","sa","sa"); //D:\Oracle Files\h2DB\data
            System.out.println("Online....");
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }

        return con;
    }

    public ResultSet consultar(){
        Connection con = connect();
        ResultSet resultSet = null;

        try {
            PreparedStatement ps = con.prepareStatement("select * from persons");
            resultSet = ps.executeQuery();
        }catch (Exception e){
            System.out.println(e.toString());
        }/*finally {
            try {
                con.close();
            }catch (Exception e){
                System.out.println(e.toString());
            }
        }*/
        return resultSet;
    }

    public static void main(String[] args) {
        h2Connection con = new h2Connection();
        ResultSet rs = con.consultar();

        try{
            while (rs.next()){
                System.out.printf("%s",rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
}
