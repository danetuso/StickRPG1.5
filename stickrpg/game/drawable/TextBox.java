package stickrpg.game.drawable;

import jtwod.engine.Scene;
import jtwod.engine.drawable.Text;
import jtwod.engine.metrics.Vector;
import stickrpg.game.core.StickEngine;
import stickrpg.game.core.StickScene;

import java.awt.*;

public class TextBox extends Text<StickEngine> {

    public boolean focused;

    public TextBox(int layer, String text, Font font, Color color, Vector position, StickEngine engine, Scene<StickEngine> scene) {
        super(layer, text, font, color, position, engine, scene);
    }

    @Override
    public void mouseClicked(int button, Vector location)
    {
        if(!((StickScene)this.getParentScene()).textBoxFocused)
            this.setText("");
        ((StickScene)this.getParentScene()).textBoxFocused = !((StickScene)this.getParentScene()).textBoxFocused;
    }
}
