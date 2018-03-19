package stickrpg.game.drawable;

import jtwod.engine.Scene;
import jtwod.engine.drawable.Text;
import jtwod.engine.metrics.Vector;
import stickrpg.game.core.StickEngine;
import stickrpg.game.drawable.entity.MenuButton;

import java.awt.*;

public class ButtonText extends Text<StickEngine> {
    public boolean highlight;
    private MenuButton parent;
    public ButtonText(String s, Vector buttonPosition, StickEngine stickEngine, Scene<StickEngine> scene, MenuButton parent) {
        super(10, s, new Font("Arial", Font.BOLD, 14), new Color(0,51,153), buttonPosition.plus(45,10), stickEngine, scene);
        this.parent = parent;
    }

    @Override
    protected void mouseEntered()
    {
        this.setColor(new Color(149,202,255));
        this.parent.setTexture(this.parent.hoverTexture);
    }

    @Override
    protected void mouseLeft()
    {
        this.setColor(new Color(0,51,153));
        this.parent.setTexture(this.parent.OGTexture);
    }

    @Override
    protected final void mouseClicked(int button, Vector location)
    {
        this.parent.mouseClicked(button, location);
    }

    public final void toggleColor()
    {
        this.highlight = !this.highlight;
        if(!highlight)
            this.setColor(new Color(0,51,153));
        else
            this.setColor(new Color(149,202,255));
    }
}
