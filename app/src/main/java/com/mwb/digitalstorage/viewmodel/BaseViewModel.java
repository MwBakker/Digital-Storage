package com.mwb.digitalstorage.viewmodel;

import com.mwb.digitalstorage.misc.ImageProcessor;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import androidx.lifecycle.ViewModel;


public abstract class BaseViewModel extends ViewModel
{
    public final ImageProcessor imgProcessor;
    public final Executor executor;

    BaseViewModel()
    {
        imgProcessor = new ImageProcessor();
        executor = Executors.newSingleThreadExecutor();
    }
}