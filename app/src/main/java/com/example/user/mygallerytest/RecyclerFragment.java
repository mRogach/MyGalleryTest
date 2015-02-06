package com.example.user.mygallerytest;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 04.02.2015.
 */
public class RecyclerFragment extends Fragment  implements  MyCallBack{
    private String url = "http://random.cat/meow";
    private RecyclerView recyclerView;
    private int columnCount;
    private String imgUrl;

    private List <Picture> imageList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        imageList = new ArrayList<Picture>();
        columnCount = 2;
        //imageLoader = new ImageLoader(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
              recyclerView.setLayoutManager(new StaggeredGridLayoutManager(columnCount, StaggeredGridLayoutManager.VERTICAL));
        new StaggeredGridLayoutManager.LayoutParams(5,10);
        final MyRecyclerAdapter adapter = new MyRecyclerAdapter(getActivity(), imageList, R.layout.item, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        //Toast.makeText(getActivity(), "Photo", Toast.LENGTH_LONG).show();
                        final Fragment fragment = DetailPhotoFragment.newInstance(imageList);
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.fragmentContainer, fragment);
                        ft.addToBackStack("tag");
                        ft.commitAllowingStateLoss();
                    }
                })
        );
        return view;

    }
//    private List<Picture> createList() {
//        List<Picture> items = new ArrayList<Picture>();
//        for (int i = 0; i < 200; i++) {
//            items.add(new Picture(imgUrl ));
//        }
//        return items;
//    }


    @Override
    public void callBack(String str) {
        imgUrl = str;
        imageList.add(new Picture(str));
        Log.v("Exceptiohdsgfdhfgdhdfgdhf",str);
    }
}
