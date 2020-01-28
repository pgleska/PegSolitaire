package com.gleska.pegsolitaire.ui.settings;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.gleska.pegsolitaire.Figure;
import com.gleska.pegsolitaire.GameViewModel;
import com.gleska.pegsolitaire.R;
import com.gleska.pegsolitaire.Size;

public class SettingsFragment extends Fragment {
    private static final String TAG = SettingsFragment.class.getName();

    private View root;

    private GameViewModel gameViewModel;

    private SeekBar seekBar;
    private TextView currentTimeTextView;
    private ImageButton radio1, radio2, radio3;
    private ImageView imageBtn1, imageBtn2, imageBtn3;

    private GridLayout gridLayout;

    private final int maxTime = 3;
    private final int startingValue = 60;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        gameViewModel =
                ViewModelProviders.of(getActivity()).get(GameViewModel.class);
        root = inflater.inflate(R.layout.fragment_settings, container, false);

        initComponents();
        initListeners();

        setGameTime(maxTime * startingValue);

        return root;
    }

    private void initComponents() {
        seekBar = root.findViewById(R.id.seek_bar_time);
        seekBar.setProgress(startingValue);

        currentTimeTextView = root.findViewById(R.id.current_time);

        imageBtn1 = root.findViewById(R.id.option_1);
        imageBtn2 = root.findViewById(R.id.option_2);
        imageBtn3 = root.findViewById(R.id.option_3);

        radio1 = root.findViewById(R.id.radio_btn_min);
        radio2 = root.findViewById(R.id.radio_btn_medium);
        radio3 = root.findViewById(R.id.radio_btn_max);

        int time = maxTime * startingValue;
        gameViewModel.setTime(time);

        switch (gameViewModel.getFigure().getValue()) {
            case OPTION1:
                seekBar.setThumb(getResources().getDrawable(R.drawable.thumb_theme_1));
                seekBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.brown)));
                break;
            case OPTION2:
                seekBar.setThumb(getResources().getDrawable(R.drawable.thumb_theme_2));
                seekBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_blue)));
                break;
            case OPTION3:
                seekBar.setThumb(getResources().getDrawable(R.drawable.thumb_theme_3));
                seekBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                break;
        }

        switch (gameViewModel.getSize().getValue()) {
            case MINIMUM:
                radio1.setActivated(true);
                radio2.setActivated(false);
                radio3.setActivated(false);
                break;
            case MEDIUM:
                radio1.setActivated(false);
                radio2.setActivated(true);
                radio3.setActivated(false);
                break;
            case MAXIMUM:
                radio1.setActivated(false);
                radio2.setActivated(false);
                radio3.setActivated(true);
                break;
        }

        gridLayout = root.findViewById(R.id.layout_options);
        gridLayout.setColumnCount(3);
        gridLayout.setRowCount(1);

        gridLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int width = gridLayout.getWidth();
                int height = width / 3;
                ViewGroup.LayoutParams layoutParams = gridLayout.getLayoutParams();
                layoutParams.width = width;
                layoutParams.height = height;
                gridLayout.setLayoutParams(layoutParams);

                GridLayout.LayoutParams params1 = new GridLayout.LayoutParams(GridLayout.spec(0, 1f),
                        GridLayout.spec(0, 1f));
                params1.height = 0;
                params1.width = 0;
                params1.setMargins(4,2,4,2);
                imageBtn1.setLayoutParams(params1);
                imageBtn1.setImageDrawable(getResources().getDrawable(R.drawable.field_active_1));

                GridLayout.LayoutParams params2 = new GridLayout.LayoutParams(GridLayout.spec(0, 1f),
                        GridLayout.spec(1, 1f));
                params2.height = 0;
                params2.width = 0;
                params2.setMargins(4,2,4,2);
                imageBtn2.setLayoutParams(params2);
                imageBtn2.setImageDrawable(getResources().getDrawable(R.drawable.field_active_2));

                GridLayout.LayoutParams params3 = new GridLayout.LayoutParams(GridLayout.spec(0, 1f),
                        GridLayout.spec(2, 1f));
                params3.height = 0;
                params3.width = 0;
                params3.setMargins(4,2,4,2);
                imageBtn3.setLayoutParams(params3);
                imageBtn3.setImageDrawable(getResources().getDrawable(R.drawable.field_active_3));


                gridLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private void initListeners() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int time = maxTime * i;
                gameViewModel.setTime(time);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        gameViewModel.getTime().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer i) {
                setGameTime(i);
            }
        });

        imageBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seekBar.setThumb(getResources().getDrawable(R.drawable.thumb_theme_1));
                seekBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.brown)));
                gameViewModel.setFigure(Figure.OPTION1);
            }
        });

        imageBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seekBar.setThumb(getResources().getDrawable(R.drawable.thumb_theme_2));
                seekBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_blue)));
                gameViewModel.setFigure(Figure.OPTION2);
            }
        });

        imageBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seekBar.setThumb(getResources().getDrawable(R.drawable.thumb_theme_3));
                seekBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                gameViewModel.setFigure(Figure.OPTION3);
            }
        });

        radio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radio1.setActivated(true);
                radio2.setActivated(false);
                radio3.setActivated(false);

                gameViewModel.setSize(Size.MINIMUM);
            }
        });

        radio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radio1.setActivated(false);
                radio2.setActivated(true);
                radio3.setActivated(false);

                gameViewModel.setSize(Size.MEDIUM);
            }
        });

        radio3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radio1.setActivated(false);
                radio2.setActivated(false);
                radio3.setActivated(true);

                gameViewModel.setSize(Size.MAXIMUM);
            }
        });
    }

    private void setGameTime(Integer time){
        int timeOfProgress = time;
        int min = timeOfProgress / 60;
        timeOfProgress %= 60;
        int sec = timeOfProgress;
        String sec_text;
        if (sec <= 9) {
            sec_text = "0" + sec;
        } else {
            sec_text = Integer.toString(sec);
        }
        String text = min + "min " + sec_text + "sec";
        currentTimeTextView.setText(text);
    }
}
