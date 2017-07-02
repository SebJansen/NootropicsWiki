package nl.hva.mobileapplicationdevelopment.jansens015.nootropicswiki.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import nl.hva.mobileapplicationdevelopment.jansens015.nootropicswiki.fragments.EightFragment;
import nl.hva.mobileapplicationdevelopment.jansens015.nootropicswiki.fragments.FiveFragment;
import nl.hva.mobileapplicationdevelopment.jansens015.nootropicswiki.fragments.FourFragment;
import nl.hva.mobileapplicationdevelopment.jansens015.nootropicswiki.fragments.NineFragment;
import nl.hva.mobileapplicationdevelopment.jansens015.nootropicswiki.fragments.OneFragment;
import nl.hva.mobileapplicationdevelopment.jansens015.nootropicswiki.R;
import nl.hva.mobileapplicationdevelopment.jansens015.nootropicswiki.fragments.SevenFragment;
import nl.hva.mobileapplicationdevelopment.jansens015.nootropicswiki.fragments.SixFragment;
import nl.hva.mobileapplicationdevelopment.jansens015.nootropicswiki.fragments.TenFragment;
import nl.hva.mobileapplicationdevelopment.jansens015.nootropicswiki.fragments.ThreeFragment;
import nl.hva.mobileapplicationdevelopment.jansens015.nootropicswiki.fragments.TwoFragment;

public class ScrollableTabsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollable_tabs);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        SharedPreferences requestCodes = getSharedPreferences("RequestCodes", 0);
        SharedPreferences.Editor editor = requestCodes.edit();

        editor.putInt("UpdateArticle", 24816);

        editor.commit();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        OneFragment main = new OneFragment();

        adapter.addFrag(main, "General");
        adapter.addFrag(new TwoFragment(), "Noots");
        adapter.addFrag(new ThreeFragment(), "Warnings");
        adapter.addFrag(new FourFragment(), "Sources");
        adapter.addFrag(new FiveFragment(), "FIVE");
        adapter.addFrag(new SixFragment(), "SIX");
        adapter.addFrag(new SevenFragment(), "SEVEN");
        adapter.addFrag(new EightFragment(), "EIGHT");
        adapter.addFrag(new NineFragment(), "NINE");
        adapter.addFrag(new TenFragment(), "TEN");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context_menu, menu);
        super.onCreateContextMenu(menu, view, menuInfo);
    }
}
