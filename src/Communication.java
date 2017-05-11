import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017-04-11.
 */
public class Communication {
    static private String user;

    public static Connection connectionForReadingFromFile;

    public static void setConnectionForReadingFromFile() {
        try {
            Communication.connectionForReadingFromFile = DriverManager.getConnection("jdbc:sqlite:projektIOIO");
            Communication.connectionForReadingFromFile.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeConnectionForReadingFromFile(){
        try {
            connectionForReadingFromFile.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean logIn(String username, String pwd){
        boolean dec = false;

        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:projektIOIO");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM users");

            while(rs.next()){
                if(rs.getString("username").equals(username) && rs.getString("password").equals(pwd)){
                    dec = true;
                    user = rs.getString("username");
                    break;
                }
                else{
                    user = null;
                }
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dec;
    }

    /*
        Wysłanie wprowadzonych danych do bazy MySQL
        Połączyć się z nią można jedynie z wewnątrz sieci AGH
        lub za pomocą VPN
     */
    public void send(String product, float amount, float value, float tax, int clientid, String typeA, String typeB, String id, boolean fromFile){
        if(user.equals("boss") || user.equals("accountant")) {

            int il = 0;
            try {
                if(!fromFile){
                    Connection connection = DriverManager.getConnection("jdbc:sqlite:projektIOIO");

                    Statement statement = connection.createStatement();

                    String data = "(" + "'" + product + "'" + "," + String.valueOf(amount) + "," + String.valueOf(value) + "," + String.valueOf(tax) + "," + String.valueOf(clientid) + "," + "'" + typeA + "'" + "," + "'" + typeB + "'" + "," + "'" + id + "'" + ")";
                    statement.executeUpdate("INSERT INTO data VALUES " + data);

                    ResultSet rs = statement.executeQuery("SELECT Count(*) FROM data");
                    il = rs.getInt(1);

                    connection.close();
                }
                else{
                    Statement statement = connectionForReadingFromFile.createStatement();

                    String data = "(" + "'" + product + "'" + "," + String.valueOf(amount) + "," + String.valueOf(value) + "," + String.valueOf(tax) + "," + String.valueOf(clientid) + "," + "'" + typeA + "'" + "," + "'" + typeB + "'" + "," + "'" + id + "'" + ")";
                    statement.executeUpdate("INSERT INTO data VALUES " + data);

                    ResultSet rs = statement.executeQuery("SELECT Count(*) FROM data");
                    il = rs.getInt(1);

                    if(Invoice.willGenerate){
                        connectionForReadingFromFile.commit();
                        connectionForReadingFromFile.close();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                Invoice.setCurrentAmount(il, true);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public String[][] receive(){
        String[][] data = null;
        if(user.equals("boss")) {

            try {
                Connection connection = DriverManager.getConnection("jdbc:sqlite:projektIOIO");
                Statement statement = connection.createStatement();

                ResultSet rs = statement.executeQuery("SELECT Count(*) FROM data");
                data = new String[rs.getInt(1)][8];

                rs = statement.executeQuery("SELECT * FROM data");

                int i = 0;
                while (rs.next()) {
                    data[i][0] = rs.getString("Product");
                    data[i][1] = rs.getString("Amount");
                    data[i][2] = rs.getString("Value");
                    data[i][3] = rs.getString("Tax");
                    data[i][4] = rs.getString("ClientID");
                    data[i][5] = rs.getString("TypeA");
                    data[i][6] = rs.getString("TypeB");
                    data[i][7] = rs.getString("Number");
                    i++;
                }

                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        else if(user.equals("accountant")){
            data = new String[1][1];
            data[0][0] = "Not permited";
        }

        return data;
    }


    public void delete() {                                       // funkcja do wyczyszczenia tabeli
        if (user.equals("boss")) {                               // z przywroceniem iteracji wierszy od 0

            try {
                Connection connection = DriverManager.getConnection("jdbc:sqlite:projektIOIO");

                Statement statement = connection.createStatement();
                statement.executeUpdate("DELETE FROM data");

                ResultSet rs = statement.executeQuery("SELECT Count(*) FROM data");
                Invoice.setCurrentAmount(rs.getInt(1), false);

                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
