package securityAndDatabase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteConfig;

import dataModels.User;
import outputInput.Output;

public class DataBaseHelper {
	public static final String DRIVER = "org.sqlite.JDBC";
	public static final String DB_URL = "jdbc:sqlite:users.db";

	private Connection conn;
	private Statement stat;
	private Output output;

	public SHAController sHAController = new SHAController();

	public DataBaseHelper(Output output) {

		this.output = output;

		try {

			Class.forName(DataBaseHelper.DRIVER);

		} catch (ClassNotFoundException e) {
			output.printError("No JDBC driver");
			e.printStackTrace();
		}

		try {

			// Enforcing UTF-8 encoding removes problem with sqllite database changing
			// values of stroed hashed password and salt
			SQLiteConfig config = new SQLiteConfig();
			config.setEncoding(SQLiteConfig.Encoding.UTF8);

			conn = DriverManager.getConnection(DB_URL, config.toProperties());
			stat = conn.createStatement();

		} catch (SQLException e) {
			output.printError("Error in establishing connection");
			e.printStackTrace();
		}

		createTables();
	}

	private boolean createTables() {
		String createUsers = "CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTOINCREMENT, name varchar(255), password varchar(255), salt varchar(255), email varchar(255), phoneNumber varchar(255));";
		try {
			stat.execute(createUsers);
		} catch (SQLException e) {
			output.printError("Error in creating table");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean insertUser(String name, String passwordHash, String salt, String email, String phoneNumber) {

		// usage of PreparedStatement protects from sql injection attacks

		try {
			PreparedStatement prepStmt = conn.prepareStatement("insert into users values (NULL, ?, ?, ?, ?, ?);");
			prepStmt.setString(1, name);
			prepStmt.setString(2, passwordHash);
			prepStmt.setString(3, salt);
			prepStmt.setString(4, email);
			prepStmt.setString(5, phoneNumber);
			prepStmt.execute();
		} catch (SQLException e) {
			output.printError("Error in adding user");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean checkIfUserNameFree(String userName) {

		ResultSet result = this.selectUser(userName);
		try {

			// in this case result.next() returns true if name is already taken, so we
			// negate and return it
			return !(result.next());

		} catch (SQLException e) {
			output.printError("Error in conecting to base");
			e.printStackTrace();
			return false;
		}
	}

	public User login(String userName, String password) throws SQLException{
		ResultSet result = this.selectUser(userName);
		User userResult = new User();

		if (result == null) {
			// user was not found in database, return invalid user

			userResult.setId(-1);
			return userResult;
		}
	

			result.next();
			String salt = result.getString(4);
			String passwordHash = result.getString(3);

			if (sHAController.comparePasswords(password, passwordHash, salt)) {
				// password is correct, return full user data
				userResult.setId(result.getInt(1));
				userResult.setName(userName);
				userResult.setEmail(result.getString(5));
				userResult.setPhoneNumber(result.getString(6));

			} else {
				// password incorrect, return invalid user
				userResult.setId(-1);

			}
		return userResult;
	}
	

	public boolean updatePhoneNumber(int id, String phoneNumber) {
		try {
			PreparedStatement prepStmt = conn.prepareStatement("update users set phoneNumber=? where id=?;");
			prepStmt.setString(1, phoneNumber);
			prepStmt.setInt(2, id);
			prepStmt.execute();
		} catch (SQLException e) {
			output.printError("Error in updating data");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean updateEmail(int id, String email) {
		try {
			PreparedStatement prepStmt = conn.prepareStatement("update users set email=? where id=?;");
			prepStmt.setString(1, email);
			prepStmt.setInt(2, id);
			prepStmt.execute();
		} catch (SQLException e) {
			output.printError("Error in updating data");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			output.printError("Error in closing connection");
			e.printStackTrace();
		}
	}

	private ResultSet selectUser(String userName) {
		ResultSet result = null;
		try {
			PreparedStatement prepStmt = conn.prepareStatement("select * from users where name=?");
			prepStmt.setString(1, userName);

			result = prepStmt.executeQuery();

		} catch (SQLException e) {
			output.printError("Error in seaching for user");
			e.printStackTrace();
		}

		return result;
	}
	
}
