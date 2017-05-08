import java.io.*;

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

   public static void setCurrentAmount(int currentAmount) throws IOException, InterruptedException {
       Invoice.currentAmount = currentAmount;
       System.out.println(currentAmount);
       if(currentAmount % Conf.getAmount() == 1){   // ---> generuj raport co 10 wpisow

          File raportfile = new File("raport.txt");
          PrintWriter zapis = new PrintWriter(new FileWriter(raportfile, true));
          String [][] raport = Communication.receive();
          float przychod = 0, wydatek = 0, saldo, podatek = 0;
          int iloscIncome = 0, iloscOutcome = 0;

          //-------------------------ZESTAWIENIE TRANSAKCJI------------------------
          for (int i = 0; i < raport.length ; i++) {
             zapis.print("Transakcja " + i + ": " + raport[i][0] + " " + raport[i][1] + " " + raport[i][2] + " " + raport[i][3] + " " + raport[i][4] + " " + raport[i][5] + " " + raport[i][6] + " " + raport[i][7]);
             zapis.println();

             if(raport[i][5] == "Income"){
                iloscIncome++;
                przychod = przychod + Float.parseFloat(raport[i][2]);
             }
             else if(raport[i][5] == "Outcome"){
                iloscOutcome++;
                wydatek = wydatek + Float.parseFloat(raport[i][2]);
             }

             podatek = podatek + Float.parseFloat(raport[i][3]);
          }
          saldo = przychod - wydatek;
          zapis.println("Ilosc transakcji: " + raport.length);
          zapis.println("Saldo: " + saldo);
          zapis.println("Ilosc wplywow: " + iloscIncome);
          zapis.println("Ilosc wydatkow: " + iloscOutcome);
          zapis.println("Sumaryczny podatek: " + podatek);
          zapis.println();
          //------------------------------------------------------------------------
          zapis.close();

          Communication.delete();      // ---> czysci baze danych po raporcie
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
