import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;

/**
 * Created by Administrator on 2017-04-11.
 */
public class Communication {
    private String destination;
    static private String user;

    public String getDestination() {
        return destination;
    }

    public boolean logIn(String username, String pwd){
        String url = "jdbc:mysql://mysql.agh.edu.pl:3306/";
        String dbName = "jszczerb";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "jszczerb";
        String password = "KCUEGuy92YtVig25";

        boolean dec = false;

        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url+dbName, userName, password);

            Statement st = conn.createStatement();
            ResultSet res = st.executeQuery("SELECT * FROM Users");

            while(res.next()){
                if(res.getString("username").equals(username) && res.getString("passwd").equals(pwd)){
                    dec = true;
                    user = res.getString("username");
                    break;
                }
                else{
                    user = null;
                }
            }

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

        return dec;
    }

    /*
        Wysłanie wprowadzonych danych do bazy MySQL
        Połączyć się z nią można jedynie z wewnątrz sieci AGH
        lub za pomocą VPN
     */
    public void send(String product, float amount, float value, float tax, int clientid, String typeA, String typeB, String id){
        if(user.equals("boss") || user.equals("accountant")) {
            String url = "jdbc:mysql://mysql.agh.edu.pl:3306/";
            String dbName = "jszczerb";
            String driver = "com.mysql.jdbc.Driver";
            String userName = "jszczerb";
            String password = "KCUEGuy92YtVig25";

            try {
                Class.forName(driver).newInstance();
                Connection conn = DriverManager.getConnection(url + dbName, userName, password);

                Statement st = conn.createStatement();
                String data = "(" + "'" + product + "'" + "," + String.valueOf(amount) + "," + String.valueOf(value) + "," + String.valueOf(tax) + "," + String.valueOf(clientid) + "," + "'" + typeA + "'" + "," + "'" + typeB + "'" + "," + "'" + id + "'" + ")";
                st.executeUpdate("INSERT INTO projektIOIO(Product, Amount, Value, Tax, ClientID, TypeA, TypeB, Number) VALUES " + data);
                // Dziadostwo :( nie pozwala na przeniesienie Invoice.setCuttentAmount w to miejsce
                ResultSet res = st.executeQuery("SELECT * FROM projektIOIO");
                res.last();
                Invoice.setCurrentAmount(res.getRow());

                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String[][] receive(){
        String[][] data = null;
        if(user.equals("boss")) {
            String url = "jdbc:mysql://mysql.agh.edu.pl:3306/";
            String dbName = "jszczerb";
            String driver = "com.mysql.jdbc.Driver";
            String userName = "jszczerb";
            String password = "KCUEGuy92YtVig25";

            try {
                Class.forName(driver).newInstance();
                Connection conn = DriverManager.getConnection(url + dbName, userName, password);

                Statement st = conn.createStatement();
                ResultSet res = st.executeQuery("SELECT * FROM projektIOIO");
                res.last();
                data = new String[res.getRow()][8];
                res.first();
                res.previous();
                int i = 0;
                while (res.next()) {
                    data[i][0] = res.getString("Product");
                    data[i][1] = res.getString("Amount");
                    data[i][2] = res.getString("Value");
                    data[i][3] = res.getString("Tax");
                    data[i][4] = res.getString("ClientID");
                    data[i][5] = res.getString("TypeA");
                    data[i][6] = res.getString("TypeB");
                    data[i][7] = res.getString("Number");
                    i++;
                }

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
        else if(user.equals("accountant")){
            data = new String[1][1];
            data[0][0] = "Not permited";
        }

        return data;
    }


    public void delete() {                               // funkcja do wyczyszczenia tabeli
        if (user.equals("boss")) {                              // z przywroceniem iteracji wierszy od 0
            String url = "jdbc:mysql://mysql.agh.edu.pl:3306/";
            String dbName = "jszczerb";
            String driver = "com.mysql.jdbc.Driver";
            String userName = "jszczerb";
            String password = "KCUEGuy92YtVig25";

            try {
                Class.forName(driver).newInstance();
                Connection conn = DriverManager.getConnection(url + dbName, userName, password);

                Statement st = conn.createStatement();
                st.executeUpdate("TRUNCATE TABLE projektIOIO");

                conn.close();

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
