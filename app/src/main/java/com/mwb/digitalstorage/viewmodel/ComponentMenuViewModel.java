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

    public ObservableField<String> componentNameObs = new ObservableField<>("");
    public ObservableField<String> componentCodeObs = new ObservableField<>("");
    public ObservableField<Bitmap> componentImgObs = new ObservableField<>();
    public ObservableField<ArrayAdapter<UIComponentCategory>> spinnerAdapterObs = new ObservableField<>();
    // the selected category from the spinner
    public ObservableField<UIComponentCategory> selectedCategoryObs = new ObservableField<>();
    // new category
    public ObservableField<String> newComponentCategoryObs = new ObservableField<>();
    public ObservableField<Boolean> isEditObs = new ObservableField();
    public ObservableField<Boolean> newCategoryObs = new ObservableField<>(false);

    //
    //  sets the elements belonging to the viewModel
    //
    public void setViewModelElements(ComponentRepository componentRepository, LifecycleOwner owner, Context context, long rackID)
    {
        this.rackID = rackID;
        this.componentRepository = componentRepository;
        componentRepository.getAllComponentCategories().observe(owner, componentCategories ->
        {
            spinnerAdapterObs.set(new ArrayAdapter<UIComponentCategory>(context, R.layout.component_category_spinner_item, componentCategories));
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
        componentImgObs.set(BitmapFactory.decodeFile(imgFile.getAbsolutePath()));
    }

    //
    //  removes the image
    //
    public void removePhoto()
    {
        componentImgObs.set(null);
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
            if (newCategoryObs.get())
            {
                processNewCategoryInput();
            }
            componentRepository.insertComponent(rackID, catID, null, componentNameObs.get(),
                                         componentCodeObs.get(), componentImgPath, 0);
        });
    }

    //
    //  checks if category exists
    //  if not, add the new category and set the ID
    //
    private void processNewCategoryInput()
    {
        catID = componentRepository.getComponentCategory(newComponentCategoryObs.get());
        // if default value is returned...
        if (catID == 0L)
        {
            // add the new category
            catID = componentRepository.insertComponentCategory(newComponentCategoryObs.get());
        }
    }
}