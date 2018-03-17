package stickrpg.game.drawable;

import jtwod.engine.Scene;
import jtwod.engine.drawable.Text;
import jtwod.engine.metrics.Vector;
import stickrpg.game.core.StickEngine;

import java.awt.*;

public class ButtonTextAttribute extends Text<StickEngine>
{
    public ButtonTextAttribute(String s, Vector position, StickEngine stickEngine, Scene<StickEngine> scene) {
        super(10, s, new Font("Arial", Font.BOLD, 12), new Color(0,36,111), position.plus(45,26), stickEngine, scene);
    }
}
