import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**-----------------------------------------------------------------------------
 * Okienko POPUP informujące o gotowości wczytanych danych
 ------------------------------------------------------------------------------*/

public class ReadyPanel {
    private JPanel readyPanel;
    private JButton OKButton;

    public ReadyPanel(JFrame frame) {
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
    }

    public JPanel getReadyPanel() {
        return readyPanel;
    }
}
