import javax.swing.*;
import javax.swing.text.MaskFormatter;

import java.awt.*;
import java.util.Vector;

public class Window extends JFrame {
    private boolean isEditing = false;
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
        lowerPanel.setLayout(new GridBagLayout());

        Vector<Section> sections = DataManagement.getSections();
        JList<Section> sectionList = new JList<>(sections);
        sectionList.setCellRenderer(new SectionListRenderer());
        sectionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sectionList.setLayoutOrientation(JList.VERTICAL);
        sectionList.setVisibleRowCount(-1);


        JScrollPane scrollPane = new JScrollPane(sectionList);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        lowerPanel.add(scrollPane, gbc);

        // date format: YYYY-MM-DD HH:MM:SS
        MaskFormatter dateFormatter;
        try
        {
            dateFormatter = new MaskFormatter("####-##-## ##:##:##");
            dateFormatter.setPlaceholderCharacter('0');
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return;
        }

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 2));
        formPanel.add(new JLabel("Section: "));
        JTextField sectionField = new JTextField();
        formPanel.add(sectionField);
        formPanel.add(new JLabel("Type: "));
        // type: combobox
        String[] types = {"Zoom", "Webex"};
        JComboBox<String> typeField = new JComboBox<>(types);
        formPanel.add(typeField);
        formPanel.add(new JLabel("Room: "));
        JTextField roomField = new JTextField();
        formPanel.add(roomField);
        formPanel.add(new JLabel("Start: "));
        JFormattedTextField startField = new JFormattedTextField(dateFormatter);
        formPanel.add(startField);
        formPanel.add(new JLabel("End: "));
        JFormattedTextField endField = new JFormattedTextField(dateFormatter);
        formPanel.add(endField);
        formPanel.add(new JLabel("Password: "));
        JTextField passwordField = new JTextField();
        formPanel.add(passwordField);
        formPanel.add(new JLabel("Name: "));
        JTextField nameField = new JTextField();
        formPanel.add(nameField);
        formPanel.add(new JLabel("Email: "));
        JTextField emailField = new JTextField();
        formPanel.add(emailField);
        formPanel.add(new JLabel("Keep: "));
        JCheckBox keepCheckBox = new JCheckBox();
        formPanel.add(keepCheckBox);

        gbc.weightx = 2;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        lowerPanel.add(formPanel, gbc);
        container.add(lowerPanel, "Center");

        JPanel editPanel = new JPanel();
        JButton addButton = new JButton("New");
        JButton saveButton = new JButton("Save");
        JButton deleteButton = new JButton("Delete");
        editPanel.add(addButton);
        editPanel.add(saveButton);
        editPanel.add(deleteButton);

        container.add(editPanel, "South");
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