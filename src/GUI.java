import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by kuba on 05.04.17.
 */
public class GUI {
    private JButton addInvoice;
    private JPanel GUI;
    private JButton display;
    private JTextField loginField;
    private JTextField pwdField;
    private JTextField ammountField;
    private JButton showRaport;
    private JButton clearButton;
    private JButton readButton;

    public GUI() {
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

        clearButton.addActionListener(new ActionListener() {                    // testowy guzik do spr czy czyszczenie dziala
            @Override                                                           // mozna wywalic a moze tez zostac
            public void actionPerformed(ActionEvent e) {
                if(new Communication().logIn(loginField.getText(), pwdField.getText())) {
                    new Communication().delete();
                }
            }
        });

        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(new Communication().logIn(loginField.getText(), pwdField.getText())) {
                    new Invoice().readFromFile();
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Program Ksiegowy");
        frame.setContentPane(new GUI().GUI);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
