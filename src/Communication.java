import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

/**--------------------------------------------------------------------------------
 * Created by Administrator on 2017-04-11.
 *
 * Są tu funkcje służące głównie do komunikacji z bazą danych SQLite:
 * logowanie, wysyłanie, odbieranie i usuwanie wpisów z bazy
 --------------------------------------------------------------------------------**/

public class Communication {
    static private String user;

    static private boolean allow = true;

    private static void waitForAllow(){
        while(!allow) if(false) allow = false;
        allow = false;
    }

    /**-------------------------------------------------------------------------------
     * Logowanie użytkowników
     * Po udanym logowaniu zmienna user równa jest nazwie zalogowanego użytkownika
     * Jeśli logowanie się nie powiodło to zmienna user jest równa null
     ------------------------------------------------------------------------------**/

    public boolean logIn(String username, String pwd){
        boolean dec = false;
        waitForAllow();
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

        allow = true;

        return dec;
    }

    /**----------------------------------------------------------------------------------------
     * Wysłanie wprowadzonych danych do bazy SQLite
     * Po wysłaniu wpisu funkcja ustawnia wartość amount w klasie Invoice
     * oraz wywołuje funkcje Invoice().generateRaport() do generowania raportu
     ---------------------------------------------------------------------------------------**/

    public void send(String product, float amount, float value, float tax, int clientid, String typeA, String typeB, String id){
        if(user.equals("boss") || user.equals("accountant")) {

            int il = 0;
            try {
                    waitForAllow();
                    Connection connection = DriverManager.getConnection("jdbc:sqlite:projektIOIO");

                    Statement statement = connection.createStatement();

                    String data = "(" + "'" + product + "'" + "," + String.valueOf(amount) + "," + String.valueOf(value) + "," + String.valueOf(tax) + "," + String.valueOf(clientid) + "," + "'" + typeA + "'" + "," + "'" + typeB + "'" + "," + "'" + id + "'" + ")";
                    statement.executeUpdate("INSERT INTO data VALUES " + data);

                    ResultSet rs = statement.executeQuery("SELECT Count(*) FROM data");
                    il = rs.getInt(1);

                    connection.close();
                    allow = true;
                    try {
                        new Invoice().generateRaport();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Invoice.setCurrentAmount(il);
        }
    }

    /**-----------------------------------------------------------------------------------
     * Podobna funkcjonalność co przypadku funkcji send
     * tylko ta funkcja służy do wysyłania wielu wpisów na raz
     * ---------------------------------------------------------------------------------**/

    public void sendManyData(Object[][] dt){
        if(user.equals("boss") || user.equals("accountant")) {

            try {
                waitForAllow();
                Connection connection = DriverManager.getConnection("jdbc:sqlite:projektIOIO");
                connection.setAutoCommit(false);
                Statement statement = connection.createStatement();

                for(int i=0;i<Conf.getAmount();i++) {
                    String data = "(" + "'" + dt[i][0] + "'" + "," + String.valueOf(dt[i][1]) + "," + String.valueOf(dt[i][2]) + "," + String.valueOf(dt[i][3]) + "," + String.valueOf(dt[i][4]) + "," + "'" + dt[i][5] + "'" + "," + "'" + dt[i][6] + "'" + "," + "'" + dt[i][7] + "'" + ")";
                    statement.executeUpdate("INSERT INTO data VALUES " + data);
                }

                connection.commit();

                ResultSet rs = statement.executeQuery("SELECT Count(*) FROM data");
                Invoice.setCurrentAmount(rs.getInt(1));

                connection.close();

                allow = true;

                try {
                    new Invoice().generateRaport();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**--------------------------------------------------------------------------------------------------------
     * Funkcja pobiera dane z bazy i zwraca je w postaci tablicy
     * Argument n, który przyjmuje funkcja służy do określnie ilości wpisów do odebrania
     * ------------------------------------------------------------------------------------------------------**/

    public String[][] receive(int n){
        String[][] data = null;
        if(user.equals("boss")) {

            try {
                waitForAllow();
                Connection connection = DriverManager.getConnection("jdbc:sqlite:projektIOIO");
                Statement statement = connection.createStatement();

                ResultSet rs = statement.executeQuery("SELECT Count(*) FROM data");
                if(n>rs.getInt(1) || n==0) n = rs.getInt(1);

                data = new String[n][8];

                String amount = "";
                if(n!=0) amount = " LIMIT " + String.valueOf(n);
                rs = statement.executeQuery("SELECT * FROM data" + amount);

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
                allow = true;
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

    /** ------------------------------------------------------------------------------------------
     * Funkcja do wyczyszczenia tabeli z przywroceniem iteracji wierszy od 0
     * Argument n służy do określnia ilości wpisów do usunięcia
     * -----------------------------------------------------------------------------------------**/

    public void delete(int n) {
        if (user.equals("boss")) {
            waitForAllow();
            try {
                Connection connection = DriverManager.getConnection("jdbc:sqlite:projektIOIO");

                Statement statement = connection.createStatement();
                String amount = "";
                if(n!=0) amount =  " where Number in (select Number from data limit " + String.valueOf(n) + ")";
                statement.executeUpdate("delete from data" + amount);

                ResultSet rs = statement.executeQuery("SELECT Count(*) FROM data");
                Invoice.setCurrentAmount(rs.getInt(1));

                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            allow = true;
        }
    }

}
