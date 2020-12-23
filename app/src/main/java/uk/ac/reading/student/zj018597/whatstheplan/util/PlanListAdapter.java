package uk.ac.reading.student.zj018597.whatstheplan.util;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import uk.ac.reading.student.zj018597.whatstheplan.R;
import uk.ac.reading.student.zj018597.whatstheplan.db.PlanEntity;
import uk.ac.reading.student.zj018597.whatstheplan.ui.PlansFragment;

/**
 * Displays {@link List<PlanEntity>} in the {@link PlansFragment}.
 */
public class PlanListAdapter extends RecyclerView.Adapter<PlanListAdapter.PlanViewHolder> {

    private final LayoutInflater mInflater;
    private List<PlanEntity> mPlans;    // Cached copy of plans

    public PlanListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new PlanViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanViewHolder holder, int position) {
        if (mPlans != null) {
            PlanEntity current = mPlans.get(position);
            holder.tvPlan.setText(current.getName());
        } else {
            // Covers the case of data not being ready yet.
            holder.tvPlan.setText(R.string.no_plan);
        }
    }

    /**
     * getItemCount() is called many times, and when it is first called,
     * {@link #mPlans} has not been updated (means initially, it's null, and we can't return null).
     */
    @Override
    public int getItemCount() {
        if (mPlans != null) {
            return mPlans.size();
        }
        else return 0;
    }

    public void setPlans(List<PlanEntity> plans) {
        mPlans = plans;
        notifyDataSetChanged();
    }

    static class PlanViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvPlan;

        private PlanViewHolder(View itemView) {
            super(itemView);
            tvPlan = itemView.findViewById(R.id.tv_recycler_view);
        }
    }
}
