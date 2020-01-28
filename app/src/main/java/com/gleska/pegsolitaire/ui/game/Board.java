package com.gleska.pegsolitaire.ui.game;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.gleska.pegsolitaire.GameViewModel;
import com.gleska.pegsolitaire.R;

import static android.os.SystemClock.sleep;

public class Board extends Fragment {
    private static final String TAG = Board.class.getName();

    private View root;

    private GameViewModel gameViewModel;
    private GridLayout layout;

    private Field[][] fields;
    private Field currentField;
    private int size;
    private int shift;

    private int win = 32;
    private int end = 32;

    private int resIDActive;
    private int resIDMarked;
    private int resIDPotential;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, @Nullable Bundle savedInstanceState) {
        gameViewModel =
                ViewModelProviders.of(getActivity()).get(GameViewModel.class);

        switch (gameViewModel.getSize().getValue()) {
            case MINIMUM:
                size = 7;
                win = 32;
                end = 32;
                shift = 1;
                break;
            case MEDIUM:
                size = 13;
                win = 114;
                end = 32;
                shift = 3;
                break;
            case MAXIMUM:
                size = 19;
                win = 216;
                end = 32;
                shift = 5;
                break;
        }

        switch (gameViewModel.getFigure().getValue()) {
            case OPTION1:
                resIDActive = getResources().getIdentifier("field_active_1", "drawable", getActivity().getPackageName());
                resIDMarked = getResources().getIdentifier("field_marked_1", "drawable", getActivity().getPackageName());
                resIDPotential = getResources().getIdentifier("field_potential_1", "drawable", getActivity().getPackageName());
                break;
            case OPTION2:
                resIDActive = getResources().getIdentifier("field_active_2", "drawable", getActivity().getPackageName());
                resIDMarked = getResources().getIdentifier("field_marked_2", "drawable", getActivity().getPackageName());
                resIDPotential = getResources().getIdentifier("field_potential_2", "drawable", getActivity().getPackageName());
                break;

            case OPTION3:
                resIDActive = getResources().getIdentifier("field_active_3", "drawable", getActivity().getPackageName());
                resIDMarked = getResources().getIdentifier("field_marked_3", "drawable", getActivity().getPackageName());
                resIDPotential = getResources().getIdentifier("field_potential_3", "drawable", getActivity().getPackageName());
                break;
        }

        root = inflater.inflate(R.layout.board, container, false);

        layout = root.findViewById(R.id.board);

        setParams();

        layout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int length;
                int width = layout.getWidth();
                int height = layout.getHeight();

                length = width > height ? height : width;
                ViewGroup.LayoutParams layoutParams = layout.getLayoutParams();
                layoutParams.width = length;
                layoutParams.height = length;
                layout.setLayoutParams(layoutParams);

                setFields();

                layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });


        return root;
    }

    private void setParams() {
        layout.setColumnCount(size);
        layout.setRowCount(size);
        fields = new Field[size][size];
    }

    private void setFields() {
        for(int row = 0; row < size; row++){
            for(int col = 0; col < size; col++) {
                Field field = new Field(getActivity());

                field.setPositionX(row);
                field.setPositionY(col);

                fields[row][col] = field;

                GridLayout.LayoutParams params = new GridLayout.LayoutParams(GridLayout.spec(
                        col,1f),
                        GridLayout.spec(row,1f));
                params.height = 0;
                params.width = 0;
                params.setMargins(2,2,2,2);
                field.setLayoutParams(params);

                field.setImageDrawable(getResources().getDrawable(resIDActive));

                if((row == (size / 2)) && (col == (size / 2)) ) {
                    field.setImageDrawable(getResources().getDrawable(R.drawable.field_inactive));
                    field.setActive(false);
                }

                if((row <= shift && col <= shift) || (row >= size - (shift + 1) && col <= shift) ||
                        (row <= shift && col >= size - (shift + 1)) || (row >= size - (shift + 1) && col >= size - (shift + 1))){
                    field.setVisibility(View.GONE);
                    field.setActive(false);
                }

                field.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Field f = (Field) view;

                        if(f.isActive()) {
                            currentField = f;

                            restoreFields();
                            f.setImageDrawable(getResources().getDrawable(resIDMarked));


                            int x = f.getPositionX();
                            int y = f.getPositionY();

                            //check left direction
                            if(x - 2 >= 0) {
                                if (fields[x - 1][y].isActive()) {
                                    if (!fields[x - 2][y].isActive()) {
                                        fields[x - 2][y].setPotentialMovement(true);
                                        fields[x - 2][y].setImageDrawable(getResources()
                                                .getDrawable(resIDPotential));
                                    }
                                }
                            }

                            //check right direction
                            if(x + 2 < size) {
                                if (fields[x + 1][y].isActive()) {
                                    if (!fields[x + 2][y].isActive()) {
                                        fields[x + 2][y].setPotentialMovement(true);
                                        fields[x + 2][y].setImageDrawable(getResources()
                                                .getDrawable(resIDPotential));
                                    }
                                }
                            }

                            //check top direction
                            if(y - 2 >= 0) {
                                if (fields[x][y - 1].isActive()) {
                                    if (!fields[x][y - 2].isActive()) {
                                        fields[x][y - 2].setPotentialMovement(true);
                                        fields[x][y - 2].setImageDrawable(getResources()
                                                .getDrawable(resIDPotential));
                                    }
                                }
                            }

                            //check bottom direction
                            if(y + 2 < size) {
                                if (fields[x][y + 1].isActive()) {
                                    if (!fields[x][y + 2].isActive()) {
                                        fields[x][y + 2].setPotentialMovement(true);
                                        fields[x][y + 2].setImageDrawable(getResources()
                                                .getDrawable(resIDPotential));
                                    }
                                }
                            }
                        }

                        if(f.isPotentialMovement()) {
                            f.setActive(true);
                            f.setPotentialMovement(false);
                            f.setImageDrawable(getResources().getDrawable(resIDActive));

                            currentField.setActive(false);
                            currentField.setImageDrawable(getResources().getDrawable(R.drawable.field_inactive));

                            Field target = fields[(f.getPositionX() + currentField.getPositionX()) / 2]
                                    [(f.getPositionY() + currentField.getPositionY()) / 2];
                            target.setActive(false);
                            target.setImageDrawable(getResources().getDrawable(R.drawable.field_inactive));

                            currentField = null;

                            restoreFields();
                            win--;

                            //check win or defeat
                            if(checkWin()) {
                                if((f.getPositionX() == (size / 2)) && (f.getPositionY() == (size / 2)) ) {
                                    gameViewModel.setCenter(true);
                                } else {
                                    gameViewModel.setCenter(false);
                                }
                                showDialog(true);
                                gameViewModel.setGameRunning(false);
                                Log.i(TAG, "win");
                            } else if(checkDefeat()) {
                                showDialog(false);
                                gameViewModel.setGameRunning(false);
                                Log.i(TAG, "defeat");
                            }
                        }
                    }
                });

                layout.addView(field);
            }
        }
    }

    private void restoreFields() {
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                fields[i][j].setPotentialMovement(false);
                fields[i][j].setMarked(false);
                if(fields[i][j].isActive()) {
                    fields[i][j].setImageDrawable(getResources()
                            .getDrawable(resIDActive));
                }
                if(!fields[i][j].isActive()) {
                    fields[i][j].setImageDrawable(getResources()
                            .getDrawable(R.drawable.field_inactive));
                }
            }
        }
    }

    private boolean checkWin() {
        if(win == 1) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkDefeat() {
        switch (gameViewModel.getSize().getValue()) {
            case MINIMUM:
                if(fields[shift + 1][0].isActive() && fields[shift + 2][0].isActive() && fields[shift + 3][0].isActive()
                        && !fields[shift + 1][1].isActive() && !fields[shift + 2][1].isActive() && !fields[shift + 3][1].isActive() && win < (end / 3)) {
                    return true;
                }

                if(fields[shift + 1][size - 1].isActive() && fields[shift + 2][size - 1].isActive() && fields[shift + 3][size - 1].isActive()
                        && !fields[shift + 1][size - 2].isActive() && !fields[shift +  2][size - 2].isActive() && !fields[shift + 3][size - 2].isActive() && win < (end / 3)) {
                    return true;
                }

                if(fields[0][shift].isActive() && fields[0][shift + 1].isActive() && fields[0][shift + 2].isActive()
                        && !fields[1][shift].isActive() && !fields[1][shift + 1].isActive() && !fields[1][shift + 2].isActive() && win < (end / 3)) {
                    return true;
                }

                if(fields[size - 1][shift + 1].isActive() && fields[size - 1][shift + 2].isActive() && fields[size - 1][shift + 3].isActive()
                        && !fields[size - 2][shift + 1].isActive() && !fields[size - 2][shift + 2].isActive() && !fields[size - 2][shift + 3].isActive() && win < (end / 3)) {
                    return true;
                }
                break;
        }

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if(j < size - 1) {
                    if(fields[i][j].isActive() && fields[i][j + 1].isActive())
                        return false;
                }
                if(i < size - 1) {
                    if (fields[i][j].isActive() && fields[i + 1][j].isActive())
                        return false;
                }
            }
        }

        return true;
    }

    private void showDialog(boolean isGameWon){
        Dialog dialog = new Dialog(isGameWon, false);
        sleep(10);
        dialog.show(getFragmentManager(), "Dialog");
    }
}
