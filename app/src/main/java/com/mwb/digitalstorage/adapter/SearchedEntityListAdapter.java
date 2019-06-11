package com.mwb.digitalstorage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.mwb.digitalstorage.R;
import com.mwb.digitalstorage.command_handlers.SearchedEntityCmdHandler;
import com.mwb.digitalstorage.databinding.SearchedEntityBinding;
import com.mwb.digitalstorage.misc.ImageProcessor;
import com.mwb.digitalstorage.model.Component;
import com.mwb.digitalstorage.modelUI.UIEntity;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


public class SearchedEntityListAdapter extends RecyclerView.Adapter<SearchedEntityListAdapter.SearchedEntityViewHolder>
{
    private List<UIEntity> uiEntities;
    private SearchedEntityCmdHandler searchedEntityCmdHandlerCallBack;
    private ImageProcessor imgProcessor;


    public SearchedEntityListAdapter(SearchedEntityCmdHandler searchedEntityCmdHandlerCallBack)
    {
        this.searchedEntityCmdHandlerCallBack = searchedEntityCmdHandlerCallBack;
        imgProcessor = new ImageProcessor();
    }

    //  sets the list source of the adapter
    public void setListSource(List<UIEntity> uiEntities)
    {
        this.uiEntities = uiEntities; }

    //  creates the viewHolder
    @Override
    public SearchedEntityListAdapter.SearchedEntityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        SearchedEntityBinding binding = DataBindingUtil.inflate(inflater, R.layout.searched_entity, parent, false);
        return new SearchedEntityViewHolder(binding);
    }

    //  binds recyclerView to viewHolder
    @Override
    public void onBindViewHolder(@NonNull SearchedEntityListAdapter.SearchedEntityViewHolder holder, int position)
    {
        holder.bind(uiEntities.get(position));
    }

    //  returns the list size
    @Override
    public int getItemCount() { return (uiEntities != null) ? uiEntities.size() : 0; }

    //__________________________________________________________________________________________________
    //
    //  internal class ViewHolder
    //
    class SearchedEntityViewHolder extends RecyclerView.ViewHolder
    {
        private SearchedEntityBinding binding;

        SearchedEntityViewHolder(SearchedEntityBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
            binding.setCmdHandler(searchedEntityCmdHandler());
        }

        //  sets handlers belonging to the holder
        private SearchedEntityCmdHandler searchedEntityCmdHandler()
        {
            // handlers and methods
            return new SearchedEntityCmdHandler()
            {
                @Override
                public void goToSearchedEntity(Class belongingClass, String className, long id)
                {
                    searchedEntityCmdHandlerCallBack.goToSearchedEntity(belongingClass, className, id);
                }
            };
        }

        //  binds the storage unit to the model
        private void bind(@NonNull UIEntity uiEntity)
        {
            uiEntity.setImg(imgProcessor.decodeImgPath(uiEntity.getImgPath()));
            binding.setUIEntity(uiEntity);
            binding.executePendingBindings();
        }
    }
}