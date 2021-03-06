package com.vsokoltsov.stackqa.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.models.Question;
import com.vsokoltsov.stackqa.util.MaterialProgressBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vsokoltsov on 02.01.16.
 */
public class RVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    public List<Question> questions;
    private Activity activity;
    private List<Question> orig;

    private final int VISIBLE_THRESHOLD = 5;

    private final int ITEM_VIEW_TYPE_BASIC = 0;
    private final int ITEM_VIEW_TYPE_FOOTER = 1;

    private int firstVisibleItem, visibleItemCount, totalItemCount, previousTotal = 0;
    private boolean loading = true;


    public RVAdapter(List<Question> questions, Activity activity){
        this.questions = questions;
        this.activity = activity;
    }

    public RVAdapter(List<Question> questions, Activity activity, RecyclerView recyclerView,
                     final OnLoadMoreListener onLoadMoreListener){
        this.questions = questions;
        this.activity = activity;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    totalItemCount = linearLayoutManager.getItemCount();
                    visibleItemCount = linearLayoutManager.getChildCount();
                    firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if (totalItemCount > previousTotal) {
                            loading = false;
                            previousTotal = totalItemCount;
                        }
                    }
                    if (!loading && (totalItemCount - visibleItemCount)
                            <= (firstVisibleItem + VISIBLE_THRESHOLD) && (totalItemCount != visibleItemCount)) {
                        // End has been reached

                        addQuestion(null);
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });
        }
    }

    public void addQuestion(Question question) {
        if (!questions.contains(question)) {
            questions.add(question);
            notifyItemInserted(questions.size() - 1);
        }
    }

    public void removeItem(Question item) {
        int indexOfItem = questions.indexOf(item);
        if (indexOfItem != -1) {
            this.questions.remove(indexOfItem);
            notifyItemRemoved(indexOfItem);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return questions.get(position) != null ? ITEM_VIEW_TYPE_BASIC : ITEM_VIEW_TYPE_FOOTER;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_VIEW_TYPE_BASIC) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.questions_list_row, parent, false);
            QuestionsViewHolder qvh = new QuestionsViewHolder(v, this);
            return qvh;
        }
        else if (viewType == ITEM_VIEW_TYPE_FOOTER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_progress_bar, parent, false);
            ProgressViewHolder pvh = new ProgressViewHolder(v);
            return pvh;
        }
        else {
            throw new IllegalStateException("Invalid type, this type ot items");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ITEM_VIEW_TYPE_BASIC) {
            QuestionsViewHolder questionHolder = (QuestionsViewHolder) holder;
            questionHolder.questionId = questions.get(position).getID();
            questionHolder.adapter = this;
            questionHolder.title.setText(questions.get(position).getTitle());
            questionHolder.rate.setText(String.valueOf(questions.get(position).getRate()));
            questionHolder.category.setText(questions.get(position).getCategory().getTitle());
            questionHolder.createdAt.setText(questions.get(position).getCreatedAt());
            questionHolder.answersCount.setText(String.valueOf(questions.get(position).getAnswersCount()));
            questionHolder.commentsCount.setText(String.valueOf(questions.get(position).getCommentsCount()));
            questionHolder.views.setText(String.valueOf(questions.get(position).getViews()));
        }
        else {
//            ((ProgressViewHolder) holder).progressBar.;
        }
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public Question getQuestion(int position) {
        return questions.get(position);
    }


    public static class QuestionsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CardView cv;

        private int questionId;
        private RVAdapter adapter;
        private TextView title;
        private TextView rate;
        private TextView category;
        private TextView createdAt;
        private TextView answersCount;
        private TextView commentsCount;
        private TextView views;
        private QuestionViewHolderCallbacks callbacks;

        QuestionsViewHolder(View itemView, RVAdapter newAdapter) {
            super(itemView);
            adapter = newAdapter;
            cv = (CardView) itemView.findViewById(R.id.cardView);
            callbacks = (QuestionViewHolderCallbacks) adapter.activity;
            cv.setOnClickListener(this);
            title =  (TextView) itemView.findViewById(R.id.title);
            rate = (TextView) itemView.findViewById(R.id.rate);
            category = (TextView) itemView.findViewById(R.id.category);
            createdAt = (TextView) itemView.findViewById(R.id.createdAt);
            answersCount = (TextView) itemView.findViewById(R.id.answersCount);
            commentsCount = (TextView) itemView.findViewById(R.id.commentsCount);
            views = (TextView) itemView.findViewById(R.id.viewsCount);
        }


        @Override
        public void onClick(View v) {
            callbacks.onItemClicked(this.adapter.questions.get(getAdapterPosition()));
        }

        public interface QuestionViewHolderCallbacks {
            void onItemClicked(Question question);
        }


    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public MaterialProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (MaterialProgressBar) v.findViewById(R.id.footer_progress_bar);
            progressBar.setDrawingCacheBackgroundColor(Color.parseColor("#FFC107"));
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final List<Question> results = new ArrayList<Question>();
                if (orig == null)
                    orig = questions;
                if (constraint != null) {
                    if (orig != null & orig.size() > 0) {
                        for (final Question g : orig) {
                            if (g.getTitle().toLowerCase().contains(constraint.toString().toLowerCase()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                questions = (ArrayList<Question>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
