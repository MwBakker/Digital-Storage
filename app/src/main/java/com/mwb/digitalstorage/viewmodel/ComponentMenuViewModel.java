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
    private long catID;
    private LifecycleOwner owner;

    public ObservableField<Integer> catIDObsv = new ObservableField<>();

    private ComponentRepository componentRepository;

    public ObservableField<ArrayAdapter<UIComponentCategory>> spinnerAdapterObsv = new ObservableField<>();
    // the selected category from the spinner
    public ObservableField<UIComponentCategory> selectedCategoryObsv = new ObservableField<>();
    // new category
    public ObservableField<String> newComponentCategoryObsv = new ObservableField<>();
    public ObservableField<Boolean> newCategoryObsv = new ObservableField<>(false);


    //  sets the elements belonging to the viewModel
    public void setViewModelElements(LifecycleOwner owner, Context context, long rackID)
    {
        this.rackID = rackID;
        this.owner = owner;
        uiComponent = new UIComponent(0L,0L,0L,"",
                                    "","","","",0);

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
                if (newCategoryObsv.get()) { processNewCategoryInput(); }
                componentRepository.insertComponent(rackID, componentCategories.get(catIDObsv.get()).getComponentCatID(), null,
                                                    uiComponent.nameObsv.get(), uiComponent.codeObsv.get(), uiComponent.getImgPath(), 0);
            });
        });
    }


    //  checks if category exists
    //  if not, add the new category and set the ID
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