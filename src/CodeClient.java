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
	static BufferedReader user;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		System.out.println("Client Initializing...");
		String textin = "INIT";
		String ip = "127.0.0.1";
		int port = 12345;

		user = new BufferedReader(new InputStreamReader(System.in)); // Takes
																		// user
																		// input

		if (args.length == 2) {
			// Take args and sets ip and port to connect to
			ip = args[0];
			port = Integer.parseInt(args[1]);
		} else if (args.length == 1) {
			// Take args and assumes localhost, sets the port number
			port = Integer.parseInt(args[0]);
		}

		try {
			System.out.println("Connecting to " + ip + " : " + port);
			s = new Socket(ip, port);

			out = new PrintWriter(s.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));

		} catch (Exception e) {
			e.printStackTrace();
		}

		// initial handshake -->
		out.println("ASCII");
		System.out.println("CLIENT: ASCII");

		// read ascii: ok <--
		if (!"ASCII: OK".equals((textin = in.readLine()))) {
			System.out.println("Invalid response from server.");
			System.exit(0);
		} else {

			// display ascii ok V
			System.out.println("SERVER: " + textin);

			// input stuff V
			System.out.println("Usage:");
			System.out.println("[AC, CA, BYE, END]");
			System.out.print(">");
			textin = user.readLine();

			// display client: V
			System.out.println("Client: " + textin);
		}

		while (true) {

			// send to server -->
			out.println(textin);

			// read server <--
			textin = in.readLine();
			// display server: V
			System.out.println("Server: " + textin);

			// determine if END or BYE
			if ("END: OK".equals(textin) || "BYE: OK".equals(textin)) {
				System.out.println("Client shutting down");
				System.exit(0);
			}

			// read ascii: ok <--
			if ("ASCII: OK".equals(textin = in.readLine())) {
				// display ascii ok V
				System.out.println("SERVER: " + textin);

				// input stuff V
				System.out.print(">");
				textin = user.readLine();
				// display client: V
				System.out.println("Client: " + textin);
			}
			// loop
		}
	}
}