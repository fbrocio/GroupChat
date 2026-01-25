package cliente;

import gui.*;
import controller.*;
import persistencia.GrupoDAO;
import modelo.Grupo;
import modelo.Usuario;

import java.sql.SQLException;

import javax.swing.*;

/**
 * Punto de entrada principal de la aplicación cliente.
 * Gestiona todo el flujo de la interfaz gráfica: login, registro, selección de grupo y chat
 * 
 * Coordina los controladores (LoginController, ChatController) y DAOs necesarios
 */
public class ClienteApp {
	
	/**
	 * Método principal de la aplicación
	 * Inicia la interfaz gráfica en el hilo correcto de Swing
	 * 
	 * @param args
	 */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginController loginController = new LoginController();
            GrupoDAO grupoDAO = new GrupoDAO();

            // Ventana de login
            Login loginPanel = new Login();
            loginPanel.addRegisterListener(() -> {
                JFrame frame = new JFrame("Registrar usuario");
                RegisterPanel registerPanel = new RegisterPanel();
                frame.add(registerPanel);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                
                //Listener del botón "Registrarse"
                registerPanel.addRegisterListener(() -> {
                    String usuario = registerPanel.getUsername();
                    String password = registerPanel.getPassword();
                               
                    boolean registrado = loginController.register(usuario, password);
                    if (registrado) {
                        JOptionPane.showMessageDialog(frame, "Usuario registrado correctamente");
                        frame.dispose(); // cerrar ventana de registro
                    } else {
                        JOptionPane.showMessageDialog(frame, "Error: alias ya existe o fallo en la BD");
                    }
                });
                
                //Listener del botón "Cancelar"
                registerPanel.addCancelListener(frame::dispose);
            });

            // JFrame de login
            JFrame loginFrame = new JFrame("Iniciar sesión");
            loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            loginFrame.setContentPane(loginPanel);
            loginFrame.setSize(400, 300);
            loginFrame.setLocationRelativeTo(null);
            loginFrame.setVisible(true);

            //Listener del botón "Iniciar sesión"
            loginPanel.addLoginListener(() -> {
                String usuario = loginPanel.getUsername();
                String password = loginPanel.getPassword();

                Usuario u = loginController.login(usuario, password);

                if (u != null) {
                    // Login correcto - abrir panel de selección de grupo
                    GroupLogin groupPanel = new GroupLogin();
                    JFrame groupFrame = new JFrame("Seleccionar grupo");
                    groupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    groupFrame.setContentPane(groupPanel);
                    groupFrame.setSize(400, 250);
                    groupFrame.setLocationRelativeTo(null);
                    groupFrame.setVisible(true);

                    // Listener del botón "Unirse al grupo"
                    groupPanel.addJoinListener(() -> {
                        String codigoGrupo = groupPanel.getGroupCode();
                        Grupo grupo = null;
						try {
							grupo = grupoDAO.obtenerGrupoPorClave(codigoGrupo);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

                        if (grupo != null) {
                            // El usuario decide si cargar historial 
                            int resp = JOptionPane.showConfirmDialog(null, "¿Desea cargar el historial de mensajes?", 
                                                                   "Historial", JOptionPane.YES_NO_OPTION);
                            boolean cargar = (resp == JOptionPane.YES_OPTION);

                            GroupChat chatGUI = new GroupChat();
                            ChatController chatController = new ChatController(chatGUI);
                            chatGUI.setController(chatController);
                            
                            // Pasamos usuario (u), grupo y la elección del historial
                            chatController.conectar("localhost", grupo.getPuerto(), u, grupo, cargar);

                            // Mostrar ventana del chat
                            JFrame chatFrame = new JFrame("Chat de " + grupo.getNombre());
                            chatFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            chatFrame.setContentPane(chatGUI);
                            chatFrame.setSize(500, 500);
                            chatFrame.setLocationRelativeTo(null);
                            chatFrame.setVisible(true);
                            
                            // Cerrar ventanas anteriores
                            groupFrame.dispose(); 
                            loginFrame.dispose(); 
                        } else {
                            JOptionPane.showMessageDialog(groupFrame, "Código de grupo incorrecto");
                        }
                    });

                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Usuario o contraseña incorrectos");
                }
            });
        });
    }
}
