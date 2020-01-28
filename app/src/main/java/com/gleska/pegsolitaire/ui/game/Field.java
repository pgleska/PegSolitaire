package com.gleska.pegsolitaire.ui.game;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class Field extends AppCompatImageView {
    private boolean marked = false;
    private boolean active = true;
    private boolean potentialMovement = false;
    private int positionX;
    private int positionY;

    public Field(Context context) {
        super(context);
    }

    public Field(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Field(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isPotentialMovement() {
        return potentialMovement;
    }

    public void setPotentialMovement(boolean potentialMovement) {
        this.potentialMovement = potentialMovement;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
}
