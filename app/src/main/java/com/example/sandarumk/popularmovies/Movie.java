package com.example.sandarumk.popularmovies;

/**
 * Created by dinu on 9/18/17.
 */

public class Movie {

    private String title;
    private String originalTitle;
    private String plotSypnosis;
    private String rating;
    private String releaseDate;
    private String posterPath;

    public Movie(String title, String originalTitle, String plotSypnosis, String rating, String releaseDate, String posterPath) {
        this.title = title;
        this.originalTitle = originalTitle;
        this.plotSypnosis = plotSypnosis;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getPlotSypnosis() {
        return plotSypnosis;
    }

    public String getRating() {
        return rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

}
