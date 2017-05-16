import javax.swing.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**----------------------------------------------------------------------------------------------------------
 * Created by Administrator on 2017-04-11.
 *
 * Klasa zawiera funkcje generującą raporty o fakturach oraz wczytującą dane do bazy z pliku
 ----------------------------------------------------------------------------------------------------------*/

public class Invoice implements EndReadingObservable{
   private String id;
   private String typeA;
   private String typeB;
   private String product;
   private float amount;
   private float value;
   private float tax;
   private int clientid;
   private OnEndReadingListener onEndReadingListener;

   public static boolean sending;

   static private int currentAmount;
    static private int raportAmount = 0;

   public static int getCurrentAmount() {
      return currentAmount;
   }

   public static void setCurrentAmount(int currentAmount) {             // funkcja zmienia na bieżąco ilość aktualnie przechowywanych wpisów
       Invoice.currentAmount = currentAmount;
   }

   public void generateRaport() throws IOException {
      if(currentAmount >= Conf.getAmount()){                                           // ---> generuj raport co zadana ilosc wpisow
         System.out.println("Generuje raport" + String.valueOf(++raportAmount));

         File raportfile = new File("raport.txt");                                     // tworzy plik tekstowy z raportem
         PrintWriter zapis = new PrintWriter(new FileWriter(raportfile, true));        // nowy raport dopisuje do istniejacego pliku
         String[][] raport = new Communication().receive(Conf.getAmount());

         float przychod = 0, wydatek = 0, saldo, podatekinc = 0, podatekout = 0;
         int iloscIncome = 0, iloscOutcome = 0;

         /**----------------------------------------------------------------------------------------------------------------
          * Zestawienie transakcji do tworzenia raportu ilościowego i jakościowego
          ----------------------------------------------------------------------------------------------------------------*/

         for (int i = 0; i < raport.length ; i++) {
            zapis.print("Transakcja " + i + ": |produkt: " + raport[i][0] + " |ilosc: " + raport[i][1] + " |wartosc: " +
                    raport[i][2]+ "zl" + " |podatek: " + raport[i][3] + "%" + " |ID: " + raport[i][4] + " |typ: " + raport[i][5] +
                    " |rachunek: " + raport[i][6] + " |numer: " + raport[i][7]);
            zapis.println();

            if(raport[i][5].equals("income")){
               ++iloscIncome;
               przychod = przychod + Float.parseFloat(raport[i][2]);
               podatekinc = podatekinc + Float.parseFloat(raport[i][3]) * Float.parseFloat(raport[i][2]);
            }
            else if(raport[i][5].equals("outcome")){
               ++iloscOutcome;
               wydatek = wydatek + Float.parseFloat(raport[i][2]);
               podatekout = podatekout + Float.parseFloat(raport[i][3]) * Float.parseFloat(raport[i][2]);
            }
         }
         saldo = przychod - wydatek;
         zapis.println("Ilosc transakcji: " + raport.length);
         zapis.println("Saldo: " + saldo);
         zapis.println("Ilosc wplywow: " + iloscIncome);
         zapis.println("Ilosc wydatkow: " + iloscOutcome);
         zapis.println("Sumaryczny podatek przychodow: " + podatekinc);
         zapis.println("Sumaryczny podatek wydatkow: " + podatekout);
         zapis.println();
         //------------------------------------------------------------------------
         zapis.close();

         new Communication().delete(Conf.getAmount());      // ---> czysci baze danych po raporcie

         if(currentAmount >= Conf.getAmount()) generateRaport();
      }
   }

   public void setTypeA(String typeA) {
      this.typeA = typeA;
   }

   public void setTypeB(String typeB) {
      this.typeB = typeB;
   }

   public void setProduct(String product) {
      this.product = product;
   }

   public void setAmount(float amount) {
      this.amount = amount;
   }

   public void setValue(float value) {
      this.value = value;
   }

   public void setTax(float tax) {
      this.tax = tax;
   }

   public void setClientid(int clientid) {
      this.clientid = clientid;
   }

   public String generateNumber(){
       id = String.valueOf(System.currentTimeMillis());

       return id;
   }

   public void saveToFile(){
      new Communication().send(product, amount, value, tax, clientid, typeA, typeB, id);
   }

   public static void setSending(boolean sending) {
      Invoice.sending = sending;
   }

   /**----------------------------------------------------------------------------------------------------
    * Wczytuje dane z pliku i wysyła je do bazy danych
    * Robi to dopóki zmienna sending==true
    -----------------------------------------------------------------------------------------------------*/

   public void readFromFile(){
      sending = true;
      new Thread(new Runnable() {
         @Override
         public void run() {
            File dataInput = new File("DataInputGroupA.csv");
            Scanner scanner = null;
            try {
               scanner = new Scanner(dataInput);
            } catch (FileNotFoundException e) {
               e.printStackTrace();
            }
            String line;
            String[] data;
            Object[][] dataTable = new Object[Conf.getAmount()][8];

            while(sending) {
                int i = 0;
                while(i<Conf.getAmount()){
                   try {
                      line = scanner.nextLine();
                   } catch(NoSuchElementException e){
                      sending = false;
                      break;
                   }
                   data = line.split(",");
                   try {
                      int clientID = Integer.valueOf(data[8].substring(6, data[8].length()));
                      float percent = Float.valueOf(data[7].substring(0, data[7].length() - 1)) / 100;

                      // TODO: clientID jest opcjonalne

                      dataTable[i][0] = data[4];
                      dataTable[i][1] = Float.parseFloat(data[6]);
                      dataTable[i][2] = Float.parseFloat(data[5]);
                      dataTable[i][3] = percent;
                      dataTable[i][4] = clientID;
                      dataTable[i][5] = data[2];
                      dataTable[i][6] = data[3];
                      dataTable[i][7] = data[0];
                   } catch (NumberFormatException e){
                      System.out.println("Błędne dane");
                      JFrame frame = new JFrame("ERROR");
                      frame.setContentPane(new WrongData(frame).getWrongData());
                      frame.setLocationRelativeTo(null);
                      frame.pack();
                      frame.setVisible(true);
                   }

                   i++;
                }
                if(sending) new Communication().sendManyData(dataTable);
            }
            System.out.println("READY");
            onEndReadingListener.endReading();
         }
      }).start();
   }

   @Override
   public void setOnEndReadingListener(OnEndReadingListener l) {
      onEndReadingListener = l;
   }
}
