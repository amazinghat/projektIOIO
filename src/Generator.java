import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**-------------------------------------------------------------------------------------
 * Created by Administrator on 2017-04-11.
 *
 * Funkcja generuje fakture do pliku
 --------------------------------------------------------------------------------------*/

public class Generator {
    private static int nrfaktury;
    private int currentAmount;

    public static void generate(String produkt, float liczba, float wartosc, float podatek, int klient) throws IOException {
        nrfaktury++;
        java.util.Date date = new java.util.Date();
        File faktura = new File("faktura.txt");
        PrintWriter zapis = new PrintWriter(new FileWriter(faktura, true));

        zapis.println("                              FAKTURA NR " + nrfaktury);
        zapis.println("------------------------------------------------------------------------");
        zapis.println("\t\t\t\t\tData: " + date);
        zapis.println("\t\t\t\t\tMiejsce wystawienia: Kraków");
        zapis.println("------------------------------------------------------------------------");
        zapis.println("------------------------------------------------------------------------");
        zapis.println("SPRZEDAWCA: Sknerus S.A.\t\t\tNABYWCA: Klient " + klient);
        zapis.println("ul. Czarnowiejska 10\t\t\t\tul. jakaś z bazy dantch");
        zapis.println("31-555 Kraków\t\t\t\t\tcd. z bazy dantych");
        zapis.println("NIP: 334-245-23-58\t\t\t\tNIP: 000-000-00-00");
        zapis.println("------------------------------------------------------------------------");
        zapis.println("------------------------------------------------------------------------");
        zapis.println();
        zapis.println("------------------------------------------------------------------------");
        zapis.println("Produkt\t\t|Liczba\t|Wartosc|Podatek|Klient\t|");
        zapis.println(produkt + "\t|" + liczba + "\t|" + wartosc + "\t\t|" + podatek + "\t|" + klient + "\t|");
        zapis.println("------------------------------------------------------------------------");
        zapis.println();
        zapis.println("Do zapłaty: " + wartosc*liczba);
        zapis.println();
        zapis.println();
        zapis.println();
        zapis.println("Wystawił: Sknerus S.A.\t\t\t\tOdebrał: Klient " + klient);
        zapis.println("------------------------------------------------------------------------");
        zapis.println();
        zapis.println();
        zapis.println();
        zapis.close();
    }
}
