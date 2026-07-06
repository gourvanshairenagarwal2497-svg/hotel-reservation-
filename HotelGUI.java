import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class HotelGUI extends JFrame implements ActionListener {

    JTextField nameField, roomField;
    JComboBox<String> paymentBox;
    JButton bookBtn, cancelBtn, viewBtn;
    JTable table;
    DefaultTableModel model;
    String bookings = "";

    public HotelGUI() {

        setTitle("Hotel Reservation System");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new GridLayout(3, 2, 10, 10));

        top.add(new JLabel("Customer Name"));
        nameField = new JTextField();
        top.add(nameField);

        top.add(new JLabel("Room Number"));
        roomField = new JTextField();
        top.add(roomField);

        top.add(new JLabel("Payment"));
        paymentBox = new JComboBox<>();
        paymentBox.addItem("Cash");
        paymentBox.addItem("Card");
        paymentBox.addItem("UPI");
        top.add(paymentBox);

        add(top, BorderLayout.NORTH);

        String col[] = { "Room No", "Category", "Price", "Status" };

        model = new DefaultTableModel(col, 0);

        model.addRow(new Object[] { 101, "Standard", 1500, "Available" });
        model.addRow(new Object[] { 102, "Standard", 1500, "Available" });
        model.addRow(new Object[] { 201, "Deluxe", 2500, "Available" });
        model.addRow(new Object[] { 202, "Deluxe", 2500, "Available" });
        model.addRow(new Object[] { 301, "Suite", 5000, "Available" });

        table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel();

        bookBtn = new JButton("Book Room");
        cancelBtn = new JButton("Cancel Booking");
        viewBtn = new JButton("View Bookings");

        bottom.add(bookBtn);
        bottom.add(cancelBtn);
        bottom.add(viewBtn);

        add(bottom, BorderLayout.SOUTH);

        bookBtn.addActionListener(this);
        cancelBtn.addActionListener(this);
        viewBtn.addActionListener(this);

        setVisible(true);
    }

    // ---------- BOOK ROOM ----------
    private void bookRoom() {

        String name = nameField.getText().trim();
        String room = roomField.getText().trim();

        if (name.isEmpty() || room.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter Name and Room Number");
            return;
        }

        for (int i = 0; i < model.getRowCount(); i++) {

            if (model.getValueAt(i, 0).toString().equals(room)) {

                if (model.getValueAt(i, 3).equals("Booked")) {

                    JOptionPane.showMessageDialog(this,
                            "Room Already Booked!");
                    return;

                }

                model.setValueAt("Booked", i, 3);

                bookings += "Customer : " + name
                        + "\nRoom : " + room
                        + "\nCategory : " + model.getValueAt(i, 1)
                        + "\nPrice : ₹" + model.getValueAt(i, 2)
                        + "\nPayment : " + paymentBox.getSelectedItem()
                        + "\n-----------------------------\n";

                JOptionPane.showMessageDialog(this,
                        "Booking Successful!");

                nameField.setText("");
                roomField.setText("");

                return;
            }

        }

        JOptionPane.showMessageDialog(this,
                "Invalid Room Number");

    }

    // ---------- CANCEL BOOKING ----------
    private void cancelBooking() {

        String room = roomField.getText().trim();

        if (room.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Enter Room Number");
            return;
        }

        for (int i = 0; i < model.getRowCount(); i++) {

            if (model.getValueAt(i, 0).toString().equals(room)) {

                model.setValueAt("Available", i, 3);

                JOptionPane.showMessageDialog(this,
                        "Booking Cancelled!");

                return;
            }

        }

        JOptionPane.showMessageDialog(this,
                "Invalid Room Number");

    }

    // ---------- VIEW BOOKINGS ----------
    private void viewBookings() {

        if (bookings.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "No Bookings Yet");

        } else {

            JTextArea area = new JTextArea(bookings);

            area.setEditable(false);

            JOptionPane.showMessageDialog(this,
                    new JScrollPane(area),
                    "Booking Details",
                    JOptionPane.INFORMATION_MESSAGE);

        }

    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new HotelGUI());

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == bookBtn) {

            bookRoom();

        } else if (e.getSource() == cancelBtn) {

            cancelBooking();

        } else if (e.getSource() == viewBtn) {

            viewBookings();

        }

    }

}