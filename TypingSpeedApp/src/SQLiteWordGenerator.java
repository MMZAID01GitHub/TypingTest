import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteWordGenerator {
	public SQLiteWordGenerator(){
			}
	public String getWord() {
		try {
			Class.forName("org.sqlite.JDBC");
			String dbURL = "jdbc:sqlite:C:\\Users\\damma\\Documents\\PersonalProjects\\englishwords\\Dictionary.db";
			//retrieves random word from words that don't have a space or a dash
			String myQuery = "SELECT word FROM entries WHERE word NOT LIKE '% %' ORDER BY RANDOM() LIMIT 1;";
			Connection conn = DriverManager.getConnection(dbURL);
			if (conn != null) {
				//System.out.println("Connected to the database");
				Statement selectRandom = conn.createStatement();
				String word = selectRandom.executeQuery(myQuery).getString("word");
				//System.out.println(word);
				conn.close();
				return word;
			}
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return "error";
		
	}
	
}
