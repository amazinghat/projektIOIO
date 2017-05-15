import javax.swing.*;
import java.awt.event.*;

/**--------------------------------------------------------------------------------------------
 * Okienko POPUP informujące o błędnie wprowadzonych danych
 --------------------------------------------------------------------------------------------*/

public class WrongData extends JDialog {
    private JPanel wrongData;
    private JButton OKButton;
    private JButton buttonOK;
    private JButton buttonCancel;

    public WrongData(JFrame frame) {
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
    }

    public JPanel getWrongData() {
        return wrongData;
    }
}
