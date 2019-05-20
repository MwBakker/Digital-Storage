package com.mwb.digitalstorage.viewmodel;

import android.graphics.Bitmap;
import com.mwb.digitalstorage.adapter.ComponentCategoryListAdapter;
import com.mwb.digitalstorage.adapter.ComponentListAdapter;
import com.mwb.digitalstorage.command_handlers.ComponentCategoryCmdHandler;
import com.mwb.digitalstorage.command_handlers.ComponentCmdHandler;
import com.mwb.digitalstorage.database.ComponentRepository;
import com.mwb.digitalstorage.database.RackRepository;
import com.mwb.digitalstorage.modelUI.UIComponent;
import com.mwb.digitalstorage.modelUI.UIComponentCategory;
import com.mwb.digitalstorage.modelUI.UIRack;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LifecycleOwner;


public class ComponentOverViewViewModel extends BaseViewModel
{
    private long rackID;
    private long storageID;
    private ComponentRepository componentRepository;
    private UIComponentCategory uiComponentCategory;
    private UIComponent uiComponent;
    private LifecycleOwner lifecycleOwner;

    public ObservableField<String> rackNameObsv = new ObservableField<>("");
    public ObservableField<Bitmap> rackImgObsv = new ObservableField<>();
    public ObservableField<ComponentListAdapter> componentListAdapterObsv = new ObservableField<>();
    public ObservableField<ComponentCategoryListAdapter> componentCatListAdapterObsv = new ObservableField<>();


    // sets the elements belonging to the viewModel
    public void setViewModelElements(long storageID, long rackID, LifecycleOwner lifecycleOwner, ComponentCategoryCmdHandler componentCategoryCmdHandler,
                                        ComponentCmdHandler componentCmdHandler)
    {
        componentRepository = new ComponentRepository();
        RackRepository rackRepository = new RackRepository();
        this.rackID = rackID;
        this.storageID = storageID;
        this.lifecycleOwner = lifecycleOwner;

        componentRepository.getRackComponentCategories(rackID).observe(lifecycleOwner, componentCategories ->
        {
            componentCatListAdapterObsv.set(new ComponentCategoryListAdapter(componentCategories, componentCategoryCmdHandler));
        });

        componentRepository.getRackComponents(rackID).observe(lifecycleOwner, components ->
        {
            componentListAdapterObsv.set(new ComponentListAdapter(components, componentCmdHandler));
        });

        executor.execute(() ->
        {
            UIRack UIRack = rackRepository.getRack(rackID);
            rackNameObsv.set(UIRack.nameObsv.get());
            rackImgObsv.set(UIRack.getImg());
        });
    }

    // gets the storage ID
    public long getStorageID() { return storageID; }

    // gets the rack ID
    public long getRackID() { return rackID; }

    // gets the component
    public UIComponent getComponent() { return uiComponent; }

    //
    //  sets the to-be-edited component category
    //
    public void setEditableComponentCategory(UIComponentCategory uiComponentCategory)
    {
        if (this.uiComponentCategory != null)
        {
            this.uiComponentCategory.isEdit.set(false);
        }
        uiComponentCategory.isEdit.set(true);
        this.uiComponentCategory = uiComponentCategory;
    }

    //  sets the to-be-edited component
    public void setEditableComponent(UIComponent uiComponent)
    {
        if (this.uiComponent != null)
        {
            this.uiComponent.isEditObsv.set(false);
        }
        uiComponent.isEditObsv.set(true);
        this.uiComponent = uiComponent;
    }

    //  performs sorting on the components per selected category
    public void sort(UIComponentCategory uiComponentCategory)
    {
        uiComponentCategory.setSelectedState();

        if (this.uiComponentCategory.getComponentCatID() == uiComponentCategory.getComponentCatID())
        {
            componentRepository.getCatFilteredComponents(rackID, uiComponentCategory.getComponentCatID()).observe(lifecycleOwner, components ->
            {
                componentListAdapterObsv.get().setComponents(components);
            });
        } else {
            componentRepository.getRackComponents(rackID).observe(lifecycleOwner, components ->
            {
                componentListAdapterObsv.get().setComponents(components);
            });
        };
    }

    //  saves the category edit
    public void saveComponentCategoryEdit()
    {
        executor.execute(() ->
        {
            componentRepository.editComponentCategory(uiComponentCategory.getComponentCatID(),
                                                uiComponentCategory.componentCatName.get());
        });
    }

    //  saves the component edit
    public void saveComponentEdit()
    {
        executor.execute(() ->
        {
            componentRepository.editComponent(uiComponent.getId(), uiComponent.getComponentCatID(), uiComponent.nameObsv.get(),
                                        uiComponent.codeObsv.get(), uiComponent.getImgPath());
        });
    }

    //  removes the component
    public void deleteComponent()
    {
        componentRepository.deleteComponent(uiComponent.getId());
    }
}