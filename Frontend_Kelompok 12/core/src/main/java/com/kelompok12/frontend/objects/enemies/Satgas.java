package com.kelompok12.frontend.objects.enemies;

import com.badlogic.gdx.graphics.Color;

public class Satgas extends Enemy {

    public Satgas(String name, int hp, float x, float y) {
        super(x, y, 62f, 89f, Color.RED, name, hp);
    }
}
