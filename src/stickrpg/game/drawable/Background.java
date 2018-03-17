package stickrpg.game.drawable;

import jtwod.engine.Drawable;
import jtwod.engine.Scene;
import jtwod.engine.drawable.Image;
import jtwod.engine.graphics.Texture;
import jtwod.engine.metrics.Vector;
import stickrpg.game.core.StickEngine;

public class Background extends Drawable<StickEngine>
{
    private Image<StickEngine> background;
    public Background(int layer, Texture texture, StickEngine engine, Scene<StickEngine> scene) {
        super(layer, engine, scene);
        background = new Image<>(
                layer,
                texture,
                Vector.Zero(),
                this.getParentEngine(),
                this.getParentScene()
        );
        this.getSubDrawableGroup().addDrawable(background);
    }

    public Background(int layer, Vector position, Texture texture, StickEngine engine, Scene<StickEngine> scene) {
        super(layer, engine, scene);
        background = new Image<>(
                layer,
                texture,
                position,
                this.getParentEngine(),
                this.getParentScene()
        );
        this.getSubDrawableGroup().addDrawable(background);
    }

    @Override
    protected final void update() {}
}

