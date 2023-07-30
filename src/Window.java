import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridLayout;

public class Window extends JFrame
{
    public Window()
    {
        super("config");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // upper panel
        JPanel upperPanel = new JPanel();
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new GridLayout(2, 2));
        
        // status labels
        JLabel statusLabelName = new JLabel("Status: ");
        JLabel currentLabelName = new JLabel("Current section: ");
        JLabel statusValue = new JLabel("Idle");
        JLabel currentRunningValue = new JLabel("None");
        statusPanel.add(statusLabelName);
        statusPanel.add(statusValue);
        statusPanel.add(currentLabelName);
        statusPanel.add(currentRunningValue);

        // status panel align to left
        upperPanel.add(statusPanel, "West");

        // 
        add(upperPanel);
    }
}
