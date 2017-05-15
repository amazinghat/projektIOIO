import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**------------------------------------------------------------------------------------------------------
 * Created by kuba on 05.04.17.
 *
 * Główna klasa zawierająca main() oraz sterująca elementami GUI
 ------------------------------------------------------------------------------------------------------*/

public class GUI implements OnEndReadingListener{
    private JButton addInvoice;
    private JPanel GUI;
    private JButton display;
    private JTextField loginField;
    private JTextField pwdField;
    private JTextField ammountField;
    private JButton showRaport;
    private JButton clearButton;
    private JButton readButton;
    private JButton stopButton;
    private JTextField wpisyField;
    private JButton wpisyButton;

    public GUI() {
        stopButton.setEnabled(false);

        // ---------------------- Listenery do poszczególnych przycisków GUI --------------------------------

        addInvoice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(new Communication().logIn(loginField.getText(), pwdField.getText())) {
                    JFrame frame = new JFrame("Dodawanie faktur/paragonów");
                    frame.setContentPane(new AddInvoice(frame).getInvoice());
                    frame.pack();
                    frame.setVisible(true);
                }
            }
        });

        display.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(new Communication().logIn(loginField.getText(), pwdField.getText())) {
                    JFrame frame = new JFrame("Wyświetlanie");
                    frame.setContentPane(new Display(frame).getPanel1());
                    frame.setSize(1000,500);
                    //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    frame.setUndecorated(true);
                    frame.setVisible(true);
                }
            }
        });

        showRaport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(new Communication().logIn(loginField.getText(), pwdField.getText())) {
                    ammountField.setText(Integer.toString(Invoice.getCurrentAmount()));
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(new Communication().logIn(loginField.getText(), pwdField.getText())) {
                    new Communication().delete(0);
                }
            }
        });

        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(new Communication().logIn(loginField.getText(), pwdField.getText())) {
                    stopButton.setEnabled(true);
                    addInvoice.setEnabled(false);
                    readButton.setEnabled(false);
                    display.setEnabled(false);
                    clearButton.setEnabled(false);
                    showRaport.setEnabled(false);
                    Invoice invoice = new Invoice();
                    invoice.setOnEndReadingListener(GUI.this);
                    invoice.readFromFile();
                }
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Invoice.sending = false;
                Invoice.setSending(false);
                stopButton.setEnabled(false);
                addInvoice.setEnabled(true);
                readButton.setEnabled(true);
                display.setEnabled(true);
                clearButton.setEnabled(true);
                showRaport.setEnabled(true);
            }
        });

        wpisyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Conf.setAmount(Integer.parseInt(wpisyField.getText()));
                } catch (NumberFormatException e1){
                    wpisyField.setText("Nieprawidłowa warość!");
                }

            }
        });
    }

    @Override
    public void endReading() {
        stopButton.doClick();
        JFrame frame = new JFrame("INFO");
        frame.setContentPane(new ReadyPanel(frame).getReadyPanel());
        frame.pack();
        frame.setVisible(true);
    }

    // ----------------------------------------------- MAIN ---------------------------------------------
    public static void main(String[] args) {
        JFrame frame = new JFrame("Program Ksiegowy");
        frame.setContentPane(new GUI().GUI);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
    // --------------------------------------------------------------------------------------------------
}
