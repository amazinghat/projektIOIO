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

   public String generateNumber(){
       id = String.valueOf(System.currentTimeMillis());

       return id;
   }

   public void saveToFile(){

   }

   public void write(){

   }

}
