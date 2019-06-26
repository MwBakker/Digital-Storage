package com.mwb.digitalstorage.database;

import com.mwb.digitalstorage.command_handlers.CompanyCmdHandler;
import com.mwb.digitalstorage.command_handlers.RetrieveCompanyCmdHandler;
import com.mwb.digitalstorage.command_handlers.entity.RetrieveEntityCmdHandler;
import com.mwb.digitalstorage.model.Company;
import com.mwb.digitalstorage.modelUI.UICompany;
import com.mwb.digitalstorage.modelUI.UIComponent;


public class CompanyRepository extends BaseRepository
{
    //  checks existence of a company
    public void checkExistence(CompanyCmdHandler retrievalCallback)
    {
        executor.execute(() ->
        {
            if (getDao().getCompany() != null)
            {
                retrievalCallback.goToMainPage();
            }
        });
    }

    //  returns allRacks with correct storage_id
    public void getUiCompany(RetrieveCompanyCmdHandler retrieveCompanyCmdHandler)
    {
        executor.execute(() ->
        {
            Company company = getDao().getCompany();
            UICompany uiCompany = new UICompany(company.id, company.getName(), company.getLocation(), company.getImgPath());
            setCompanyUIElements(uiCompany);
            retrieveCompanyCmdHandler.companyRetrieved(uiCompany);
        });
    }

    //  sets the ui elements of the company
    private void setCompanyUIElements(UICompany uiCompany)
    {
        uiCompany.setAmountOfStorages(getDao().getAmountOfStorageUnits());
        uiCompany.setAmountOfRacks(getDao().getAmountOfRacks());
        uiCompany.setAmountOfComponents(getDao().getAmountOfComponents());
    }

    //  performs company insert
    public void insertCompany(String companyName, String companyLoc, String companyImgPath, CompanyCmdHandler companyCmdHandler)
    {
        executor.execute(() ->
        {
            getDao().insertCompany(new Company(companyName, companyLoc, companyImgPath));
            companyCmdHandler.goToMainPage();
        });
    }

    //  performs storage insert
    public void editCompany(String companyName, String companyLoc, String companyImgPath)
    {
        executor.execute(() ->
        {
            getDao().editCompany(companyName, companyLoc, companyImgPath);
        });
    }
}