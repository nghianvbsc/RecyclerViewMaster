package cf.bscenter.recyclerviewmaster.adapters.intalize;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import cf.bscenter.recyclerviewmaster.R;

/**
 * Created by nghianv on 05/08/2016
 */
public class BaseListAdapter extends BaseAdapter {

    private final BaseAdapter mAdapter;
    private boolean mProgressBarVisible;

    public BaseListAdapter(@NonNull Context context, @NonNull BaseAdapter adapter) {
        super(context);
        mAdapter = adapter;
    }

    /**
     * @param progressBarVisible : is show load more row
     */
    public void displayLoadMore(boolean progressBarVisible) {
        mProgressBarVisible = progressBarVisible;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_load_more, parent, false);
            return new LoadMoreHolder(view);
        }
        return mAdapter.onCreateViewHolder(parent, viewType - 1);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == 0) {
            onBindLoadMoreHolder((LoadMoreHolder) holder);
        } else {
            mAdapter.onBindViewHolder(holder, position);
        }
    }

    private void onBindLoadMoreHolder(LoadMoreHolder holder) {
        holder.mProgressBar.setVisibility(mProgressBarVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return mAdapter.getItemCount() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return 0;
        }
        return mAdapter.getItemViewType(position) + 1;
    }

    class LoadMoreHolder extends RecyclerView.ViewHolder {

        private final ProgressBar mProgressBar;

        public LoadMoreHolder(View itemView) {
            super(itemView);
            if (itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams layoutParams =
                        (StaggeredGridLayoutManager.LayoutParams) itemView.getLayoutParams();
                layoutParams.setFullSpan(true);
            }
            mProgressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }
}