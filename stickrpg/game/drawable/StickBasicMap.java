package stickrpg.game.drawable;

import jtwod.engine.Drawable;
import jtwod.engine.Scene;
import jtwod.engine.drawable.Image;
import jtwod.engine.metrics.Vector;
import stickrpg.game.core.StickEngine;

public class StickBasicMap extends Drawable<StickEngine>
{
    public Image<StickEngine> map;
    public int mapStartX;
    public int mapStartY;

    public StickBasicMap(int layer, StickEngine engine, Scene<StickEngine> scene) {
        super(layer, engine, scene);
        int mapWidth = this.getParentEngine().getTextureGroup().getTexture("Map").getWidth();
        int mapHeight = this.getParentEngine().getTextureGroup().getTexture("Map").getHeight();
        //+200 to move the map right so player starts within street bounds
        mapStartX = -(mapWidth / 2) + 200;
        mapStartY =  -(mapHeight / 2);

        map = new Image<>(
                0,
                this.getParentEngine().getTextureGroup().getTexture("Map"),
                new Vector(
                        mapStartX,
                        mapStartY
                ),
                this.getParentEngine(),
                this.getParentScene()
        );
        this.getSubDrawableGroup().addDrawable(map);
    }

    @Override
    protected final void update(){}
}
