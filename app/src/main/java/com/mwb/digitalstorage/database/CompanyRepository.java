package com.mwb.digitalstorage.database;

import com.mwb.digitalstorage.model.Company;
import com.mwb.digitalstorage.modelUI.UICompany;


public class CompanyRepository extends BaseRepository
{
    //  returns allRacks with correct storage_id
    public UICompany getUiCompany()
    {
        Company company = BaseRepository.getDao().getCompany();
        if (company != null)
        {
            UICompany uiCompany = new UICompany(company.id, company.getName(), company.getLocation(), company.getImgPath());
            uiCompany.setAmountOfStorages(getDao().getAmountOfStorageUnits());
            uiCompany.setAmountOfRacks(getDao().getAmountOfRacks());
            uiCompany.setAmountOfComponents(getDao().getAmountOfComponents());
            return uiCompany;
        }
        else { return null; }
    }

    //  performs storage insert
    public void insertCompany(String companyName, String companyLoc, String companyImgPath)
    {
        BaseRepository.getDao().insertCompany(new Company(companyName, companyLoc, companyImgPath));
    }

    //  performs storage insert
    public void editCompany(String companyName, String companyLoc, String companyImgPath)
    {
        BaseRepository.getDao().editCompany(companyName, companyLoc, companyImgPath);
    }
}