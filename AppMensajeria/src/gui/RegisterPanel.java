package gui;

import javax.swing.*;
import java.awt.*;

public class RegisterPanel extends JPanel {

    private JTextField txtUser;
    private JPasswordField txtPassword;
    private JButton btnRegister;
    private JButton btnCancel;

    public RegisterPanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitle = new JLabel("Registrar nuevo usuario", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Dialog", Font.BOLD, 20));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(lblTitle, gbc);

        // Usuario
        JLabel lblUser = new JLabel("Usuario:");
        gbc.gridy++; gbc.gridwidth = 1;
        add(lblUser, gbc);

        txtUser = new JTextField(20);
        gbc.gridx = 1;
        add(txtUser, gbc);
        
        // Contraseña
        JLabel lblPass = new JLabel("Contraseña:");
        gbc.gridx = 0; gbc.gridy++; 
        add(lblPass, gbc);

        txtPassword = new JPasswordField(20);
        gbc.gridx = 1;
        add(txtPassword, gbc);

        // Botones
        btnRegister = new JButton("Registrar");
        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 1;
        add(btnRegister, gbc);

        btnCancel = new JButton("Cancelar");
        gbc.gridx = 1;
        add(btnCancel, gbc);
    }

    public String getUsername() {
        return txtUser.getText().trim();
    }

    public String getPassword() {
        return new String(txtPassword.getPassword());
    }

    public void addRegisterListener(Runnable action) {
        btnRegister.addActionListener(e -> action.run());
    }

    public void addCancelListener(Runnable action) {
        btnCancel.addActionListener(e -> action.run());
    }
}
