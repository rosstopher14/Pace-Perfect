package runTrackerv3;

import java.awt.EventQueue;

import javax.swing.ButtonGroup;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javax.swing.JLabel;
import javax.swing.JRadioButton;
import java.awt.Font;
import java.sql.Connection;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DisplaySecondWindow extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DisplaySecondWindow frame = new DisplaySecondWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	Connection conn = null;

	/**
	 * Create the frame.
	 */
	public DisplaySecondWindow() {
		conn = DbConnection.dbConnector();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 646, 437);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JRadioButton runRB = new JRadioButton("Add a run!");
		runRB.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				if(runRB.isSelected()) {
				
				}
			
			}
		});
		runRB.setFont(new Font("Tahoma", Font.PLAIN, 14));
		runRB.setBounds(409, 104, 109, 23);
		contentPane.add(runRB);
		
		JRadioButton progressRB = new JRadioButton("See your progress!");
		progressRB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		progressRB.setFont(new Font("Tahoma", Font.PLAIN, 14));
		progressRB.setBounds(409, 152, 148, 23);
		contentPane.add(progressRB);
		
		JRadioButton historyRB = new JRadioButton("Check your running history!");
		historyRB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		historyRB.setFont(new Font("Tahoma", Font.PLAIN, 14));
		historyRB.setBounds(409, 206, 197, 23);
		contentPane.add(historyRB);
		
		JRadioButton recRB = new JRadioButton("Test weekly recommendations.");
		recRB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		recRB.setFont(new Font("Tahoma", Font.PLAIN, 14));
		recRB.setBounds(409, 259, 215, 23);
		contentPane.add(recRB);
		
		JRadioButton weightRB = new JRadioButton("Change weight.");
		weightRB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			
				}
			});
		
		
		weightRB.setFont(new Font("Tahoma", Font.PLAIN, 14));
		weightRB.setBounds(409, 312, 128, 23);
		contentPane.add(weightRB);
		
		ButtonGroup G = new ButtonGroup();
		G.add(weightRB);
		G.add(recRB);
		G.add(historyRB);
		G.add(progressRB);
		G.add(runRB);
		
		JLabel lblNewLabel = new JLabel("Menu:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblNewLabel.setBounds(21, 35, 109, 53);
		contentPane.add(lblNewLabel);
		
		JFormattedTextField distTF = new JFormattedTextField();
		distTF.setBounds(21, 106, 87, 23);
		contentPane.add(distTF);
		
		JFormattedTextField timeTF = new JFormattedTextField();
		timeTF.setBounds(118, 106, 128, 23);
		contentPane.add(timeTF);
		
		JFormattedTextField dateTF = new JFormattedTextField();
		dateTF.setBounds(256, 106, 128, 23);
		contentPane.add(dateTF);
		
		JLabel lblNewLabel_1 = new JLabel("Distance (miles)");
		lblNewLabel_1.setBounds(21, 88, 87, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Time taken");
		lblNewLabel_2.setBounds(118, 88, 74, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Date (MM/DD/YYYY)");
		lblNewLabel_3.setBounds(256, 88, 128, 14);
		contentPane.add(lblNewLabel_3);
	}
}
	
