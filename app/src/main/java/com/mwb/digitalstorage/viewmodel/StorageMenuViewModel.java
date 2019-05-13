package com.mwb.digitalstorage.viewmodel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.mwb.digitalstorage.database.RackRepository;
import com.mwb.digitalstorage.database.StorageRepository;
import java.util.concurrent.Executors;
import androidx.databinding.ObservableField;


public class StorageMenuViewModel extends BaseViewModel
{
    private StorageRepository storageRep;
    private String storageImgPath;

    public ObservableField<String> storageNameObs = new ObservableField<>("");
    public ObservableField<String> storageLocObs = new ObservableField<>("");
    public ObservableField<Integer> storageRackAmountObs = new ObservableField<>(0);
    public ObservableField<Bitmap> storageImgObs = new ObservableField<>();


    public void setViewModelElements(StorageRepository storageRepository)
    {
        executor = Executors.newSingleThreadExecutor();
        this.storageRep = storageRepository;
    }

    //
    //  sets the image of the storage
    //
    public void setStorageBitmap(String storageImgPath)
    {
        this.storageImgPath = storageImgPath;
        storageImgObs.set(BitmapFactory.decodeFile(storageImgPath));
    }

    //
    //  removes the photo from the storage
    //
    public void removeStoragePhoto()
    {
        storageImgObs.set(null);
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
           long storageID = storageRep.insertStorage(storageNameObs.get(), storageLocObs.get(), storageImgPath);
           addRacksToStorage(storageID, rackRepository);
        });
    }

    //
    //  adds the new racks belonging to the newly created storage
    //
    private void addRacksToStorage(long storageID, RackRepository rackRepository)
    {
        for (int i = 1; i <= storageRackAmountObs.get(); i++)
        {
            String title = "Kast_" + i;
            executor.execute(() ->
            {
                rackRepository.insertRack(storageID, title, "");
            });
        }
    }
}