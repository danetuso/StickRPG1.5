package stickrpg.game.drawable.entity;

import jtwod.engine.Scene;
import jtwod.engine.drawable.Entity;
import jtwod.engine.graphics.Texture;
import jtwod.engine.metrics.Vector;
import stickrpg.game.core.StickEngine;
import stickrpg.game.core.StickScene;
import stickrpg.game.util.DoorTypeEnum;

public class Person extends Entity<StickEngine>
{
    public Vector mapPos;
    private DoorTypeEnum type;
    public Person(Vector position, Texture texture, Scene<StickEngine> scene, Vector mapPos, DoorTypeEnum type) {
        super(position, texture, scene);
        this.mapPos = mapPos;
        this.type = type;
    }

    @Override
    public void mouseClicked(int button, Vector location)
    {
        if(type == DoorTypeEnum.KID)
            ((StickScene)this.getParentScene()).toggleMenuBackground(DoorTypeEnum.KID);
        else if(type == DoorTypeEnum.HOMELESS)
            ((StickScene)this.getParentScene()).toggleMenuBackground(DoorTypeEnum.HOMELESS);
        else if(type == DoorTypeEnum.DEALER)
            ((StickScene)this.getParentScene()).toggleMenuBackground(DoorTypeEnum.DEALER);
    }
}
