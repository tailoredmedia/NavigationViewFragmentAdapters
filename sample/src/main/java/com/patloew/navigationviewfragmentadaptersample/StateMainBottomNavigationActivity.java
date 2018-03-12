/* Copyright 2016 Patrick Löwenstein
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * --------
 *
 * FILE MODIFIED 2018 Tailored Media GmbH */

package com.patloew.navigationviewfragmentadaptersample;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.patloew.navigationviewfragmentadapters.NavigationViewStateFragmentAdapter;
import com.patloew.navigationviewfragmentadapters.OnNavigationItemSelectedListener;
import com.patloew.navigationviewfragmentadaptersample.databinding.ActivityMainBottomnavigationBinding;

public class StateMainBottomNavigationActivity extends AppCompatActivity implements MainActivityView {


    private ActivityMainBottomnavigationBinding binding;
    private MyNavigationViewAdapter myNavigationViewAdapter;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(myNavigationViewAdapter != null) { myNavigationViewAdapter.onSaveInstanceState(outState); }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_bottomnavigation);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.adapter_label);

        myNavigationViewAdapter = new MyNavigationViewAdapter(getSupportFragmentManager(), R.id.container, R.id.navitem_1, savedInstanceState);
        myNavigationViewAdapter.attachTo(binding.bottomNavigation);
        myNavigationViewAdapter.setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out);
        myNavigationViewAdapter.setNavigationItemSelectedListener(new OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if(item.getItemId() == R.id.navitem_sample_activity) {
                    startActivity(new Intent(StateMainBottomNavigationActivity.this, SampleActivity.class));
                }

                return false;
            }
        });
    }


    public void activateDrawerLayout() {
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    public void activateBackLayout() {
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
    }

    private class MyNavigationViewAdapter extends NavigationViewStateFragmentAdapter {

        public MyNavigationViewAdapter(FragmentManager fragmentManager, @IdRes int containerId, @IdRes int defaultMenuItemId, Bundle savedInstanceState) {
            super(fragmentManager, containerId, defaultMenuItemId, savedInstanceState);
        }

        @NonNull
        @Override
        public Fragment getFragment(@IdRes int menuItemId) {
            switch (menuItemId) {
                case R.id.navitem_1:
                    return SampleFragment.newInstance("Fragment 1");
                case R.id.navitem_2:
                    return SampleFragment.newInstance("Fragment 2");
                case R.id.navitem_3:
                    return SampleFragment.newInstance("Fragment 3");
                default:
                    return null;
            }
        }
    }
}
