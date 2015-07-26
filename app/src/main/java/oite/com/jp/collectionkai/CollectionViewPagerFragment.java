package oite.com.jp.collectionkai;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;

import oite.com.jp.collectionkai.CollectAdapter;
import oite.com.jp.collectionkai.R;

/**
 * Created by Ume on 2015/07/14.
 */
public class CollectionViewPagerFragment extends Fragment {

    private View calendarpagerLayout = null;

    ViewPager viewPager;
    CollectAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        calendarpagerLayout = inflater.inflate(R.layout.fragment_collectionpager,container,false);

        adapter = new CollectAdapter(this.getActivity().getApplicationContext(),this.getChildFragmentManager());

        viewPager = (ViewPager)calendarpagerLayout.findViewById(R.id.calendar_viewpager);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);

        adapter.notifyDataSetChanged();

        return calendarpagerLayout;

    }


}
