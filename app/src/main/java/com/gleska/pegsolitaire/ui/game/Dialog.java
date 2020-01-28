package com.gleska.pegsolitaire.ui.game;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.gleska.pegsolitaire.GameViewModel;
import com.gleska.pegsolitaire.R;

public class Dialog extends DialogFragment {

    private View root;

    private GameViewModel gameViewModel;

    private Button retryBtn;
    private Button exitBtn;
    private TextView textContent;
    private boolean isGameWon;
    private boolean endTime;

    Dialog(boolean isGameWon, boolean endTime){
        this.isGameWon = isGameWon;
        this.endTime = endTime;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.dialog, container, false);

        gameViewModel =
                ViewModelProviders.of(getActivity()).get(GameViewModel.class);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(false);
        initComponents();
        setListeners();
        if (isGameWon){
            if(gameViewModel.getCenter().getValue()) {
                textContent.setTextColor(Color.parseColor("#388E3C"));
                textContent.setText("Wooow!!! Super win!");
            } else {
                textContent.setTextColor(Color.parseColor("#388E3C"));
                textContent.setText("You won!");
            }

        }
        else{
            if(endTime) {
                textContent.setTextColor(Color.parseColor("#FFD32F2F"));
                textContent.setText("Time is up!");
            } else {
                textContent.setTextColor(Color.parseColor("#FFD32F2F"));
                textContent.setText("You lost!");
            }
        }
        return root;
    }

    public void initComponents(){
        retryBtn = root.findViewById(R.id.retryBtn);
        exitBtn = root.findViewById(R.id.exitBtn);
        textContent = root.findViewById(R.id.dialog_content);
    }

    public void setListeners(){
        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.nav_game);
                getDialog().dismiss();
            }
        });
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.nav_starting);
                getDialog().dismiss();
            }
        });
    }

}

