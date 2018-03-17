package stickrpg.game.drawable.entity;

import jtwod.engine.Scene;
import jtwod.engine.drawable.Entity;
import jtwod.engine.graphics.Texture;
import jtwod.engine.metrics.Vector;
import stickrpg.game.core.StickEngine;
import stickrpg.game.core.StickScene;

public class TopLayerButton extends Entity<StickEngine>
{
    public int type;
    public TopLayerButton(Vector position, Texture texture, Scene<StickEngine> scene, int type) {
        super(position, texture, scene);
        this.type = type;
    }

    @Override
    protected final void mouseClicked(int button, Vector location)
    {
        if(type == 0)
            ((StickScene)this.getParentScene()).showInventory();
        else if(type == 1)
            ((StickScene)this.getParentScene()).showStats();
    }
}
