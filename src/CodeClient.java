import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author Andrew Parkinson c3128094 Client and Server for converting ASCII to
 *         TEXT and TEXT to ASCII For SENG3400 S2 2016 - University of Newcastle
 */
public class CodeClient {
	static Socket s;

	static BufferedReader in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		System.out.println("Client Initializing...");
		String textin = "";
		String ip = "127.0.0.1";
		int port = 1337;

		if (args.length == 2) {
			// Take args and sets ip and port to connect to
			ip = args[0];
			port = Integer.parseInt(args[1]);
		} else if (args.length == 1) {
			// Take args and assumes localhost, sets the port number
			port = Integer.parseInt(args[0]);
		}

		try {
			s = new Socket(ip, port);

			out = new PrintWriter(s.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));

		} catch (Exception e) {
			e.printStackTrace();
		}

		out.println("ASCII");
		System.out.println("CLIENT: ASCII");

		while ((textin = in.readLine()) != null) {
			System.out.println(textin);
		}
	}
}