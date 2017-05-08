import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.File;

/**
 * Created by Administrator on 2017-04-11.
 */
public class Invoice {
   private String id;
   private String typeA;
   private String typeB;
   private String product;
   private int amount;
   private float value;
   private float tax;
   private int clientid;

   static private int currentAmount;

   public static int getCurrentAmount() {
      return currentAmount;
   }

   public static void setCurrentAmount(int currentAmount) throws FileNotFoundException, InterruptedException {
       Invoice.currentAmount = currentAmount;
       System.out.println(currentAmount);
       if(currentAmount>Conf.getAmount()){   // tu bedzie modulo, zeby generowal co iles a nie za kazdym razem po przekroczeniu pewnej ilosci

          PrintWriter zapis = new PrintWriter("raport.txt");
          String [][] raport = Communication.receive();

          for (int i = 0; i < raport.length ; i++) {
             zapis.print("Transakcja " + i + ": " + raport[i][0] + " " + raport[i][1] + " " + raport[i][2] + " " + raport[i][3] + " " + raport[i][4] + " " + raport[i][5] + " " + raport[i][6] + " " + raport[i][7]);
             zapis.println();
          }
          zapis.println("Ilosc transakcji: " + raport.length);
          zapis.close();

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

   public void setAmount(int amount) {
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

   public void write(){

   }

}
