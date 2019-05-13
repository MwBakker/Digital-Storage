package com.mwb.digitalstorage.misc;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.NumberPicker;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)//For backward-compability
public class CustomNumberPicker extends NumberPicker {

    public CustomNumberPicker(Context context) {
        super(context);
    }

    //
    // 2nd overridable constructor
    //
    public CustomNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        processAttributeSet(attrs);
    }

    //
    // 3rd overridable constructor
    //
    public CustomNumberPicker(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        processAttributeSet(attrs);
    }

    //
    // set attribute on the UI element
    //
    void processAttributeSet(AttributeSet attrs)
    {
        //This method reads the parameters given in the xml file and sets the properties according to it
        this.setMinValue(attrs.getAttributeIntValue(null, "min", 0));
        this.setMaxValue(attrs.getAttributeIntValue(null, "max", 0));
    }
}
