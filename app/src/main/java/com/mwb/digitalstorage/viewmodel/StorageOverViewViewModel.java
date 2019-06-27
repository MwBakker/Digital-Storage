package com.mwb.digitalstorage.viewmodel;

import com.mwb.digitalstorage.command_handlers.RetrieveCompanyCmdHandler;
import com.mwb.digitalstorage.modelUI.UICompany;
import com.mwb.digitalstorage.modelUI.UIStorage;
import java.util.List;
import androidx.lifecycle.LiveData;


public class StorageOverViewViewModel extends BaseViewModel
{
    private UIStorage uiStorage;
    private UICompany uiCompany;


    //  gets the involved company from database
    public void getUICompanyFromDB(RetrieveCompanyCmdHandler retrieveCompanyCmdHandler)
    {
       repositoryFactory.companyRepository.getUiCompany(retrieveCompanyCmdHandler);
    }

    //  gets the involved uiCompany (XML binding)
    public UICompany getUiCompany() { return uiCompany; }

    //  sets the involved company
    public void setUiCompany(UICompany uiCompany)
    {
        this.uiCompany = uiCompany;
    }

    //  gets all storage units
    public LiveData<List<UIStorage>> getStorageUnits()
    {
        return repositoryFactory.storageRepository.getStorageUnits();
    }

    //  sets all the counting elements belonging to the storage (like amount of racks)
    public void setStorageUnitCountingProperties(List<UIStorage> uiStorageUnits)
    {
        repositoryFactory.storageRepository.setStorageCountingProperties(uiStorageUnits);
    }

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