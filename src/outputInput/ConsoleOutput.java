package outputInput;
public class ConsoleOutput implements Output {

	@Override
	public void print(String message) {
		System.out.println(message);
	}

	@Override
	public void printError(String message) {
		System.err.println(message);

	}

}
