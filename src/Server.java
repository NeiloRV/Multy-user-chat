
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
	Connection connection;
	ArrayList<Connection> connections = new ArrayList<>();

	public Server(int port) {
		try {
			server = new ServerSocket(port);
			System.out.println("new server was created");
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (true) {
			try {
				socket = server.accept();
				Connection connection = new Connection(socket);
				connections.add(connection);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private class Connection extends Thread {
		private BufferedReader in;
		private PrintWriter out;
		private Socket socket;
		private String name = "";

		public Connection(Socket inSocket) {

			socket = inSocket;
			try {
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				name = in.readLine();
				out = new PrintWriter(socket.getOutputStream(), true);
				
				for(Connection c : connections){
					out.println("%%" + c.name);  // send the name of other connections to new client
					c.out.println("%%"+name);  		// send new client name to other connected client
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
					if (str != null) {
						if (str.equals("exit")) {
							for (Connection c : connections) {
								c.out.println(name + " has left chat.");
							}
							break;
						}
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
