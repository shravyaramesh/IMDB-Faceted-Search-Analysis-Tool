import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class PopulateDatabase {
	public Connect connect ;
	PopulateDatabase(){
	connect = new Connect();
	}
	public static void main(String args[]) {
		PopulateDatabase populateDatabase = new PopulateDatabase();
		populateDatabase.run();
	}

	

	public ArrayList<String> fileReader(String path) throws IOException{
		ArrayList<String> lineno = new ArrayList<>();
		BufferedReader buf = null;
		try {
			buf = new BufferedReader(new FileReader(path));
			String line = buf.readLine();
			while (line != null) {
				lineno.add(line);
				line = buf.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			buf.close();
		}
		return lineno;
	}

	public static boolean isDigit(String str)
	{
		try {
			Double.parseDouble(str);
		} catch(NumberFormatException e) {
			return false;
		}
		return true;
	}

	
	public void populate_movie_countries(Connection connection) throws SQLException{
		ArrayList<String> file_lines = null;
		try {
			file_lines = fileReader("C:\\Users\\shravya\\Downloads\\sramesh\\IMDB_DA\\IMDB_DA\\src\\data_files\\movie_countries.dat");
		} catch (IOException e) {
			System.err.println("Error! File Not Read. " + e.getMessage());
			e.printStackTrace();
		}
		Statement statement = connection.createStatement();
		statement.executeUpdate("delete from movie_countries");
		PreparedStatement insert = connection.prepareStatement("insert into movie_countries values(?,?)");
		String values[] = null;
		for (int i=1; i<file_lines.size(); i++) {
			values = file_lines.get(i).split("\\t");
			if (values.length < 2) {
				insert.setInt(1, Integer.parseInt(values[0]));
				insert.setString(2, "<Blank>");
			} else {
				insert.setInt(1, Integer.parseInt(values[0]));
				insert.setString(2, values[1]);

			}
			insert.executeUpdate();
		}
		statement.close();
		insert.close();
	}

	
	public void populateGenres(Connection connection) throws SQLException{
		ArrayList<String> file_lines = null;
		try {
			file_lines = fileReader("C:\\Users\\shravya\\Downloads\\sramesh\\IMDB_DA\\IMDB_DA\\src\\data_files\\movie_genres.dat");
		} catch (IOException e) {
			System.err.println("Error while reading movie_genres.dat file: " + e.getMessage());
			e.printStackTrace();
		}
		Statement statement = connection.createStatement();
		statement.executeUpdate("delete from movie_genres");
		PreparedStatement insert_statement = connection.prepareStatement("insert into movie_genres values(?,?)");
		String values[] = null;
		for (int i=1; i<file_lines.size(); i++) {
			values = file_lines.get(i).split("\\s");
			insert_statement.setInt(1, Integer.parseInt(values[0]));
			insert_statement.setString(2, values[1]);
			insert_statement.executeUpdate();
		}
		statement.close();
		insert_statement.close();
	}

	
	public void populateFilmLocations(Connection connection) throws SQLException{
		ArrayList<String> file_lines = null;
		try {
			file_lines = fileReader("C:\\Users\\shravya\\Downloads\\sramesh\\IMDB_DA\\IMDB_DA\\src\\data_files\\movie_locations.dat");
		} catch (IOException e) {
			System.err.println("Error! File Not Read. " + e.getMessage());
			e.printStackTrace();
		}
		Statement s2 = connection.createStatement();
		s2.executeUpdate("delete from movie_locations");
		PreparedStatement insert = connection.prepareStatement("insert into movie_locations_temp values(?,?)");
		String values[] = null;
		for (int i=1; i<file_lines.size(); i++) {
			values = file_lines.get(i).split("\\t");
			if (values.length < 2) {
				insert.setInt(1, Integer.parseInt(values[0]));
				insert.setString(2, "<Blank>");
			} else {
				insert.setInt(1, Integer.parseInt(values[0]));
				insert.setString(2, values[1]);
			}
			insert.executeUpdate();
		}
		Statement s1 = connection.createStatement();
		s1.executeUpdate("insert into movie_locations select distinct * from movie_locations_temp");

		Statement statement2 = connection.createStatement();
		statement2.executeUpdate("delete from movie_locations_temp");

		s2.close();
		s1.close();
		statement2.close();
		insert.close();
	}

	
	public void populateMovieTags(Connection connection) throws SQLException{
		ArrayList<String> file_lines = null;
		try {
			file_lines = fileReader("C:\\Users\\shravya\\Downloads\\sramesh\\IMDB_DA\\IMDB_DA\\src\\data_files\\movie_tags.dat");
		} catch (IOException e) {
			System.err.println("Error while reading movie_tags.dat file: " + e.getMessage());
			e.printStackTrace();
		}
		Statement statement = connection.createStatement();
		statement.executeUpdate("delete from movie_tags");
		PreparedStatement insert = connection.prepareStatement("insert into movie_tags values(?,?,?)");
		String values[] = null;
		for (int i=1; i<file_lines.size(); i++) {
			values = file_lines.get(i).split("\\s");
			insert.setInt(1, Integer.parseInt(values[0]));
			insert.setInt(2, Integer.parseInt(values[1]));
			insert.setInt(3, Integer.parseInt(values[2]));
			insert.executeUpdate();
		}
		statement.close();
		insert.close();
	}

	
	public void populateMovies(Connection connection) throws SQLException{
		ArrayList<String> file_lines = null;
		try {
			file_lines = fileReader("C:\\Users\\shravya\\Downloads\\sramesh\\IMDB_DA\\IMDB_DA\\src\\data_files\\movies.dat");
		} catch (IOException e) {
			System.err.println("Error! File Not Read. " + e.getMessage());
			e.printStackTrace();
		}
		Statement statement = connection.createStatement();
		statement.executeUpdate("delete from movies");
		PreparedStatement insert = connection.prepareStatement("insert into movies values(?,?,?,?,?)");
		String values[] = null;
		for (int i=1; i<file_lines.size(); i++) {
			values = file_lines.get(i).split("\\t");
			insert.setInt(1, Integer.parseInt(values[0]));
			insert.setString(2, values[1]);
			if (isDigit(values[5]))
				insert.setInt(3, Integer.parseInt(values[5]));
			else
				insert.setInt(3, -1);

			if (isDigit(values[7]))
				insert.setFloat(4, Float.parseFloat(values[7]));
			else
				insert.setInt(4, -1);

			if (isDigit(values[8]))
				insert.setInt(5, Integer.parseInt(values[8]));
			else
				insert.setInt(5, -1);
			insert.executeUpdate();
		}
		statement.close();
		insert.close();
	}

	
	public void populateTags(Connection connection) throws SQLException{
		ArrayList<String> file_lines = null;
		try {
			file_lines = fileReader("C:\\Users\\shravya\\Downloads\\sramesh\\IMDB_DA\\IMDB_DA\\src\\data_files\\tags.dat");
		} catch (IOException e) {
			System.err.println("Error! File Not Read. " + e.getMessage());
			e.printStackTrace();
		}
		Statement s = connection.createStatement();
		s.executeUpdate("delete from tags");
		PreparedStatement insert_statement = connection.prepareStatement("insert into tags values(?,?)");
		String values[] = null;
		for (int i=1; i<file_lines.size(); i++) {
			values = file_lines.get(i).split("\\t");
			insert_statement.setInt(1, Integer.parseInt(values[0]));
			insert_statement.setString(2, values[1]);
			insert_statement.executeUpdate();
		}
		s.close();
		insert_statement.close();
	}

	
	public void populateUTM(Connection connection) throws SQLException{
		ArrayList<String> file_lines = null;
		try {
			file_lines = fileReader("C:\\Users\\shravya\\Downloads\\sramesh\\IMDB_DA\\IMDB_DA\\src\\data_files\\user_taggedmovies.dat");
		} catch (IOException e) {
			System.err.println("Error!File Not Read.  " + e.getMessage());
			e.printStackTrace();
		}
		Statement statement = connection.createStatement();
		statement.executeUpdate("delete from user_tagged_movies");
		PreparedStatement insert_statement = connection.prepareStatement("insert into user_tagged_movies values(?,?,?,?,?,?,?,?,?)");
		String values[] = null;
		for (int i=1; i<file_lines.size(); i++) {
			values = file_lines.get(i).split("\\s");
			for(int j=0;j<values.length;j++) {
				insert_statement.setInt(j+1, Integer.parseInt(values[j]));
			}
			insert_statement.executeUpdate();
		}
		statement.close();
		insert_statement.close();
	}
	
	public void run() {
		Connection connection = null;
		try {
			connection = connect.create_connection();
			populate_movie_countries(connection);
			populateGenres(connection);
			populateFilmLocations(connection);
			populateMovieTags(connection);
			populateMovies(connection);
			populateTags(connection);
			populateUTM(connection);
			
		} catch (SQLException e) {
			System.err.println("Error! Database Connection Not Established" + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.err.println("Couldn't find the database driver");
		} finally {
			connect.close_connection(connection);
		}
	}
}
