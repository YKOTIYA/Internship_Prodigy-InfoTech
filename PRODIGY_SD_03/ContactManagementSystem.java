import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ContactManagementSystem extends JFrame implements ActionListener {
    private List<Contact> contacts;
    private JTable table;
    private DefaultTableModel model;

    public ContactManagementSystem() {
        setTitle("Contact Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        contacts = new ArrayList<>();
        loadContacts();

        JPanel panel = new JPanel(new BorderLayout());

        // Table
        String[] columnNames = {"Name", "Phone", "Email"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton addButton = new JButton("Add");
        addButton.addActionListener(this);
        buttonPanel.add(addButton);

        JButton editButton = new JButton("Edit");
        editButton.addActionListener(this);
        buttonPanel.add(editButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this);
        buttonPanel.add(deleteButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().add(panel);
        setVisible(true);
        populateTable();
    }

    @SuppressWarnings("unchecked")
    private void loadContacts() {
        try {
            FileInputStream fis = new FileInputStream("contacts.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            contacts = (List<Contact>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            // File not found or other error, start with an empty list
            contacts = new ArrayList<>();
        }
    }

    private void saveContacts() {
        try {
            FileOutputStream fos = new FileOutputStream("contacts.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(contacts);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void populateTable() {
        model.setRowCount(0);
        for (Contact contact : contacts) {
            model.addRow(new Object[]{contact.getName(), contact.getPhone(), contact.getEmail()});
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        switch (action) {
            case "Add":
                addContact();
                break;
            case "Edit":
                editContact();
                break;
            case "Delete":
                deleteContact();
                break;
        }
    }

    private void addContact() {
        String name = JOptionPane.showInputDialog(this, "Enter name:");
        if (name == null || name.trim().isEmpty()) {
            return;
        }

        String phone = JOptionPane.showInputDialog(this, "Enter phone:");
        if (phone == null || phone.trim().isEmpty()) {
            return;
        }

        String email = JOptionPane.showInputDialog(this, "Enter email:");
        if (email == null || email.trim().isEmpty()) {
            return;
        }

        Contact contact = new Contact(name, phone, email);
        contacts.add(contact);
        populateTable();
        saveContacts();
    }

    private void editContact() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a contact to edit.");
            return;
        }

        String name = (String) table.getValueAt(selectedRow, 0);
        String phone = (String) table.getValueAt(selectedRow, 1);
        String email = (String) table.getValueAt(selectedRow, 2);

        String newName = JOptionPane.showInputDialog(this, "Enter new name:", name);
        if (newName == null || newName.trim().isEmpty()) {
            return;
        }

        String newPhone = JOptionPane.showInputDialog(this, "Enter new phone:", phone);
        if (newPhone == null || newPhone.trim().isEmpty()) {
            return;
        }

        String newEmail = JOptionPane.showInputDialog(this, "Enter new email:", email);
        if (newEmail == null || newEmail.trim().isEmpty()) {
            return;
        }

        Contact updatedContact = new Contact(newName, newPhone, newEmail);
        contacts.set(selectedRow, updatedContact);
        populateTable();
        saveContacts();
    }

    private void deleteContact() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a contact to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this contact?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            contacts.remove(selectedRow);
            populateTable();
            saveContacts();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ContactManagementSystem();
            }
        });
    }
}

class Contact implements Serializable {
    private String name;
    private String phone;
    private String email;

    public Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}