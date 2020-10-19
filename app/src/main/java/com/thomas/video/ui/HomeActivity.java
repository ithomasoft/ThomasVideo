package com.thomas.video.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.thomas.video.R;
import com.thomas.video.adapter.ThomasFragmentStateAdapter;
import com.thomas.video.core.AbstractActivity;
import com.thomas.video.fragment.CategoryFragment;
import com.thomas.video.fragment.FindFragment;
import com.thomas.video.fragment.IndexFragment;
import com.thomas.video.fragment.MineFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeActivity extends AbstractActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.nav_view)
    BottomNavigationView mBottomNavigationView;
    @BindView(R.id.vp_home)
    ViewPager2 vpHome;

    private List<Fragment> fragments = new ArrayList<>();

    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_home;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState, @Nullable View contentView) {
        mBottomNavigationView.setItemIconTintList(null);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        clearToast(mBottomNavigationView);

        fragments.add(IndexFragment.newInstance());
        fragments.add(CategoryFragment.newInstance());
        fragments.add(FindFragment.newInstance());
        fragments.add(MineFragment.newInstance());
        vpHome.setAdapter(new ThomasFragmentStateAdapter(mActivity, fragments));
        vpHome.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        vpHome.setUserInputEnabled(false);
        vpHome.setOffscreenPageLimit(fragments.size());
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_index:
                vpHome.setCurrentItem(0, false);
                return true;
            case R.id.navigation_category:
                vpHome.setCurrentItem(1, false);
                return true;
            case R.id.navigation_find:
                vpHome.setCurrentItem(2, false);
                return true;
            case R.id.navigation_mine:
                vpHome.setCurrentItem(3, false);
                return true;
            default:
                break;
        }
        return false;
    }


    public static void clearToast(BottomNavigationView bottomNavigationView) {
        ViewGroup bottomNavigationMenuView = (ViewGroup) bottomNavigationView.getChildAt(0);
        for (int i = 0; i < bottomNavigationMenuView.getChildCount(); i++) {
            bottomNavigationMenuView.getChildAt(i).setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return true;
                }
            });
        }
    }
}