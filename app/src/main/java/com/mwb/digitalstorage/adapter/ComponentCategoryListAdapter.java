package com.mwb.digitalstorage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.mwb.digitalstorage.R;
import com.mwb.digitalstorage.command_handlers.ComponentCategoryCmdHandler;
import com.mwb.digitalstorage.databinding.ComponentCategoryItemBinding;
import com.mwb.digitalstorage.modelUI.UIComponentCategory;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


public class ComponentCategoryListAdapter extends RecyclerView.Adapter<ComponentCategoryListAdapter.ComponentCategoryViewHolder>
{
    private List<UIComponentCategory> componentCategories;
    private ComponentCategoryCmdHandler componentCategoryCmdHandler;


    public ComponentCategoryListAdapter(List<UIComponentCategory> componentCats, ComponentCategoryCmdHandler componentCategoryCmdHandler)
    {
        this.componentCategories = componentCats;
        this.componentCategoryCmdHandler = componentCategoryCmdHandler;
    }

    @NonNull
    @Override
    public ComponentCategoryListAdapter.ComponentCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        ComponentCategoryItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.component_category_item,
                                                                        parent, false);
        return new ComponentCategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ComponentCategoryListAdapter.ComponentCategoryViewHolder holder, int position)
    {
        holder.bind(componentCategories.get(position));
    }

    //  returns the list, or 0
    @Override
    public int getItemCount()
    {
        return  componentCategories != null ? componentCategories.size() : 0;
    }


    //__________________________________________________________________________________________________
    //
    //  internal class ViewHolder
    //
    public class ComponentCategoryViewHolder extends RecyclerView.ViewHolder
    {
        private ComponentCategoryItemBinding binding;

        ComponentCategoryViewHolder(ComponentCategoryItemBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
            binding.setCmdHandler(componentCategoryCmdHandler());
        }

        //  sets handlers belonging to the holder
        private ComponentCategoryCmdHandler componentCategoryCmdHandler()
        {
            return new ComponentCategoryCmdHandler()
            {
                @Override
                // the cmdHandler takes the input to the Activity class there it shall be
                // performing the list sorting on the LiveData (LiveData requires no UpdateObservable())
                public void sort(UIComponentCategory uiComponentCategory)
                {
                    componentCategoryCmdHandler.sort(uiComponentCategory);
                }
                @Override
                public boolean editComponentCat(UIComponentCategory uiComponentCategory)
                {
                    componentCategoryCmdHandler.editComponentCat(uiComponentCategory);
                    return false;
                }
                @Override
                public void editComponentName(CharSequence s, int start, int before, int count)
                {
                    componentCategoryCmdHandler.editComponentName(s, start, before, count);
                }
                @Override
                public void saveEdit() { componentCategoryCmdHandler.saveEdit(); }
            };
        }

        //  binds the component unit to the model
        private void bind(@NonNull UIComponentCategory componentCat)
        {
            binding.setUIComponentCategory(componentCat);
            binding.executePendingBindings();
        }
    }
}