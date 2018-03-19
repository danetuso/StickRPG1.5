package stickrpg.game.drawable;


import jtwod.engine.*;
import jtwod.engine.drawable.Image;
import jtwod.engine.drawable.Text;
import jtwod.engine.graphics.Texture;
import jtwod.engine.metrics.Dimensions;
import jtwod.engine.metrics.Vector;
import stickrpg.game.core.StickEngine;
import stickrpg.game.core.StickEntityController;
import stickrpg.game.core.StickScene;
import stickrpg.game.drawable.entity.Player;
import stickrpg.game.util.ButtonTypeEnum;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.text.NumberFormat;
import java.util.Locale;


public class TextButton extends Image<StickEngine>
{
    private int textWidth;
    private int textHeight;
    private int width;
    private int height;
    private Text<StickEngine> textObject;
    private Texture rRectTexture;
    private Image<StickEngine> rRectBackground;
    private Vector position;
    private ButtonTypeEnum type;
    AffineTransform affinetransform = new AffineTransform();
    private FontRenderContext frc = new FontRenderContext(affinetransform,true,true);

    public TextButton(Vector position, String text, int width, int height, StickEngine engine, Scene<StickEngine> scene, ButtonTypeEnum type) {
        super(8, Texture.colorTexture(new Color(0,0,0,0), new Dimensions(width, height)), position, engine, scene);
        //super(10, text, new Font("Arial", Font.BOLD, 14), new Color(0,51,153), position, engine, scene);
        this.type = type;
        this.position = position;
        this.textWidth = (int)(new Font("Arial", Font.BOLD, 14).getStringBounds(text, frc).getWidth());
        this.textHeight = (int)(new Font("Arial", Font.BOLD, 14).getStringBounds(text, frc).getHeight());
        this.width = width;
        this.height = height;

        rRectTexture = Texture.colorRoundedRectangleTexture(
                new Color(0,51,153),
                new Color(74,139,255),
                1,
                width,
                height,
                12,
                12
        );
        rRectBackground = new Image<>(9, rRectTexture, position, engine, scene);
        this.getSubDrawableGroup().addDrawable(rRectBackground);
        this.textObject = new Text<>(
                10,
                text,
                new Font("Arial", Font.BOLD, 14),
                new Color(0,51,153),
                new Vector(position.getX() + ((width - textWidth) / 2), position.getY() + textHeight),
                engine,
                scene
        );
        this.getSubDrawableGroup().addDrawable(textObject);
    }

    @Override
    protected void mouseEntered()
    {
        this.rRectTexture = Texture.colorRoundedRectangleTexture(
                new Color(0,51,153),
                new Color(149,202,255),
                1,
                this.width,
                this.height,
                12,
                12
        );
        this.getSubDrawableGroup().removeDrawable(rRectBackground);
        rRectBackground = new Image<>(9, rRectTexture, this.position, this.getParentEngine(), this.getParentScene());
        this.getSubDrawableGroup().addDrawable(rRectBackground);
    }

    @Override
    protected void mouseLeft()
    {
        this.rRectTexture = Texture.colorRoundedRectangleTexture(
                new Color(0,51,153),
                new Color(74,139,255),
                1,
                this.width,
                this.height,
                12,
                12
        );
        this.getSubDrawableGroup().removeDrawable(rRectBackground);
        rRectBackground = new Image<>(9, rRectTexture, this.position, this.getParentEngine(), this.getParentScene());
        this.getSubDrawableGroup().addDrawable(rRectBackground);
    }

    @Override
    public final void mouseClicked(int button, Vector location)
    {
        System.out.println(type);
        StickScene sc = (StickScene)this.getParentScene();
        Player player = this.getParentScene().getEntityController(StickEntityController.class).player;
        switch(type)
        {
            case CANCEL:{

                break;
            }
            case DEPOSIT:{
                if(!sc.textBox.getText().equals("")) {
                    sc.textBoxFocused = false;
                    if (player.money >= Integer.parseInt(sc.textBox.getText().replace(",", ""))) {
                        player.bankMoney += Integer.parseInt(sc.textBox.getText().replace(",", ""));
                        player.addMoney(-Integer.parseInt(sc.textBox.getText().replace(",", "")));
                        sc.stats.get(1).setText("Current Balance: $" + NumberFormat.getNumberInstance(Locale.US).format(player.bankMoney));
                    }
                    sc.textBox.setText("0");
                }
                break;
            }
            case WITHDRAW:{

                if(!sc.textBox.getText().equals(""))
                {
                    sc.textBoxFocused = false;
                    if(player.bankMoney >= Integer.parseInt(sc.textBox.getText().replace(",", "")))
                    {
                        player.bankMoney -= Integer.parseInt(sc.textBox.getText().replace(",", ""));
                        player.addMoney(Integer.parseInt(sc.textBox.getText().replace(",", "")));
                        sc.stats.get(1).setText("Current Balance: $" + NumberFormat.getNumberInstance(Locale.US).format(player.bankMoney));
                    }
                    sc.textBox.setText("0");
                }
                break;
            }
            default:{break;}
        }
    }
}
