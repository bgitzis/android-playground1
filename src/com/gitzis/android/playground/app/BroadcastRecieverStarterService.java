package com.gitzis.android.playground.app;

import android.app.IntentService;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * this service is just for the sake of demonstrating starterService setup.
 * same code as in onHandleIntent can be placed instead of startService(...)
 * 
 * @author bgitzis
 */
public class BroadcastRecieverStarterService extends IntentService {
    public BroadcastRecieverStarterService() {
        super(BroadcastRecieverStarterService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        this.registerReceiver(new PhoneInteractionBroadcastReceiver(), new IntentFilter(Intent.ACTION_SCREEN_ON));
    }

}
