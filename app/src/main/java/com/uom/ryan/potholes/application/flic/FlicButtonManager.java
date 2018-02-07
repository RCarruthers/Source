package com.uom.ryan.potholes.application.flic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import io.flic.lib.FlicAppNotInstalledException;
import io.flic.lib.FlicBroadcastReceiverFlags;
import io.flic.lib.FlicButton;
import io.flic.lib.FlicManager;
import io.flic.lib.FlicManagerInitializedCallback;

/**
 * Created by Ryan on 04/11/2017.
 */

public class FlicButtonManager {

    private Context context;

    public FlicButtonManager(Context c) {
        this.context = c;
        setFlicCredentials();
    }

    private void setFlicCredentials() {
        FlicManager.setAppCredentials("65f3f603-2e0c-4c66-8d27-bb79294849fd","1d4520d4-94f6-43c5-ab56-0d1256e4f14e","FlicAppPoC");
    }

    public void grabButton() {
        try {
            FlicManager.getInstance(context, new FlicManagerInitializedCallback() {
                @Override
                public void onInitialized(FlicManager manager) {
                    manager.initiateGrabButton((Activity) context);
                }
            });
        } catch (FlicAppNotInstalledException err) {
            Toast.makeText(context, "Flic App is not installed", Toast.LENGTH_SHORT).show();
        }
    }

    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        FlicManager.getInstance(context, new FlicManagerInitializedCallback() {
            @Override
            public void onInitialized(FlicManager manager) {
                FlicButton button = manager.completeGrabButton(requestCode, resultCode, data);
                if (button != null) {
                    button.registerListenForBroadcast(FlicBroadcastReceiverFlags.ALL | FlicBroadcastReceiverFlags.UP_OR_DOWN | FlicBroadcastReceiverFlags.REMOVED);
                } else {
                }
            }
        });
    }

}
