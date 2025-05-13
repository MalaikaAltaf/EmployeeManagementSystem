package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ChatView extends JPanel {
    public JTextArea chatArea;
    public JTextField inputField;
    public JButton sendButton;
    private JPanel messagePanel; // Panel for displaying messages

    public ChatView() {
        // Set up layout with CardLayout
        setLayout(new BorderLayout());

        // Create chat area (read-only)
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        // Panel to hold chat messages
        messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS)); // Vertical stack for messages
        scrollPane.setViewportView(messagePanel); // Attach message panel to scroll

        // Panel for input and send button
        JPanel bottomPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        sendButton = new JButton("Send");

        // Set button color to match the theme (assuming dark mode for example)
        sendButton.setBackground(new Color(34, 45, 55)); // Dark color
        sendButton.setForeground(Color.WHITE);

        // Add action listener to send button
        sendButton.addActionListener(e -> sendMessage());

        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        // Add components to main panel
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Method to handle sending messages
    private void sendMessage() {
        String message = inputField.getText().trim();
        if (!message.isEmpty()) {
            // Display the message in the chat area (left side for user)
            JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            messagePanel.setBackground(new Color(240, 240, 240)); // Light background color for user message

            JTextArea messageArea = new JTextArea(message);
            messageArea.setEditable(false);
            messageArea.setBackground(new Color(240, 240, 240));
            messageArea.setLineWrap(true);
            messageArea.setWrapStyleWord(true);
            messagePanel.add(messageArea);

            // Add the message panel to the scrollable area
            this.messagePanel.add(messagePanel);
            this.messagePanel.revalidate();
            this.messagePanel.repaint();

            inputField.setText(""); // Clear the input field
        }
    }
}
