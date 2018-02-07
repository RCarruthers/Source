package com.uom.ryan.potholes.application.flic;

import android.content.Context;

import com.uom.ryan.potholes.application.model.Report;
import com.uom.ryan.potholes.application.presenter.SourcePresenterImpl;

import io.flic.lib.FlicBroadcastReceiver;
import io.flic.lib.FlicButton;

/**
 * Created by Ryan on 04/11/2017.
 */

public class FlicReportBroadcastReceiver extends FlicBroadcastReceiver {

    @Override
    protected void onRequestAppCredentials(Context context) {

    }

    @Override
    public void onButtonSingleOrDoubleClickOrHold(Context context, FlicButton button, boolean wasQueued, int timeDiff, boolean isSingleClick, boolean isDoubleClick, boolean isHold) {
        if(isSingleClick) {
            SourcePresenterImpl.addReportToDatabase(context, Report.Severity.LOW);
        } else if(isDoubleClick)
            SourcePresenterImpl.addReportToDatabase(context, Report.Severity.MEDIUM);
        else if(isHold)
            SourcePresenterImpl.addReportToDatabase(context, Report.Severity.HIGH);
    }
}
