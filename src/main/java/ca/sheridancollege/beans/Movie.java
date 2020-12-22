package ca.sheridancollege.beans;

import java.io.Serializable;

public class Movie implements Serializable {

	private Long movieId;
	private String title;
	private String movieDuration;
	private String language;
	private String showTime;
	private int seatsAvailable;
	
	public Movie() {
		
	}
	


	public Movie(Long movieId, String title, String movieDuration, String language, String showTime,
			int seatsAvailable) {
		this.movieId = movieId;
		this.title = title;
		this.movieDuration = movieDuration;
		this.language = language;
		this.showTime = showTime;
		this.seatsAvailable = seatsAvailable;
	}



	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMovieDuration() {
		return movieDuration;
	}

	public void setMovieDuration(String movieDuration) {
		this.movieDuration = movieDuration;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getShowTime() {
		return showTime;
	}

	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}

	public int getSeatsAvailable() {
		return seatsAvailable;
	}

	public void setSeatsAvailable(int seatsAvailable) {
		this.seatsAvailable = seatsAvailable;
	}



	public Long getMovieId() {
		return movieId;
	}



	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}



	@Override
	public String toString() {
		return "Movie [ title=" + title + ", movieDuration=" + movieDuration + ", language="
				+ language + ", showTime=" + showTime + ", seatsAvailable=" + seatsAvailable
				+ "]";
	}

	
	
	
}
