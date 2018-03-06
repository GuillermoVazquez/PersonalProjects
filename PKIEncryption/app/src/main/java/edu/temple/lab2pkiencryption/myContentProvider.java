package edu.temple.lab2pkiencryption;

import android.app.Application;
import android.content.ClipData;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.view.View;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guillermo on 2/14/18.
 */

public class myContentProvider extends ContentProvider {

    static final String PROVIDER_NAME = "com.example.PKIEncryption.myContentProvider";
    static final String URL = "content://" + PROVIDER_NAME + "/keys";
    static final Uri CONTENT_URI = Uri.parse(URL);
    Context context;
    List<MyObject>  items = new ArrayList<MyObject>();

    public void passContext(Context context){
        this.context = context;
    }

    @Override
    public boolean onCreate() {
        return true ;
    }

    @Override
    public int delete( Uri uri, String s,  String[] strings) {
        return 0;
    }

    @Override
    public String getType( Uri uri) {
        return null;
    }

    @Override
    public int update( Uri uri,  ContentValues contentValues,  String s, String[] strings) {
        return 0;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        MatrixCursor cursor = new MatrixCursor(new String[] { "public", "private"});

        for (MyObject kp : items) {
            MatrixCursor.RowBuilder builder = cursor.newRow();
            builder.add("public", kp.getPublicKey());
            builder.add("private", kp.getPrivateKey());
        }

        cursor.setNotificationUri(getContext().getContentResolver(),uri);

        return cursor;
    }


    @Override
    public Uri insert(Uri uri, ContentValues value) {
        //from strings to byte arrays
        String publicKey = value.getAsString("public");
        String privateKey = value.getAsString("private");
        //lets convert the byte arrays to keys
        items.add(new MyObject(publicKey,privateKey));



        //THE MAGIC COMES HERE !!!! when notify change and its observers registred make a requery
        // so they are going to call query on the content provider and now we are going to get a
        // new Cursor with the new item
        getContext().getContentResolver().notifyChange(uri, null);

        return uri;
    }

}

