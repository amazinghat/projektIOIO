import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**---------------------------------------------------------------------------------------------
 * Klasa służy do sterowania GUI ręcznego wprowadzania wpisów do bazy danych
 ----------------------------------------------------------------------------------------------*/

public class AddInvoice {
    private JFrame jframe;
    private JPanel Invoice;
    private JTextField productField;
    private JComboBox clientList;
    private JComboBox typeBList;
    private JComboBox typeAList;
    private JTextField valueField;
    private JTextField taxField;
    private JButton addButton;
    private JLabel idLabel;
    private JSpinner amountSpinner;
    private JButton fakturaButton;

    public AddInvoice(JFrame frame) {
        this.jframe = frame;

        /**------------------------------------------------------------------------------------------
         * Lista Klientów
         * Dodawanie elementow do listy w GUI
         * To może być wczytywane z pliku później, albo z jakiejś bazy
         -------------------------------------------------------------------------------------------*/

        clientList.addItem("[0] Ktos tam");
        clientList.addItem("[1] Ktos tam inny");
        clientList.addItem("[2] Ktos tam całkiem inny");

        /**-------------------------------------------------------------------------------------------
         * Automatyczne generowanie numeru faktury na początku
         * Oraz stworzenie nowego obiektu faktury
         * Może od potem zostać zapisany do pliku(Invoice.saveToFile) lub bazy(Invoice.write)
         --------------------------------------------------------------------------------------------*/

        Invoice invoice = new Invoice();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                invoice.setProduct(productField.getText());
                invoice.setAmount(new Float((int)amountSpinner.getValue()));
                try {
                    invoice.setValue(Float.parseFloat(valueField.getText()));
                    invoice.setTax(Float.parseFloat(taxField.getText()));
                    invoice.setClientid(clientList.getSelectedIndex());
                    invoice.setTypeA((String) typeAList.getSelectedItem());
                    invoice.setTypeB((String) typeBList.getSelectedItem());
                    idLabel.setText(invoice.generateNumber());

                    invoice.saveToFile();
                } catch (NumberFormatException e1){
                    System.out.println("Błęne dane");
                    JFrame frame = new JFrame("ERROR");
                    frame.setContentPane(new WrongData(frame).getWrongData());
                    frame.pack();
                    frame.setVisible(true);
                    frame.setLocationRelativeTo(null);
                }

                jframe.dispatchEvent(new WindowEvent(jframe, WindowEvent.WINDOW_CLOSING));
            }
        });

        fakturaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Generator.generate(productField.getText(), new Float((int)amountSpinner.getValue()), Float.parseFloat(valueField.getText()), Float.parseFloat(taxField.getText()), clientList.getSelectedIndex());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public JPanel getInvoice() {
        return Invoice;
    }
}
