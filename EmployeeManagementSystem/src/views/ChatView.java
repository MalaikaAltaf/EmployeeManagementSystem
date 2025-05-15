package views;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class ChatView extends JPanel {
    public JTextField inputField;
    public JButton sendButton;
    private JPanel messagePanel; // Panel for displaying messages

    public ChatView() {
        // Set up layout with BorderLayout
        setLayout(new BorderLayout());

        // Panel to hold chat messages
        messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS)); // Vertical stack for messages
        JScrollPane scrollPane = new JScrollPane(messagePanel);

        // Panel for input and send button
        JPanel bottomPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        sendButton = new JButton("Send");

        // Improve input field styling
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        inputField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(34, 45, 55), 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        // Improve send button styling
        sendButton.setBackground(new Color(0, 123, 255)); // Bootstrap primary blue
        sendButton.setForeground(Color.WHITE);
        sendButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        sendButton.setFocusPainted(false);
        sendButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        sendButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        // Add components to main panel
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Public method to add a message to the chat panel
    public void addMessage(String message, boolean isSentByUser) {
        JPanel messageBubble = new RoundedPanel(isSentByUser ? new Color(179, 229, 252) : new Color(220, 220, 220));
        messageBubble.setLayout(new BorderLayout());
        messageBubble.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JTextArea messageArea = new JTextArea(message);
        messageArea.setEditable(false);
        messageArea.setBackground(isSentByUser ? new Color(179, 229, 252) : new Color(220, 220, 220));
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        messageArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        messageBubble.add(messageArea, BorderLayout.CENTER);

        // Reduce gap between messages by setting margin and alignment
        JPanel wrapper = new JPanel(new FlowLayout(isSentByUser ? FlowLayout.RIGHT : FlowLayout.LEFT, 5, 0));
        wrapper.setOpaque(false);
        wrapper.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0)); // Add vertical padding to reduce gap
        wrapper.add(messageBubble);

        this.messagePanel.add(wrapper);
        this.messagePanel.revalidate();
        this.messagePanel.repaint();
    }

    // Custom JPanel with rounded corners for message bubbles
    private static class RoundedPanel extends JPanel {
        private Color backgroundColor;

        public RoundedPanel(Color bgColor) {
            super();
            backgroundColor = bgColor;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Dimension arcs = new Dimension(15, 15);
            int width = getWidth();
            int height = getHeight();
            Graphics2D graphics = (Graphics2D) g;
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draws the rounded panel with background color
            graphics.setColor(backgroundColor);
            graphics.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
        }
    }
}
