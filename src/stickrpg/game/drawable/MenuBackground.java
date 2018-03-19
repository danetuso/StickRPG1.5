package stickrpg.game.drawable;

import jtwod.engine.Drawable;
import jtwod.engine.Scene;
import jtwod.engine.drawable.Image;
import jtwod.engine.metrics.Dimensions;
import jtwod.engine.metrics.Vector;
import stickrpg.game.core.StickEngine;

public class MenuBackground extends Drawable<StickEngine>
{
    private Image<StickEngine> background;

    public MenuBackground(int layer, StickEngine engine, Scene<StickEngine> scene) {
        super(layer, engine, scene);
        background = new Image<>(
                layer,
                //this.getParentEngine().getTextureGroup().getTexture("menuBackground"),
                this.getParentEngine().getTextureGroup().getTexture("testRoundedRect"),
                new Vector(
                        (engine.getWindowSize().getWidth()) - (engine.getTextureGroup().getTexture("menuBackground").getWidth() + 5),
                        40
                ),
                this.getParentEngine(),
                this.getParentScene()
        );
        this.getSubDrawableGroup().addDrawable(background);
    }

    public final Vector getPosition()
    {
        return background.getPosition();
    }

    public final void setPosition(Vector pos)
    {
        this.background.setPosition(pos);
    }

    public final Dimensions getDimensions()
    {
        return new Dimensions(this.getParentEngine().getTextureGroup().getTexture("menuBackground").getWidth(), this.getParentEngine().getTextureGroup().getTexture("menuBackground").getHeight());
    }

    @Override
    protected final void update(){}
}
