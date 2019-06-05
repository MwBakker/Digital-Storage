package com.mwb.digitalstorage.database.DAO;

import com.mwb.digitalstorage.model.Company;
import com.mwb.digitalstorage.model.Component;
import com.mwb.digitalstorage.model.ComponentCategory;
import com.mwb.digitalstorage.model.Rack;
import com.mwb.digitalstorage.model.Storage;
import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


@Dao
public interface DAO extends DAOStorage, DAORack, DAOComponent
{
    //  insert company
    @Insert
    void insertCompany(Company company);

    //  get company
    @Query("SELECT * FROM company LIMIT 1")
    Company getCompany();

    //  edit company
    @Query("UPDATE company SET name = :companyName, location = :companyLocation, img_path = :imgPath ")
    void editCompany(String companyName, String companyLocation, String imgPath);
}