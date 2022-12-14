package com.example.project_group_6;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class ComplaintsAdaptor extends RecyclerView.Adapter<ComplaintsAdaptor.ViewHolder>{
    private ArrayList<String> complaintSet;
    private ArrayList<String> cookIDs;
    private ItemClickListener complaintClickListener;
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView complaint;
        private TextView cookID;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            complaint = (TextView) view.findViewById(R.id.complain_text);
            complaint.setOnClickListener(this);
            cookID = (TextView) view.findViewById(R.id.complain_cook);
            cookID.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (complaintClickListener != null) complaintClickListener.onItemClick(view, getAdapterPosition());
        }

        public TextView getComplaint() {
            return complaint;
        }
        public TextView getCook() {
            return cookID;
        }
    }


    public ComplaintsAdaptor(ArrayList<String> dataSet,ArrayList<String> cooks) {
        this.complaintSet = dataSet;
        this.cookIDs  = cooks;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.complaints_row, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getComplaint().setText(getItem(position));
        viewHolder.getCook().setText(getCook(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return complaintSet.size();
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return complaintSet.get(id);
    }
    String getCook(int id) {
        return cookIDs.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.complaintClickListener = itemClickListener;
    }


    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }


}