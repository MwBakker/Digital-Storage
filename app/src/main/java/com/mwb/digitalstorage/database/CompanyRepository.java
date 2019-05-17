package com.mwb.digitalstorage.database;

import com.mwb.digitalstorage.model.Company;
import com.mwb.digitalstorage.modelUI.UICompany;


public class CompanyRepository
{
    //  returns allRacks with correct storage_id
    public UICompany getCompany()
    {
        Company company = BaseRepository.getDao().getCompany();
        return company != null ?
                new UICompany(company.id, company.getName(), company.getLocation(), company.getImgPath()) : null;
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