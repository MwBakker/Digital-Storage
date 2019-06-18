package com.mwb.digitalstorage;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;

import com.mwb.digitalstorage.command_handlers.CompanyCmdHandler;
import com.mwb.digitalstorage.command_handlers.RegistrationCmdHandler;
import com.mwb.digitalstorage.databinding.ActivityMainBinding;
import com.mwb.digitalstorage.viewmodel.RegistrationViewModel;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;


public class RegistrationActivity extends BaseActivity
{
    private RegistrationViewModel companyRegistrationVM;


    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        checkPermissions();

        // command handler
        CompanyCmdHandler companyCmdHandler = companyCmdHandler();
        RegistrationCmdHandler registrationCmdHandler = registrationCmdHandler(companyCmdHandler);

        // viewModel
        companyRegistrationVM = ViewModelProviders.of(this).get(RegistrationViewModel.class);
        companyRegistrationVM.setViewModelElements(companyCmdHandler);

        // bindings
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setVm(companyRegistrationVM);
        binding.setCmdHandler(registrationCmdHandler);
    }

    //  checks for permissions
    private void checkPermissions()
    {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {

            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)
                    this, Manifest.permission.READ_EXTERNAL_STORAGE))
            {

            }
            else
            {
                ActivityCompat.requestPermissions((Activity) this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        200);
                ActivityCompat.requestPermissions((Activity) this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        200);
            }
        }
    }

    //  registration activity handler
    public RegistrationCmdHandler registrationCmdHandler(CompanyCmdHandler companyCmdHandler)
    {
        return new RegistrationCmdHandler()
        {
            @Override
            public void editCompany() { }
            @Override
            public void takePhoto()
            {
                companyRegistrationVM.imgProcessor.setCamerabool(true);
                startActivityForResult(companyRegistrationVM.imgProcessor.dispatchTakePictureIntent(getApplicationContext(),
                        getExternalFilesDir(Environment.DIRECTORY_PICTURES)), 1);
            }
            @Override
            public void browsePhoto()
            {
                companyRegistrationVM.imgProcessor.setCamerabool(false);
                startActivityForResult(new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI),1);
            }
            @Override
            public void removePhoto() { companyRegistrationVM.getUiCompany().imgObsv.set(null); }
            @Override
            public void saveCompany()
            {
                companyRegistrationVM.addCompany(companyCmdHandler);
            }
        };
    }

    //  handles the company registration or existence case
    public CompanyCmdHandler companyCmdHandler()
    {
        return new CompanyCmdHandler()
        {
            @Override
            public void goToMainPage() { switchActivity(); }
        };
    }

    //  immediately retrieves file from taken img
    //  sets the VM img resource property
    //  loadImg will be called on after trigger of set()
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (companyRegistrationVM.imgProcessor.isFromCamera())
        {
            companyRegistrationVM.getUiCompany().setImgPath(companyRegistrationVM.imgProcessor.getImgPath());
        }
        else
        {
            companyRegistrationVM.getUiCompany().setImgPath(companyRegistrationVM.imgProcessor.browseImage(data, getApplication()));
        }
        companyRegistrationVM.getUiCompany().imgObsv.set(companyRegistrationVM.imgProcessor.decodeImgPath());
    }

    //  switches activity
    public void switchActivity()
    {
        Intent intent = new Intent(RegistrationActivity.this, StorageOverViewActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_out, android.R.anim.fade_in);
        finish();
    }
}