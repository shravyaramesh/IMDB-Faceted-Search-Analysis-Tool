import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.CompoundBorder;

public class GUI {

	public static JFrame mainframe;
	
	public static JList<String> genreJList, countrylistj, locationlistj, taglistj, resultlistj;
	public static JComboBox searchmenu, tagmenu, ratingmenu, comboBox_3, reviewsmenu;
	public static JTextField tagvalue, ratingtext, reviewtext, fromyear, toyear;
	public static JTextArea querytext ;
	public static DefaultListModel genremodel, countryModel, locationModel, tagmodel, resultmodel; 
	public static ArrayList<String> genreList, countrylist1, locationlist, taglist;
	
	JButton btnGetCountry ;
	JButton btnGetFilmingLocation;
	JButton btnGetTag, btnExecuteQuery ;
	JPanel querypanel ;
	
	public static Connect connect;
	public static Connection connection ;
	
	public void customisingButtons() {
		btnGetCountry.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnGetCountry.setBounds(25, 336, 136, 29);
		btnGetFilmingLocation.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnGetFilmingLocation.setBounds(160, 336, 130, 29);
		btnGetTag.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnGetTag.setBounds(288, 336, 130, 29);
		btnExecuteQuery.setFont(new Font("Times New Roman", Font.PLAIN, 15));

		
	}
	
	public void addingActionListeners() {
		//Adding all the action listeners to the buttons
				//---->Action Listener to display countries according to the selected genres
				btnGetCountry.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						clearselections();
						displayCountry(event);
					}
				});
				
				//---->Action Listener to display filming locations according to first two selected
				btnGetFilmingLocation.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						locationModel.clear();
						displayFilmingLocations();
					}
				});
				
				//---->Action Listener to display Movie Tags
				btnGetTag.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						tagmodel.clear();
						getTagValues();
					}
				});
				
				//---->Button to display the query formed and the result according to user selection
				btnExecuteQuery.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						resultmodel.clear();
						querytext.setText("");
						displayQueryResult();
					}
				});
				
				
			
			}		
	
	//Constructor
	public GUI() {
		
		connect = new Connect();
		connection = null;
		
		genremodel = new DefaultListModel();
		countryModel = new DefaultListModel();
		locationModel = new DefaultListModel();
		tagmodel = new DefaultListModel();
		resultmodel = new DefaultListModel();
		genreList = new ArrayList<>();
		countrylist1 = new ArrayList<>();
		locationlist = new ArrayList<>();
		taglist = new ArrayList<>();
		
		mainframe = new JFrame();
		mainframe.setTitle("IMDb");
		mainframe.getContentPane().setFont(new Font("Times New Roman", Font.PLAIN, 15));
		mainframe.getContentPane().setBackground(Color.white);
		mainframe.setBounds(100, 100, 850, 750);
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainframe.getContentPane().setLayout(null);

		
		JPanel panel = new JPanel();
		panel.setBackground(Color.white);
		panel.setBounds(10, 16, 788, 26);
		mainframe.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel movielabel = new JLabel("IMDB Faceted Search Tool");
		movielabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
		movielabel.setBounds(190, 0, 432, 17);
		movielabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(movielabel);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(UIManager.getBorder("TextField.border"));
		panel_1.setBackground(SystemColor.activeCaptionBorder);
		panel_1.setBounds(160, 39, 131, 39);
		mainframe.getContentPane().add(panel_1);

		JLabel lblCountry = new JLabel("Country");
		lblCountry.setHorizontalAlignment(SwingConstants.CENTER);
		lblCountry.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		panel_1.add(lblCountry);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(UIManager.getBorder("TextField.border"));
		panel_2.setBackground(SystemColor.activeCaptionBorder);
		panel_2.setBounds(285, 39, 133, 39);
		mainframe.getContentPane().add(panel_2);

		JLabel lblFilmingLocation = new JLabel("Filming Location");
		lblFilmingLocation.setHorizontalAlignment(SwingConstants.CENTER);
		lblFilmingLocation.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		panel_2.add(lblFilmingLocation);

		JPanel panel_4 = new JPanel();
		panel_4.setBorder(UIManager.getBorder("TextField.border"));
		panel_4.setBackground(SystemColor.activeCaptionBorder);
		panel_4.setBounds(418, 39, 164, 39);
		mainframe.getContentPane().add(panel_4);

		JLabel lblCritics = new JLabel("Critics' Rating");
		lblCritics.setHorizontalAlignment(SwingConstants.CENTER);
		lblCritics.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		panel_4.add(lblCritics);

		JPanel panel_5 = new JPanel();
		panel_5.setBorder(UIManager.getBorder("TextField.border"));
		panel_5.setBackground(SystemColor.activeCaptionBorder);
		panel_5.setBounds(582, 39, 231, 39);
		mainframe.getContentPane().add(panel_5);

		JLabel lblMovieTagValues = new JLabel("Movie Tag Values");
		lblMovieTagValues.setHorizontalAlignment(SwingConstants.CENTER);
		lblMovieTagValues.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		panel_5.add(lblMovieTagValues);

		JScrollPane scrollPane_5 = new JScrollPane();
		scrollPane_5.setBounds(26, 77, 135, 258);
		mainframe.getContentPane().add(scrollPane_5);

		JPanel panel_6 = new JPanel();
		scrollPane_5.setViewportView(panel_6);
		panel_6.setBorder(new CompoundBorder(new CompoundBorder(), null));

		genreJList = new JList<>();
		panel_6.add(genreJList);

		JScrollPane scrollPane_6 = new JScrollPane();
		scrollPane_6.setBounds(160, 77, 130, 258);
		mainframe.getContentPane().add(scrollPane_6);

		JPanel panel_7 = new JPanel();
		scrollPane_6.setViewportView(panel_7);

		countrylistj = new JList();
		panel_7.add(countrylistj);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(288, 77, 131, 258);
		mainframe.getContentPane().add(scrollPane_1);

		JPanel panel_8 = new JPanel();
		scrollPane_1.setViewportView(panel_8);

		locationlistj = new JList();
		panel_8.add(locationlistj);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(582, 77, 231, 210);
		mainframe.getContentPane().add(scrollPane_2);

		final JPanel panel_10 = new JPanel();
		panel_10.setBorder(UIManager.getBorder("TextField.border"));
		scrollPane_2.setViewportView(panel_10);

		taglistj = new JList();
		panel_10.add(taglistj);
		querypanel=new JPanel();

		JPanel panel_11 = new JPanel();
		panel_11.setBorder(UIManager.getBorder("TextField.border"));
		panel_11.setBounds(25, 364, 394, 39);
		mainframe.getContentPane().add(panel_11);

		JLabel lblNewLabel_1 = new JLabel("Search OR/AND");
		lblNewLabel_1.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		panel_11.add(lblNewLabel_1);

		searchmenu = new JComboBox();
		searchmenu.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		searchmenu.setBounds(332, 1, 100, 45);
		searchmenu.setMaximumRowCount(3);
		searchmenu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select AND, OR between attributes", "AND", "OR" }));
		panel_11.add(searchmenu);

		JPanel panel_9 = new JPanel();
		panel_9.setBorder(UIManager.getBorder("TextField.border"));
		panel_9.setBounds(418, 78, 164, 95);
		panel_9.setLayout(null);
		mainframe.getContentPane().add(panel_9);

		ratingmenu = new JComboBox();
		ratingmenu.setBounds(65, 10, 84, 27);
		ratingmenu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "=", ">", "<" }));
		panel_9.add(ratingmenu);

		JLabel lblNewLabel_3 = new JLabel("Rating");
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel_3.setBounds(10, 16, 45, 16);
		panel_9.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Value");
		lblNewLabel_4.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBounds(10, 59, 45, 16);
		panel_9.add(lblNewLabel_4);

		ratingtext = new JTextField();
		ratingtext.setBounds(65, 53, 84, 26);
		panel_9.add(ratingtext);
		ratingtext.setColumns(10);

		JPanel panel_13 = new JPanel();
		panel_13.setBounds(418, 173, 164, 114);
		panel_13.setLayout(null);
		mainframe.getContentPane().add(panel_13);

		reviewsmenu = new JComboBox();
		reviewsmenu.setBounds(59, 35, 90, 27);
		reviewsmenu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "=", ">", "<" }));
		panel_13.add(reviewsmenu);

		JLabel lblNewLabel_6 = new JLabel("Value");
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_6.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel_6.setBounds(0, 73, 58, 26);
		panel_13.add(lblNewLabel_6);

		reviewtext = new JTextField();
		reviewtext.setBounds(59, 73, 90, 26);
		panel_13.add(reviewtext);
		reviewtext.setColumns(10);
		
		JLabel lblNo = new JLabel("No.");
		lblNo.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNo.setBounds(15, 38, 43, 20);
		panel_13.add(lblNo);
		
		JPanel panel_18 = new JPanel();
		panel_18.setBackground(SystemColor.activeCaptionBorder);
		panel_18.setBounds(0, 0, 164, 27);
		panel_13.add(panel_18);
		
		JLabel lblReviews = new JLabel("Reviews");
		lblReviews.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		panel_18.add(lblReviews);

		JPanel panel_12 = new JPanel();
		panel_12.setBackground(SystemColor.activeCaptionBorder);
		panel_12.setBounds(418, 288, 164, 29);
		mainframe.getContentPane().add(panel_12);

		JLabel lblMovieYear = new JLabel("Movie Year");
		lblMovieYear.setHorizontalAlignment(SwingConstants.CENTER);
		lblMovieYear.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		panel_12.add(lblMovieYear);

		JPanel panel_14 = new JPanel();
		panel_14.setBorder(UIManager.getBorder("TextField.border"));
		panel_14.setBounds(418, 315, 164, 88);
		panel_14.setLayout(null);
		mainframe.getContentPane().add(panel_14);

		JLabel lblNewLabel_7 = new JLabel("From");
		lblNewLabel_7.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel_7.setBounds(15, 25, 37, 16);
		panel_14.add(lblNewLabel_7);

		JLabel lblNewLabel_8 = new JLabel("To");
		lblNewLabel_8.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel_8.setBounds(15, 57, 22, 16);
		panel_14.add(lblNewLabel_8);

		fromyear = new JTextField();
		fromyear.setBounds(57, 19, 92, 26);
		panel_14.add(fromyear);
		fromyear.setColumns(10);

		toyear = new JTextField();
		toyear.setBounds(57, 51, 92, 26);
		panel_14.add(toyear);
		toyear.setColumns(10);

		JPanel panel_15 = new JPanel();
		panel_15.setBounds(582, 288, 231, 88);
		panel_15.setLayout(null);
		mainframe.getContentPane().add(panel_15);

		JLabel lblNewLabel = new JLabel("Tag Weight");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel.setBounds(15, 13, 98, 23);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_15.add(lblNewLabel);

		tagmenu = new JComboBox();
		tagmenu.setMaximumRowCount(3);
		tagmenu.setBounds(129, 11, 79, 24);
		tagmenu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "=", ">", "<" }));
		panel_15.add(tagmenu);

		JLabel lblNewLabel_2 = new JLabel("Tag Value");
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(0, 45, 114, 23);
		panel_15.add(lblNewLabel_2);

		tagvalue = new JTextField();
		tagvalue.setBounds(129, 46, 79, 23);
		tagvalue.setColumns(10);
		panel_15.add(tagvalue);
		

		JPanel panel_17 = new JPanel();

		resultlistj = new JList();
		panel_17.add(resultlistj);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(582, 400, 231, 260);
		scrollPane.setViewportView(panel_17);
		mainframe.getContentPane().add(scrollPane);

		JPanel panel_19 = new JPanel();
		panel_19.setBackground(Color.LIGHT_GRAY);
		panel_19.setForeground(Color.WHITE);
		panel_19.setBounds(582, 374, 231, 29);
		mainframe.getContentPane().add(panel_19);

		JLabel lblResult = new JLabel("Result");
		lblResult.setForeground(Color.black);
		lblResult.setFont(new Font("Times New Roman", Font.BOLD, 20));
		panel_19.add(lblResult);
		
		querypanel=new JPanel();
		querypanel.setBounds(25, 402, 557, 258);
		mainframe.getContentPane().add(querypanel);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		querypanel.add(scrollPane_3);
		
		querytext = new JTextArea();
		querytext.setColumns(45);
		querytext.setRows(12);
		querytext.setLineWrap(true);
		scrollPane_3.setViewportView(querytext);
		
		JLabel lblQueryDisplay = new JLabel("Query Display");
		lblQueryDisplay.setBackground(SystemColor.activeCaptionBorder);
		scrollPane_3.setColumnHeaderView(lblQueryDisplay);
		
		btnExecuteQuery = new JButton("Execute Query");
		querypanel.add(btnExecuteQuery);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(SystemColor.activeCaptionBorder);
		panel_3.setBounds(25, 39, 136, 39);
		mainframe.getContentPane().add(panel_3);
		
		JLabel lblGenres = new JLabel("Genres");
		lblGenres.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		panel_3.add(lblGenres);
		
				btnGetCountry = new JButton("Get Country");
				btnGetCountry.setBounds(25, 335, 136, 29);
				mainframe.getContentPane().add(btnGetCountry);
				btnGetFilmingLocation = new JButton("Get Location");
				btnGetFilmingLocation.setBounds(160, 335, 131, 29);
				mainframe.getContentPane().add(btnGetFilmingLocation);
				btnGetTag = new JButton("Get Tags");
				btnGetTag.setBounds(285, 335, 133, 29);
				mainframe.getContentPane().add(btnGetTag);
		addingActionListeners();
		//Populating genres as initial step
		run();
	}
		//DIsplaying Genres
		public void displayGenres() {
			String genresquery = "select distinct genre from movie_genres";
			ResultSet resultSet = null;
			try {
				
				PreparedStatement preparedStatement = connection.prepareStatement(genresquery);
				resultSet = preparedStatement.executeQuery(genresquery);
				genremodel.clear();
				while (resultSet.next()) {
					String genre = resultSet.getString("genre");
					genremodel.addElement(genre);
				}
				preparedStatement.close();
				resultSet.close();
			} catch(Exception e) {
				System.err.println("Error! Cannot load genres.");
				e.printStackTrace();
			}

			genreJList.setModel(genremodel);

			MouseListener mouseListener = new MouseAdapter()
			{
				public void mouseClicked(MouseEvent event) {
					if (event.getClickCount() == 1) {
						genreList = (ArrayList<String>) genreJList.getSelectedValuesList();
					}
				}
			};
			genreJList.addMouseListener(mouseListener);
		}
		
		//Displaying countries
		public void displayCountry(ActionEvent event) {

			String genreid = getGenres();
			String query = genreid;
			String countriesquery = "select distinct country from movie_countries where movie_id in(" + query + ")";
			ResultSet resultSet = null;
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(countriesquery);
				resultSet = preparedStatement.executeQuery(countriesquery);
				while (resultSet.next()) {
					String country = resultSet.getString("country");
					countryModel.addElement(country);
				}
				preparedStatement.close();
				resultSet.close();
			} catch(SQLException e) {
				System.err.println("Error! Cannoot load countries.");
				e.printStackTrace();
			}

			countrylistj.setModel(countryModel);
			MouseListener mouseListener = new MouseAdapter()
			{
				public void mouseClicked(MouseEvent e)
				{
					if (e.getClickCount() == 1)
					{
						countrylist1 = (ArrayList<String>) countrylistj.getSelectedValuesList();
					}
				}
			};
			countrylistj.addMouseListener(mouseListener);
		}

		//Displaying filming locations
		public void displayFilmingLocations() {
			String locationsquery = "select distinct location1 from movie_locations where movie_id in(" + getQuery2() + ")";

			ResultSet resultSet = null;
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(locationsquery);
				resultSet = preparedStatement.executeQuery(locationsquery);
				while (resultSet.next()) {
					String location = resultSet.getString("location1");
					locationModel.addElement(location);
				}
				preparedStatement.close();
				resultSet.close();
			} catch(SQLException e) {
				System.err.println("Error! Cannot load filming locations.");
				e.printStackTrace();
			}

			locationlistj.setModel(locationModel);
			MouseListener mouseListener = new MouseAdapter()
			{
				public void mouseClicked(MouseEvent e)
				{
					if (e.getClickCount() == 1)
					{
						locationlist = (ArrayList<String>) locationlistj.getSelectedValuesList();
					}
				}
			};
			locationlistj.addMouseListener(mouseListener);
		}
		
		//Generating a list countries
		private String getCountries () {
			String selection_list = getSelectionList(countrylist1);
			if (!selection_list.isEmpty())
				return "select distinct movie_id from movie_countries where country in(" + selection_list + ")";
			return "";
		}

		//Generating a list of genres
		private String getGenres() {
			String selection_list = getSelectionList(genreList);
			if (!selection_list.isEmpty())
				return "select distinct movie_id from movie_genres where genre in(" + selection_list + ")";
			return "";
		}

		//Generating a list of filming locations
		private String getFilmingLocations() {
			String selection_list = getSelectionList(locationlist);
			if (!selection_list.isEmpty())
				return "select distinct movie_id from movie_locations where location1 in(" + selection_list + ")";
			return "";
		}
		
		
		//Setting all fields to default value
		public void clearselections() {
			
			querytext.setText("");
			ratingtext.setText("");
			countryModel.clear();
			tagmodel.clear();
			reviewtext.setText("");
			locationModel.clear();
			resultmodel.clear();
			fromyear.setText("");
			toyear.setText("");
		}

		//Getting critics rating 
		private String getCriticsRating() {
			String operation = "=";
			if (ratingmenu.getSelectedIndex() == 1) {
				operation = ">";
			} else if (ratingmenu.getSelectedIndex() == 2) {
				operation = "<";
			}
			if (!(ratingtext.getText().isEmpty())) {
				Integer value = Integer.parseInt(ratingtext.getText());
				return "select distinct movie_id from movies where rt_all_critics_rating " + operation + value;
			} else {
				return "";
			}
		}

		//Getting ratings from selected index
		private String getRatings() {
			String operation = "=";
			if (reviewsmenu.getSelectedIndex() == 1) {
				operation = ">";
			} else if (reviewsmenu.getSelectedIndex() == 2) {
				operation = "<";
			}
			if (!(reviewtext.getText().isEmpty())) {
				int value = Integer.parseInt(reviewtext.getText());
				return "select distinct movie_id from movies where rt_all_critics_num_reviews " + operation + value;
			} else {
				return "";
			}
		}

		//Getting movie years and forming queries with movie year constraint 
		private String getMovieYears() {
			String operation = "";
			int value_from = -1;
			int value_to = -1;
			if (!fromyear.getText().isEmpty())
				value_from = Integer.parseInt(fromyear.getText());
			if (!toyear.getText().isEmpty())
				value_to = Integer.parseInt(toyear.getText());

			if (value_from != -1 && value_to != -1) {
				operation = ">= " + value_from + " and movie_year <=" + value_to;
			} else if (value_to == -1 && value_from != -1) {
				operation = ">= " + value_from;
			} else if (value_from == -1 && value_to != -1) {
				operation = "<=" + value_to;
			}

			if (!operation.isEmpty()) {
				return "select distinct movie_id from movies where movie_year " + operation;
			}
			return "";
		}
		
		//Getting tag vaules and imposing tags value constraint 
		private String getTagsWeight() {
			String operation = "=";
			if (tagmenu.getSelectedIndex() == 1) {
				operation = ">";
			} else if (tagmenu.getSelectedIndex() == 2) {
				operation = "<";
			}
			if (!(tagvalue.getText().isEmpty())) {
				int value = Integer.parseInt(tagvalue.getText());
				return "select distinct movie_id from movie_tags where tag_weight " + operation + value;
			} else {
				return "";
			}
		}

		//Getting value from search OR/AND and executing either UNION/INTERSECTION
		private String getOperation() {
			if(searchmenu.getSelectedIndex()==0 || searchmenu.getSelectedIndex()==1) {
				return "IN";
			}
			else {
				return "UNION";
			}
		}

		//Forming query from selected country and genres
		private String getQuery1() {
			String ids_genre = getGenres();
			String ids_countries = getCountries();
			String operation = getOperation();

			String query2 = "";
			if (ids_countries.isEmpty()) {
				query2 = ids_genre;
			} else {
				if (operation.equals("IN")) {
					//query = ids_genre + " " + operation + " " +ids_countries;
					query2 = ids_genre + " and movie_id " + operation + " (" +ids_countries + ")";
				} else {
					query2 = ids_genre + operation + " (" +ids_countries + ")";
				}
			}
			return query2;
		}

		//Forming query from all three attributes- country, genre and filming location
		private String getQuery2() {
			String query3 = "";
			String operation = getOperation();
			String ids_locations = getFilmingLocations();
			if (ids_locations.isEmpty()) {
				query3 = getQuery1();
			} else {
				if (operation.equals("IN")) {
					//query = ids_genre + " " + operation + " " +ids_countries;
					query3 = getQuery1() + " and movie_id " + operation + " (select distinct movie_id from movie_locations)";
				} else {
					query3 = getQuery1() + operation + "(select distinct movie_id from movie_locations)";
				}
			}
			return query3;
		}

		
		//forming query for genre, country, filming location, rating, num of reviews, tag weight.
		 
		private String getQuery3() {
			String query4 = getQuery2();
			String operation = getOperation();
			ArrayList<String> queryList = new ArrayList<>();
			queryList.add(getFilmingLocations());
			queryList.add(getCriticsRating());
			queryList.add(getRatings());
			queryList.add(getMovieYears());
			queryList.add(getTagsWeight());

			for (int i=0; i<queryList.size(); i++) {
				String temp = queryList.get(i);
				if(!temp.isEmpty()) {
					if (operation.equals("IN"))
						query4  = query4 + " and movie_id " + operation + " (" + temp + ")";
					else {
						query4 = query4 + operation + " (" + temp + ")";
					}
				}
			}
			return query4;
		}
		
		
		//Forming queries with all attributes
		private String getQuery4() {
			String query5 = getQuery3();
			String operation = getOperation();
			if (taglist.isEmpty()) {
				return query5;
			} else {
				if (operation.equals("IN")) {
					//query = ids_genre + " " + operation + " " +ids_countries;
					if (!getQuery3().isEmpty())
						query5 = query5 + " and movie_id " + operation + "(select distinct movie_id from movie_tags)";
				} else {
					if (!getQuery3().isEmpty())
						query5 = query5 + operation + "(select distinct movie_id from movie_tags)";
				}
			}
			return query5;
		}

		//Displaying tag values
		public void getTagValues() {

			String get_tag_values = "select distinct tag_value from tags, movie_tags where movie_tags.tag_id=tags.tag_id and movie_tags.movie_id in(" + getQuery4() + ")";
			ResultSet resultSet = null;
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(get_tag_values);
				resultSet = preparedStatement.executeQuery(get_tag_values);
				while (resultSet.next()) {
					String tag_value = resultSet.getString("tag_value");
					tagmodel.addElement(tag_value);
				}
				preparedStatement.close();
				resultSet.close();
			} catch(SQLException e) {
				System.err.println("Error while populate genre values on the UI");
				e.printStackTrace();
			}

			taglistj.setModel(tagmodel);
			MouseListener mouseListener = new MouseAdapter()
			{
				public void mouseClicked(MouseEvent e)
				{
					if (e.getClickCount() == 1)
					{
						taglist = (ArrayList<String>) taglistj.getSelectedValuesList();
					}
				}
			};
			taglistj.addMouseListener(mouseListener);
		}

		//Displaying results from selection 
		public void displayQueryResult() {
			String query = getQuery4();
			String ids_set;
			if (!query.isEmpty())
				ids_set = query;
			else {
				ids_set = getGenres();
			}
			System.out.println(ids_set);
			String get_movie_details = "select distinct title, movie_year, rt_all_critics_rating from movies where movie_id in(" + ids_set + ")";
			querytext.setText(get_movie_details);

			ArrayList<String> resultList = new ArrayList<>();
			ResultSet resultSet = null;
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(get_movie_details);
				resultSet = preparedStatement.executeQuery(get_movie_details);
				while (resultSet.next()) {
					String title = resultSet.getString("title");
					String year = Integer.toString(resultSet.getInt("movie_year"));
					String rating = Float.toString(resultSet.getFloat("rt_all_critics_rating"));
					String temp = title + "  " + year + "  " + rating;
					resultList.add(temp);
					resultmodel.addElement(temp);
				}
				preparedStatement.close();
				resultSet.close();
			} catch(SQLException e) {
				System.err.println("Error while populate genre values on the UI");
				e.printStackTrace();
			}
			resultlistj.setModel(resultmodel);
		}
		
		//Beginning execution with populating genres
		public void run(){
			try {
				connection = connect.create_connection();
				displayGenres();
			} catch (SQLException e) {
				System.err.println("Errors occurs when communicating with the database server: " + e.getMessage());
			} catch (ClassNotFoundException e) {
				System.err.println("Cannot find the database driver");
			}
		}

			private String getSelectionList(ArrayList<String> list) {
			String selection_list  = "";
			if (list.size() != 0) {
				for (int i=0;i<list.size()-1;i++) {
					selection_list += "'" + list.get(i) + "'" + ", ";
				}
				selection_list += "'" + list.get(list.size()-1) +  "'";
			}
			return selection_list;
		}
	}
