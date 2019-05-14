package com.mwb.digitalstorage.viewmodel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ArrayAdapter;
import com.mwb.digitalstorage.R;
import com.mwb.digitalstorage.database.ComponentRepository;
import com.mwb.digitalstorage.modelUI.UIComponentCategory;
import java.io.File;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LifecycleOwner;


public class ComponentMenuViewModel extends BaseViewModel
{
    private long rackID;
    private long catID;

    private ComponentRepository componentRepository;
    private String componentImgPath;

    public ObservableField<String> componentNameObsv = new ObservableField<>("");
    public ObservableField<String> componentCodeObsv = new ObservableField<>("");
    public ObservableField<Bitmap> componentImgObsv = new ObservableField<>();
    public ObservableField<ArrayAdapter<UIComponentCategory>> spinnerAdapterObsv = new ObservableField<>();
    // the selected category from the spinner
    public ObservableField<UIComponentCategory> selectedCategoryObsv = new ObservableField<>();
    // new category
    public ObservableField<String> newComponentCategoryObsv = new ObservableField<>();
    public ObservableField<Boolean> isEditObsv = new ObservableField();
    public ObservableField<Boolean> newCategoryObsv = new ObservableField<>(false);

    //
    //  sets the elements belonging to the viewModel
    //
    public void setViewModelElements(LifecycleOwner owner, Context context, long rackID)
    {
        this.rackID = rackID;
        componentRepository = new ComponentRepository();
        componentRepository.getAllComponentCategories().observe(owner, componentCategories ->
        {
            spinnerAdapterObsv.set(new ArrayAdapter<UIComponentCategory>(context, R.layout.component_category_spinner_item, componentCategories));
        });
    }

    public long getRackID() { return rackID; }

    //
    //  sets the image of the rack
    //
    public void setComponentBitmap(String imgPath)
    {
        File imgFile = new  File(imgPath);
        this.componentImgPath = imgPath;
        componentImgObsv.set(BitmapFactory.decodeFile(imgFile.getAbsolutePath()));
    }

    //
    //  removes the image
    //
    public void removePhoto()
    {
        componentImgObsv.set(null);
        componentImgPath = null;
    }

    //
    //  adds the component
    //  checks if category is new
    //
    public void addComponent()
    {
        executor.execute(() ->
        {
            if (newCategoryObsv.get())
            {
                processNewCategoryInput();
            }
            componentRepository.insertComponent(rackID, catID, null, componentNameObsv.get(),
                                         componentCodeObsv.get(), componentImgPath, 0);
        });
    }

    //
    //  checks if category exists
    //  if not, add the new category and set the ID
    //
    private void processNewCategoryInput()
    {
        catID = componentRepository.getComponentCategory(newComponentCategoryObsv.get());
        // if default value is returned...
        if (catID == 0L)
        {
            // add the new category
            catID = componentRepository.insertComponentCategory(newComponentCategoryObsv.get());
        }
    }
}