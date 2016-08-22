import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Andrew Parkinson c3128094 Client and Server for converting ASCII to
 *         TEXT and TEXT to ASCII For SENG3400 S2 2016 - University of Newcastle
 */
public class CodeConverter {
	static ServerSocket ss;
	static Socket s;

	static BufferedReader in;
	static PrintWriter out;

	private static final int AC = 0;
	private static final int CA = 1;
	private static final int BYE = 2;
	private static final int Error = 4;

	public static void main(String[] args) throws IOException {

		System.out.println("Server Initializing...");
		int port = 1337;
		int state = BYE;
		String textin = null;

		if (args.length > 0) {
			// Take args and sets port to listen on
			port = Integer.parseInt(args[0]);
		}

		System.out.println("Server is running on port: " + port);
		System.out.println("Waiting for Client...");

		// initialize server socket
		ss = new ServerSocket(port);
		state = connect();
		textin = "ASCII";

		while (true) {
			System.out.println("Start of while loop");
			if (!"ASCII".equals(textin) && textin != null) {
				System.out.println("REQUEST: " + textin);
				if ("AC".equals(textin)) {
					state = AC;
					out.flush();
					out.println("CHANGE: OK");
					System.out.println("RESPONSE: CHANGE: OK");
					textin = "ASCII";

				} else if ("CA".equals(textin)) {
					state = CA;
					out.flush();
					out.println("CHANGE: OK");
					System.out.println("RESPONSE: CHANGE: OK");
					textin = "ASCII";

				} else if ("END".equals(textin)) {
					textin = null;

				} else {
					if (state == AC) {
						try {
							System.out.println("AC CONVERTER");
							int temp = Integer.parseInt(textin);
							if (temp >= 32 || temp <= 255) {
								char c = (char) temp;
								System.out.println(c);
								out.println(c);
							} else {
								out.println("ERR");
							}
						} catch (Exception e) {
							out.println("ERR");
						}
						textin = "ASCII";

					} else if (state == CA) {
						try {
							System.out.println("CA CONVERTER");
							/*
							 * String temp = String.toCharArray(); String result
							 * = Character.toString(temp); out.println(result);
							 */
						} catch (Exception e) {
							e.printStackTrace();
						}
						textin = "ASCII";
					} else {
						System.out.println("oops.. you shouldn't be able to see this");
					}
				}
			}

			if (textin.equals("END")) {
				System.exit(0);
			}

			/*
			 * while (state == BYE) { state = connect(); } while (state ==
			 * Error) {
			 *
			 * }
			 */
			if ("ASCII".equals(textin)) {
				out.flush();
				out.println("ASCII: OK");
				System.out.println("RESPONSE: ASCII: OK");
				textin = null;
				while ((textin = in.readLine()) == null) {
					System.out.println("Line Read: " + textin);

				}
			}
		}
	}

	public static int connect() {
		// handles client connection
		// will return as an int to tell the server that the client is connected
		try {
			s = ss.accept();
			out = new PrintWriter(s.getOutputStream(), false);
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));

			System.out.println("Client Connected");
			return AC;

		} catch (IOException e) {
			System.out.println("Error... Try again ");
			e.printStackTrace();
			return Error;
		}

	}

}
