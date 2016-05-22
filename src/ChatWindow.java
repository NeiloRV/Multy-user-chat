import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JList;

public class ChatWindow {

	JFrame frame;
	ClientDataStart newClient;
	 TextArea messageField;
	private TextArea newMessageField;
	private JLabel lblInsertYourNew;
	private JLabel lblMessageHistory;
	private JButton btnSend;
	private ClientEngine clientEngine;
	private JLabel lblNewLabel;
	private JList guestList;
	private JTextField nicTextField;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JList<String> listOfGuests;
	private DefaultListModel <String> listModel = new DefaultListModel<>();
	
	public ChatWindow (ClientDataStart clData) {
		newClient = clData;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					initialize();
					setConnection();
				} catch (Exception e) {
					e.printStackTrace();
				}
//				finally{
//					clientEngine.sendMessage("exit");
//				}
			}
		});
	}

	private void setConnection(){
		clientEngine = new ClientEngine(newClient, this);
	}
	
	public void addToGuestList(String guestNicName){
		listModel.addElement(guestNicName);
		frame.repaint();
	}
	
	public void removeFromGuestList(String guestNicName){
		listModel.removeElement(guestNicName);
		frame.repaint();
	}
	
	private void sendMessage(){
		String str = newMessageField.getText();
		clientEngine.sendMessage(str);
		newMessageField.setText ("");
	}
	
	private void initialize() {
		
		frame = new JFrame("Neilo chat");
		frame.setBounds(100, 100, 480, 400);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent arg0) {}
			
			@Override
			public void windowIconified(WindowEvent arg0) {}
			
			@Override
			public void windowDeiconified(WindowEvent arg0) {}
			
			@Override
			public void windowDeactivated(WindowEvent arg0) {}
			
			@Override
			public void windowClosing(WindowEvent arg0) {
				clientEngine.sendMessage("exit");
			}
			
			@Override
			public void windowClosed(WindowEvent arg0) {}
			
			@Override
			public void windowActivated(WindowEvent arg0) {}
		});
		frame.getContentPane().setLayout(null);
		
		messageField = new TextArea("Wellcome to NeiloChat", 20, 10, 1);
		messageField.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		messageField.setBounds(10, 33, 316, 204);
		frame.getContentPane().add(messageField);
		
		newMessageField = new TextArea("",20,4,1);
		newMessageField.setBounds(10, 265, 316, 61);
		frame.getContentPane().add(newMessageField);
		newMessageField.setColumns(10);
		newMessageField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					newMessageField.setText(newMessageField.getText().substring(0, newMessageField.getText().length()-1));
					sendMessage();
				}
			}
		});
		
		lblInsertYourNew = new JLabel("Insert your  new message:");
		lblInsertYourNew.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblInsertYourNew.setBounds(10, 243, 200, 16);
		frame.getContentPane().add(lblInsertYourNew);
		
		lblMessageHistory = new JLabel("Message history:");
		lblMessageHistory.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblMessageHistory.setBounds(10, 11, 200, 24);
		frame.getContentPane().add(lblMessageHistory);
		
		btnSend = new JButton("Send");
		btnSend.setBounds(220, 332, 101, 24);
		frame.getContentPane().add(btnSend);
		btnSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sendMessage();
			}
		});
		
		// field of current date and time
		lblNewLabel = new JLabel(DateFormat.getDateTimeInstance().format(new Date()));
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblNewLabel.setBounds(220, 11, 101, 16);
		frame.getContentPane().add(lblNewLabel);
		
		nicTextField = new JTextField();
		nicTextField.setBounds(332, 33, 122, 25);
		frame.getContentPane().add(nicTextField);
		nicTextField.setColumns(10);
		nicTextField.setText(newClient.getNicName());
		
		lblNewLabel_1 = new JLabel("  Your NICname:");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(331, 11, 102, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("Avialable guests:");
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(342, 65, 101, 24);
		frame.getContentPane().add(lblNewLabel_2);
		
		// List of guests		
		listOfGuests = new JList<>(listModel);
		listOfGuests.setBounds(333, 95, 121, 231);
		frame.getContentPane().add(listOfGuests);
		
		
		frame.setVisible(true);
		frame.repaint();
	}
}
