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

		outerloop: while (true) {
			if (!"ASCII".equals((textin = in.readLine()))) { // Waits for ASCII
																// command from
																// Client
				System.out.println("Error, unexpected response from client: " + textin);
			} else {
				// out.println("ASCII: OK");
				textin = "OK";
				break outerloop;
			}
		}

		while (true) {
			System.out.println("Start of while loop");
			if (!"OK".equals(textin) && textin != null) {
				System.out.println("REQUEST: " + textin);
				if ("AC".equals(textin)) {
					state = AC;
					out.flush();
					out.print("CHANGE: OK" + "\r\n");
					System.out.println("RESPONSE: CHANGE: OK");
					textin = "OK";

				} else if ("CA".equals(textin)) {
					state = CA;
					out.flush();
					out.print("CHANGE: OK" + "\r\n");
					System.out.println("RESPONSE: CHANGE: OK");
					textin = "OK";

				} else if ("END".equals(textin)) {
					System.exit(0);

				} else {
					if (state == AC) {
						try {
							System.out.println("AC CONVERTER");
							// error checking goes here
							int temp = Integer.parseInt(textin);
							if (temp >= 32 || temp <= 255) {
								char c = (char) temp;
								System.out.println(c);
								out.print(c + "\r\n");
								textin = "OK";
							} else {
								textin = "OK";
								out.print("ERR" + "\r\n");
							}
						} catch (Exception e) {
							out.print("ERR" + "\r\n");
							e.printStackTrace();
							textin = "OK";
						}

					} else if (state == CA) {
						try {
							System.out.println("CA CONVERTER");
							/*
							 * String temp = String.toCharArray(); String result
							 * = Character.toString(temp); out.println(result);
							 */
							textin = "OK";
						} catch (Exception e) {
							e.printStackTrace();
							out.print("ERR" + "\r\n");
							textin = "OK";
						}
					} else {
						System.out.println("oops.. you shouldn't be able to see this");
					}
				}
			} else if ("OK".equals(textin)) {
				// out.flush();
				// out.print("ASCII: OK" + "\r\n");
				out.println("ASCII: OK");// this was moved from the top loop,
											// hope it works. (it does :D )
				System.out.println("RESPONSE: ASCII: OK");
				while ((textin = in.readLine()) == "OK") {
					System.out.println("Line Read: " + textin);
				}
			}
			/*
			 * while (state == BYE) { state = connect(); } while (state ==
			 * Error) {
			 *
			 * }
			 */
		}
	}

	public static int connect() {
		// handles client connection
		// will return as an int to tell the server that the client is connected
		try {
			s = ss.accept();
			out = new PrintWriter(s.getOutputStream(), true);
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
