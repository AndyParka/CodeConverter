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

	public static void main(String[] args) throws IOException {

		System.out.println("Server Initializing...");
		int port = 1337; // 1337 because i'm L33T
		int state = CA; // holds the state of the converter, AC or CA
		String textin = null;

		if (args.length > 0) {
			// Take args and sets port to listen on
			port = Integer.parseInt(args[0]);
		}

		System.out.println("Server is running on port: " + port);

		// initialize server socket
		ss = new ServerSocket(port);

		// accepts client connection and initiates handshake
		textin = connect();

		while (true) {
			if (!"OK".equals(textin) && textin != null) {
				System.out.println("REQUEST: " + textin); // Outputs the text
															// received from the
															// client
				if ("AC".equals(textin)) {
					state = AC;
					out.println("CHANGE: OK");
					System.out.println("RESPONSE: CHANGE: OK");
					textin = "OK";
				} else if ("CA".equals(textin)) {
					state = CA;
					out.println("CHANGE: OK");
					System.out.println("RESPONSE: CHANGE: OK");
					textin = "OK";
				} else if ("BYE".equals(textin)) {
					out.println("BYE: OK");
					System.out.println("RESPONSE: BYE: OK");
					textin = connect();
				} else if ("END".equals(textin)) {
					out.println("END: OK");
					System.out.println("RESPONSE: END: OK");
					System.exit(0);

				} else {
					if (state == AC) { // if STATE is AC, then take the entered
										// text and do stuff with it
						try {
							System.out.println("AC CONVERTER");
							int temp = Integer.parseInt(textin);
							// checks to see if the ascii is within the valid
							// range
							if (temp >= 48 && temp <= 57 || temp >= 65 && temp <= 90 || temp >= 97 && temp <= 122) {
								char c = (char) temp;
								System.out.println(c);
								out.println(c);
							} else {
								out.println("ERR");
							}
						} catch (Exception e) {
							out.println("ERR");
							// e.printStackTrace();
						}
						textin = "OK";
					} else if (state == CA) { // if STATE is CA, then take the
												// entered text and do stuff
												// with it
						try {
							System.out.println("CA CONVERTER");
							if (textin.length() == 1) {
								char temp = textin.charAt(0); // converts string
																// to char
								int ascii = temp; // converts char to int
													// representing the ASCII
													// value of the char
								System.out.println(ascii);
								out.println(ascii);
							} else {
								out.println("ERR");
							}
						} catch (Exception e) {
							// e.printStackTrace();
							out.println("ERR");

						}
						textin = "OK";
					} else {
						System.out.println("oops.. you shouldn't be able to see this");
					}
				}
			} else if ("OK".equals(textin)) {
				out.println("ASCII: OK");// this was moved from the top loop,
											// hope it works. (it does :D )
				System.out.println("RESPONSE: ASCII: OK");
				while ((textin = in.readLine()) == "OK") {
					System.out.println("Line Read: " + textin);
				}
			}
		}
	}

	public static String connect() {
		// handles client connection
		// will return as an int to tell the server that the client is connected
		try {
			System.out.println("Waiting for Client...");
			s = ss.accept();
			out = new PrintWriter(s.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			String textin;
			while (true) {
				if (!"ASCII".equals((textin = in.readLine()))) { // Waits for
																	// ASCII
																	// command
																	// from
																	// Client
					System.out.println("Error, unexpected response from client: " + textin);
					return "BYE";
				} else {
					System.out.println("Client Connected");
					return "OK";
				}
			}

		} catch (IOException e) {
			System.out.println("Error... Try again ");
			return "BYE";
		}

	}

}
