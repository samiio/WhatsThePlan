package uk.ac.reading.student.zj018597.whatstheplan.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import uk.ac.reading.student.zj018597.whatstheplan.R;
import uk.ac.reading.student.zj018597.whatstheplan.db.RestaurantEntity;
import uk.ac.reading.student.zj018597.whatstheplan.ui.RestaurantsFragment;

/**
 * {@link RecyclerView.Adapter} that displays {@link List<RestaurantEntity>} in
 * the {@link RestaurantsFragment}.
 */
public class RestaurantListAdapter extends
        RecyclerView.Adapter<RestaurantListAdapter.RestaurantViewHolder> {

    private final LayoutInflater mInflater;
    private List<RestaurantEntity> mRestaurants; // Cached copy of restaurants

    public RestaurantListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new RestaurantViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        if (mRestaurants != null) {
            RestaurantEntity current = mRestaurants.get(position);
            holder.tvRestaurant.setText(current.getName());
        } else {    // Covers the case of data not being ready yet.
            holder.tvRestaurant.setText(R.string.no_restaurant);
        }
    }

    /**
     * getItemCount() is called many times, and when it is first called,
     * {@link #mRestaurants} has not been updated (means initially, it's null, and we can't return null).
     */
    @Override
    public int getItemCount() {
        if (mRestaurants != null) {
            return mRestaurants.size();
        } else return 0;
    }

    public void setRestaurants(List<RestaurantEntity> restaurants) {
        mRestaurants = restaurants;
        notifyDataSetChanged();
    }

    class RestaurantViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvRestaurant;

        private RestaurantViewHolder(View itemView) {
            super(itemView);
            tvRestaurant = itemView.findViewById(R.id.tv_recycler_view);
        }
    }
}
