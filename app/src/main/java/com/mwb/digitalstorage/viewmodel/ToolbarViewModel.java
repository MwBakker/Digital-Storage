package com.mwb.digitalstorage.viewmodel;

import java.text.SimpleDateFormat;
import java.util.Date;
import androidx.lifecycle.ViewModel;


public class ToolbarViewModel extends ViewModel
{
    private String activityName;
    private String date;

    public ToolbarViewModel(String previousActivty)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd / MM");
        this.activityName = previousActivty;
        date = sdf.format(new Date());
    }

    //
    //  gets previous activity
    //
    public String getActivityName() { return activityName; }

    //
    //  gets date
    //
    public String getDate() { return date; }
}
