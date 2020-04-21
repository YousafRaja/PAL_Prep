package ca2.testproject.shotgunstudios2.palprepbeta1;

import android.app.Application;
import android.content.res.Configuration;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.util.HashMap;

import ca2.testproject.shotgunstudios.palprepbeta1.R;





/**GunTestCanada
 *- object to contain google analytics tracking info
 */


/**
 * Enum used to identify the tracker that needs to be used for tracking.
 *
 * A single tracker is usually enough for most purposes. In case you do need multiple trackers,
 * storing them all in Application object helps ensure that they are created only once per
 * application instance.
 */
public class GunTestCanada extends Application {

    public enum TrackerName {
        APP_TRACKER, // Tracker used only in this app.
        GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg: roll-up tracking.
        ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a company.

    }

    HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

    synchronized Tracker getTracker(TrackerName trackerId) {
        if (!mTrackers.containsKey(trackerId)) {

            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            Tracker t = analytics.newTracker(R.xml.analytics_global_config);
            mTrackers.put(trackerId, t);

        }
        return mTrackers.get(trackerId);
    }



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }




    @Override
    public void onCreate() {
        super.onCreate();




    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}