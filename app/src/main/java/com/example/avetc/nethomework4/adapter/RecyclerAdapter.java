package com.example.avetc.nethomework4.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.avetc.nethomework4.R;
import com.example.avetc.nethomework4.entities.Repository;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by avetc on 11.04.2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{
    private final String TAG = "SelectCarAdapter";
    private IListPresenter presenter;

    public RecyclerAdapter(IListPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repo, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.pos = position;
        presenter.bindView(holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getViewCount();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements IListRawView, View.OnClickListener {
        int pos = -1;
        @BindView(R.id.repo_header)
        TextView headerTextView;

        @BindView(R.id.repo_url)
        TextView urlTextView;


        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
          //  headerTextView.setOnClickListener(this);
        }

        @Override
        public int getPos() {
            return pos;
        }

        @Override
        public void setText(Repository repository) {
            headerTextView.setText(repository.getName());
            urlTextView.setText(repository.getHtmlUrl());
        }




        @Override
        public void onClick(View view) {
            int pos = getLayoutPosition();
            Log.d(TAG, "click "+pos);
            presenter.selectItem(pos);
        }
    }
}
