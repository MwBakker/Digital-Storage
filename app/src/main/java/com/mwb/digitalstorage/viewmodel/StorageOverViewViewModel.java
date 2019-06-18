package com.mwb.digitalstorage.viewmodel;

import com.mwb.digitalstorage.modelUI.UICompany;
import com.mwb.digitalstorage.modelUI.UIStorage;
import java.util.List;
import androidx.lifecycle.LiveData;


public class StorageOverViewViewModel extends BaseViewModel
{
    private UIStorage uiStorage;
    private UICompany uiCompany;


    public void setViewModelElements()
    {
        //uiCompany = repositoryFactory.companyRepository.getUiCompany();
    }

    public LiveData<List<UIStorage>> getStorageUnits()
    {
        return repositoryFactory.storageRepository.getStorageUnits();
    }

    //  gets the involved uiCompany
    public UICompany getUiCompany() { return uiCompany; }

    //  saves the company edit
    public void saveCompanyEdit()
    {
        uiCompany.isEdit.set(false);
        repositoryFactory.companyRepository.editCompany(uiCompany.nameObsv.get(), uiCompany.locationObsv.get(),
                                                            uiCompany.getImgPath());
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
        repositoryFactory.storageRepository.editStorage(uiStorage.id, uiStorage.getName(), uiStorage.getLocation());
        uiStorage.isEditObsv.set(false);
    }

    //  delete the storage
    public void deleteStorage()
    {
       repositoryFactory.storageRepository.deleteStorage(uiStorage.id);
    }
}