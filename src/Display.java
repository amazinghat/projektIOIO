import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

/**
 * Created by Administrator on 2017-04-25.
 */
public class Display {
    private JFrame jframe;
    private JPanel panel1;
    private JTable table1;

    public JPanel getPanel1() {
        return panel1;
    }

    public Display(JFrame frame) {
        jframe = frame;
        String data[][] = new Communication().receive();
        String[] column = {"Product", "Amount", "Value", "Tax", "ClientID", "TypeA", "TypeB", "Number"};

        DefaultTableModel model = new DefaultTableModel(data, column);
        table1.setModel(model);
        panel1.setLayout(new BorderLayout());
        panel1.add(table1, BorderLayout.CENTER);
        panel1.add(table1.getTableHeader(), BorderLayout.NORTH);
    }
}
