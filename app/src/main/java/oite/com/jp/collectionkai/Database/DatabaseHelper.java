package oite.com.jp.collectionkai.Database;

/**
 * Created by Ume on 2015/07/11.
 */

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "n2_database.db";
    public static final int DATABASE_VERSION = 1;

    Context mContext = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context ;
    }

    public void onCreate(SQLiteDatabase db) {
        try{
            this.execSql(db,"create");
            Log.d("db", "create");
        }catch(Exception e){
            e.printStackTrace();
        }
    }



    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try{
            execSql(db,"sql/drop");
        }catch(IOException e){
            e.printStackTrace();
        }
        onCreate(db);
    }

    private void execSql(SQLiteDatabase db,String assetsDir) throws IOException {
        AssetManager as = mContext.getResources().getAssets();
        try {
            String files[] = as.list(assetsDir);
            for (int i = 0; i < files.length; i++) {
                String str = readFile(as.open(assetsDir + "/" + files[i]));
                for (String sql: str.split("/")){
                    db.execSQL(sql);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readFile(InputStream is) throws IOException{
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(is,"SJIS"));

            StringBuilder sb = new StringBuilder();
            String str;
            while((str = br.readLine()) != null){
                sb.append(str +"\n");
            }
            return sb.toString();
        } finally {
            if (br != null) br.close();
        }
    }



}
