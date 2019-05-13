package com.mwb.digitalstorage.viewmodel;

import com.mwb.digitalstorage.misc.ImageProcessor;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import androidx.lifecycle.ViewModel;

public class BaseViewModel extends ViewModel
{
    protected ImageProcessor imgProcessor;
    protected Executor executor;


    public BaseViewModel()
    {
        imgProcessor = new ImageProcessor();
        executor = Executors.newSingleThreadExecutor();
    }
}