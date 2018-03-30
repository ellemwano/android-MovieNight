package com.mwano.lauren.popular_movies.Data;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

import com.mwano.lauren.popular_movies.model.Movie;
import com.mwano.lauren.popular_movies.model.Video;
import com.mwano.lauren.popular_movies.utils.JsonUtils;
import com.mwano.lauren.popular_movies.utils.MovieApi;
import com.mwano.lauren.popular_movies.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class VideoLoader extends AsyncTaskLoader<ArrayList<Video>> {

    public static final int VIDEO_QUERY_LOADER = 20;
    public static final String MOVIE_ID = "movie_id";
    Bundle mArgs;
    ArrayList<Video> mVideoData;

    // Constructor
    public VideoLoader(Context context, Bundle args) {
        super(context);
        mArgs = args;
    }

    @Override
    protected void onStartLoading() {
        if (mVideoData != null) {
            // Use cached data
            deliverResult(mVideoData);
        } else {
            // Load data as there's none
            forceLoad();
        }
    }

    @Override
    public ArrayList<Video> loadInBackground() {
        String movieId = mArgs.getString(MOVIE_ID);
        String id = String. valueOf(getId());

        if (movieId != null && movieId.equals("")) {
            return null;
        }
        URL videoRequestUrl = MovieApi.buildVideoUrl(movieId);

        try {
            if (id.equals(MOVIE_ID)) {
                videoRequestUrl = MovieApi.buildVideoUrl(MOVIE_ID);

            } else {
                throw new UnsupportedOperationException("Unknown url: " + videoRequestUrl);
            }
            String jsonResponse = NetworkUtils.httpConnect(videoRequestUrl);
            return JsonUtils.parseVideoJson(jsonResponse);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}