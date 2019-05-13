package com.mwb.digitalstorage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.mwb.digitalstorage.R;
import com.mwb.digitalstorage.command_handlers.RackCmdHandler;
import com.mwb.digitalstorage.command_handlers.entity.PhotoCmdHandler;
import com.mwb.digitalstorage.databinding.RackItemBinding;
import com.mwb.digitalstorage.modelUI.UIEntity;
import com.mwb.digitalstorage.modelUI.UIRack;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


public class RackListAdapter extends RecyclerView.Adapter<RackListAdapter.RackViewHolder>
{
    private RackCmdHandler rackCmdHandlerCallBack;
    private PhotoCmdHandler photoCmdHandlerCallBack;
    private List<UIRack> UIRacks;


    public RackListAdapter(List<UIRack> UIRacks, RackCmdHandler rackCmdHandlerCallBack, PhotoCmdHandler photoCmdHandlerCallBack)
    {
        this.rackCmdHandlerCallBack = rackCmdHandlerCallBack;
        this.photoCmdHandlerCallBack = photoCmdHandlerCallBack;
        this.UIRacks = UIRacks;
    }

    //
    // viewHolder creator, returner with listener and viewItem attached
    //
    @Override
    public RackListAdapter.RackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        RackItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.rack_item, parent, false);
        return new RackListAdapter.RackViewHolder(binding);
    }

    //
    // binds the viewHolder to the adapter
    //
    @Override
    public void onBindViewHolder(@NonNull RackViewHolder holder, int position)
    {
        holder.bind(UIRacks.get(position));
    }

    //
    //  returns the list, or 0
    //
    @Override
    public int getItemCount()
    {
        return (UIRacks != null) ? UIRacks.size() : 0;
    }

    //__________________________________________________________________________________________________
    //
    //  internal class ViewHolder
    //
    public class RackViewHolder extends RecyclerView.ViewHolder
    {
        private RackItemBinding binding;

        RackViewHolder(RackItemBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
            binding.setCmdHandler(rackCmdHandler());
            binding.setPhotoCmdHandler(photoCmdHandler());
        }

        //
        //  sets handlers belonging to the rack
        //
        private RackCmdHandler rackCmdHandler()
        {
            // handlers and methods
            return new RackCmdHandler()
            {
                @Override
                public void enterEntity(long rackID) { rackCmdHandlerCallBack.enterEntity(rackID); }
                @Override
                public void addNewEntity() { rackCmdHandlerCallBack.addNewEntity(); }
                @Override
                public boolean editEntity(UIEntity uiEntity)
                {
                    rackCmdHandlerCallBack.editEntity(uiEntity);
                    return true;
                }
                @Override
                public void editEntityTitle(CharSequence s, int start, int before, int count)
                {
                    rackCmdHandlerCallBack.editEntityTitle(s, start, before, count);
                }
                @Override
                public void saveEntityEdit() { rackCmdHandlerCallBack.saveEntityEdit(); }
                @Override
                public void deleteEntity() { rackCmdHandlerCallBack.deleteEntity(); }
            };
        }

        //
        //  sets photo related handlers belonging to the rack
        //
        private PhotoCmdHandler photoCmdHandler()
        {
            return new PhotoCmdHandler()
            {
                @Override
                public void takePhoto() { photoCmdHandlerCallBack.takePhoto(); }
                @Override
                public void browsePhoto() { photoCmdHandlerCallBack.browsePhoto(); }
                @Override
                public void removePhoto() { photoCmdHandlerCallBack.removePhoto(); }
            };
        }

        //
        //  binds the UIRack unit to the model
        //
        private void bind(@NonNull UIRack UIRack)
        {
            binding.setUIRack(UIRack);
            binding.executePendingBindings();
        }
    }
}
