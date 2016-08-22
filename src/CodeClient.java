import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author Andrew Parkinson c3128094 Client and Server for converting ASCII to
 *         TEXT and TEXT to ASCII For SENG3400 S2 2016 - University of Newcastle
 */
public class CodeClient {
	public static void main(String[] args) throws IOException {
		System.out.println("Client Initializing...");
		String textin = "";
		String textout = "";
		String userinput = "";

		try {
			s = new Socket("127.0.0.1", 1337);
			// din = new DataInputStream(s.getInputStream());
			// dout = new DataOutputStream(s.getOutputStream());

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

	static Socket s;

	static BufferedReader in;
	static PrintWriter out;

	private static String TakeInput() {
		// Input
		Console console = null;
		String inputString = null;

		try {
			// creates a console object
			console = System.console();
			// if console is not null
			if (console != null) {
				// read line from the user input
				inputString = console.readLine("> ");
				// prints
				// System.out.println("Name entered : " + inputString);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inputString;

	}

}