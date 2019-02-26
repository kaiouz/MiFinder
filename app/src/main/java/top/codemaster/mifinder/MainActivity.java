package top.codemaster.mifinder;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import top.codemaster.mifinder.data.Resource;
import top.codemaster.mifinder.databinding.ActivityMainBinding;
import top.codemaster.mifinder.resources.ResourcesFragment;
import top.codemaster.mifinder.sd.ServiceDialog;

public class MainActivity extends AppCompatActivity implements ServiceDialog.ServiceChangeListener {

    public static final String DIALOG_TAG = "service_discovery";

    private ServiceDialog mDialog;

    private ActivityMainBinding binding;

    private MainViewPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDialog = (ServiceDialog) getSupportFragmentManager().findFragmentByTag(DIALOG_TAG);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);

        mAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        binding.viewpager.setAdapter(mAdapter);
        binding.tabLayout.setupWithViewPager(binding.viewpager);

        if (savedInstanceState == null) {
            startServiceDiscovery();
        }
//        updateUI("http://192.168.0.6:8080");
    }


    private void updateUI(String baseUrl) {
        String[] titles = {
                getString(R.string.res_all),
                getString(R.string.res_video),
                getString(R.string.res_image),
                getString(R.string.res_audio)
        };

        Fragment[] fragments = {
                ResourcesFragment.newInstance(null, baseUrl),
                ResourcesFragment.newInstance(Resource.ResType.video, baseUrl),
                ResourcesFragment.newInstance(Resource.ResType.image, baseUrl),
                ResourcesFragment.newInstance(Resource.ResType.audio, baseUrl)
        };
        mAdapter.submit(titles, fragments);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_actovity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.service_discovery:
                startServiceDiscovery();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onServiceChange(String url) {
        updateUI(url);
        stopServiceDiscovery();
    }

    private void startServiceDiscovery() {
        mDialog = new ServiceDialog();
        mDialog.show(getSupportFragmentManager(), DIALOG_TAG);
    }

    private void stopServiceDiscovery() {
        mDialog.dismiss();
        mDialog = null;
    }
}
