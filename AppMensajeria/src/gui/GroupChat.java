package gui;

import javax.swing.*;

import cliente.MessageObserver;
import controller.ChatController;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GroupChat extends JPanel implements MessageObserver {

    // Componentes
    private JButton btnExit;

    private JTextField txtSearch;
    private JButton btnClear;

    private JTextArea chatArea;
    private JScrollPane chatScroll;

    private JTextArea inputArea;
    private JScrollPane inputScroll;
    private JButton btnSend;

    private ChatController controller;

    // Historial local de mensajes (para búsqueda)
    private List<String> messageHistory = new ArrayList<>();

    public GroupChat() {
        initComponents();
    }

    @Override
    public void recibirMensaje(String message) {
    	String[] parts = message.split("\\|", 3);
        if (parts.length != 3) {
        	 return;
        }

        String user = parts[0]; // nombre de usuario
        LocalDateTime dateTime = LocalDateTime.parse(parts[1]);
        String messageText = parts[2];

        // Formato de fecha para mostrar: dd/MM/yyyy HH:mm
        String formattedTime = dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        // Muestra el mensaje en el chat
        addMessage(user, messageText, formattedTime);
    }

    public void setController(ChatController controller) {
        this.controller = controller;
    }

    private void initComponents() {
        setLayout(new BorderLayout(5, 5));

        
        // PANEL SUPERIOR
        JPanel topPanel = new JPanel(new BorderLayout());


        // Panel de búsqueda
        JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
        txtSearch = new JTextField();
        txtSearch.addActionListener(e -> filterMessages());

        btnClear = new JButton("Limpiar chat");
        btnClear.addActionListener(e -> clearChat());
        
        btnExit = new JButton("Salir");
        btnExit.addActionListener(e -> onExit());

        searchPanel.add(new JLabel("Buscar usuario:"), BorderLayout.WEST);
        searchPanel.add(txtSearch, BorderLayout.CENTER);
        searchPanel.add(btnClear, BorderLayout.EAST);

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(topPanel, BorderLayout.NORTH);
        northPanel.add(searchPanel, BorderLayout.SOUTH);

        // PANEL CENTRAL (CHAT)
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);

        chatScroll = new JScrollPane(chatArea);
        chatScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // PANEL INFERIOR (INPUT)
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));

        inputArea = new JTextArea(3, 20);
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);

        inputScroll = new JScrollPane(inputArea);
        inputScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        btnSend = new JButton("Enviar");
        btnSend.addActionListener(e -> onSend());

        bottomPanel.add(inputScroll, BorderLayout.CENTER);
        bottomPanel.add(btnSend, BorderLayout.EAST);
        
        // AÑADIR TODO
        add(northPanel, BorderLayout.NORTH);
        add(chatScroll, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // MÉTODOS PÚBLICOS

    /**
     * Añade un mensaje al chat y al historial (seguro para hilos)
     */
    public void addMessage(String user, String message, String time) {
        SwingUtilities.invokeLater(() -> {
            String fullMessage = "[" + time + "] " + user + ": " + message;
            messageHistory.add(fullMessage);
            chatArea.append(fullMessage + "\n");
            chatArea.setCaretPosition(chatArea.getDocument().getLength());
        });
    }

    public void showError(String mensaje) {
        SwingUtilities.invokeLater(() ->
            JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE)
        );
    }

    // FUNCIONALIDAD EXTRA

    private void filterMessages() {
        String filter = txtSearch.getText().trim().toLowerCase();
        chatArea.setText("");

        for (String msg : messageHistory) {
            int startIdx = msg.indexOf("] ");
            int endIdx = msg.indexOf(":", startIdx);
            if (startIdx != -1 && endIdx != -1) {
                String userPart = msg.substring(startIdx + 2, endIdx).toLowerCase();
                if (userPart.contains(filter)) {
                    chatArea.append(msg + "\n");
                }
            }
        }
    }

    private void clearChat() {
        chatArea.setText("");
        messageHistory.clear();
    }

    // EVENTOS

    private void onSend() {
        String text = inputArea.getText().trim();

        if (text.isEmpty()) {
            return;
        }

        if (controller != null) {
        	String alias = controller.getUsuarioAlias();
            controller.sendMessage(text, alias);
        }

        inputArea.setText("");
    }

    private void onExit() {
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window != null) {
            window.dispose();
        }
    }
}
