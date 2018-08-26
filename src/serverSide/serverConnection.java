package serverSide;

import java.io.IOException;
import java.net.ServerSocket;

import javax.net.ssl.SSLServerSocketFactory;

import org.json.simple.parser.ParseException;

public class serverConnection {
	public static void main(String[] args) throws IOException, ParseException  {
		System.setProperty("javax.net.ssl.keyStore", "sp.store");
		System.setProperty("javax.net.ssl.keyStorePassword", "password");
		ServerSocket serverSocket = SSLServerSocketFactory.getDefault().createServerSocket(7000);
		System.out.println("Ready for connaction...");
		while (true) new serverClass(serverSocket.accept()).start();
	}
}
