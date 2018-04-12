package com.example.avetc.nethomework4;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.avetc.nethomework4.adapter.RecyclerAdapter;
import com.example.avetc.nethomework4.image.GlideLoader;
import com.example.avetc.nethomework4.image.ImageLoader;
import com.example.avetc.nethomework4.presenter.MainPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;


public class MainActivity extends MvpAppCompatActivity implements MainView {

    @BindView(R.id.avatar)
    ImageView avaImageView;

    @BindView(R.id.nameTv)
    TextView nameTextView;

    @BindView(R.id.repos_rv)
    RecyclerView rv;

    @InjectPresenter
    MainPresenter presenter;
    ImageLoader<ImageView > imageLoader;

    RecyclerAdapter adapter;

    @ProvidePresenter
    public MainPresenter provideMainPresenter(){
        return new MainPresenter(AndroidSchedulers.mainThread());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
         imageLoader=new GlideLoader();
    }

    @Override
    public void setName(String username) {
        nameTextView.setText(username);
    }

    @Override
    public void loadAvatar(String url) {
     imageLoader.loadInto(url, avaImageView);
    }

    @Override
    public void updateList() {
        adapter.notifyDataSetChanged();
    }
    @Override
    public void init() {
      //  presenter.initMarksList();
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter= new RecyclerAdapter(presenter.getListPresenter());
        rv.setAdapter(adapter);

    }


}
