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

    /*
    DISPLAY MA BYĆ W GUI - ALBO POLACZYC KONSOLE Z GUI ALBO DODATKOWE POLA JAKO DISPLAY -
    Z PKT WIDZENIA UZYTKOWNIA LEPIEJ MIEC WSZYSTKO W JEDNYM MIEJSCU
    */
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

    }



    public static void main(String[] args) {
        JFrame frame = new JFrame("Program Ksiegowy");
        frame.setContentPane(new GUI().GUI);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
