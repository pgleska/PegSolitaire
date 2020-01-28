package com.gleska.pegsolitaire.ui.starting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.gleska.pegsolitaire.GameViewModel;
import com.gleska.pegsolitaire.R;

public class StartingFragment extends Fragment {

    private View root;
    private Button newGameBtn, settingsBtn, highScoresBtn, exitBtn;

    private GameViewModel gameViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        gameViewModel =
                ViewModelProviders.of(this).get(GameViewModel.class);
        root = inflater.inflate(R.layout.fragment_starting, container, false);

        initComponents();
        setListeners();


        return root;
    }

    private void initComponents() {
        newGameBtn = root.findViewById(R.id.new_game);
        settingsBtn = root.findViewById(R.id.settings);
        exitBtn = root.findViewById(R.id.exit);
    }

    private void setListeners() {
        newGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment)
                        .navigate(R.id.action_nav_starting_to_nav_game);
            }
        });

        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment)
                        .navigate(R.id.action_nav_starting_to_nav_settings);
            }
        });

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void onBackPressed() {
        Toast.makeText(getContext(), getResources().getString(R.string.goodbye), Toast.LENGTH_SHORT).show();
        getActivity().finish();
    }
}