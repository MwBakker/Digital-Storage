package com.mwb.digitalstorage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.mwb.digitalstorage.R;
import com.mwb.digitalstorage.command_handlers.ComponentCmdHandler;
import com.mwb.digitalstorage.databinding.ComponentItemBinding;
import com.mwb.digitalstorage.modelUI.UIComponent;
import com.mwb.digitalstorage.modelUI.UIEntity;

import java.util.List;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


public class ComponentListAdapter extends RecyclerView.Adapter<ComponentListAdapter.ComponentViewHolder>
{
    private List<UIComponent> components;
    private ComponentCmdHandler componentCmdHandlerCallBack;


    public ComponentListAdapter(List<UIComponent> components, ComponentCmdHandler componentCmdHandlerCallBack)
    {
        this.components = components;
        this.componentCmdHandlerCallBack = componentCmdHandlerCallBack;
    }

    //
    //  sets the filtered components
    //
    public void setComponents(List<UIComponent> components)
    {
        this.components = components;
    }

    //
    //  creates the viewHolder
    //
    @Override
    public ComponentListAdapter.ComponentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        ComponentItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.component_item, parent, false);
        return new ComponentViewHolder(binding);
    }

    //
    //  binding each model class to it's xml element
    //
    @Override
    public void onBindViewHolder(@NonNull ComponentListAdapter.ComponentViewHolder holder, int position)
    {
        holder.bind(components.get(position));
    }

    //
    //  returns the list size
    //
    @Override
    public int getItemCount()
    {
       return (components != null) ? components.size() : 0;
    }

    //__________________________________________________________________________________________________
    //
    //  internal class ViewHolder
    //
    public class ComponentViewHolder extends RecyclerView.ViewHolder
    {
        private ComponentItemBinding binding;

        ComponentViewHolder(ComponentItemBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
            binding.setCmdHandler(componentCmdHandler());
        }

        //
        //  sets handlers belonging to the holder
        //
        private ComponentCmdHandler componentCmdHandler()
        {
            return new ComponentCmdHandler() {
                @Override
                public void enterEntity(long id) { }
                @Override
                public boolean editEntity(UIEntity uiEntity)
                {
                    return false;
                }
                @Override
                public void addNewEntity() { }
                @Override
                public void editEntityTitle(CharSequence s, int start, int before, int count) { }
                @Override
                public void saveEntity(boolean isNew) { }
                @Override
                public void deleteEntity() { }
            };
        }

        //
        //  binds the storage unit to the model
        //
        private void bind(@NonNull UIComponent UIComponent)
        {
            binding.setUIComponent(UIComponent);
            binding.executePendingBindings();
        }
    }
}