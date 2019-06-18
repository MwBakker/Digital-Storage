package com.mwb.digitalstorage.viewmodel;

import com.mwb.digitalstorage.database.RepositoryFactory;
import com.mwb.digitalstorage.misc.ImageProcessor;
import androidx.lifecycle.ViewModel;


public abstract class BaseViewModel extends ViewModel
{
    public final ImageProcessor imgProcessor;
    public final RepositoryFactory repositoryFactory;

    BaseViewModel()
    {
        imgProcessor = new ImageProcessor();
        repositoryFactory = new RepositoryFactory();
    }
}