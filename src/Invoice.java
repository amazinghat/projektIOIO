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

   public static void setCurrentAmount(int currentAmount) {
       Invoice.currentAmount = currentAmount;
       System.out.println(currentAmount);
       if(currentAmount>Conf.getAmount()){
           //TODO: Generuj Raport do pliku
           //Funkcja Communiction.receive() zwraca dane w postaci tabeli Stringów - należy to zapisać do nowego pliku
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
