package stickrpg.game.drawable.entity;

import jtwod.engine.Scene;
import jtwod.engine.drawable.Entity;
import jtwod.engine.graphics.Texture;
import jtwod.engine.metrics.Vector;
import stickrpg.game.core.StickScene;
import stickrpg.game.drawable.ButtonText;
import stickrpg.game.drawable.ButtonTextAttribute;
import stickrpg.game.util.ButtonTypeEnum;
import stickrpg.game.core.StickEngine;
import stickrpg.game.core.StickEntityController;
import stickrpg.game.util.DoorTypeEnum;

import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class MenuButton extends Entity<StickEngine>
{
    private ButtonTypeEnum t;
    public boolean clickable;
    public int slot;
    private int timeTaken;
    public int cAmount;
    public Texture OGTexture;
    public Texture hoverTexture;
    private String name;
    private String attr = "";
    public ButtonText bt;
    public ButtonTextAttribute bt2 = null;

    public MenuButton(Texture texture, Texture hoverTexture, Scene<StickEngine> scene, ButtonTypeEnum t, int slot, String name)
    {
        super(Vector.Zero(), texture, scene);
        this.setPosition(getPosForMenuSlot(slot));
        this.t = t;
        this.slot = slot;
        this.cAmount = 0;
        this.OGTexture = texture;
        this.hoverTexture = hoverTexture;
        this.name = name;
        this.bt = null;
        addButtonTextDrawable();
    }

    public MenuButton(Texture texture, Texture hoverTexture, Scene<StickEngine> scene, ButtonTypeEnum t, int slot, String name, String attr)
    {
        super(Vector.Zero(), texture, scene);
        this.setPosition(getPosForMenuSlot(slot));
        this.t = t;
        this.slot = slot;
        this.cAmount = 0;
        this.OGTexture = texture;
        this.hoverTexture = hoverTexture;
        this.name = name;
        this.bt = null;
        this.attr = attr;
        addButtonTextDrawable();
    }

    public MenuButton(Texture texture, Texture hoverTexture, Scene<StickEngine> scene, ButtonTypeEnum t, int slot, int time, String name)
    {
        super(Vector.Zero(), texture, scene);
        this.setPosition(getPosForMenuSlot(slot));
        this.t = t;
        this.slot = slot;
        this.timeTaken = time;
        this.OGTexture = texture;
        this.hoverTexture = hoverTexture;
        this.name = name;
        this.bt = null;
        addButtonTextDrawable();
    }

    public MenuButton(Texture texture, Texture hoverTexture, Scene<StickEngine> scene, ButtonTypeEnum t, int slot, int time, String name, String attr)
    {
        super(Vector.Zero(), texture, scene);
        this.setPosition(getPosForMenuSlot(slot));
        this.t = t;
        this.slot = slot;
        this.timeTaken = time;
        this.OGTexture = texture;
        this.hoverTexture = hoverTexture;
        this.name = name;
        this.bt = null;
        this.attr = attr;
        addButtonTextDrawable();
    }

    public MenuButton(Texture texture, Texture hoverTexture, Scene<StickEngine> scene, ButtonTypeEnum t, int slot)
    {
        super(Vector.Zero(), texture, scene);
        this.setPosition(getPosForMenuSlot(slot));
        this.t = t;
        this.slot = slot;
        this.cAmount = 0;
        this.OGTexture = texture;
        this.hoverTexture = hoverTexture;
        this.bt = null;
    }

    public MenuButton(Texture texture, Texture hoverTexture, Scene<StickEngine> scene, ButtonTypeEnum t, int slot, int time)
    {
        super(Vector.Zero(), texture, scene);
        this.setPosition(getPosForMenuSlot(slot));
        this.t = t;
        this.slot = slot;
        this.timeTaken = time;
        this.OGTexture = texture;
        this.hoverTexture = hoverTexture;
        this.bt = null;
    }

    private void addButtonTextDrawable()
    {
        bt = new ButtonText(
                this.name.toUpperCase(),
                this.getPosition(),
                this.getParentEngine(),
                this.getParentScene(),
                this
        );
        this.getSubDrawableGroup().addDrawable(bt);
        bt.setVisible(true);

        if(!attr.equals(""))
        {
            bt2 =  new ButtonTextAttribute(
                    this.attr.toUpperCase(),
                    this.getPosition(),
                    this.getParentEngine(),
                    this.getParentScene()
            );
            this.getSubDrawableGroup().addDrawable(bt2);
            bt2.setVisible(true);
        }
    }

    @Override
    protected final void mouseEntered()
    {
        this.setTexture(hoverTexture);
        if(bt != null)
            this.bt.toggleColor();
    }

    @Override
    protected final void mouseLeft()
    {
        this.setTexture(OGTexture);
        if(bt != null)
            this.bt.toggleColor();
    }

    @Override
    public final void mouseClicked(int button, Vector location)
    {
        if(clickable)
        {
            System.out.println(this.t);
            Player player = this.getParentScene().getEntityController(StickEntityController.class).player;
            StickEntityController ec = this.getParentScene().getEntityController(StickEntityController.class);
            StickScene sc = (StickScene)this.getParentScene();
            switch (t)
            {
                case EXIT:{
                    sc.toggleMenuBackground(null);
                    sc.hideStatsInv();
                    break;
                }
                case OK:{
                    sc.hideResponse();
                    ec.ok.setVisible(false);
                    break;
                }
                //commodities
                case ACCEPT:{
                    player.addMoney(cAmount);
                    sc.hideResponse();
                    sc.toggleMenuBackground(null);
                    sc.hideStatsInv();
                    break;
                }
                case NY:{
                    if(player.addTime(timeTaken) != -1 && player.money >= 115)
                    {
                        sc.showResponse(ButtonTypeEnum.NY);
                        player.addMoney(-115);
                    }
                    else
                    {
                        sc.stats.get(3).setText("It's Too Late! You're exhausted!");
                        sc.stats.get(3).setColor(Color.RED);
                        sc.stats.get(3).setVisible(true);
                        sc.stats.get(3).setPosition(new Vector(0,sc.stats.get(3).getPosition().getY() + 10));
                    }
                    break;
                }
                case IL:{
                    if(player.addTime(timeTaken) != -1 && player.money >= 115)
                    {
                        sc.showResponse(ButtonTypeEnum.IL);
                        player.addMoney(-115);
                    }
                    else
                    {
                        sc.stats.get(3).setText("It's Too Late! You're exhausted!");
                        sc.stats.get(3).setColor(Color.RED);
                        sc.stats.get(3).setVisible(true);
                        sc.stats.get(3).setPosition(new Vector(0,sc.stats.get(3).getPosition().getY() + 10));
                    }
                    break;
                }
                case CA:{
                    if(player.addTime(timeTaken) != -1 && player.money >= 100)
                    {
                        sc.showResponse(ButtonTypeEnum.CA);
                        player.addMoney(-100);
                    }
                    else
                    {
                        sc.stats.get(3).setText("It's Too Late! You're exhausted!");
                        sc.stats.get(3).setColor(Color.RED);
                        sc.stats.get(3).setVisible(true);
                        sc.stats.get(3).setPosition(new Vector(0,sc.stats.get(3).getPosition().getY() + 10));
                    }
                    break;
                }
                case MI:{
                    if(player.addTime(timeTaken) != -1 && player.money >= 100)
                    {
                        sc.showResponse(ButtonTypeEnum.MI);
                        player.addMoney(-100);
                    }
                    else
                    {
                        sc.stats.get(3).setText("It's Too Late! You're exhausted!");
                        sc.stats.get(3).setColor(Color.RED);
                        sc.stats.get(3).setVisible(true);
                        sc.stats.get(3).setPosition(new Vector(0,sc.stats.get(3).getPosition().getY() + 10));
                    }
                    break;
                }
                case NJ:{
                    if(player.addTime(timeTaken) != -1 && player.money >= 130)
                    {
                        sc.showResponse(ButtonTypeEnum.NJ);
                        player.addMoney(-130);
                    }
                    else
                    {
                        sc.stats.get(3).setText("It's Too Late! You're exhausted!");
                        sc.stats.get(3).setColor(Color.RED);
                        sc.stats.get(3).setVisible(true);
                        sc.stats.get(3).setPosition(new Vector(0,sc.stats.get(3).getPosition().getY() + 10));
                    }
                    break;
                }
                case NV:{
                    if(player.addTime(timeTaken) != -1 && player.money >= 130)
                    {
                        sc.showResponse(ButtonTypeEnum.NV);
                        player.addMoney(-130);
                    }
                    else
                    {
                        sc.stats.get(3).setText("It's Too Late! You're exhausted!");
                        sc.stats.get(3).setColor(Color.RED);
                        sc.stats.get(3).setVisible(true);
                        sc.stats.get(3).setPosition(new Vector(0,sc.stats.get(3).getPosition().getY() + 10));
                    }
                    break;
                }
                //bank
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
                case LOAN:{
                    sc.textBoxFocused = false;
                    sc.showResponse(ButtonTypeEnum.LOAN);
                    break;
                }
                case LOANACCEPT:{
                    if(player.loanday == -1)
                    {
                        player.addMoney(1000);
                        player.loanday = player.day;
                        player.loan = 1000;
                    }
                    else if(player.money >= player.loan)
                    {
                        player.addMoney(-player.loan);
                        player.loanday = -1;
                        player.loan = 0;
                    }
                    sc.hideResponse();
                    ec.ok.setVisible(false);
                    break;
                }
                case BHOUSE:{
                    sc.textBoxFocused = false;
                    sc.showResponse(ButtonTypeEnum.BHOUSE);
                    break;
                }
                case BAPT:{
                    if(player.money >= 25000 && !player.bApt)
                    {
                        player.addMoney(-25000);
                        player.bApt = true;
                        ec.bApt.setVisible(false);
                    }
                    break;
                }
                case PENTHOUSE:{
                    if(player.money >= 50000 && !player.penthouse)
                    {
                        player.addMoney(-50000);
                        player.penthouse = true;
                        ec.penthouse.setVisible(false);
                    }
                    break;
                }
                case MANSION:{
                    if(player.money >= 100000 && !player.mansion)
                    {
                        player.addMoney(-100000);
                        player.mansion = true;
                        ec.mansion.setVisible(false);
                        ec.mansionBuilding.setVisible(true);
                    }
                    break;
                }
                case CASTLE:{
                    if(player.money >= 500000 && !player.castle)
                    {
                        player.addMoney(-500000);
                        player.castle = true;
                        ec.castle.setVisible(false);
                        ec.vDoors.get(0).setPosition(ec.vDoors.get(0).getPosition().plusX(-47));
                    }
                    break;
                }
                //store
                case ADDSMOKES: {
                    if(player.money >= 10)
                    {
                        player.smokes++;
                        player.addMoney(-10);
                        sc.showResponse(ButtonTypeEnum.ADDSMOKES);
                    }
                    break;
                }
                case CANDY:{
                    if(player.money >= 2)
                    {
                        player.addMoney(-2);
                        player.addHealth(3);
                        sc.showResponse(ButtonTypeEnum.CANDY);
                    }
                    break;
                }
                case PILLS:{
                    if(player.money >= 45)
                    {
                        player.pills++;
                        player.addMoney(-45);
                        sc.showResponse(ButtonTypeEnum.PILLS);
                    }
                    break;
                }
                case NACHOS:{
                    if(player.money >= 4)
                    {
                        player.addMoney(-4);
                        player.addHealth(7);
                        sc.showResponse(ButtonTypeEnum.NACHOS);
                    }
                    break;
                }
                case SLUSHEE:{
                    if(player.money >= 1)
                    {
                        player.addMoney(-1);
                        player.addHealth(1);
                        sc.showResponse(ButtonTypeEnum.SLUSHEE);
                    }
                    break;
                }
                case ROB:{
                    if(player.addTime(timeTaken) != -1)
                        sc.showResponse(ButtonTypeEnum.ROB);
                    else
                    {
                        sc.stats.get(3).setText("It's Too Late! You're exhausted!");
                        sc.stats.get(3).setColor(Color.RED);
                        sc.stats.get(3).setVisible(true);
                        sc.stats.get(3).setPosition(new Vector(0,sc.stats.get(3).getPosition().getY() + 10));
                    }
                    break;
                }
                //people
                case COKE:{
                    if(player.money >= 400)
                    {
                        player.addMoney(-400);
                        player.coke++;
                        sc.showResponse(ButtonTypeEnum.COKE);
                    }
                    break;
                }
                case COINS:{
                    if(player.money >= 10)
                    {
                        if(!player.gCoins)
                            player.charm+=6;
                        player.addMoney(-10);
                        sc.showResponse(ButtonTypeEnum.COINS);
                        player.gCoins = true;
                    }
                    break;
                }
                case GIVESMOKES: {
                    if(player.smokes > 0)
                    {
                        player.smokes--;
                        player.skateboard = true;
                        sc.showResponse(ButtonTypeEnum.GIVESMOKES);
                    }
                    break;
                }
                //school
                case GYM:{
                    if(player.addTime(timeTaken) != -1)
                    {
                        player.addStrength(1);
                        sc.showResponse(ButtonTypeEnum.GYM);
                    }
                    else
                    {
                        sc.stats.get(1).setText("It's Too Late! You're exhausted!");
                        sc.stats.get(1).setColor(Color.RED);
                        sc.stats.get(1).setVisible(true);
                    }
                    break;
                }
                case STUDY:{
                    if(player.addTime(timeTaken) != -1)
                    {
                        player.intelligence++;
                        sc.showResponse(ButtonTypeEnum.STUDY);
                    }
                    else
                    {
                        sc.stats.get(1).setText("It's Too Late! You're exhausted!");
                        sc.stats.get(1).setColor(Color.RED);
                        sc.stats.get(1).setVisible(true);
                    }
                    break;
                }
                case SCLASS:{
                    if(player.money >= 20 && player.addTime(timeTaken) != -1)
                    {
                        player.intelligence += 2;
                        player.addMoney(-20);
                        sc.showResponse(ButtonTypeEnum.SCLASS);
                    }
                    else
                    {
                        sc.stats.get(1).setText("It's Too Late! You're exhausted!");
                        sc.stats.get(1).setColor(Color.RED);
                        sc.stats.get(1).setVisible(true);
                    }
                    break;
                }
                //home
                case SLEEP:{
                    player.sleep();
                    sc.showResponse(ButtonTypeEnum.SLEEP);
                    break;
                }
                case PHONE:{
                    //scene.showPhoneMessages()
                    break;
                }
                //inventory
                case GOHOUSE:{
                    if(this.getParentScene().getEntityController(StickEntityController.class).player.mansion || this.getParentScene().getEntityController(StickEntityController.class).player.castle)
                        sc.moveAllTo(-107-400-210, 81-125);
                    else
                        sc.moveAllTo(-107,-131);

                    sc.toggleMenuBackground(null);
                    if(this.getParentScene().getEntityController(StickEntityController.class).player.castle)
                        sc.toggleMenuBackground(DoorTypeEnum.CASTLE);
                    else if(this.getParentScene().getEntityController(StickEntityController.class).player.mansion)
                        sc.toggleMenuBackground(DoorTypeEnum.MANSION);
                    else if(this.getParentScene().getEntityController(StickEntityController.class).player.penthouse)
                        sc.toggleMenuBackground(DoorTypeEnum.PENTHOUSE);
                    else if(this.getParentScene().getEntityController(StickEntityController.class).player.bApt)
                        sc.toggleMenuBackground(DoorTypeEnum.BAPT);
                    else
                        sc.toggleMenuBackground(DoorTypeEnum.HOME);
                    sc.hideStatsInv();
                    break;
                }
                //pawn
                case GUN:{
                    if(player.money >= 400 && !player.gun)
                    {
                        player.addMoney(-400);
                        player.gun = true;
                        ec.gun.setVisible(false);
                        sc.showResponse(ButtonTypeEnum.GUN);
                    }
                    break;
                }
                case KNIFE:{
                    if(player.money >= 100 && !player.knife)
                    {
                        player.addMoney(-100);
                        player.knife = true;
                        ec.knife.setVisible(false);
                        sc.showResponse(ButtonTypeEnum.KNIFE);
                    }
                    break;
                }
                case ALARM:{
                    if(player.money >= 200 && !player.alarm)
                    {
                        player.addMoney(-200);
                        player.alarm = true;
                        ec.alarm.setVisible(false);
                        sc.showResponse(ButtonTypeEnum.ALARM);
                    }
                    break;
                }
                case CELL:{
                    if(player.money >= 200 && !player.cell)
                    {
                        player.addMoney(-200);
                        player.cell = true;
                        ec.cell.setVisible(false);
                        sc.showResponse(ButtonTypeEnum.CELL);
                    }
                    break;
                }
                case BULLETS:{
                    if(player.money >= 10)
                    {
                        player.addMoney(-10);
                        player.bullets += 5;
                        sc.showResponse(ButtonTypeEnum.BULLETS);
                    }
                    break;
                }
                //fstore
                case TV:{
                    if(player.money >= 2500 && !player.tv)
                    {
                        player.addMoney(-2500);
                        player.tv = true;
                        ec.tv.setVisible(false);
                    }
                    break;
                }
                case COMPUTER:{
                    if(player.money >= 2000 && !player.computer)
                    {
                        player.addMoney(-2000);
                        player.computer = true;
                        ec.computer.setVisible(false);
                    }
                    break;
                }
                case SATELLITE:{
                    if(player.money >= 3000 && !player.satellite)
                    {
                        player.addMoney(-3000);
                        player.satellite = true;
                        ec.satellite.setVisible(false);
                    }
                    break;
                }
                case AC:{
                    if(player.money >= 2500 && !player.ac)
                    {
                        player.addMoney(-2500);
                        player.ac = true;
                        ec.ac.setVisible(false);
                    }
                    break;
                }
                case PEDIA:{
                    if(player.money >= 2000 && !player.pedia)
                    {
                        player.addMoney(-2000);
                        player.pedia = true;
                        ec.pedia.setVisible(false);
                    }
                    break;
                }
                case MINIBAR:{
                    if(player.money >= 3500 && !player.minibar)
                    {
                        player.addMoney(-3500);
                        player.minibar = true;
                        ec.minibar.setVisible(false);
                    }
                    break;
                }
                case TREADMILL:{
                    if(player.money >= 3500 && !player.treadmill)
                    {
                        player.addMoney(-3500);
                        player.treadmill = true;
                        ec.treadmill.setVisible(false);
                    }
                    break;
                }
                case BED:{
                    if(player.money >= 500)
                    {
                        player.addMoney(-500);
                        player.bed = true;
                        ec.bed.setVisible(false);
                    }
                    break;
                }
                //bar
                case BUYB:{
                    if(player.money >= 30)
                    {
                        player.addMoney(-30);
                        player.bottles++;
                        sc.showResponse(ButtonTypeEnum.BUYB);
                    }
                    break;
                }
                case DRINKB:{
                    if(player.addTime(timeTaken) != -1 && player.money >= 20)
                    {
                        player.addMoney(-20);
                        player.charm+=2;
                        sc.showResponse(ButtonTypeEnum.DRINKB);
                    }
                    else
                    {
                        sc.stats.get(3).setText("It's Too Late! You're exhausted!");
                        sc.stats.get(3).setColor(Color.RED);
                        sc.stats.get(3).setVisible(true);
                        sc.stats.get(3).setPosition(new Vector(0,sc.stats.get(3).getPosition().getY() + 10));
                    }
                    break;
                }
                //MCD
                case SHAKE:{
                    if(player.addTime(timeTaken) != -1 && player.money >= 8)
                    {
                        player.addMoney(-8);
                        player.addHealth(12);
                        sc.showResponse(ButtonTypeEnum.SHAKE);
                    }
                    else
                    {
                        sc.stats.get(1).setText("It's Too Late! You're exhausted!");
                        sc.stats.get(1).setColor(Color.RED);
                        sc.stats.get(1).setVisible(true);
                    }
                    break;
                }
                case FRIES:{
                    if(player.addTime(timeTaken) != -1 && player.money >= 12)
                    {
                        player.addMoney(-12);
                        player.addHealth(20);
                        sc.showResponse(ButtonTypeEnum.FRIES);
                    }
                    else
                    {
                        sc.stats.get(1).setText("It's Too Late! You're exhausted!");
                        sc.stats.get(1).setColor(Color.RED);
                        sc.stats.get(1).setVisible(true);
                    }
                    break;
                }
                case CHEESEBURGER:{
                    if(player.addTime(timeTaken) != -1 && player.money >= 25)
                    {
                        player.addMoney(-25);
                        player.addHealth(40);
                        sc.showResponse(ButtonTypeEnum.CHEESEBURGER);
                    }
                    else
                    {
                        sc.stats.get(1).setText("It's Too Late! You're exhausted!");
                        sc.stats.get(1).setColor(Color.RED);
                        sc.stats.get(1).setVisible(true);
                    }
                    break;
                }
                case TRIPLECHEESE:{
                    if(player.addTime(timeTaken) != -1 && player.money >= 50)
                    {
                        player.addMoney(-50);
                        player.addHealth(80);
                        sc.showResponse(ButtonTypeEnum.TRIPLECHEESE);
                    }
                    else
                    {
                        sc.stats.get(1).setText("It's Too Late! You're exhausted!");
                        sc.stats.get(1).setColor(Color.RED);
                        sc.stats.get(1).setVisible(true);
                    }
                    break;
                }
                //Corp
                case WORK:{
                    if(player.addTime(timeTaken) != -1)
                    {
                        if(player.corpTier == 0)
                            player.addMoney(6 * 6);
                        if(player.corpTier == 1)
                            player.addMoney(6 * 8);
                        if(player.corpTier == 2)
                            player.addMoney(6 * 10);
                        if(player.corpTier == 3)
                            player.addMoney(6 * 15);
                        if(player.corpTier == 4)
                            player.addMoney(6 * 25);
                        if(player.corpTier == 5)
                            player.addMoney(6 * 50);
                        if(player.corpTier == 6)
                            player.addMoney(6 * 100);
                        sc.showResponse(ButtonTypeEnum.WORK);
                    }
                    else
                    {
                        sc.stats.get(3).setText("It's Too Late! You're exhausted!");
                        sc.stats.get(3).setColor(Color.RED);
                        sc.stats.get(3).setVisible(true);
                        sc.stats.get(3).setPosition(new Vector(0,sc.stats.get(3).getPosition().getY() + 10));
                    }
                    break;
                }
                case APPLY:{
                    sc.showResponse(ButtonTypeEnum.APPLY);
                    if(player.corpTier == 0 && player.intelligence >= 20)
                        player.corpTier++;
                    else if(player.corpTier == 1 && player.intelligence >= 40)
                        player.corpTier++;
                    else if(player.corpTier == 2 && player.intelligence >= 80)
                        player.corpTier++;
                    else if(player.corpTier == 3 && player.intelligence >= 140)
                        player.corpTier++;
                    else if(player.corpTier == 4 && player.intelligence >= 220)
                        player.corpTier++;
                    else if(player.corpTier == 5 && player.intelligence >= 300)
                        player.corpTier++;
                    break;
                }
                default:{
                    break;
                }
            }
        }
    }

    public final Vector getPosForMenuSlot(int slot)
    {
        if(slot == 1)
        {
            return new Vector(
                    ((StickScene)this.getParentScene()).menuBackground.getPosition().getX() + 15,
                    ((StickScene)this.getParentScene()).menuBackground.getPosition().getY()
                            + ((StickScene)this.getParentScene()).menuBackground.getDimensions().getHeight() - 200
            );
        }
        else if(slot == 2)
        {
            return new Vector(
                    ((StickScene)this.getParentScene()).menuBackground.getPosition().getX() + 15,
                    ((StickScene)this.getParentScene()).menuBackground.getPosition().getY()
                            + ((StickScene)this.getParentScene()).menuBackground.getDimensions().getHeight() - 150
            );
        }
        else if(slot == 3)
        {
            return new Vector(
                    ((StickScene)this.getParentScene()).menuBackground.getPosition().getX() + 15,
                    ((StickScene)this.getParentScene()).menuBackground.getPosition().getY()
                            + ((StickScene)this.getParentScene()).menuBackground.getDimensions().getHeight() - 100
            );
        }
        else if(slot == 4)
        {
            return new Vector(
                    ((StickScene)this.getParentScene()).menuBackground.getPosition().getX() + 15,
                    ((StickScene)this.getParentScene()).menuBackground.getPosition().getY()
                            + ((StickScene)this.getParentScene()).menuBackground.getDimensions().getHeight() - 50
            );
        }
        else if(slot == 5)
        {
            return new Vector(
                    ((StickScene)this.getParentScene()).menuBackground.getPosition().getX()
                            + ((StickScene)this.getParentScene()).menuBackground.getDimensions().getWidth() / 2 + 15,
                    ((StickScene)this.getParentScene()).menuBackground.getPosition().getY()
                            + ((StickScene)this.getParentScene()).menuBackground.getDimensions().getHeight() - 200
            );
        }
        else if(slot == 6)
        {
            return new Vector(
                    ((StickScene)this.getParentScene()).menuBackground.getPosition().getX()
                            + ((StickScene)this.getParentScene()).menuBackground.getDimensions().getWidth() / 2 + 15,
                    ((StickScene)this.getParentScene()).menuBackground.getPosition().getY()
                            + ((StickScene)this.getParentScene()).menuBackground.getDimensions().getHeight() - 150
            );
        }
        else if(slot == 7)
        {
            return new Vector(
                    ((StickScene)this.getParentScene()).menuBackground.getPosition().getX()
                            + ((StickScene)this.getParentScene()).menuBackground.getDimensions().getWidth() / 2 + 15,
                    ((StickScene)this.getParentScene()).menuBackground.getPosition().getY()
                            + ((StickScene)this.getParentScene()).menuBackground.getDimensions().getHeight() - 100
            );
        }
        else if(slot == 8)
        {

            return new Vector(
                    ((StickScene)this.getParentScene()).menuBackground.getPosition().getX()
                            + ((StickScene)this.getParentScene()).menuBackground.getDimensions().getWidth() / 2 + 15,
                    ((StickScene)this.getParentScene()).menuBackground.getPosition().getY()
                            + ((StickScene)this.getParentScene()).menuBackground.getDimensions().getHeight() - 50
            );
        }
        else
            return null;
    }
}