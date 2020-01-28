package com.gleska.pegsolitaire;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GameViewModel extends ViewModel {

    private MutableLiveData<Figure> figure;
    private MutableLiveData<Integer> time;
    private MutableLiveData<Size> size;
    private MutableLiveData<Boolean> gameRunning;
    private MutableLiveData<Boolean> center;
    private int startingTime = 180;

    public GameViewModel() {
        figure = new MutableLiveData<>();
        time = new MutableLiveData<>();
        size = new MutableLiveData<>();
        gameRunning = new MutableLiveData<>();
        center = new MutableLiveData<>();

        figure.setValue(Figure.OPTION1);
        time.setValue(startingTime);
        size.setValue(Size.MINIMUM);
    }

    public void setTime(Integer time) {
        this.time.setValue(time);
    }
    public LiveData<Integer> getTime() {
        return time;
    }

    public void setFigure(Figure figure) {
        this.figure.setValue(figure);
    }
    public LiveData<Figure> getFigure() {
        return figure;
    }

    public void setSize(Size size) {
        this.size.setValue(size);
    }
    public LiveData<Size> getSize() {
        return size;
    }

    public void setGameRunning(boolean b) {
        this.gameRunning.setValue(b);
    }
    public LiveData<Boolean> isGameRunning() {
        return gameRunning;
    }

    public void setCenter(Boolean center) {
        this.center.setValue(center);
    }
    public LiveData<Boolean> getCenter() {
        return center;
    }
}