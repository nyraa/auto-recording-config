import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Window extends JFrame {
    private boolean isEditing = false;
    private int prevIndex = -1;
    private boolean isAdjusting = false;
    private boolean isNewAdding = true;
    public Window(String filename) {
        super("config");
        setSize(600, 400);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


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

        DataManagementModel dataManagementModel = new DataManagementModel(filename);
        JList<Section> sectionList = new JList<>(dataManagementModel);
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

        ActionListener editFormListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isEditing = true;
            }
        };
        DocumentListener editFormDocumentListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                isEditing = true;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                isEditing = true;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                isEditing = true;
            }
        };

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 2));
        formPanel.add(new JLabel("Section: "));
        JTextField sectionField = new JTextField();
        sectionField.getDocument().addDocumentListener(editFormDocumentListener);
        formPanel.add(sectionField);

        formPanel.add(new JLabel("Type: "));
        String[] types = {"zoom", "webex"};
        JComboBox<String> typeField = new JComboBox<>(types);
        typeField.addActionListener(editFormListener);
        formPanel.add(typeField);

        formPanel.add(new JLabel("Room: "));
        JTextField roomField = new JTextField();
        roomField.getDocument().addDocumentListener(editFormDocumentListener);
        formPanel.add(roomField);

        formPanel.add(new JLabel("Start: "));
        JFormattedTextField startField = new JFormattedTextField(dateFormatter);
        startField.getDocument().addDocumentListener(editFormDocumentListener);
        formPanel.add(startField);

        formPanel.add(new JLabel("End: "));
        JFormattedTextField endField = new JFormattedTextField(dateFormatter);
        endField.getDocument().addDocumentListener(editFormDocumentListener);
        formPanel.add(endField);

        formPanel.add(new JLabel("Password: "));
        JTextField passwordField = new JTextField();
        passwordField.getDocument().addDocumentListener(editFormDocumentListener);
        formPanel.add(passwordField);

        formPanel.add(new JLabel("Name: "));
        JTextField nameField = new JTextField();
        nameField.getDocument().addDocumentListener(editFormDocumentListener);
        formPanel.add(nameField);

        formPanel.add(new JLabel("Email: "));
        JTextField emailField = new JTextField();
        emailField.getDocument().addDocumentListener(editFormDocumentListener);
        emailField.setEnabled(false);
        formPanel.add(emailField);

        formPanel.add(new JLabel("Keep: "));
        String[] keepOptions = {"Default", "Yes", "No"};
        JComboBox<String> keepField = new JComboBox<>(keepOptions);
        keepField.addActionListener(editFormListener);
        formPanel.add(keepField);

        // add event to combo box
        typeField.addActionListener((e) ->
        {
            if(typeField.getSelectedItem().equals("zoom"))
            {
                passwordField.setEnabled(true);
                emailField.setEnabled(false);
            }
            else if(typeField.getSelectedItem().equals("webex"))
            {
                passwordField.setEnabled(false);
                emailField.setEnabled(true);
            }
        });


        gbc.weightx = 2;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        lowerPanel.add(formPanel, gbc);
        container.add(lowerPanel, "Center");

        JPanel editPanel = new JPanel();
        JButton addButton = new JButton("New");
        addButton.addActionListener((e) ->
        {
            if(isNewAdding)
            {
                return;
            }
            sectionList.clearSelection();
            if(sectionList.getSelectedIndex() == -1)
            {
                sectionField.setText("");
                typeField.setSelectedIndex(0);
                roomField.setText("");
                startField.setText("");
                endField.setText("");
                passwordField.setText("");
                nameField.setText("");
                emailField.setText("");
                keepField.setSelectedIndex(0);
                isNewAdding = true;
            }
        });
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener((e) ->
        {
            Section section;
            if(prevIndex == -1)
            {
                section = new Section();
            }
            else
            {
                section = dataManagementModel.getElementAt(prevIndex);
            }
            String sectionName = sectionField.getText();
            if(sectionName.equals(""))
            {
                JOptionPane.showMessageDialog(null, "Section name cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            section.setSectionName(sectionName);
            section.setType((String)typeField.getSelectedItem());
            section.setRoomInfo(roomField.getText());
            section.setStartTime(startField.getText());
            section.setEndTime(endField.getText());
            section.setPassword(passwordField.getText());
            section.setUsername(nameField.getText());
            section.setUseremail(emailField.getText());
            section.setKeep(keepField.getSelectedIndex());
            isEditing = false;
            isNewAdding = false;
            if(sectionList.getSelectedIndex() == -1)
            {
                dataManagementModel.addElement(section);
                sectionList.setSelectedIndex(dataManagementModel.getSize() - 1);
            }
            else
            {
                sectionList.repaint();
            }
        });
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener((e) ->
        {
            if(sectionList.getSelectedIndex() == -1)
            {
                return;
            }
            Section section = sectionList.getSelectedValue();
            int result = JOptionPane.showConfirmDialog(null, "Delete section " + section.getSectionName() + "?", "Warning", JOptionPane.YES_NO_OPTION);
            if(result == JOptionPane.YES_OPTION)
            {
                isEditing = false;
                dataManagementModel.removeElement(section);
                if(prevIndex > 0)
                {
                    sectionList.setSelectedIndex(prevIndex - 1);
                }
                else
                {
                    sectionList.setSelectedIndex(0);
                }
                prevIndex = sectionList.getSelectedIndex();
            }
        });
        editPanel.add(addButton);
        editPanel.add(saveButton);
        editPanel.add(deleteButton);

        
        // add event to section list
        sectionList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(isAdjusting)
                {
                    return;
                }
                if(e.getValueIsAdjusting()) {
                    return;
                }
                if(isEditing)
                {
                    // prompt to save or cancel
                    int result = JOptionPane.showConfirmDialog(null, "Unsaved changes, save?", "Warning", JOptionPane.YES_NO_CANCEL_OPTION);
                    if(result == JOptionPane.YES_OPTION)
                    {
                        saveButton.doClick();
                    }
                    else if(result == JOptionPane.NO_OPTION)
                    {

                    }
                    else
                    {
                        // restore selection
                        isAdjusting = true;
                        if(prevIndex != -1)
                        {
                            sectionList.setSelectedIndex(prevIndex);
                        }
                        else
                        {
                            sectionList.clearSelection();
                        }
                        isAdjusting = false;
                        return;
                    }
                }
                isNewAdding = false;
                prevIndex = sectionList.getSelectedIndex();
                Section section = sectionList.getSelectedValue();
                if (section == null) {
                    return;
                }
                sectionField.setText(section.getSectionName());
                typeField.setSelectedItem(section.getType());
                roomField.setText(section.getRoomInfo());
                startField.setText(section.getStartTime());
                endField.setText(section.getEndTime());
                passwordField.setText(section.getPassword());
                nameField.setText(section.getUsername());
                emailField.setText(section.getUseremail());
                keepField.setSelectedItem(section.getKeep());
                isEditing = false;
            }
        });

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