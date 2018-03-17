package stickrpg.game.drawable.entity;

import jtwod.engine.Scene;
import jtwod.engine.drawable.Entity;
import jtwod.engine.metrics.Vector;
import stickrpg.game.util.DoorTypeEnum;
import stickrpg.game.core.StickEngine;

public class hDoor extends Entity<StickEngine>
{
    public Vector mapPos;
    public DoorTypeEnum type;

    public hDoor(Vector position, Scene<StickEngine> scene, Vector mapPos, DoorTypeEnum type)
    {
        super(position, scene.getParentEngine().getTextureGroup().getTexture("hDoor"), scene);
        this.mapPos = mapPos;
        this.type = type;
    }
}
