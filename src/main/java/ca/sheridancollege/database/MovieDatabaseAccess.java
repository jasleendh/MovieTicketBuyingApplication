package ca.sheridancollege.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.beans.Movie;

@Repository
public class MovieDatabaseAccess {

	@Autowired
	protected NamedParameterJdbcTemplate jdbc;
	
	public void addMovie(Movie movie) {
		MapSqlParameterSource movieParameters = new MapSqlParameterSource();
		String query = "INSERT INTO movies "
				+ "(title, movieDuration, language,showTime, seatsAvailable)" 
				+ " VALUES " + 
				"(:title, :movieDuration, :language, :showTime, :seatsAvailable)";
movieParameters.addValue("title", movie.getTitle());
movieParameters.addValue("movieDuration", movie.getMovieDuration());
movieParameters.addValue("language", movie.getLanguage());
movieParameters.addValue("showTime", movie.getShowTime());
movieParameters.addValue("seatsAvailable", movie.getSeatsAvailable());
		
		jdbc.update(query, movieParameters);
	}
	
	public ArrayList<Movie> getMovies() {
		String query = "SELECT * FROM movies";
		ArrayList<Movie> movies = new ArrayList<Movie>();
		List<Map<String, Object>> rows = jdbc.queryForList(query, new HashMap<String,Object>());
		for (Map<String, Object> row: rows) {
			Movie m = new Movie();
			m.setMovieId((Long) row.get("movieId"));
			m.setTitle((String) row.get("title"));
			m.setMovieDuration((String) row.get("movieDuration"));
			m.setLanguage((String) row.get("language"));
			m.setShowTime((String) row.get("showTime"));
			m.setSeatsAvailable((Integer) row.get("seatsAvailable"));
			movies.add(m);
		}
		return movies;
	}
	
	public  Movie getMoviesbyIds(int movieId) {
		ArrayList<Movie> movies = new ArrayList<Movie>();
		String query = "SELECT * FROM movies"
				+ " WHERE movieId=:movieId"; 
		MapSqlParameterSource movieParameter = new MapSqlParameterSource();
		movieParameter.addValue("movieId", movieId);
		
		List<Map<String, Object>> rows = jdbc.queryForList(query, movieParameter);
		for (Map<String, Object> row: rows) {
			Movie m = new Movie();

			m.setMovieId((Long) row.get("movieId"));
			m.setTitle((String) row.get("title"));
			m.setMovieDuration((String) row.get("movieDuration"));
			m.setLanguage((String) row.get("language"));
			m.setShowTime((String) row.get("showTime"));
			m.setSeatsAvailable((Integer) row.get("seatsAvailable"));
			movies.add(m);
		}
		if (movies.size() > 0) {
			return movies.get(0);
		}
		return null ;
	}
	
	public void editMovie(Movie movie) {
		MapSqlParameterSource movieParameter = new MapSqlParameterSource();
		String query = "UPDATE movies SET title=:title,"
				+ " movieDuration=:movieDuration, language=:language, "
				+ " showTime=:showTime, seatsAvailable =:seatsAvailable "
				+ "WHERE movieId=:movieId";
		movieParameter.addValue("movieId", movie.getMovieId());
		movieParameter.addValue("title", movie.getTitle());
		movieParameter.addValue("movieDuration", movie.getMovieDuration());
		movieParameter.addValue("language", movie.getLanguage());
		movieParameter.addValue("showTime", movie.getShowTime());
		movieParameter.addValue("seatsAvailable", movie.getSeatsAvailable());
		
		jdbc.update(query, movieParameter);
	}
	
	public void deleteMovie(int movieId) {
		MapSqlParameterSource movieParameter = new MapSqlParameterSource();
		String query = "DELETE FROM movies WHERE movieId=:movieId";
		movieParameter.addValue("movieId", movieId);
		jdbc.update(query, movieParameter);
	}
	
	public  void updateSeats(int movieId, int seats, Movie movie) {
		MapSqlParameterSource movieParameter = new MapSqlParameterSource();
		String query = "UPDATE movies SET seatsAvailable =:seatsAvailable "
				+ "WHERE movieId=:movieId";
		movieParameter.addValue("movieId", movie.getMovieId());
		movieParameter.addValue("seatsAvailable", (movie.getSeatsAvailable() - seats));
		jdbc.update(query, movieParameter);
	}
	
}
