package com.mwb.digitalstorage.viewmodel;

import android.content.Context;
import android.widget.ArrayAdapter;
import com.mwb.digitalstorage.R;
import com.mwb.digitalstorage.database.ComponentRepository;
import com.mwb.digitalstorage.modelUI.UIComponent;
import com.mwb.digitalstorage.modelUI.UIComponentCategory;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LifecycleOwner;


public class ComponentMenuViewModel extends BaseViewModel
{
    private UIComponent uiComponent;
    private long rackID;
    private LifecycleOwner owner;
    private ComponentRepository componentRepository;

    public ObservableField<Integer> categoryIdObsv = new ObservableField<>();
    public ObservableField<ArrayAdapter<UIComponentCategory>> spinnerAdapterObsv = new ObservableField<>();
    // new category
    public ObservableField<String> newComponentCategoryNameObsv = new ObservableField<>();
    public ObservableField<Boolean> newCategoryObsv = new ObservableField<>(false);


    //  sets the elements belonging to the viewModel
    public void setViewModelElements(LifecycleOwner owner, Context context, long rackID)
    {
        this.rackID = rackID;
        this.owner = owner;
        uiComponent = new UIComponent(0L,0L,0L, "",
                                     "","","", 0);
        componentRepository = new ComponentRepository();
        componentRepository.getAllComponentCategories().observe(owner, componentCategories ->
        {
            spinnerAdapterObsv.set(new ArrayAdapter<>(context, R.layout.component_category_spinner_item, componentCategories));
        });
    }

    //  gets the rack id
    public long getRackID() { return rackID; }

    //  gets the uiComponent
    public UIComponent getUiComponent() { return uiComponent; }


    //  adds the component
    //  checks if category is new
    public void addComponent()
    {
        componentRepository.getAllComponentCategories().observe(owner, componentCategories ->
        {
            executor.execute(() ->
            {
                if (newCategoryObsv.get())
                {
                    componentRepository.insertComponent(rackID, processNewCategoryInput(), uiComponent.nameObsv.get(), uiComponent.codeObsv.get(),
                                                        uiComponent.getImgPath(), 0);
                }
                else
                {
                    componentRepository.insertComponent(rackID, componentCategories.get(categoryIdObsv.get()).getID(), uiComponent.nameObsv.get(),
                            uiComponent.codeObsv.get(), uiComponent.getImgPath(), 0);
                }
            });
        });
    }


    //  retrieves possible existing long categoryID or creates one
    private long processNewCategoryInput()
    {
        // check if category exists
        long categoryID = componentRepository.getComponentCategory(newComponentCategoryNameObsv.get());
        // if default value is returned...
        if (categoryID == 0L)
        {
            // add the new category
            categoryID = componentRepository.insertComponentCategory(newComponentCategoryNameObsv.get());
        }
        return categoryID;
    }
}