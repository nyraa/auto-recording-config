import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    public Window() {
        super("config");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = getContentPane();
        container.setLayout(new BorderLayout());

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

        // action panel
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));
        Box box = new Box(BoxLayout.Y_AXIS);

        JButton checkNowButton = new JButton("Check now");
        JButton resetButton = new JButton("Reset");

        box.add(checkNowButton);
        box.add(Box.createVerticalStrut(10));
        box.add(resetButton);
        actionPanel.add(box);

        // action panel align to right
        upperPanel.add(actionPanel, "East");

        container.add(upperPanel, "North");

        // lower panel
        JPanel lowerPanel = new JPanel();
        JList<Section> sectionList = new JList<>();
        sectionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sectionList.setLayoutOrientation(JList.VERTICAL);
        sectionList.setVisibleRowCount(-1);
        sectionList.setCellRenderer(new SectionListRenderer());


        JScrollPane scrollPane = new JScrollPane(sectionList);
        lowerPanel.add(scrollPane);
        container.add(lowerPanel, "West");
    }
}

class SectionListRenderer extends DefaultListCellRenderer {
    public Component getListCellRendererComponent(JList<?> list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof Section) {
            Section section = (Section) value;
            setText(section.getSectionName());
        }
        return this;
    }
}