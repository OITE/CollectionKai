package oite.com.jp.collectionkai;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import oite.com.jp.collectionkai.Database.DatabaseHelper;

/**
 * Created by fuyuk on 2015/07/25.
 */
public class CultivationFragment extends Fragment {

    Context mContext;
    LinearLayout layout;
    DatabaseHelper helper;
    Handler handler ;
    Random rand;
    TextView infoText1 = null;
    TextView infoText2 = null;

    boolean[] isKai = new boolean[2];

    Button b1,b2,b3;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout = (LinearLayout)inflater.inflate(R.layout.fragment_cultivation,container,false);
        mContext = this.getActivity().getApplicationContext();
        helper = new DatabaseHelper(mContext);
        isKai[0]=false;isKai[1]=false;
        rand=new Random();
        infoText1 = (TextView)layout.findViewById(R.id.infoText1);
        infoText2 = (TextView)layout.findViewById(R.id.infoText2);


        recount();

        return layout;
    }

    void recount(){
        b1 = (Button)layout.findViewById(R.id.b1);
        b2 = (Button)layout.findViewById(R.id.b2);
        b3 = (Button)layout.findViewById(R.id.b3);

        SQLiteDatabase readDB = helper.getReadableDatabase();
        Cursor c1 =readDB.rawQuery("select * from stackKai where kai_id=?", new String[]{"0"});
        Cursor c2 =readDB.rawQuery("select * from stackKai where kai_id=?", new String[]{"1"});
        Cursor c3 =readDB.rawQuery("select * from stackKai where kai_id=?", new String[]{"2"});

        final int c1c = c1.getCount();
        final int c2c = c2.getCount();
        final int c3c = c3.getCount();

        b1.setText("x"+c1.getCount());
        b2.setText("x"+c2.getCount());
        b3.setText("x"+c3.getCount());

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isKai[0] == false && c1c>0) {
                    ImageView image = (ImageView) layout.findViewById(R.id.kaiImage1);
                    try {
                        InputStream is = mContext.getResources().getAssets().open("image/kaiA.png");
                        image.setImageBitmap(BitmapFactory.decodeStream(is));
                        image.setAlpha(255.0f);
                        is.close();
                        isKai[0] = true;
                        kai_delete(0);
                        handler = new Handler(){
                            @Override
                            public void dispatchMessage(Message msg){
                                if(msg.what==1){
                                    getPearl(0,0);
                                    ((ImageView) layout.findViewById(R.id.kaiImage1)).setAlpha(0.0f);
                                    isKai[0]=false;
                                }else{
                                    super.dispatchMessage(msg);
                                }
                            }
                        };
                        handler.sendEmptyMessageDelayed(1,5*1000);
                    } catch (IOException e) {
                    }
                }else if (isKai[1] == false && c1c>0) {
                    ImageView image = (ImageView) layout.findViewById(R.id.kaiImage2);
                    try {
                        InputStream is = mContext.getResources().getAssets().open("image/kaiA.png");
                        image.setImageBitmap(BitmapFactory.decodeStream(is));
                        is.close();
                        kai_delete(0);
                        isKai[1] = true;
                        handler = new Handler(){
                            @Override
                            public void dispatchMessage(Message msg){
                                if(msg.what==1){
                                    getPearl(0,1);
                                    ((ImageView) layout.findViewById(R.id.kaiImage2)).setAlpha(0.0f);
                                    isKai[1]=false;
                                }else{
                                    super.dispatchMessage(msg);
                                }
                            }
                        };
                        handler.sendEmptyMessageDelayed(1,5*1000);
                    } catch (IOException e) {
                    }
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isKai[0] == false && c2c>0) {
                    ImageView image = (ImageView) layout.findViewById(R.id.kaiImage1);
                    try {
                        InputStream is = mContext.getResources().getAssets().open("image/kaiB.png");
                        image.setImageBitmap(BitmapFactory.decodeStream(is));
                        is.close();
                        isKai[0]=true;
                        kai_delete(1);
                        handler = new Handler(){
                            @Override
                            public void dispatchMessage(Message msg){
                                if(msg.what==1){
                                    getPearl(1,0);
                                    ((ImageView) layout.findViewById(R.id.kaiImage1)).setAlpha(0.0f);
                                    isKai[0]=false;
                                }else{
                                    super.dispatchMessage(msg);
                                }
                            }
                        };
                        handler.sendEmptyMessageDelayed(1,5*1000);
                    } catch (IOException e) {
                    }
                }else if (isKai[1] == false && c2c>0) {
                    ImageView image = (ImageView) layout.findViewById(R.id.kaiImage2);
                    try {
                        InputStream is = mContext.getResources().getAssets().open("image/kaiB.png");
                        image.setImageBitmap(BitmapFactory.decodeStream(is));
                        is.close();
                        isKai[1]=true;
                        kai_delete(1);
                        handler = new Handler(){
                            @Override
                            public void dispatchMessage(Message msg){
                                if(msg.what==1){
                                    getPearl(1,1);
                                    ((ImageView) layout.findViewById(R.id.kaiImage2)).setAlpha(0.0f);
                                    isKai[1]=false;
                                }else{
                                    super.dispatchMessage(msg);
                                }
                            }
                        };
                        handler.sendEmptyMessageDelayed(1,5*1000);
                    } catch (IOException e) {
                    }
                }
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (isKai[0] == false && c3c>0) {
                ImageView image = (ImageView) layout.findViewById(R.id.kaiImage1);
                try {
                    InputStream is = mContext.getResources().getAssets().open("image/kaiC.png");
                    image.setImageBitmap(BitmapFactory.decodeStream(is));
                    image.setAlpha(255.0f);
                    is.close();
                    isKai[0]=true;
                    kai_delete(2);
                    handler = new Handler(){
                        @Override
                        public void dispatchMessage(Message msg){
                            if(msg.what==1){
                                getPearl(2,0);
                                ((ImageView) layout.findViewById(R.id.kaiImage1)).setAlpha(0.0f);
                                isKai[0]=false;
                            }else{
                                super.dispatchMessage(msg);
                            }
                        }
                    };
                    handler.sendEmptyMessageDelayed(1,5*1000);
                } catch (IOException e) {
                }
            } else if (isKai[1] == false && c3c>0) {
                ImageView image = (ImageView) layout.findViewById(R.id.kaiImage2);
                try {
                    InputStream is = mContext.getResources().getAssets().open("image/kaiC.png");
                    image.setImageBitmap(BitmapFactory.decodeStream(is));
                    image.setAlpha(255.0f);
                    is.close();
                    isKai[1]=true;
                    kai_delete(2);
                    handler = new Handler(){
                        @Override
                        public void dispatchMessage(Message msg){
                            if(msg.what==1){
                                getPearl(2,1);
                                ((ImageView) layout.findViewById(R.id.kaiImage2)).setAlpha(0.0f);
                                isKai[1]=false;
                            }else{
                                super.dispatchMessage(msg);
                            }
                        }
                    };
                    handler.sendEmptyMessageDelayed(1,5*1000);
                } catch (IOException e) {
                }
            }
            }
        });

        readDB.close();
    }


    void getPearl(int rare,int place){
        int getID = 0;
        int r = rand.nextInt(100);
        if(rare == 0){
            if(r<50)getID=0;
            else if(r<70)getID=1;
            else if(r<80)getID=2;
            else if(r<85)getID=3;
            else if(r<88)getID=4;
            else if(r<90)getID=5;
            else if(r<92)getID=6;
            else if(r<94)getID=7;
            else if(r<96)getID=8;
            else getID=0;
        }else if(rare ==1){
            if(r<30)getID=0;
            else if(r<50)getID=0;
            else if(r<60)getID=1;
            else if(r<68)getID=2;
            else if(r<75)getID=3;
            else if(r<80)getID=4;
            else if(r<84)getID=5;
            else if(r<87)getID=6;
            else if(r<89)getID=7;
            else if(r<90)getID=8;
            else getID=0;
        }else if(rare == 2){
            if(r<10)getID=0;
            else if(r<20)getID=0;
            else if(r<30)getID=1;
            else if(r<40)getID=2;
            else if(r<50)getID=3;
            else if(r<65)getID=4;
            else if(r<75)getID=5;
            else if(r<82)getID=6;
            else if(r<89)getID=7;
            else if(r<98)getID=8;
            else getID=0;
        }
        SQLiteDatabase writeDB = helper.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("pearl_id", getID);
        writeDB.insert("stackPearl", null, value);
        writeDB.close();

        SQLiteDatabase readDB = helper.getReadableDatabase();
        Cursor c = readDB.rawQuery("select name from pearl where _id=?", new String[]{String.valueOf(getID)});
        c.moveToFirst();
        String name = c.getString(c.getColumnIndex("name"));
        if(place==0)infoText1.setText("I get a pearl !! [ " + name + " ]");
        if(place==1)infoText2.setText("I get a pearl !! [ " + name + " ]");
        readDB.close();


    }

    void kai_delete(int rare){
        SQLiteDatabase readDB = helper.getReadableDatabase();
        Cursor c = readDB.rawQuery("select * from stackKai where kai_id=?",new String[]{""+rare});
        c.moveToFirst();
        while(c.moveToNext()){
            Log.d("d",c.getInt(c.getColumnIndex("_id"))+":"+c.getInt(c.getColumnIndex("kai_id")));
        }

        c.moveToLast();
        int did = c.getInt(c.getColumnIndex("_id"));
        readDB.close();

        SQLiteDatabase writeDB = helper.getWritableDatabase();
        writeDB.delete("stackKai", "_id=?", new String[]{""+did});
        writeDB.close();

        readDB = helper.getReadableDatabase();
        Cursor c1 =readDB.rawQuery("select * from stackKai where kai_id=?",new String[]{"0"});
        Cursor c2 =readDB.rawQuery("select * from stackKai where kai_id=?",new String[]{"1"});
        Cursor c3 =readDB.rawQuery("select * from stackKai where kai_id=?",new String[]{"2"});

        b1.setText("x" + c1.getCount());
        b2.setText("x" + c2.getCount());
        b3.setText("x" + c3.getCount());

        readDB.close();
    }





}
