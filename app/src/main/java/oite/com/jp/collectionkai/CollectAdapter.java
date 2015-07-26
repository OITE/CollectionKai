package oite.com.jp.collectionkai;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import oite.com.jp.collectionkai.Database.DatabaseHelper;

public class CollectAdapter extends FragmentPagerAdapter {

   // private ArrayList<SparseArray<Integer>> dateList;
    DatabaseHelper helper;

    public CollectAdapter(Context c , FragmentManager fm){
        super(fm);
        helper = new DatabaseHelper(c);
        //dateList = new ArrayList<SparseArray<Integer>>();
    }

    @Override
    public int getCount() {
        return 9;
    }

    boolean isDiscover(int id){
        SQLiteDatabase readDB = helper.getReadableDatabase();
        Cursor c = readDB.rawQuery("select * from stackPearl where _id=?",new String[]{""+id});
        if(c.getCount()>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Fragment getItem(int position) {

        SQLiteDatabase readDB = helper.getReadableDatabase();
        Cursor c = readDB.rawQuery("select * from pearl",null);
        c.moveToFirst();
        String name="",path="";boolean isDiscover;
        do{
            int id = c.getInt(c.getColumnIndex("_id"));
            name = c.getString(c.getColumnIndex("name"));
            path = c.getString(c.getColumnIndex("path"));
            isDiscover = this.isDiscover(id);
            if(id==position)break;
        }while(c.moveToNext());

        readDB.close();
        Bundle bundle = new Bundle();
        bundle.putInt("page", position);
        bundle.putString("path", path);
        bundle.putString("name", name);
        bundle.putBoolean("discover",isDiscover);

        CollectionFragment fragment = new CollectionFragment();
        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    public CharSequence getPageTitle(int Position){
        SQLiteDatabase readDB = helper.getReadableDatabase();
        Cursor c = readDB.rawQuery("select * from pearl",null);
        c.moveToFirst();
        String name="",path="";
        do{
            int id = c.getInt(c.getColumnIndex("_id"));
            name = c.getString(c.getColumnIndex("name"));
            if(id==Position)break;
        }while(c.moveToNext());
        readDB.close();
        return Position+" : "+name;
    }
}
