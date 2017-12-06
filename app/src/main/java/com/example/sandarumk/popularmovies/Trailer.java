package com.example.sandarumk.popularmovies;

/**
 * Created by dinu on 12/3/17.
 */

public class Trailer {
    private String trailerURL;
    private String trailerName;
    private String id;

    public Trailer(String trailerURL, String trailerName, String id) {
        this.trailerURL = trailerURL;
        this.trailerName = trailerName;
        this.id = id;
    }

    public String getTrailerURL() {
        return trailerURL;
    }

    public void setTrailerURL(String trailerURL) {
        this.trailerURL = trailerURL;
    }

    public String getTrailerName() {
        return trailerName;
    }

    public void setTrailerName(String trailerName) {
        this.trailerName = trailerName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
