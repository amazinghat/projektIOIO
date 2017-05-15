/**----------------------------------------------------------------------------------------------
 * Klasa zawierająca parametr określający ilość wpisów do wygenerowania raportu,
 * ustawiany za pomocą GUI
 ----------------------------------------------------------------------------------------------*/

class Conf {
    private static int amount = 50000;

    static int getAmount() {
        return amount;
    }

    static void setAmount(int wpisy) {
        amount = wpisy;
    }       // funkcja wywoływana w GUI do ustawiania wartości
}
