package com.mwb.digitalstorage;

import android.content.Intent;
import android.os.Bundle;
import com.mwb.digitalstorage.command_handlers.SearchCmdHandler;
import com.mwb.digitalstorage.command_handlers.SearchedEntityCmdHandler;
import com.mwb.digitalstorage.databinding.ActivitySearchBinding;
import com.mwb.digitalstorage.viewmodel.SearchViewModel;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;


public class SearchActivity extends AppCompatActivity
{
    private SearchViewModel searchVM;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        searchVM = ViewModelProviders.of(this).get(SearchViewModel.class);
        searchVM.setViewModelElements( getIntent().getStringExtra("previous_activity_name"), searchedEntityCmdHandler());

        ActivitySearchBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        binding.setVm(searchVM);
        binding.setCmdHandler(searchCmdHandler());
    }

    //  handler for the search activity
    private SearchCmdHandler searchCmdHandler()
    {
        return new SearchCmdHandler()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                String input = s.toString();
                if (input != "")
                {
                    searchVM.searchRelevance(input);
                }
            }
            @Override
            public void goBack() { onBackPressed(); }
        };
    }

    // handler for the searched entity
    private SearchedEntityCmdHandler searchedEntityCmdHandler()
    {
        return new SearchedEntityCmdHandler()
        {
            @Override
            public void goToSearchedEntity(Class entityClass, long entityID)
            {
                Intent intent = new Intent(SearchActivity.this, entityClass);
                intent.putExtra("entity_id", entityID);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        };
    }
}
