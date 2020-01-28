package com.gleska.pegsolitaire.ui.game;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.gleska.pegsolitaire.GameViewModel;
import com.gleska.pegsolitaire.R;

import java.time.Duration;
import java.time.Instant;

public class GameFragment extends Fragment implements Runnable {
    private View root;

    private GameViewModel gameViewModel;
    private TextView timeTextView;
    private ImageView restartBtn;

    private Duration duration;
    private Instant startTime;
    private Handler handler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        gameViewModel =
                ViewModelProviders.of(getActivity()).get(GameViewModel.class);
        root = inflater.inflate(R.layout.fragment_game, container, false);
        timeTextView = root.findViewById(R.id.time_text_view);
        restartBtn = root.findViewById(R.id.restart);

        gameViewModel.setGameRunning(true);

        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.nav_game);
            }
        });

        startTime = Instant.now();
        duration = Duration.ofSeconds(gameViewModel.getTime().getValue());

        if (duration.isZero()){
            timeTextView.setText("Time");
        }

        handler = new Handler();
        handler.post(this);

        return root;
    }

    @Override
    public void run() {
        if (gameViewModel.isGameRunning().getValue()) {
            Duration runTime;
            Duration remainingTime;
            runTime = Duration.between(startTime, Instant.now());
            if (duration.getSeconds() > 0) {
                remainingTime = duration.minus(runTime);
                if(remainingTime.getSeconds() == 0){
                    gameViewModel.setGameRunning(false);
                    Dialog dialog = new Dialog(false, true);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    dialog.show(fragmentManager, "Dialog");
                }
            } else {
                remainingTime = runTime;
            }
            timeTextView.setText(String.format("%02d:%02d", remainingTime.toMinutes(), remainingTime.getSeconds() % 60));
            if ((remainingTime.getSeconds() > 0 && duration.getSeconds()>0) || duration.getSeconds() == 0) {
                handler.postDelayed(this, 1000);
            }
        }
    }

    @Override
    public void onDestroyView() {
        handler.removeCallbacks(this);
        super.onDestroyView();
    }
}
