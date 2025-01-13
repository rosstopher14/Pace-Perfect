package runTrackerv3;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


import javax.swing.JFormattedTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;


public final class PPGUI {

	private JFrame frame;

	/**
	 * Launch the application.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		EventQueue.invokeLater(() -> {
				try {
					PPGUI window = PPGUI.createInstance();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
		});
	}
	
	Connection conn = null;

	/**
	 * Create the application.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public PPGUI() {}

	public static PPGUI createInstance() throws ClassNotFoundException, SQLException {
		PPGUI instance = new PPGUI();
		instance.initialize();
		instance.conn = DbConnection.dbConnector();
		return instance;
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	private void initialize() throws ClassNotFoundException, SQLException {
		
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JFormattedTextField userIDField = new JFormattedTextField();
		userIDField.setBounds(98, 85, 132, 20);
		frame.getContentPane().add(userIDField);
		
		JFormattedTextField passwordField = new JFormattedTextField();
		passwordField.setBounds(98, 133, 132, 20);
		frame.getContentPane().add(passwordField);
		
		
		
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				PreparedStatement pst = null;
				ResultSet rs = null;

	                try {
	                	String query = "SELECT * FROM HASH WHERE username=? and password=?";
	                    pst = conn.prepareStatement(query);
	                    pst.setString(1, userIDField.getText());
	                    pst.setString(2, passwordField.getText());
	                    
	                    rs = pst.executeQuery();
	                    int count = 0;
	                    while(rs.next()) {
	                    	count++;
	                    }
	                    if(count == 1) {
	                    	JOptionPane.showMessageDialog(null, "Username and password are correct.");
	                    	frame.dispose();
	                    	DisplaySecondWindow second = new DisplaySecondWindow();
	                    	second.setVisible(true);
	                    }
	                    else if (count >1) {
	                    	JOptionPane.showMessageDialog(null, "Duplicate Username and password");
	                    }
	                    else {
	                    	JOptionPane.showMessageDialog(null, "Username and password are not correct.");
	                    }

	                } catch (SQLException e1) {
	                    e1.printStackTrace();
	                } finally {
						try {
							if (pst != null) {
								pst.close();
							}
							if (rs != null) {
								rs.close();
							}
						} catch (SQLException e1) {
							throw new RuntimeException(e1);
						}

					}
	                }
			
		});
		loginButton.setBounds(70, 190, 89, 23);
		frame.getContentPane().add(loginButton);
		
		JLabel lblNewLabel = new JLabel("User ID:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 86, 78, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPassword.setBounds(10, 134, 78, 14);
		frame.getContentPane().add(lblPassword);
		
		JLabel lblNewLabel_1 = new JLabel("Welcome to Pace Perfect!");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(10, 31, 359, 43);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel imageLabel = new JLabel("");
		Image image = new ImageIcon(this.getClass().getResource("/pacepr.jpg")).getImage();
		Image newImage = image.getScaledInstance(168, 175, Image.SCALE_DEFAULT);
		imageLabel.setIcon(new ImageIcon(newImage));
		imageLabel.setBounds(242, 38, 169, 175);
		frame.getContentPane().add(imageLabel);
		
	}
	
	
	
	
}
