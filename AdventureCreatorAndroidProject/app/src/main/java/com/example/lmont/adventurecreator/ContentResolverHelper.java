package com.example.lmont.adventurecreator;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by lmont on 10/10/2016.
 */

public class ContentResolverHelper {

    public static ContentResolverHelper instance;

    public static final String ACCOUNT_TYPE = "com.lmont.iceicebb";
    public static final String ACCOUNT = "default_account";
    public static final String AUTHORITY = "com.example.lmont.adventurecreator.AdventureContentProvider";
    Bundle settingsBundle;
    Account mAccount;
    Activity setupActivity;

    private float refreshTime = 60f;
    private boolean willAutoRefresh = false;

    public static ContentResolverHelper getInstance(Activity activity) {
        if (instance == null)
            instance = new ContentResolverHelper(activity);

        return instance;
    }

    private ContentResolverHelper(Activity activity) {
        setupActivity = activity;
        setupContentResolver();
    }

    public void requestSync() {
        ContentResolver.requestSync(mAccount, AUTHORITY, settingsBundle);
    }

    private void setupContentResolver()  {
        mAccount = createSyncAccount(setupActivity.getApplicationContext());

        setupActivity.getContentResolver().registerContentObserver(AdventureContentProvider.CONTENT_URI,true,new NewsContentObserver(new Handler()));

        settingsBundle = new Bundle();
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_EXPEDITED, true);

        ContentResolver.setSyncAutomatically(mAccount,AUTHORITY,willAutoRefresh);
        ContentResolver.addPeriodicSync(
                mAccount,
                AUTHORITY,
                Bundle.EMPTY,
                60);
    }

    private Account createSyncAccount(Context context) {
        // Create the account type and default account
        Account newAccount = new Account(
                ACCOUNT, ACCOUNT_TYPE);
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(
                        setupActivity.ACCOUNT_SERVICE);
        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call context.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */
        } else {
            /*
             * The account exists or some other error occurred. Log this, report it,
             * or handle it internally.
             */
        }
        return newAccount;
    }

    private class NewsContentObserver extends ContentObserver {

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public NewsContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {

        }
    }
}
