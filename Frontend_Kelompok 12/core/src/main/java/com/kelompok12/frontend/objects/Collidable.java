package com.kelompok12.frontend.objects;
import com.badlogic.gdx.math.Rectangle;

public interface Collidable {
    Rectangle getHitbox();
    void onCollision(Collidable other);
}
