package com.mwb.digitalstorage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.mwb.digitalstorage.R;
import com.mwb.digitalstorage.command_handlers.StorageCmdHandler;
import com.mwb.digitalstorage.databinding.StorageItemBinding;
import com.mwb.digitalstorage.modelUI.UIEntity;
import com.mwb.digitalstorage.modelUI.UIStorage;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


public class StorageListAdapter extends RecyclerView.Adapter<StorageListAdapter.StorageViewHolder>
{
    private List<UIStorage> storageUnits;
    private StorageCmdHandler mainViewCmdHandlerCallBack;


    public StorageListAdapter(List<UIStorage> storageUnits, StorageCmdHandler mainViewCmdHandler)
    {
        this.storageUnits = storageUnits;
        this.mainViewCmdHandlerCallBack = mainViewCmdHandler;
    }

    //
    //  creates the viewHolder
    //
    @Override
    public StorageListAdapter.StorageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        StorageItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.storage_item, parent, false);
        return new StorageViewHolder(binding);
    }

    //
    //  binds recyclerView to viewHolder
    //
    @Override
    public void onBindViewHolder(@NonNull StorageListAdapter.StorageViewHolder holder, int position)
    {
        holder.bind(storageUnits.get(position));
    }

    //
    //  returns the list size
    //
    @Override
    public int getItemCount()
    {
        return (storageUnits != null) ? storageUnits.size() : 0;
    }

    //__________________________________________________________________________________________________
    //
    //  internal class ViewHolder
    //
    class StorageViewHolder extends RecyclerView.ViewHolder
    {
        private StorageItemBinding binding;

        StorageViewHolder(StorageItemBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
            binding.setCmdHandler(storageCmdHandler());
        }

        //
        //  sets handlers belonging to the holder
        //
        private StorageCmdHandler storageCmdHandler()
        {
            // handlers and methods
            return new StorageCmdHandler()
            {
                @Override
                public void enterEntity(long storageID) { mainViewCmdHandlerCallBack.enterEntity(storageID); }
                @Override
                public void addNewEntity() {  mainViewCmdHandlerCallBack.addNewEntity(); }
                @Override
                public boolean editEntity(UIEntity uiEntity)
                {
                    mainViewCmdHandlerCallBack.editEntity(uiEntity);
                    return true;
                }
                @Override
                public void editEntityTitle(CharSequence s, int start, int before, int count)
                {
                    mainViewCmdHandlerCallBack.editEntityTitle(s, start, before, count);
                }
                @Override
                public void saveEntityEdit() { mainViewCmdHandlerCallBack.saveEntityEdit(); }
                @Override
                public void deleteEntity() { mainViewCmdHandlerCallBack.deleteEntity(); }
                @Override
                public void editStorageLocation(CharSequence s, int start, int before, int count)
                {
                    mainViewCmdHandlerCallBack.editStorageLocation(s, start, before, count);
                }
            };
        }

        //
        //  binds the UIStorage unit to the model
        //
        private void bind(@NonNull UIStorage UIStorage)
        {
            binding.setUIStorage(UIStorage);
            binding.executePendingBindings();
        }
    }
}