package top.codemaster.mifinder;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MainViewPagerAdapter extends FragmentPagerAdapter {

    public Fragment[] mFragments;

    public String[] mTitles;

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void submit(String[] titles, Fragment[] fragments) {
        mTitles = checkNotNull(titles);
        mFragments = checkNotNull(fragments);
        checkState(mTitles.length == mFragments.length, "titles and fragments must have same length");
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments[position];
    }

    @Override
    public int getCount() {
        return mFragments != null ? mFragments.length : 0;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
