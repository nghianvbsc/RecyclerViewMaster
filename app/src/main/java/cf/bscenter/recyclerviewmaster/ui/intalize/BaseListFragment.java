package cf.bscenter.recyclerviewmaster.ui.intalize;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import cf.bscenter.recyclerviewmaster.R;
import cf.bscenter.recyclerviewmaster.adapters.intalize.BaseListAdapter;

/**
 * Created by nghianv on 05/08/2016
 */
public abstract class BaseListFragment extends Fragment {

    public static final int FIRST_LOAD = 0;
    public static final int LOAD_MORE = 1;
    public static final int REFRESH = 2;

    private static final String TAG = BaseListFragment.class.getSimpleName();

    protected View viewFragment;
    protected RecyclerView recyclerView;
    protected RecyclerView.Adapter adapter;
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected ProgressBar progressBar;

    private boolean isLoading;

    protected void onLoadMore() {
        isLoading = true;
    }

    protected void onRefresh() {
        isLoading = true;
    }

    protected abstract int getLayout();

    protected abstract RecyclerView.Adapter initAdapter();

    protected abstract RecyclerView.LayoutManager getLayoutManager();

    protected void initView() {
        recyclerView = (RecyclerView) viewFragment.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(getLayoutManager());
        swipeRefreshLayout = (SwipeRefreshLayout) viewFragment.findViewById(R.id.swipeRefreshLayout);
        progressBar = (ProgressBar) viewFragment.findViewById(R.id.progressBar);
        adapter = initAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new BaseScrollListener(recyclerView.getLayoutManager()) {
            @Override
            public void onLoadMore() {
                if (!isLoading) {
                    BaseListFragment.this.onLoadMore();
                }
            }
        });

        if (swipeRefreshLayout == null) {
            return;
        }
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(),
                R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isLoading) {
                    swipeRefreshLayout.setRefreshing(false);
                    return;
                }
                BaseListFragment.this.onRefresh();
            }
        });
    }

    /**
     * @param type = FIRST_LOAD || LOAD_MORE || REFRESH
     */
    protected void onPreRequest(int type) {
        isLoading = true;
        if (type == REFRESH) {
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.setRefreshing(true);
            }
        } else if (type == LOAD_MORE) {
            displayLoadMore(true);
        } else {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    /**
     * handle after send request
     */
    protected void onPostRequest() {
        isLoading = false;
        progressBar.setVisibility(View.GONE);
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
        displayLoadMore(false);
    }

    protected abstract void main();

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        viewFragment = inflater.inflate(getLayout(), container, false);
        initView();
        main();
        return viewFragment;
    }

    private void displayLoadMore(boolean display) {
        if (adapter instanceof BaseListAdapter) {
            ((BaseListAdapter) adapter).displayLoadMore(display);
            try {
                adapter.notifyItemChanged(adapter.getItemCount() - 1);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }
}
