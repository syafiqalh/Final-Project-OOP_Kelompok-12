package com.kelompok12.frontend.commands;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import java.util.ArrayList;
import java.util.List;

public class InputHandler {

    private int keyShoot = Input.Buttons.LEFT;


    private final MoveCommand moveCommand = new MoveCommand(0f, 0f);
    private final ShootCommand shootCommand = new ShootCommand();
    private final List<Command> activeCommands = new ArrayList<>();

    public List<Command> handleInput(float delta) {
        activeCommands.clear();
        if (Gdx.input == null) {
            return activeCommands;
        }


        float dx = 0f;
        float dy = 0f;

        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            dy += 1f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            dy -= 1f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            dx -= 1f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            dx += 1f;
        }

        if (dx != 0f && dy != 0f) { //biar arah gerak playernya kalau diagonal kecepatannya sama dengan kalau lurus
            float invSqrt2 = 0.70710678f;
            dx *= invSqrt2;
            dy *= invSqrt2;
        }

        if (dx != 0f || dy != 0f) {
            moveCommand.set(dx * delta, dy * delta);
            activeCommands.add(moveCommand);
        }


        if (Gdx.input.isButtonPressed(keyShoot)) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
            shootCommand.setTarget(mouseX, mouseY);
            activeCommands.add(shootCommand);
        }

        return activeCommands;
    }
}
