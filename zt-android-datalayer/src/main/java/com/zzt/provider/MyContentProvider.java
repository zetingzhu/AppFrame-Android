//package com.zzt.provider;
//
//import android.content.ContentProvider;
//import android.content.ContentValues;
//import android.database.Cursor;
//import android.net.Uri;
//import android.util.Log;
//
//import com.zzt.datastore.MultiProcessDataStoreUtil;
//
//public class MyContentProvider extends ContentProvider {
//    private static final String TAG = MyContentProvider.class.getSimpleName();
//
//    public MyContentProvider() {
//    }
//
//    @Override
//    public int delete(Uri uri, String selection, String[] selectionArgs) {
//        return 0;
//    }
//
//    @Override
//    public String getType(Uri uri) {
//        return null;
//    }
//
//    @Override
//    public Uri insert(Uri uri, ContentValues values) {
//        return null;
//    }
//
//    @Override
//    public boolean onCreate() {
//        Log.e(TAG, "LifecycleActivity MyContentProvider  ");
//        MultiProcessDataStoreUtil.INSTANCE.putData(0, getContext());
//        return true;
//    }
//
//    @Override
//    public Cursor query(Uri uri, String[] projection, String selection,
//                        String[] selectionArgs, String sortOrder) {
//        return null;
//    }
//
//    @Override
//    public int update(Uri uri, ContentValues values, String selection,
//                      String[] selectionArgs) {
//        return 0;
//    }
//}