package cf.bscenter.recyclerviewmaster.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cf.bscenter.recyclerviewmaster.R;
import cf.bscenter.recyclerviewmaster.adapters.intalize.BaseAdapter;

/**
 * Created by nghianv on 05/08/2016
 */
public class RecyclerViewAdapter extends BaseAdapter<RecyclerViewAdapter.ViewHolder> {

    private List<Integer> mIntegerList;

    public RecyclerViewAdapter(@NonNull Context context, List<Integer> integerList) {
        super(context);
        mIntegerList = integerList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.item_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTvItem.setText("Item " + mIntegerList.get(position));
    }

    @Override
    public int getItemCount() {
        return mIntegerList == null ? 0 : mIntegerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTvItem;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvItem = (TextView) itemView.findViewById(R.id.tvItem);
        }
    }
}
