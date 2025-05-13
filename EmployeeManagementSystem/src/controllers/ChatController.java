// ChatController.java
package controllers;

import views.ChatView;

import javax.swing.*;
import java.io.*;
import java.net.*;

public class ChatController {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private ChatView view;

    public ChatController(ChatView view, String username) {
        this.view = view;
        try {
            socket = new Socket("localhost", 12345);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Send initial login message
            out.println(username + " has joined the chat.");

            // Start listener thread
            new Thread(this::listenForMessages).start();

            view.sendButton.addActionListener(e -> sendMessage(username));
            view.inputField.addActionListener(e -> sendMessage(username));

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to connect to chat server.");
            e.printStackTrace();
        }
    }

    private void sendMessage(String username) {
        String message = view.inputField.getText().trim();
        if (!message.isEmpty()) {
            out.println(username + ": " + message);
            view.inputField.setText("");
        }
    }

    private void listenForMessages() {
        try {
            String msg;
            while ((msg = in.readLine()) != null) {
                view.chatArea.append(msg + "\n");
            }
        } catch (IOException e) {
            System.out.println("Connection to server lost.");
        }
    }
}