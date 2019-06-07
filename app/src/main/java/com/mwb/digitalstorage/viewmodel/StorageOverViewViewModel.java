package com.mwb.digitalstorage.viewmodel;

import com.mwb.digitalstorage.adapter.StorageListAdapter;
import com.mwb.digitalstorage.command_handlers.StorageCmdHandler;
import com.mwb.digitalstorage.database.CompanyRepository;
import com.mwb.digitalstorage.database.StorageRepository;
import com.mwb.digitalstorage.modelUI.UICompany;
import com.mwb.digitalstorage.modelUI.UIStorage;
import androidx.databinding.ObservableField;


public class StorageOverViewViewModel extends BaseViewModel
{
    private StorageRepository storageRepository;
    private CompanyRepository companyRepository;
    private UIStorage uiStorage;
    private UICompany uiCompany;

    public ObservableField<StorageListAdapter> storageListAdapterObsv = new ObservableField<>();

    public void setViewModelElements(androidx.lifecycle.LifecycleOwner owner, StorageCmdHandler mainViewCmdHandlerCallBack)
    {
        storageRepository = new StorageRepository();
        companyRepository = new CompanyRepository();

        executor.execute(() ->
        {
            uiCompany = companyRepository.getUiCompany();
            uiCompany.imgObsv.set(imgProcessor.decodeImgPath(uiCompany.getImgPath()));
        });
        storageRepository.getStorageUnits(executor).observe(owner, storageUnits ->
        {
            executor.execute(() ->
            {
                for (UIStorage uiStorage : storageUnits)
                {
                   storageRepository.setUIStorageElements(uiStorage);
                }
                storageListAdapterObsv.set(new StorageListAdapter(storageUnits, mainViewCmdHandlerCallBack));
            });
        });
    }

    //  gets the involved uiCompany
    public UICompany getUiCompany() { return uiCompany; }

    //  saves the company edit
    public void saveCompanyEdit()
    {
        uiCompany.isEdit.set(false);
        executor.execute(() ->
        {
            companyRepository.editCompany(uiCompany.nameObsv.get(), uiCompany.locationObsv.get(), uiCompany.getImgPath());
        });
    }

    //  sets the storage to be edited
    public void setEditableStorage(UIStorage uiStorage)
    {
        this.uiStorage = uiStorage;
        uiStorage.isEditObsv.set(true);
    }

    //  gets the editable storage
    public UIStorage getUiStorage() { return uiStorage; }

    //  edits the storage
    public void saveStorageEdit()
    {
        executor.execute(() ->
        {
            storageRepository.editStorage(uiStorage.id, uiStorage.getName(), uiStorage.getLocation());
        });
        uiStorage.isEditObsv.set(false);
    }

    //  delete the storage
    public void deleteStorage()
    {
        executor.execute(() -> { storageRepository.deleteStorage(uiStorage.id); });
    }
}