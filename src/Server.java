
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

	ServerSocket server;
	Socket socket;
	Connection connection;  // new client connection
	ArrayList<Connection> connections = new ArrayList<>();  // list of accepted connections

	/* 
	 * Next block create start server side of chat
	 * and waiting for new client connection
	 */
	public Server(int port) {    
		try {
			// next line starts server on port
			server = new ServerSocket(port);  
			
			//System.out.println("new server was created");
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (true) {
			try {
				socket = server.accept();  // 
				Connection connection = new Connection(socket);
				connections.add(connection);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * Next block describe private class of connection
	 * which creates for any new client
	 */
	private class Connection extends Thread {
		private BufferedReader in;   // input stream
		private PrintWriter out;	// output stream
		private Socket socket;		// socket of connection
		private String name = "";	// nic name of new client

		public Connection(Socket inSocket) {

			socket = inSocket;
			try {
				in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // create input stream
				name = in.readLine();													// save name of new client
				out = new PrintWriter(socket.getOutputStream(), true); 	// create output stream
				
				for(Connection c : connections){
					out.println("%%" + c.name);  // send to new client the name of other clients (for adress-book)
					c.out.println("%%"+name);  		// send new client name to other connected client (for salute)
				}
				
				start();
			} catch (IOException e) {
				e.printStackTrace();
				try {
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}

		/* 
		 * next block organize message exchanging beetween clients
		 */
		public void run() {
			try {
				for (Connection c : connections) {
					c.out.println(name + " cames here");
				}

				String str = "";
				while (true) {
					try {
						str = in.readLine();
					} catch (IOException e) {
						e.printStackTrace();
					}
					if (str != null) {  // only valuable String is allowed
						
						// if exit was accept - so, client has gone;
						// next block sends message about it
						if (str.equals("exit")) {
							for (Connection c : connections) {
								c.out.println(name + " has left chat.");
							}
							break;
						}
						
						// next block sends message to all connected clients
						for (Connection c : connections) {
							c.out.println(name + ": " + str);
						}
					}
				}
			}

			finally {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
