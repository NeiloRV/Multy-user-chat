
import java.util.Scanner;

public class MainClass {
	final static int port = 8181;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in); 
		 
	    System.out.println("Which side of CHAT do you want to run: server (s) or client (c)"); 
	    while (true) { 
	        char answer = Character.toLowerCase(in.nextLine().charAt(0)); 
	        if (answer == 's') { 
	            new Server(port); 
	            break; 
	        } else if (answer == 'c') { 
	            System.out.println("new client was created");
	        	//new Client(port); 
	           WindowClient newClient = new WindowClient();
	            break; 
	        } else { 
	            System.out.println("Uncorect data. Please try again"); 
	        } 
	    } 
	    in.close();
	} 
}
