import java.io.*;
import java.util.Scanner;

/**
 * Created by Administrator on 2017-04-11.
 */
public class Invoice {
   private String id;
   private String typeA;
   private String typeB;
   private String product;
   private float amount;
   private float value;
   private float tax;
   private int clientid;

   private static boolean sending;

   static private int currentAmount;

   public static int getCurrentAmount() {
      return currentAmount;
   }

   public static void setCurrentAmount(int currentAmount) throws IOException, InterruptedException {
       Invoice.currentAmount = currentAmount;
       if(currentAmount % Conf.getAmount() == 1){   // ---> generuj raport co 10 wpisow
          System.out.println("Generuje raport");

          File raportfile = new File("raport.txt");
          PrintWriter zapis = new PrintWriter(new FileWriter(raportfile, true));
          String [][] raport = new Communication().receive();

          for (int i = 0; i < raport.length ; i++) {
             zapis.print("Transakcja " + i + ": " + raport[i][0] + " " + raport[i][1] + " " + raport[i][2] + " " + raport[i][3] + " " + raport[i][4] + " " + raport[i][5] + " " + raport[i][6] + " " + raport[i][7]);
             zapis.println();
          }
          zapis.println("Ilosc transakcji: " + raport.length);
          zapis.close();

          new Communication().delete();      // ---> czysci baze danych po raporcie
          /*
          raport nie dodaje najnowszego wiersza bo tworzenie jest wywoływane przed dodaniem go w funkcji send



          robie szczegoly do raportu





          */
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

   public static void readFromFile(){
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

            while((line = scanner.nextLine()) != null && sending){
               data = line.split(",");
               int clientID = Integer.valueOf(data[8].substring(6, data[8].length()));
               float percent = Float.valueOf(data[7].substring(0, data[7].length()-1));
               new Communication().send(data[4], Float.parseFloat(data[6]), Float.parseFloat(data[5]), percent, clientID, data[2], data[3], data[0]);
            }
            System.out.println("READY");
         }
      }).start();
   }

   public void write(){

   }

}
