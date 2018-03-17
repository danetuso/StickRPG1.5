package stickrpg.game.drawable.entity;

import jtwod.engine.Scene;
import jtwod.engine.drawable.Entity;
import jtwod.engine.graphics.Texture;
import jtwod.engine.metrics.Vector;
import stickrpg.game.core.StickEngine;

public class Boundary extends Entity<StickEngine>
{
    public Vector mapPos;
    public Boundary(Vector position, Texture texture, Scene<StickEngine> scene, Vector mapPos) {
        super(position, texture, scene);
        this.mapPos = mapPos;
    }
}