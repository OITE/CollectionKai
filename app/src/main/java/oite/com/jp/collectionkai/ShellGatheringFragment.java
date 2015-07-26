package oite.com.jp.collectionkai;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import oite.com.jp.collectionkai.Database.DatabaseHelper;

/**
 * Created by fuyuk on 2015/07/25.
 */
public class ShellGatheringFragment extends Fragment {

    int shellNum = 0;
    Context mContext = null;
    FrameLayout layout = null;
    Random rand = new Random();
    DatabaseHelper helper = null;
    Handler handler = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout = (FrameLayout)inflater.inflate(R.layout.fragment_shellgathering,container,false);
        mContext = this.getActivity().getApplicationContext();
        helper = new DatabaseHelper(mContext);
        handler = new Handler(){
            @Override
            public void dispatchMessage(Message msg){
                if(msg.what==1){
                    if(shellNum++ <= 20) {
                        makeShell(getIdtoRare());
                    }
                    handler.sendEmptyMessageDelayed(1, 2 * 1000);
                }else{
                    super.dispatchMessage(msg);
                }
            }
        };
        handler.sendEmptyMessage(1);


        return layout;
    }



    int getIdtoRare(){
        SQLiteDatabase readDB = helper.getReadableDatabase();
        int value = rand.nextInt(100);
        Cursor c ;
        if(value<70){
            c = readDB.rawQuery("select _id from kai where rare=?",new String[]{"0"});
        }else if(value<90){
            c = readDB.rawQuery("select _id from kai where rare=?",new String[]{"1"});
        }else{
            c = readDB.rawQuery("select _id from kai where rare=?",new String[]{"2"});
        }
        c.moveToFirst();
        return c.getInt(c.getColumnIndex("_id"));
    }

    void makeShell(final int id_kai){

        int size = MainActivity.Size.x/5;

        final SQLiteDatabase readDB = helper.getReadableDatabase();
        String sqlstr = "select _id,rare,path "+"from kai "+"where _id=? ";
        Cursor c = readDB.rawQuery(sqlstr, new String[]{String.valueOf(id_kai)});
        c.moveToFirst();
        final int id = c.getInt(c.getColumnIndex("_id"));
        final String path = c.getString(c.getColumnIndex("path"));

        ImageView iv = new ImageView(mContext);
        try {
            InputStream is = mContext.getResources().getAssets().open("image/"+path);
            iv.setImageBitmap(BitmapFactory.decodeStream(is));
            is.close();
        } catch (IOException e) {
        }

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(size,size);
        lp.topMargin  = rand.nextInt(MainActivity.Size.y - size);
        lp.leftMargin = rand.nextInt(MainActivity.Size.x - size);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                layout.removeView(v);

                shellNum --;

                SQLiteDatabase writeDB = helper.getWritableDatabase();
                ContentValues value = new ContentValues();
                value.put("kai_id", id);
                writeDB.insert("stackKai", null, value);

                Log.d("db", "id:" + id);

                writeDB.close();

                debugStackPrint();
            }
        });
        readDB.close();

        layout.addView(iv, lp);
    }

    void debugStackPrint(){
        SQLiteDatabase readDB = helper.getReadableDatabase();
        String sqlstr = "select * "+"from stackKai";
        Cursor c = readDB.rawQuery(sqlstr,null);
        c.moveToFirst();
        while(c.moveToNext()){
            int id = c.getInt(c.getColumnIndex("_id"));
            int kai_id = c.getInt(c.getColumnIndex("kai_id"));
            Log.d("stackKai",id+"::"+kai_id);
        }
        readDB.close();
    }


}
