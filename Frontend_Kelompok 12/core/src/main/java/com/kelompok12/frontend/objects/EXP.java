package com.kelompok12.frontend.objects;

import com.badlogic.gdx.graphics.Color;

public class EXP extends GameObject {
    private int amount;

    EXP(float x, float y, int amount) {
        super(x, y, 8, 8, 0, Color.GREEN);
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
