package com.example.app.Adapters;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app.Model.Tv;
import com.example.app.NotificationJobService;
import com.example.app.R;

import java.util.List;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.TvViewHolder> {

    public static final String EXTRA_IS_FAVORITE = "is_favorite";
    private static final int JOB_ID = 0;
    private final JobScheduler mScheduler;
    private final Context mContext;
    private List<Tv> tvShowList;

    public TvAdapter(Context context, List<Tv> tvShowList) {
        this.mContext = context;
        this.tvShowList = tvShowList;
        this.mScheduler = (JobScheduler) mContext.getSystemService(Context.JOB_SCHEDULER_SERVICE);
    }

    public void setTvShowList(List<Tv> tvShowList) {
        this.tvShowList = tvShowList;
    }

    @NonNull
    @Override
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tv_card, parent, false);
        return new TvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvViewHolder holder, int position) {
        Tv tvShow = tvShowList.get(position);

        holder.tvShowTitle.setText(tvShow.getName());
        holder.tvShowDescription.setText(tvShow.getOverview());
        holder.tvShowRating.setRating(tvShow.getVoteAverage() / 2);

        Log.d("TvAdapter", "Name of first TV show: " + tvShowList.get(0).getOverview());

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
                .load("https://image.tmdb.org/t/p/w500" + tvShow.getPosterPath())
                .placeholder(R.drawable.ic_placeholder)
                .into(holder.tvShowImage);
    }

    @Override
    public int getItemCount() {
        return tvShowList.size();
    }

    public static class TvViewHolder extends RecyclerView.ViewHolder {
        // view holder code
        private final ImageView tvShowImage;
        private final ImageView favIcon;
        private final TextView tvShowTitle;
        private final TextView tvShowDescription;
        private final RatingBar tvShowRating;

        public TvViewHolder(@NonNull View itemView) {
            super(itemView);

            tvShowImage = itemView.findViewById(R.id.tv_image);
            tvShowTitle = itemView.findViewById(R.id.tv_title);
            tvShowDescription = itemView.findViewById(R.id.tv_description);
            tvShowRating = itemView.findViewById(R.id.tv_rating);
            favIcon = itemView.findViewById(R.id.fav_icon);
        }
    }


}





