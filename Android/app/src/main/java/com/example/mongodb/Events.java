package com.example.mongodb;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class Events extends Fragment {
    private TabLayout MyTabs;
    private ViewPager MyPage;

    public Events() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_events, container, false);

        MyTabs = (TabLayout) view.findViewById(R.id.mytabs);
        MyPage = (ViewPager) view.findViewById(R.id.MyPage);
        MyTabs.setupWithViewPager(MyPage);
        setUpViewPager(MyPage);
        return view;
    }
    public void setUpViewPager(ViewPager viewPager) {
        MyViewPageAdapter Adapter = new MyViewPageAdapter(getFragmentManager());
        Adapter.addFragmentPage(new Tevents(), "All events");
        Adapter.addFragmentPage(new Mevents(),"My events");
        viewPager.setAdapter(Adapter);
    }
    public class MyViewPageAdapter extends FragmentPagerAdapter {
        private List<Fragment> MyFragment = new ArrayList<>();
        private  List<String> MyPageTitle = new ArrayList<>();

        public MyViewPageAdapter(FragmentManager manager) {
            super(manager);
        }

        public void addFragmentPage(Fragment fragment, String title) {
            MyFragment.add(fragment);
            MyPageTitle.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return MyFragment.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return MyPageTitle.get(position);
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
