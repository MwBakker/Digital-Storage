package com.mwb.digitalstorage.viewmodel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.mwb.digitalstorage.database.RackRepository;
import com.mwb.digitalstorage.database.StorageRepository;
import java.util.concurrent.Executors;
import androidx.databinding.ObservableField;


public class StorageMenuViewModel extends BaseViewModel
{
    private StorageRepository storageRepository;
    private String storageImgPath;

    public ObservableField<String> storageNameObsv = new ObservableField<>("");
    public ObservableField<String> storageLocObsv = new ObservableField<>("");
    public ObservableField<Integer> storageRackAmountObsv = new ObservableField<>(0);
    public ObservableField<Bitmap> storageImgObsv = new ObservableField<>();


    public void setViewModelElements()
    {
        storageRepository = new StorageRepository();
    }

    //
    //  sets the image of the storage
    //
    public void setStorageBitmap(String storageImgPath)
    {
        this.storageImgPath = storageImgPath;
        storageImgObsv.set(BitmapFactory.decodeFile(storageImgPath));
    }

    //
    //  removes the photo from the storage
    //
    public void removeStoragePhoto()
    {
        storageImgObsv.set(null);
        storageImgPath = null;
    }

    //
    //  adds the storage, retrieves the through
    //  above callBack method
    //
    public void addStorage(RackRepository rackRepository)
    {
        executor.execute(() ->
        {
           long storageID = storageRepository.insertStorage(storageNameObsv.get(), storageLocObsv.get(), storageImgPath);
           addRacksToStorage(storageID, rackRepository);
        });
    }

    //
    //  adds the new racks belonging to the newly created storage
    //
    private void addRacksToStorage(long storageID, RackRepository rackRepository)
    {
        for (int i = 1; i <= storageRackAmountObsv.get(); i++)
        {
            String title = "Kast_" + i;
            executor.execute(() ->
            {
                rackRepository.insertRack(storageID, title, "");
            });
        }
    }
}