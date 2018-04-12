package com.example.avetc.nethomework4.adapter;

/**
 * Created by avetc on 11.04.2018.
 */

public interface IListPresenter {

        int pos = -1;
        void bindView (IListRawView view);
        int getViewCount();
        void selectItem(int pos);


}
