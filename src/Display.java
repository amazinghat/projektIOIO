import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * Created by Administrator on 2017-04-25.
 */
public class Display {
    private JFrame jframe;
    private JPanel panel1;
    private JTable table1;
    private JButton button1;

    public JPanel getPanel1() {
        return panel1;
    }

    public Display(JFrame frame) {
        jframe = frame;
        table1.setRowSelectionAllowed(true);
        String data[][] = new Communication().receive();
        String[] column = {"Product", "Amount", "Value", "Tax", "ClientID", "TypeA", "TypeB", "Number"};

        DefaultTableModel model = new DefaultTableModel(data, column){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        table1.setModel(model);

        panel1.setLayout(new BorderLayout());
        JScrollPane forTable = new JScrollPane(table1);
        panel1.add(forTable, BorderLayout.CENTER);
        panel1.add(table1.getTableHeader(), BorderLayout.NORTH);
        panel1.add(button1, BorderLayout.SOUTH);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jframe.dispatchEvent(new WindowEvent(jframe, WindowEvent.WINDOW_CLOSING));
            }
        });
    }
}
