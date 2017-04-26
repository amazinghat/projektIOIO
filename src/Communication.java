import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Administrator on 2017-04-11.
 */
public class Communication {
    private String destination;

    public String getDestination() {
        return destination;
    }

    /*
        Wysłanie wprowadzonych danych do bazy MySQL
        Połączyć się z nią można jedynie z wewnątrz sieci AGH
        lub za pomocą VPN
     */
    public void send(String product, int amount, float value, float tax, int clientid, String typeA, String typeB, String id){
        String url = "jdbc:mysql://mysql.agh.edu.pl:3306/";
        String dbName = "jszczerb";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "jszczerb";
        String password = "KCUEGuy92YtVig25";

        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url+dbName, userName, password);

            Statement st = conn.createStatement();
            String data = "(" + "'" + product + "'" + "," + String.valueOf(amount) + "," + String.valueOf(value) + "," + String.valueOf(tax) + "," + String.valueOf(clientid) + "," + "'" + typeA + "'" + "," + "'" + typeB + "'" + "," + "'" + id + "'" + ")";
            st.executeUpdate("INSERT INTO projektIOIO(Product, Amount, Value, Tax, ClientID, TypeA, TypeB, Number) VALUES " + data);

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String receive(){

        return "";
    }


}
