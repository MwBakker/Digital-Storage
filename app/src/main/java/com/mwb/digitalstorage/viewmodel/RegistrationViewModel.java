package com.mwb.digitalstorage.viewmodel;

import com.mwb.digitalstorage.command_handlers.CompanyExistenceCmdHandler;
import com.mwb.digitalstorage.database.CompanyRepository;
import com.mwb.digitalstorage.modelUI.UICompany;


public class RegistrationViewModel extends BaseViewModel
{
    private UICompany uiCompany;

    private CompanyRepository companyRepository;


    //  sets the elements of the viewModel
    public void setViewModelElements(CompanyExistenceCmdHandler companyExistenceCallBack)
    {
        companyRepository = new CompanyRepository();
        executor.execute(() ->
        {
            uiCompany = companyRepository.getUiCompany();
            if (uiCompany == null)
            {
                uiCompany = new UICompany(0L, null, null, null);
            }
            else { companyExistenceCallBack.goToMainPage(); }
        });
    }

    public UICompany getUiCompany() { return uiCompany; }

    //  adds the company
    public void addCompany()
    {
        companyRepository.insertCompany(uiCompany.nameObsv.get(), uiCompany.locationObsv.get(), uiCompany.getImgPath());
    }
}
