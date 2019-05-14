package com.mwb.digitalstorage.viewmodel;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.mwb.digitalstorage.adapter.StorageListAdapter;
import com.mwb.digitalstorage.command_handlers.StorageCmdHandler;
import com.mwb.digitalstorage.database.CompanyRepository;
import com.mwb.digitalstorage.database.StorageRepository;
import com.mwb.digitalstorage.modelUI.UICompany;
import com.mwb.digitalstorage.modelUI.UIStorage;
import java.io.File;
import androidx.databinding.ObservableField;


public class StorageOverViewViewModel extends BaseViewModel
{
    private StorageRepository storageRepository;
    private CompanyRepository companyRepository;
    private UIStorage uiStorage;
    private String companyImgPath = "";
    public ObservableField<StorageListAdapter> storageListAdapterObsv = new ObservableField<>();
    public ObservableField<String> companyNameObsv = new ObservableField<>("");
    public ObservableField<String> companyLocObsv = new ObservableField<>("");
    public ObservableField<Bitmap> companyImgObsv = new ObservableField<>();
    public ObservableField<Boolean> editCompanyObsv = new ObservableField<>();


    public void setViewModelElements(androidx.lifecycle.LifecycleOwner owner, StorageCmdHandler mainViewCmdHandlerCallBack)
    {
        storageRepository = new StorageRepository();
        companyRepository = new CompanyRepository();

        executor.execute(() ->
        {
            UICompany uiCompany = companyRepository.getCompany();
            companyNameObsv.set(uiCompany.getCompanyName());
            companyLocObsv.set(uiCompany.getCompanyLoc());
            companyImgObsv.set(uiCompany.getItemBitmap());
        });
        storageRepository.getStorageUnits().observe(owner, storageUnits ->
        {
            storageListAdapterObsv.set(new StorageListAdapter(storageUnits, mainViewCmdHandlerCallBack));
        });
    }

    //
    //  sets the company img
    //
    public void setCompanyBitmap(String imgPath)
    {
        File imgFile = new File(imgPath);
        this.companyImgPath = imgPath;
        companyImgObsv.set(BitmapFactory.decodeFile(imgFile.getAbsolutePath()));
    }

    //
    //  removes the storage bitmap
    //
    public void removeStorageBitmap()
    {
        companyImgObsv.set(null);
        companyImgPath = null;
    }

    //
    //  saves the company edit
    //
    public void saveCompanyEdit(Application app)
    {
        editCompanyObsv.set(false);
        companyRepository.editCompany(companyNameObsv.get(), companyLocObsv.get(), companyImgPath);
    }

    //
    //  sets the storage to be edited
    //
    public void setEditableStorage(UIStorage uiStorage)
    {
        this.uiStorage = uiStorage;
        uiStorage.isEdit.set(true);
    }

    //
    //  gets the editable storage
    //
    public UIStorage getUiStorage() { return uiStorage; }

    //
    //  edits the storage
    //
    public void saveStorageEdit()
    {
        executor.execute(() ->
        {
            storageRepository.editStorage(uiStorage.id, uiStorage.name.get(), uiStorage.location.get());
        });
        uiStorage.isEdit.set(false);
    }

    //
    //  delete the storage
    //
    public void deleteStorage()
    {
        executor.execute(() -> { storageRepository.deleteStorage(uiStorage.id); });
    }
}