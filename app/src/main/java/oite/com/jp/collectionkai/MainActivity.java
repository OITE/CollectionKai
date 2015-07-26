package oite.com.jp.collectionkai;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import oite.com.jp.collectionkai.Database.DatabaseHelper;


public class MainActivity extends FragmentActivity {

    static Point Size = null;
    Button tButton = null;
    int transactionNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper helper = new DatabaseHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.close();

        tButton = (Button)findViewById(R.id.transactionbutton);
        tButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(transactionNum==0){
                    showNextFragment();
                    transactionNum=1;
                }else if(transactionNum==1) {
                    showNNextFragment();
                    transactionNum=2;
                }else if(transactionNum==2){
                    showBeforeFragment();
                    transactionNum=0;
                }
            }
        });

        if(Size == null)Size = new Point();
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(Size);

        FragmentManager fm = this.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ShellGatheringFragment fragment = new ShellGatheringFragment();

        ft.add(R.id.mainLayout,fragment);

        ft.commit();

    }

    private void showNextFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.replace(R.id.mainLayout, new CultivationFragment());

        transaction.commit();
    }
    private void showBeforeFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.replace(R.id.mainLayout,new ShellGatheringFragment());

        transaction.commit();
    }
    private void showNNextFragment(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.replace(R.id.mainLayout,new CollectionViewPagerFragment());

        transaction.commit();
    }



}
