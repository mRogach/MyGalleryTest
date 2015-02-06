package com.example.user.mygallerytest;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by User on 04.02.2015.
 */
public class DetailPhotoFragment extends Fragment {
    private ViewPager viewPager;
    private PagerAdapter adapter;
    private List <Picture> list;
    public static DetailPhotoFragment newInstance(List<Picture> list) {
        Bundle args = new Bundle();
        args.putSerializable("image", (java.io.Serializable) list);
        DetailPhotoFragment countryDetailFragment = new DetailPhotoFragment();
        countryDetailFragment.setArguments(args);
        return countryDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        list = (List<Picture>) getArguments().getSerializable("image");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_viewpager, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.pager);
        adapter = new ViewPagerAdapter(getActivity(), list);
        viewPager.setAdapter(adapter);
        return view;
    }


}
