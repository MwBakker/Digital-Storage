package com.mwb.digitalstorage.viewmodel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.mwb.digitalstorage.database.CompanyRepository;
import com.mwb.digitalstorage.modelUI.UICompany;
import androidx.databinding.ObservableField;


public class CompanyRegistrationViewModel extends BaseViewModel
{
    private String companyImgPath;

    private CompanyRepository companyRepository;

    public ObservableField<String> companyNameObsv = new ObservableField<>("");
    public ObservableField<String> companyLocObsv = new ObservableField<>("");
    public ObservableField<Bitmap> companyImgObsv = new ObservableField<>();

    //
    //  sets the elements of the viewModel
    //
    public void setViewModelElements()
    {
        companyRepository = new CompanyRepository();
    }

    public UICompany getCompany() { return companyRepository.getCompany(); }

    //
    //  sets the image of the rack
    //
    public void setCompanyBitmap(String imgPath)
    {
        if (imgPath != null)
        {
            this.companyImgPath = imgPath;
            companyImgObsv.set(BitmapFactory.decodeFile(imgPath));
        }
        else
        { companyImgObsv.set(null); }
    }

    //
    //  adds the company
    //
    public void addCompany()
    {
        companyRepository.insertCompany(companyNameObsv.get(), companyLocObsv.get(), companyImgPath);
    }
}
