package Task3;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Task3_GUI extends JFrame {
    private StudentManagementSystem sms = new StudentManagementSystem();
    private JTextField nameField;
    private JTextField rollNumberField;
    private JTextField gradeField;
    private DefaultTableModel tableModel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Task3_GUI gui = new Task3_GUI();
            gui.setVisible(true);
        });
    }

    public Task3_GUI() {
        setTitle("Student Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(600, 400));
        setLayout(new BorderLayout());

        // Create components
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(20);
        JLabel rollNumberLabel = new JLabel("Roll Number:");
        rollNumberField = new JTextField(10);
        JLabel gradeLabel = new JLabel("Grade:");
        gradeField = new JTextField(5);

        JButton addButton = new JButton("Add");
        JButton removeButton = new JButton("Remove");
        JButton searchButton = new JButton("Search");
        JButton displayButton = new JButton("Display All");

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Name");
        tableModel.addColumn("Roll Number");
        tableModel.addColumn("Grade");
        JTable studentTable = new JTable(tableModel);

        // Set button actions
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                int rollNumber = Integer.parseInt(rollNumberField.getText());
                String grade = gradeField.getText();
                sms.addStudent(name, rollNumber, grade);
                clearFields();
                updateStudentTable();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = studentTable.getSelectedRow();
                if (selectedIndex != -1) {
                    int rollNumber = (int) studentTable.getValueAt(selectedIndex, 1);
                    sms.removeStudent(rollNumber);
                    updateStudentTable();
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rollNumber = Integer.parseInt(rollNumberField.getText());
                Student student = sms.searchStudent(rollNumber);
                if (student != null) {
                    int rowIndex = findStudentRowIndex(studentTable, rollNumber);
                    if (rowIndex != -1) {
                        studentTable.setRowSelectionInterval(rowIndex, rowIndex);
                        studentTable.scrollRectToVisible(studentTable.getCellRect(rowIndex, 0, true));
                    }
                } else {
                    JOptionPane.showMessageDialog(Task3_GUI.this,
                            "Student with roll number " + rollNumber + " not found.",
                            "Student Not Found",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStudentTable();
            }
        });

        // Create layout
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(rollNumberLabel);
        inputPanel.add(rollNumberField);
        inputPanel.add(gradeLabel);
        inputPanel.add(gradeField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(displayButton);

        JScrollPane tableScrollPane = new JScrollPane(studentTable);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(tableScrollPane, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private void clearFields() {
        nameField.setText("");
        rollNumberField.setText("");
        gradeField.setText("");
    }

    private void updateStudentTable() {
        tableModel.setRowCount(0);
        for (Student student : sms.getAllStudents()) {
            Object[] rowData = {student.getName(), student.getRollNumber(), student.getGrade()};
            tableModel.addRow(rowData);
        }
    }

    private int findStudentRowIndex(JTable table, int rollNumber) {
        for (int i = 0; i < table.getRowCount(); i++) {
            int currentRollNumber = (int) table.getValueAt(i, 1);
            if (currentRollNumber == rollNumber) {
                return i;
            }
        }
        return -1;
    }
}

