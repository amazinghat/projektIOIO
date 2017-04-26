import javax.swing.*;
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

    /*

    DISPLAY MA BYĆ W GUI - ALBO POLACZYC KONSOLE Z GUI ALBO DODATKOWE POLA JAKO DISPLAY -
    Z PKT WIDZENIA UZYTKOWNIA LEPIEJ MIEC WSZYSTKO W JEDNYM MIEJSCU
    
    */


    public GUI() {
        addInvoice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("GUI");
                frame.setContentPane(new AddInvoice().getInvoice());
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("GUI");
        frame.setContentPane(new GUI().GUI);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
