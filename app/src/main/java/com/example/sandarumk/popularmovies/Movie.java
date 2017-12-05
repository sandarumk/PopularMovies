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
    private String id;

    public Movie(String title, String originalTitle, String plotSypnosis, String rating, String releaseDate, String posterPath,String id) {
        this.title = title;
        this.originalTitle = originalTitle;
        this.plotSypnosis = plotSypnosis;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.id = id;
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

    public String getID() {
        return id;
    }


}
