package top.codemaster.mifinder;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

public class MainViewPagerAdapter extends FragmentPagerAdapter {

    public Fragment[] mFragments;

    public String[] mTitles;

    private final FragmentManager mFragmentManager;

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
        this.mFragmentManager = fm;
    }

    public void submit(String[] titles, Fragment[] fragments) {

        Fragment[] prevFragments = mFragments;
        mTitles = checkNotNull(titles);
        mFragments = checkNotNull(fragments);
        checkState(mTitles.length == mFragments.length, "titles and fragments must have same length");

        // 移除之前的fragment
        if (prevFragments != null) {
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            for (Fragment f : prevFragments) {
                ft.remove(f);
            }
            ft.commitNow();
        }

        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments[position];
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
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
