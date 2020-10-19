package com.thomas.video.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class ThomasFragmentStateAdapter extends FragmentStateAdapter {
    private List<Fragment> fragmentList;

    public ThomasFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> fragmentList) {
        super(fragmentActivity);
        this.fragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (fragmentList == null || fragmentList.size() == 0) {
            return null;
        } else {
            return fragmentList.get(position);
        }


    }

    @Override
    public int getItemCount() {
        if (fragmentList == null) {
            return 0;
        } else {
            return fragmentList.size();
        }

    }
}
