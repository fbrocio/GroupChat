package gui;

import javax.swing.*;
import java.awt.*;

public class Login extends JPanel {

    private JLabel lblTitle;
    private JLabel lblUser;
    private JLabel lblPassword;
    private JTextField txtUser;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnRegister;

    public Login() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // TÍTULO
        lblTitle = new JLabel("Iniciar sesión", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Dialog", Font.BOLD, 20));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(lblTitle, gbc);

        // USUARIO
        lblUser = new JLabel("Usuario");
        lblUser.setFont(new Font("Dialog", Font.PLAIN, 12));

        gbc.gridy++;
        gbc.gridwidth = 2;
        add(lblUser, gbc);

        txtUser = new JTextField(20);
        txtUser.setToolTipText("Introduce tu usuario");

        gbc.gridy++;
        add(txtUser, gbc);

        // CONTRASEÑA
        lblPassword = new JLabel("Contraseña");
        lblPassword.setFont(new Font("Dialog", Font.PLAIN, 12));

        gbc.gridy++;
        add(lblPassword, gbc);

        txtPassword = new JPasswordField(20);
        txtPassword.setToolTipText("Introduce tu contraseña");

        gbc.gridy++;
        add(txtPassword, gbc);

        // BOTÓN ENTRAR
        btnLogin = new JButton("Entrar");
        btnLogin.setPreferredSize(new Dimension(120, 35));

        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        add(btnLogin, gbc);
        
        //BOTÓN REGISTRAR
        btnRegister = new JButton("Registrar");
        btnRegister.setPreferredSize(new Dimension(120,35));
        gbc.gridy++;
        add(btnRegister, gbc);
    }

    // GETTERS (para la lógica)

    public String getUsername() {
        return txtUser.getText().trim();
    }

    public String getPassword() {
        return new String(txtPassword.getPassword());
    }

    public void addLoginListener(Runnable action) {
        btnLogin.addActionListener(e -> action.run());
    }
    
    public void addRegisterListener(Runnable action) {
    	btnRegister.addActionListener(e-> action.run());
    }
}
