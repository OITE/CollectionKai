package oite.com.jp.collectionkai;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by fuyuk on 2015/07/26.
 */
public class CollectionFragment extends Fragment {

    LinearLayout layout ;
    Context mContext ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mContext = this.getActivity().getApplicationContext();

        layout = (LinearLayout)inflater.inflate(R.layout.fragment_collection,container,false);

        Bundle b = this.getArguments();

        ImageView iv = (ImageView) layout.findViewById(R.id.infomationImage);

        try {
            InputStream is = mContext.getResources().getAssets().open("image/pearl/" + b.getString("path"));
            iv.setImageBitmap(BitmapFactory.decodeStream(is));
            is.close();
        } catch (IOException e) {
        }

        return layout;
    }





}
