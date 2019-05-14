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

    public ObservableField<String> rackNameObsv = new ObservableField<>("");
    public ObservableField<Bitmap> rackImgObsv = new ObservableField<>();


    public void setViewModelElements(long storageID)
    {
        rackRepository = new RackRepository();
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
        rackImgObsv.set(BitmapFactory.decodeFile(imgFile.getAbsolutePath()));
    }

    //
    //  removes the image of the rack
    //
    public void removeRackImg()
    {
        this.rackImgPath = null;
        rackImgObsv.set(null);
    }

    //
    //  add rack to repository
    //
    public void addRack()
    {
        executor.execute(() -> { rackRepository.insertRack(storageID, rackNameObsv.get(), rackImgPath); });
    }
}