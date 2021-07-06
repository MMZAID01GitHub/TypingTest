import java.sql.*;
public class SQLiteDemo {

	public static void main(String[] args) {
		try {
			Class.forName("org.sqlite.JDBC");
			String dbURL = "jdbc:sqlite:C:\\Users\\damma\\Documents\\PersonalProjects\\englishwords\\Dictionary.db";
			String myQuery = "SELECT word FROM entries ORDER BY RANDOM() LIMIT 1;";
			Connection conn = DriverManager.getConnection(dbURL);
			if (conn != null) {
				System.out.println("Connected to the database");
				Statement selectRandom = conn.createStatement();
				String word = selectRandom.executeQuery(myQuery).getString("word");
				System.out.println(word);
				conn.close();
			}
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}
}
