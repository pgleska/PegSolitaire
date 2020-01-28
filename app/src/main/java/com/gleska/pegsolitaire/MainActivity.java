package com.gleska.pegsolitaire;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.gleska.pegsolitaire.ui.game.GameFragment;
import com.gleska.pegsolitaire.ui.starting.StartingFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment)
                .getChildFragmentManager().getPrimaryNavigationFragment();

        if(currentFragment instanceof GameFragment) {
            Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.nav_starting);
        } else if(currentFragment instanceof StartingFragment) {
            ((StartingFragment) currentFragment).onBackPressed();
        } else {
            super.onBackPressed();
        }
    }
}
