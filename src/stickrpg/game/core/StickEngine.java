package stickrpg.game.core;

import jtwod.engine.Engine;
import jtwod.engine.graphics.SpriteSheet;
import jtwod.engine.graphics.Texture;
import jtwod.engine.metrics.Dimensions;

import java.awt.*;

public class StickEngine extends Engine
{

    public int playerWidth = 19;
    public SpriteSheet spriteSheet;
    public StickEngine()
    {
        super(
                "StickRPG",
                new Dimensions(
                    600,
                    424
                )
        );
    }

    @Override
    public final void onEngineStart()
    {
        this.setScene(new StickScene("StickRPG", this));
    }

    @Override
    public final void loadTextures()
    {
        spriteSheet = new SpriteSheet(new Texture("/SpriteSheet.png"), 40, 40);

        this.getTextureGroup().addTexture(
                "Player",
                Texture.colorCircleTexture(
                        null,
                        Color.BLUE,
                        0,
                        playerWidth
                )
        );
        this.getTextureGroup().addTexture(
                "Map",
                new Texture("/SpsqhVD.png")
        );

        this.getTextureGroup().addTexture("menuBackground",
                Texture.colorTexture(
                        new Color(74,139,255,255),
                        new Dimensions(
                                this.getWindowSize().getWidth() / 3 * 2,
                                getWindowSize().getHeight() / 3 * 2
                        )
                )
        );

        this.getTextureGroup().addTexture("Person",
                Texture.colorTexture(
                        new Color(0,0,0,0),
                        new Dimensions(20, 20)
                )
        );

        this.getTextureGroup().addTexture("TextBoxBackground",
                Texture.colorTexture(
                        Color.WHITE,
                        new Dimensions(117, 27)
                )
        );

        this.getTextureGroup().addTexture("TextBoxBackgroundBorder",
                Texture.colorTexture(
                        Color.BLACK,
                        new Dimensions(121, 31)
                )
        );

        //backgrounds
        this.getTextureGroup().addTexture("Home",
                new Texture("/homebackground.png")
        );
        this.getTextureGroup().addTexture("Bar",
                new Texture("/barbackground.png")
        );
        this.getTextureGroup().addTexture("Bus",
                new Texture("/busbackground.png")
        );
        this.getTextureGroup().addTexture("Commodity",
                new Texture("/commodity.png")
        );
        this.getTextureGroup().addTexture("Bank",
                new Texture("/bankbackground.png")
        );
        this.getTextureGroup().addTexture("MCD",
                new Texture("/mcdbackground.png")
        );
        this.getTextureGroup().addTexture("Corp",
                new Texture("/corpbackground.png")
        );
        this.getTextureGroup().addTexture("FStore",
                new Texture("/fstorebackground.png")
        );
        this.getTextureGroup().addTexture("Pawn",
                new Texture("/pawn.png")
        );
        this.getTextureGroup().addTexture("MansionBuilding",
                new Texture("/mansionbuilding.png")
        );
        this.getTextureGroup().addTexture("CastleBuilding",
                new Texture("/castlebuilding.png")
        );
        this.getTextureGroup().addTexture("OldApt",
                new Texture("/oldapartment.png")
        );
        this.getTextureGroup().addTexture("bApt",
                new Texture("/biggerapartment.png")
        );
        this.getTextureGroup().addTexture("Penthouse",
                new Texture("/penthouse.png")
        );
        this.getTextureGroup().addTexture("Mansion",
                new Texture("/mansionbackground.png")
        );
        this.getTextureGroup().addTexture("Castle",
                new Texture("/castlebackground.png")
        );
        this.getTextureGroup().addTexture("University",
                new Texture("/universityofstick.png")
        );
        this.getTextureGroup().addTexture("Store",
                new Texture("/storeBackground.png")
        );

        //Overlay
        this.getTextureGroup().addTexture("Health",
                new Texture("/health.png")
        );
        this.getTextureGroup().addTexture("Stats",
                new Texture("/stats.png")
        );
        this.getTextureGroup().addTexture("Inventory",
                new Texture("/inventory.png")
        );

        //car
        this.getTextureGroup().addTexture("Car",
                new Texture("/car.png")
        );

        //Buttons
        this.getTextureGroup().addTexture("Deposit",
                new Texture("/deposit.png")
        );
        this.getTextureGroup().addTexture("Withdraw",
                new Texture("/withdraw.png")
        );
        this.getTextureGroup().addTexture("Cancel",
                new Texture("/cancel.png")
        );
        this.getTextureGroup().addTexture("OK",
                new Texture("/ok.png")
        );

        //Doors and Boundaries
        this.getTextureGroup().addTexture("hDoor",
                Texture.colorTexture(
                        new Color(0,0,0,0),
                        new Dimensions(40,1)
                )
        );
        this.getTextureGroup().addTexture("vDoor",
                Texture.colorTexture(
                        new Color(0,0,0,0),
                        new Dimensions(1,60)
                )
        );

        this.getTextureGroup().addTexture(
                "b1",
                Texture.colorTexture(
                        new Color(0,0,0,0),
                        new Dimensions(20,192)
                )
        );
        this.getTextureGroup().addTexture(
                "b2",
                Texture.colorTexture(
                        new Color(0,0,0,0),
                        new Dimensions(157,257)
                )
        );
        this.getTextureGroup().addTexture(
                "b3",
                Texture.colorTexture(
                        new Color(0,0,0,0),
                        new Dimensions(576,118)
                )
        );
        this.getTextureGroup().addTexture(
                "b4",
                Texture.colorTexture(
                        new Color(0,0,0,0),
                        new Dimensions(236,23)
                )
        );
        this.getTextureGroup().addTexture(
                "b5",
                Texture.colorTexture(
                        new Color(0,0,0,0),
                        new Dimensions(504,140)
                )
        );
        this.getTextureGroup().addTexture(
                "b6",
                Texture.colorTexture(
                        new Color(0,0,0,0),
                        new Dimensions(596,140)
                )
        );
        this.getTextureGroup().addTexture(
                "b7",
                Texture.colorTexture(
                        new Color(0,0,0,0),
                        new Dimensions(20,251)
                )
        );
        this.getTextureGroup().addTexture(
                "b8",
                Texture.colorTexture(
                        new Color(0,0,0,0),
                        new Dimensions(20,158)
                )
        );
        this.getTextureGroup().addTexture(
                "b9",
                Texture.colorTexture(
                        new Color(0,0,0,0),
                        new Dimensions(298,24)
                )
        );
        this.getTextureGroup().addTexture(
                "b10",
                Texture.colorTexture(
                        new Color(0,0,0,0),
                        new Dimensions(419,59)
                )
        );
        this.getTextureGroup().addTexture(
                "b11",
                Texture.colorTexture(
                        new Color(0,0,0,0),
                        new Dimensions(84,190)
                )
        );
        this.getTextureGroup().addTexture(
                "b12",
                Texture.colorTexture(
                        new Color(0,0,0,0),
                        new Dimensions(74,517)
                )
        );
        this.getTextureGroup().addTexture(
                "b13",
                Texture.colorTexture(
                        new Color(0,0,0,0),
                        new Dimensions(75,177)
                )
        );
        this.getTextureGroup().addTexture(
                "b14",
                Texture.colorTexture(
                        new Color(0,0,0,0),
                        new Dimensions(89,96)
                )
        );
        this.getTextureGroup().addTexture(
                "b15",
                Texture.colorTexture(
                        new Color(0,0,0,0),
                        new Dimensions(20,230)
                )
        );
        this.getTextureGroup().addTexture(
                "b16",
                Texture.colorTexture(
                        new Color(0,0,0,0),
                        new Dimensions(118,10)
                )
        );
    }
}
