package gui;

import javax.swing.*;
import java.awt.*;

public class GroupLogin extends JPanel {

    private JLabel lblTitle;
    private JLabel lblGroupCode;
    private JTextField txtGroupCode;
    private JButton btnJoin;

    public GroupLogin() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // TÍTULO
        lblTitle = new JLabel("Unirse a grupo", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Dialog", Font.BOLD, 20));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(lblTitle, gbc);

        // CÓDIGO DE GRUPO
        lblGroupCode = new JLabel("Código de grupo");
        lblGroupCode.setFont(new Font("Dialog", Font.PLAIN, 12));

        gbc.gridy++;
        add(lblGroupCode, gbc);

        txtGroupCode = new JTextField(20);
        txtGroupCode.setToolTipText("Introduce el código de grupo");

        gbc.gridy++;
        add(txtGroupCode, gbc);

        // BOTÓN UNIRSE
        btnJoin = new JButton("Unirse");
        btnJoin.setPreferredSize(new Dimension(120, 35));

        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        add(btnJoin, gbc);
    }

    // GETTERS / LISTENERS
    public String getGroupCode() {
        return txtGroupCode.getText().trim();
    }

    public void addJoinListener(Runnable action) {
        btnJoin.addActionListener(e -> action.run());
    }

    // Método de preview para probar la UI
    public void preview() {
        JFrame frame = new JFrame("Unirse a grupo");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 250);
        frame.setLocationRelativeTo(null);
        frame.add(this);
        frame.setVisible(true);
    }
}
