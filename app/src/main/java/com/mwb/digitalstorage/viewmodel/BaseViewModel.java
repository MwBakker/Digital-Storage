package com.mwb.digitalstorage.viewmodel;

import com.mwb.digitalstorage.database.DAO;
import com.mwb.digitalstorage.misc.ImageProcessor;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;


public abstract class BaseViewModel extends ViewModel
{
    public ImageProcessor imgProcessor;
    public Executor executor;
    protected DAO dao;

    public BaseViewModel()
    {
        imgProcessor = new ImageProcessor();
        executor = Executors.newSingleThreadExecutor();
    }
}