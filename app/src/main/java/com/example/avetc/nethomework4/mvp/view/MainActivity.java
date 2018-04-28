package com.example.avetc.nethomework4.mvp.view;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.avetc.nethomework4.App;
import com.example.avetc.nethomework4.R;
import com.example.avetc.nethomework4.adapter.RecyclerAdapter;
import com.example.avetc.nethomework4.entities.User;
import com.example.avetc.nethomework4.image.GlideLoader;
import com.example.avetc.nethomework4.image.IImageLoader;
import com.example.avetc.nethomework4.mvp.presenter.MainPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;


public class MainActivity extends MvpAppCompatActivity implements MainView {
    private static final int PERMISSIONS_REQUEST_ID = 0;
    public static final int NUMBER_OF_REQUEST = 23401;
    private static final String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final String TAG = "MainActivity";

    @BindView(R.id.avatar)
    ImageView avaImageView;

    @BindView(R.id.nameTv)
    TextView nameTextView;

    @BindView(R.id.repos_rv)
    RecyclerView rv;

    @InjectPresenter
    MainPresenter presenter;

    @Inject
    IImageLoader<ImageView> imageLoader;

    RecyclerAdapter adapter;

    @ProvidePresenter
    public MainPresenter provideMainPresenter() {
        return new MainPresenter(AndroidSchedulers.mainThread());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        App.getInstance().getAppComponent().inject(presenter);
        App.getInstance().getAppComponent().inject(this);
        checkPermissions();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case PERMISSIONS_REQUEST_ID: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onPermissionsGranted();
                } else {
                    new AlertDialog.Builder(this)
                            .setTitle(R.string.permissons_required)
                            .setMessage(R.string.permissions_required_message)
                            .setPositiveButton("OK", (dialog, which) -> myRequestPermissions())
                            .setOnCancelListener(dialog -> myRequestPermissions())
                            .create()
                            .show();
                }
            }
        }
    }

    private void checkPermissions() {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                myRequestPermissions();
                return;
            }
        }
        onPermissionsGranted();
    }

    private void onPermissionsGranted() {
        presenter.onPermissionsGranted();
    }

    private void myRequestPermissions() {

        ActivityCompat.requestPermissions(this, permissions, PERMISSIONS_REQUEST_ID);
    }

    // MainView implementation
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

        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new RecyclerAdapter(presenter.getListPresenter());
        rv.setAdapter(adapter);
    }

    @Override
    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }




}
