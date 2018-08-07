package dau.themovieapp.app.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dau.themovieapp.R;
import dau.themovieapp.app.utils.Constants;
import dau.themovieapp.domain.model.Movie;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    private List<Movie> mMovies;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_movie)
        ImageView ivMovie;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_year)
        TextView tvYear;
        @BindView(R.id.tv_overview)
        TextView tvOverview;

        ViewHolder(View root) {
            super(root);
            ButterKnife.bind(this, root);
        }
    }

    public MoviesAdapter(List<Movie> movies) {
        this.mMovies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_movie, parent, false);

        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Movie movie = mMovies.get(position);

        holder.tvTitle.setText(movie.getTitle());
        holder.tvYear.setText(movie.getYear());
        if (movie.getOverview() != null && !TextUtils.isEmpty(movie.getOverview())) {
            holder.tvOverview.setText(movie.getOverview());
        } else {
            holder.tvOverview.setVisibility(View.GONE);
        }
        if (movie.getPosterPath() != null && !TextUtils.isEmpty(movie.getPosterPath())) {
            Picasso.with(mContext)
                    .load(Constants.BASE_POSTER_PATH.concat(movie.getPosterPath()))
                    .into(holder.ivMovie);
        } else {
            holder.ivMovie.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }
}
