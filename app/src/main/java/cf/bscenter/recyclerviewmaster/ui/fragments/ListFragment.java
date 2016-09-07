package cf.bscenter.recyclerviewmaster.ui.fragments;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cf.bscenter.recyclerviewmaster.R;
import cf.bscenter.recyclerviewmaster.adapters.RecyclerViewAdapter;
import cf.bscenter.recyclerviewmaster.adapters.intalize.BaseListAdapter;
import cf.bscenter.recyclerviewmaster.ui.intalize.BaseListFragment;

/**
 * Created by nghianv on 05/08/2016
 */
public class ListFragment extends BaseListFragment {

    private final List<Integer> mIntegerList = new ArrayList<>();
    private int lastIndex = -1;

    @Override
    protected int getLayout() {
        return R.layout.fragment_list;
    }

    @Override
    protected RecyclerView.Adapter initAdapter() {
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), mIntegerList);
        return new BaseListAdapter(getContext(), recyclerViewAdapter);
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }


    @Override
    protected void main() {
        loadData(FIRST_LOAD);
    }

    @Override
    protected void initView() {
        super.initView();
    }

    /**
     *
     * @param loadType = REFRESH || LOAD_MORE || REFRESH
     */
    private void loadData(final int loadType) {
        onPreRequest(loadType);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (loadType == REFRESH) {
                    mIntegerList.clear();
                    lastIndex = -1;
                }
                int start = lastIndex + 1;
                int end = start + 20;

                for (int index = start; index <= end; index++) {
                    mIntegerList.add(index);
                }
                lastIndex = end;
                adapter.notifyDataSetChanged();
                onPostRequest();
            }
        }, 1000);
    }

    @Override
    protected void onLoadMore() {
        super.onLoadMore();
        loadData(LOAD_MORE);
    }

    @Override
    protected void onRefresh() {
        super.onRefresh();
        loadData(REFRESH);
    }
}
