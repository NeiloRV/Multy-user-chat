
//import java.util.Scanner;

public class MainClass {
	final static int port = 8181;
	String choosenSide;
	boolean serverStarted = false;

	public static void main(String[] args) {
		//Scanner in = new Scanner(System.in); 
		 
	  //  System.out.println("Which side of CHAT do you want to run: server (s) or client (c)"); 
		
		MainClass neiloChat = new MainClass();
		StartSorCWindow startWindow = new StartSorCWindow(neiloChat);
	    while (true) {
	    	if(neiloChat.choosenSide != null){
	     //   char answer = Character.toLowerCase(in.nextLine().charAt(0)); 
	        if (neiloChat.choosenSide.equals("Server")) { 
	            new Server(port); 
	            break; 
	        } else if (neiloChat.choosenSide.equals("Client")) { 
	            System.out.println("new client was created");
	        	//new Client(port); 
	           WindowClient newClient = new WindowClient();
	            break; 
	        } else { 
	            System.out.println("Uncorect data. Please try again"); 
	        } 
	    }
	    } 
	  //  in.close();
	} 
}
