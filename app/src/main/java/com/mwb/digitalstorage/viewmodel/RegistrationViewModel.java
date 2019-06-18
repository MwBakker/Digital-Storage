package com.mwb.digitalstorage.viewmodel;

import com.mwb.digitalstorage.command_handlers.CompanyCmdHandler;
import com.mwb.digitalstorage.modelUI.UICompany;


public class RegistrationViewModel extends BaseViewModel
{
    private UICompany uiCompany;

    //  sets the elements of the viewModel
    public void setViewModelElements(CompanyCmdHandler companyExistenceCallBack)
    {
        uiCompany = new UICompany(0L, "", "", "");
        repositoryFactory.companyRepository.checkExistence(companyExistenceCallBack);
    }

    //  retrieves the uiCompany
    public UICompany getUiCompany() { return uiCompany; }

    //  adds the company
    public void addCompany(CompanyCmdHandler companyCmdHandler)
    {
        repositoryFactory.companyRepository.insertCompany(uiCompany.nameObsv.get(), uiCompany.locationObsv.get(),
                                                                uiCompany.getImgPath(), companyCmdHandler);
    }
}
