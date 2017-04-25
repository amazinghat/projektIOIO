import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Administrator on 2017-04-25.
 */
public class AddInvoice {
    private JPanel Invoice;
    private JTextField productField;
    private JComboBox clientList;
    private JComboBox typeBList;
    private JComboBox typeAList;
    private JTextField amountField;
    private JTextField valueField;
    private JTextField taxField;
    private JButton addButton;
    private JButton generateButton;
    private JLabel idLabel;

    public AddInvoice() {
        // Dodawanie elementow do listy wybieranej
        clientList.addItem("asd");

        Invoice invoice = new Invoice();

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idLabel.setText(invoice.generateNumber());
            }
        });
    }

    public JPanel getInvoice() {
        return Invoice;
    }
}
