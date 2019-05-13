package com.mwb.digitalstorage.viewmodel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.mwb.digitalstorage.database.RackRepository;
import java.io.File;
import androidx.databinding.ObservableField;


public class RackMenuViewModel extends BaseViewModel
{
    private long storageID;
    private String rackImgPath = "";
    private RackRepository rackRepository;

    public ObservableField<String> rackNameObs = new ObservableField<>("");
    public ObservableField<Bitmap> rackImgObs = new ObservableField<>();


    public RackMenuViewModel(RackRepository rackRepository, long storageID)
    {
        this.rackRepository = rackRepository;
        this.storageID = storageID;
    }

    //
    //  gets the involved storage
    //
    public long getStorageID() { return storageID; }

    //
    //  sets the image of the rack
    //
    public void setRackBitmap(String imgPath)
    {
        File imgFile = new File(imgPath);
        this.rackImgPath = imgPath;
        rackImgObs.set(BitmapFactory.decodeFile(imgFile.getAbsolutePath()));
    }

    //
    //  removes the image of the rack
    //
    public void removeRackImg()
    {
        this.rackImgPath = null;
        rackImgObs.set(null);
    }

    //
    //  add rack to repository
    //
    public void addRack()
    {
        executor.execute(() -> { rackRepository.insertRack(storageID, rackNameObs.get(), rackImgPath); });
    }
}