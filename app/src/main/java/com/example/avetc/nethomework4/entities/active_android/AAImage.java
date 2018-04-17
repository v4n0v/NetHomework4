package com.example.avetc.nethomework4.entities.active_android;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.File;


@Table(name = "images")
public class AAImage extends Model{

        @Column(name = "url")
        public String url;

        @Column(name = "file")
        public File file;
}
