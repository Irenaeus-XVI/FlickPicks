package com.example.app.Adapters;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app.Model.Movie;
import com.example.app.NotificationJobService;
import com.example.app.R;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    public static final String EXTRA_IS_FAVORITE = "is_favorite";
    private static final int JOB_ID = 0;
    private final JobScheduler mScheduler;
    private final Context mContext;
    private List<Movie> movieList;

    public MovieAdapter(Context context, List<Movie> movieList) {
        this.mContext = context;
        this.movieList = movieList;
        this.mScheduler = (JobScheduler) mContext.getSystemService(Context.JOB_SCHEDULER_SERVICE);
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);

        holder.movieTitle.setText(movie.getTitle());
        holder.movieDescription.setText(movie.getOverview());
        holder.movieRating.setRating(movie.getVoteAverage() / 2);

        holder.favIcon.setOnClickListener(new View.OnClickListener() {
            boolean isFav = false;

            @Override
            public void onClick(View v) {

                ImageView favIcon = holder.favIcon;
                if (!isFav) {
                    favIcon.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                    isFav = true;

                    PersistableBundle extras = new PersistableBundle();
                    extras.putBoolean(EXTRA_IS_FAVORITE, isFav);

                    ComponentName serviceName = new ComponentName(mContext.getPackageName(),
                            NotificationJobService.class.getName());
                    JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, serviceName).setExtras(extras);
                    JobInfo myJobInfo = builder.build();
                    mScheduler.schedule(myJobInfo);
                } else {
                    favIcon.setColorFilter(Color.parseColor("grey"), PorterDuff.Mode.SRC_IN);
                    isFav = false;
                    PersistableBundle extras = new PersistableBundle();
                    extras.putBoolean(EXTRA_IS_FAVORITE, isFav);

                    ComponentName serviceName = new ComponentName(mContext.getPackageName(),
                            NotificationJobService.class.getName());
                    JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, serviceName).setExtras(extras);
                    JobInfo myJobInfo = builder.build();
                    mScheduler.schedule(myJobInfo);
                }
            }
        });

//        Log.d("MovieAdapter", "isCalled " + "once");
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath())
                .placeholder(R.drawable.ic_placeholder)
                .into(holder.movieImage);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        // view holder code


        private final ImageView movieImage;
        private final ImageView favIcon;
        private final TextView movieTitle;
        private final TextView movieDescription;
        private final RatingBar movieRating;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            movieImage = itemView.findViewById(R.id.movie_image);
            movieTitle = itemView.findViewById(R.id.movie_title);
            movieDescription = itemView.findViewById(R.id.movie_description);
            movieRating = itemView.findViewById(R.id.movie_rating);
            favIcon = itemView.findViewById(R.id.fav_icon);
        }
    }


}
