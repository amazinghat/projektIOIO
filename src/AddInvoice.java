import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * Created by Administrator on 2017-04-25.
 */
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

    public AddInvoice(JFrame frame) {
        this.jframe = frame;
        // Lista Klientów
        // Dodawanie elementow do listy w GUI
        // To może być wczytywane z pliku później, albo z bazy jakiejś
        clientList.addItem("[0] Ktos tam");
        clientList.addItem("[1] Ktos tam inny");
        clientList.addItem("[2] Ktos tam całkiem inny");


        // Automatyczne generowanie numeru faktury na początku
        // Oraz stworzenie nowego obiektu faktury
        // Może od potem zostać zapisany do pliku(Invoice.saveToFile) lub bazy(Invoice.write)
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
    }

    public JPanel getInvoice() {
        return Invoice;
    }
}
