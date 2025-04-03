package com.example.event_hub;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FeedbackListFragment extends Fragment {

    private RecyclerView recyclerView;
    private FeedbackAdapter feedbackAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback_list, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_feedback);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<FeedbackItem> feedbackList = getMockFeedbackList();

        feedbackAdapter = new FeedbackAdapter(feedbackList, feedbackItem -> {
            Toast.makeText(getContext(), "Feedback for " + feedbackItem.getEventName() + " selected", Toast.LENGTH_SHORT).show();

            FeedbackFragment feedbackFragment = new FeedbackFragment();
            Bundle args = new Bundle();
            args.putString("event_name", feedbackItem.getEventName());
            args.putString("event_date", feedbackItem.getDate());
            args.putFloat("rating", feedbackItem.getRating());
            feedbackFragment.setArguments(args);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, feedbackFragment)
                    .addToBackStack(null)
                    .commit();
        });

        recyclerView.setAdapter(feedbackAdapter);

        return view;
    }

    private List<FeedbackItem> getMockFeedbackList() {
        List<FeedbackItem> list = new ArrayList<>();
        list.add(new FeedbackItem("Squid Game Live", "May 15, 2023", 4.5f, true));
        list.add(new FeedbackItem("Taylor Swift: The Eras Tour", "June 2, 2023", 5.0f, true));
        list.add(new FeedbackItem("Community Festival", "April 30, 2023", 0f, false));
        return list;
    }

    public static class FeedbackItem {
        private String eventName;
        private String date;
        private float rating;
        private boolean hasRated;

        public FeedbackItem(String eventName, String date, float rating, boolean hasRated) {
            this.eventName = eventName;
            this.date = date;
            this.rating = rating;
            this.hasRated = hasRated;
        }

        public String getEventName() {
            return eventName;
        }

        public String getDate() {
            return date;
        }

        public float getRating() {
            return rating;
        }

        public boolean hasRated() {
            return hasRated;
        }
    }

    // Adapter for feedback list
    public static class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder> {

        private List<FeedbackItem> feedbackList;
        private OnFeedbackItemClickListener listener;

        public static interface OnFeedbackItemClickListener {
            void onFeedbackItemClick(FeedbackItem feedbackItem);
        }

        public FeedbackAdapter(List<FeedbackItem> feedbackList, OnFeedbackItemClickListener listener) {
            this.feedbackList = feedbackList;
            this.listener = listener;
        }

        @NonNull
        @Override
        public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feedback, parent, false);
            return new FeedbackViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position) {
            FeedbackItem feedbackItem = feedbackList.get(position);
            holder.bind(feedbackItem, listener);
        }

        @Override
        public int getItemCount() {
            return feedbackList.size();
        }

        class FeedbackViewHolder extends RecyclerView.ViewHolder {

            public FeedbackViewHolder(@NonNull View itemView) {
                super(itemView);
            }

            public void bind(final FeedbackItem feedbackItem, final OnFeedbackItemClickListener listener) {


                itemView.setOnClickListener(v -> listener.onFeedbackItemClick(feedbackItem));
            }
        }
    }
}
