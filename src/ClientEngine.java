
import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.swing.plaf.SliderUI;

public class ClientEngine {

	PrintWriter out;
	BufferedReader in;
	Scanner scan;
	Socket socket;
	ClientDataStart newClient;
	ChatWindow clientWindow;
	private InComing inComingMessage;

	public ClientEngine(ClientDataStart inNewClient, ChatWindow inChatWindow) {

		newClient = inNewClient;
		clientWindow = inChatWindow;

		try {
			socket = new Socket(newClient.getIpClient(), newClient.getPort());
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		startChat();
		inComingMessage = new InComing();
	}

	private void startChat() {
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println(newClient.getNicName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(String inString) {
		if (inString.equals("exit")) {
			inComingMessage.setStop();
			out.println(inString);
			clientWindow.frame.dispose();
			System.exit(0);
			socketClose();
		} else {
			out.println(inString);
		}
	}

	private void socketClose() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	class InComing extends Thread {
		private boolean stoped = false;

		public InComing() {
			start();
		}

		public void setStop() {
			stoped = true;
		}

		@Override
		public void run() { // this thread listen for new message
			try {
				while (!stoped) {
					String str = "";
					str = (String) in.readLine();

					// Next block get user nicName for guest list
					if (str.substring(0, 2).equals("%%")) {
						String nextNic = str.substring(2, str.length());

						clientWindow.addToGuestList(nextNic);
						// if(!nextNic.equals(newClient.getNicName())){ // this
						// block analyze is it the same name, as this client
						// }
						continue;
					}

					// this block analyze new message, and looking for exit
					// action.
					// According to this it remove client from client list
					if (str.length() > 16) {
						if (str.substring(str.length() - 14, str.length()).equals("has left chat.")) {
							clientWindow.removeFromGuestList(str.substring(0, str.length() - 15));
						}
					}

					// next scope analyze: Does the new message was sent by user
					// itself?
					// if "yes" code make indent in message history window.
					String indent;

					if (str.substring(0, newClient.getNicName().toCharArray().length).equals(newClient.getNicName())) {
						indent = " ";
					} else {
						indent = "          ";
					}

					// next block put message in message text area
					clientWindow.messageField.setText(clientWindow.messageField.getText() + '\n'
							+ DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(new Date())
							+ indent + " " + str);
				}
			} catch (IOException e) {
				System.err.println("Exception during message arrivale");
				e.printStackTrace();
			} finally {
				sendMessage("exit");
			}
		}

	}
}
