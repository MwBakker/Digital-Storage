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
import java.util.concurrent.Executors;
import androidx.databinding.ObservableField;


public class StorageOverViewViewModel extends BaseViewModel
{
    private StorageRepository storageRepository;
    private UIStorage uiStorage;
    private String companyImgPath = "";
    public ObservableField<StorageListAdapter> storageListAdapterObs = new ObservableField<>();

    public ObservableField<String> companyNameObs = new ObservableField<>("");
    public ObservableField<String> companyLocObs = new ObservableField<>("");
    public ObservableField<Bitmap> companyImgObs = new ObservableField<>();
    public ObservableField<Boolean> editCompanyObs = new ObservableField<>();


    public void setViewModelElements(androidx.lifecycle.LifecycleOwner owner, CompanyRepository companyRepository,
                                    StorageRepository storageRepository, StorageCmdHandler mainViewCmdHandlerCallBack)
    {
        executor = Executors.newSingleThreadExecutor();
        this.storageRepository = storageRepository;

        executor.execute(() ->
        {
            UICompany uiCompany = companyRepository.getCompany();
            companyNameObs.set(uiCompany.getCompanyName());
            companyLocObs.set(uiCompany.getCompanyLoc());
            companyImgObs.set(uiCompany.getItemBitmap());
        });
        storageRepository.getStorageUnits().observe(owner, storageUnits ->
        {
            storageListAdapterObs.set(new StorageListAdapter(storageUnits, mainViewCmdHandlerCallBack));
        });
    }

    //
    //  sets the company img
    //
    public void setCompanyBitmap(String imgPath)
    {
        File imgFile = new File(imgPath);
        this.companyImgPath = imgPath;
        companyImgObs.set(BitmapFactory.decodeFile(imgFile.getAbsolutePath()));
    }

    //
    //  removes the storage bitmap
    //
    public void removeStorageBitmap()
    {
        companyImgObs.set(null);
        companyImgPath = null;
    }

    //
    //  saves the company edit
    //
    public void saveCompanyEdit(Application app)
    {
        editCompanyObs.set(false);
        CompanyRepository companyRepository = new CompanyRepository(app);
        companyRepository.editCompany(companyNameObs.get(), companyLocObs.get(), companyImgPath);
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