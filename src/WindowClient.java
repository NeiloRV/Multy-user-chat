import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;

public class WindowClient {

	private JFrame frame;
	private JTextField nic;
	private JTextField ip;
	private JTextField port;
	ClientDataStart newClient;

	public WindowClient() {
		initializeFirstWindow();
	}
	
	private void apply(){
		newClient = new ClientDataStart();
		if(nic.getText().equals("")){
			//newClient.setNicName(""+newClient.hashCode());
			newClient.setNicName("Roman");
		}else {newClient.setNicName(nic.getText());}
		
		if (ip.getText().equals("")){
			newClient.setIpClient("localhost"); 
		} else {newClient.setIpClient(ip.getText());}
		
		if (port.getText().equals("")){newClient.setPort(8181);}
		else {newClient.setPort(Integer.parseInt(port.getText()));}
		
		ChatWindow newClientWindow = new ChatWindow(newClient); 
		frame.dispose();
	}

	private void initializeFirstWindow() {
		frame = new JFrame("Wellcome window");
		frame.setVisible(true);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
				
		nic = new JTextField();
		nic.setHorizontalAlignment(SwingConstants.LEFT);
		nic.setBounds(190, 30, 221, 35);
		nic.setVisible(true);
		frame.getContentPane().add(nic);
		nic.setColumns(10);
		nic.addKeyListener((KeyListener) new KeyAdapter() {
			public void keyReleased (KeyEvent e){
				
				int key = e.getKeyCode(); 
				if(key == KeyEvent.VK_ENTER){
					apply();
				}
			}
		});
		
		JLabel lbl_InsertNicname = new JLabel("Insert your nic-name:");
		lbl_InsertNicname.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lbl_InsertNicname.setBounds(10, 30, 156, 35);
		frame.getContentPane().add(lbl_InsertNicname);
		
		ip = new JTextField();
		ip.setHorizontalAlignment(SwingConstants.LEFT);
		ip.setColumns(10);
		ip.setBounds(190, 97, 221, 35);
		frame.getContentPane().add(ip);
		ip.addKeyListener((KeyListener) new KeyAdapter() {
			public void keyReleased (KeyEvent e){
				
				int key = e.getKeyCode(); 
				if(key == KeyEvent.VK_ENTER){
					apply();
				}
			}
		});
		
		port = new JTextField();
		port.setHorizontalAlignment(SwingConstants.LEFT);
		port.setColumns(10);
		port.setBounds(190, 170, 221, 35);
		port.setText("");
		frame.getContentPane().add(port);
		port.addKeyListener((KeyListener) new KeyAdapter() {
			public void keyReleased (KeyEvent e){
				
				int key = e.getKeyCode(); 
				if(key == KeyEvent.VK_ENTER){
					apply();
				}
			}
		});
		
		
		JButton Apply = new JButton("Apply");
		Apply.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		Apply.setBounds(304, 228, 89, 23);
		Apply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				apply();
			}
		});
		frame.getContentPane().add(Apply);
		
		JLabel IP_text = new JLabel("Insert connection IP:");
		IP_text.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		IP_text.setBounds(10, 97, 156, 35);
		frame.getContentPane().add(IP_text);
		
		JLabel PORT_info = new JLabel("Insert connection port:");
		PORT_info.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		PORT_info.setBounds(10, 170, 156, 35);
		frame.getContentPane().add(PORT_info);
	
		frame.repaint();
	}
}
