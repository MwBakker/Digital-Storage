package com.mwb.digitalstorage.database;

import android.app.Application;
import com.mwb.digitalstorage.model.Company;
import com.mwb.digitalstorage.modelUI.UICompany;


public class CompanyRepository
{
    private DAO dao;

    public CompanyRepository(Application application)
    {
        RoomDB db = RoomDB.getDatabase(application);
        dao = db.dao();
    }

    //
    //  returns allRacks with correct storage_id
    //
    public UICompany getCompany()
    {
        Company company = dao.getCompany();
        return company != null ?
                new UICompany(company.id, company.getName(), company.getLocation(), company.getImgPath()) : null;
    }

    //
    //  performs storage insert
    //
    public void insertCompany(String companyName, String companyLoc, String companyImgPath)
    {
        dao.insertCompany(new Company(companyName, companyLoc, companyImgPath));
    }

    //
    //  performs storage insert
    //
    public void editCompany(String companyName, String companyLoc, String companyImgPath)
    {
        dao.editCompany(companyName, companyLoc, companyImgPath);
    }
}