package outputInput;
import java.io.Console;
import java.util.Scanner;

public class ConsoleInput implements Input {

	Scanner scanner = new Scanner(System.in);

	@Override
	public String scan() {

		String buffer = "";

		buffer = scanner.nextLine();

		return buffer;
	}

	@Override
	public String scanPassword() {
		Console console = System.console();

		char[] password = console.readPassword();

		return password.toString();
	}

	protected void finalize() {
		scanner.close();
	}

}
