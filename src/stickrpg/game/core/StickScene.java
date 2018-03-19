package stickrpg.game.core;

import jtwod.engine.Drawable;
import jtwod.engine.Scene;
import jtwod.engine.drawable.Entity;
import jtwod.engine.drawable.Text;
import jtwod.engine.metrics.Vector;
import stickrpg.game.drawable.*;
import stickrpg.game.util.ButtonTypeEnum;
import stickrpg.game.util.DoorTypeEnum;
import stickrpg.game.drawable.entity.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class StickScene extends Scene<StickEngine>
{
    private int speed = 3;
    private boolean skating;
    private boolean driving;

    public StickBasicMap mapObject;

    public MenuBackground menuBackground;
    private ArrayList<Background> backgrounds;

    //Top Bar
    private Background health;
    public Text<StickEngine> healthText;
    public Text<StickEngine> moneyText;
    public Text<StickEngine> dayText;
    public Text<StickEngine> timeText;

    //Menu
    private Text<StickEngine> menuTitle;

    //Stats
    public ArrayList<Text<StickEngine>> stats;
    private ArrayList<Vector> OGStatsPos;
    private Color statsColor = Color.BLACK;
    private Font statsFont = new Font("Arial", Font.BOLD, 16);

    //Stored Last Variables
    private ArrayList<MenuButton> lastVisibleButtons;
    private String lMt;
    private ArrayList<String> lastStats;
    private ArrayList<Boolean> lastStatsVis;
    private boolean lMtv;

    //Bank
    public TextBox textBox;

    public TextButton cancel;
    public TextButton withdraw;
    public TextButton deposit;

    public StickScene(String name, StickEngine engine)
    {
        super(name, engine, null, true);

        mapObject = new StickBasicMap(1, this.getParentEngine(), this);

        //Top Bar
        health = new Background(
                9,
                new Vector(0,5),
                this.getParentEngine().getTextureGroup().getTexture("Health"),
                this.getParentEngine(),
                this
        );
        initOverlayTexts();

        //Menu
        menuBackground = new MenuBackground(3, this.getParentEngine(), this);

        cancel = new TextButton(new Vector(100,100), "Cancel", 70, 20, this.getParentEngine(), this, ButtonTypeEnum.CANCEL);
        withdraw = new TextButton(new Vector(
            this.menuBackground.getPosition().getX()
                    + this.menuBackground.getDimensions().getWidth() / 2 + 15,
            this.menuBackground.getPosition().getY()
                    + this.menuBackground.getDimensions().getHeight() - 150
        ).plusX(45).plusY(3), "WITHDRAW", 88, 29, this.getParentEngine(), this, ButtonTypeEnum.WITHDRAW);
        deposit = new TextButton(new Vector(
                this.menuBackground.getPosition().getX() + 15,
                this.menuBackground.getPosition().getY()
                        + this.menuBackground.getDimensions().getHeight() - 150
        ).plusX(35).plusY(3), "DEPOSIT", 77, 29, this.getParentEngine(), this, ButtonTypeEnum.DEPOSIT);
        initBackgrounds();
        initMenuTexts();
        initStats();
        initTextBox();
    }

    @Override
    public final void allocate()
    {
        this.setEntityController(new StickEntityController(this));
        this.getDrawableGroup().addDrawable(mapObject);
        this.getDrawableGroup().addDrawable(menuBackground);
        this.getDrawableGroup().addDrawable(health);
        this.getDrawableGroup().addDrawable(healthText);
        this.getDrawableGroup().addDrawable(moneyText);
        this.getDrawableGroup().addDrawable(menuTitle);
        this.getDrawableGroup().addDrawable(dayText);
        this.getDrawableGroup().addDrawable(timeText);
        menuTitle.setVisible(false);

        this.getDrawableGroup().addDrawable(cancel);
        cancel.setVisible(false);
        this.getDrawableGroup().addDrawable(withdraw);
        withdraw.setVisible(false);
        this.getDrawableGroup().addDrawable(deposit);
        deposit.setVisible(false);

        for(Text<StickEngine> t : stats)
        {
            this.getDrawableGroup().addDrawable(t);
            t.setVisible(false);
        }

        for(Background bg : backgrounds)
        {
            this.getDrawableGroup().addDrawable(bg);
            bg.setVisible(false);
        }

        this.getDrawableGroup().addDrawable(textBox);
        textBox.setVisible(false);

        toggleMenuBackground(null);
    }

    /* Scene Handling */

    public final void toggleMenuBackground(DoorTypeEnum doorType)
    {
        StickEntityController ec = this.getEntityController(StickEntityController.class);
        menuBackground.setVisible(!menuBackground.isVisible());
        ec.eb.setVisible(!ec.eb.isVisible());
        ec.player.setVisible(!ec.player.isVisible());
        ec.inventory.setVisible(!ec.inventory.isVisible());
        ec.stats.setVisible(!ec.stats.isVisible());

        if(doorType == null)
        {
            ec.hideAllButtons();

            for(Background bg : backgrounds)
            {
                bg.setVisible(false);
            }

            textBox.setVisible(false);
            textBox.setText("0");
            textBoxFocused = false;

            resetMenuItemPos();
            if(ec.player.mansion && !ec.player.castle)
                ec.mansionBuilding.setVisible(true);
            if(ec.player.castle)
                ec.castleBuilding.setVisible(true);
            stats.get(0).setFont(statsFont);
            stats.get(1).setFont(statsFont);
            stats.get(2).setFont(statsFont);
            stats.get(3).setFont(statsFont);
            deposit.setVisible(false);
            withdraw.setVisible(false);
        }
        else
        {
            if(doorType != DoorTypeEnum.KID)
            {
                ec.mansionBuilding.setVisible(false);
                ec.castleBuilding.setVisible(false);
                displayBackgroundForDoor(doorType);
                ec.displayButtonsForDoor(doorType);
            }
            else
            {
                if(!ec.player.mansion && !ec.player.castle)
                {
                    ec.mansionBuilding.setVisible(false);
                    ec.castleBuilding.setVisible(false);
                    displayBackgroundForDoor(doorType);
                    ec.displayButtonsForDoor(doorType);
                }
            }
        }
    }

    public void displayBackgroundForDoor(DoorTypeEnum type)
    {
        switch(type)
        {
            case SCHOOL:{
                addToMenuItemPos(new Vector(0,3));
                backgrounds.get(0).setVisible(true);
                this.menuTitle.setText("University of Stick");
                this.menuTitle.setVisible(true);
                break;
            }
            case STORE:{
                addToMenuItemPos(new Vector(0,-8));
                backgrounds.get(1).setVisible(true);
                stats.get(0).setText("Welcome to the funky-town five-O convenience");
                stats.get(1).setText("store where I be servin' up some quality product");
                stats.get(2).setText("for yo' to be gettin' yo' drink on and gettin'");
                stats.get(3).setText("yo' snack on. How can I hook a brotha' up?");
                stats.get(0).setVisible(true);
                stats.get(1).setVisible(true);
                stats.get(2).setVisible(true);
                stats.get(3).setVisible(true);
                if(this.getEntityController(StickEntityController.class).player.gun)
                    this.getEntityController(StickEntityController.class).rob.setVisible(true);
                break;
            }
            case KID:{
                if(!this.getEntityController(StickEntityController.class).player.skateboard)
                {
                    stats.get(0).setText("Hey Man! Do you have any smokes? I'm old");
                    stats.get(1).setText("enough to smoke, it's just... I.. uh.. er..");
                    stats.get(2).setText("forgot my ID at home!");
                    stats.get(3).setText("Please?");
                    stats.get(0).setVisible(true);
                    stats.get(1).setVisible(true);
                    stats.get(2).setVisible(true);
                    stats.get(3).setVisible(true);
                }
                else
                {
                    stats.get(1).setText("Hey man, I hope you are enjoying that board.");
                    stats.get(1).setVisible(true);
                }
                break;
            }
            case HOMELESS:{
                stats.get(1).setText("Could you spare some change?");
                stats.get(1).setVisible(true);
                break;
            }
            case DEALER:{
                stats.get(0).setText("Hey man, come over here a minute. You wanna see");
                stats.get(1).setText("some dope-fly shit? I'm tellin' ya' man, you can");
                stats.get(2).setText("make FAT stacks off this Bonified, Purified, 100%");
                stats.get(3).setText("Colombian product. How much can I put you'z down for?");
                stats.get(0).setVisible(true);
                stats.get(1).setVisible(true);
                stats.get(2).setVisible(true);
                stats.get(3).setVisible(true);
                stats.get(0).setFont(new Font(statsFont.getName(), statsFont.getStyle(), statsFont.getSize()-2));
                stats.get(1).setFont(new Font(statsFont.getName(), statsFont.getStyle(), statsFont.getSize()-2));
                stats.get(2).setFont(new Font(statsFont.getName(), statsFont.getStyle(), statsFont.getSize()-2));
                stats.get(3).setFont(new Font(statsFont.getName(), statsFont.getStyle(), statsFont.getSize()-2));
                break;
            }
            case MCD:{
                addToMenuItemPos(new Vector(0,2));
                backgrounds.get(5).setVisible(true);
                this.menuTitle.setText("Welcome to McSticks, may I take your order?");
                this.menuTitle.setVisible(true);
                break;
            }
            case BAR:{
                addToMenuItemPos(new Vector(-4,6));
                stats.get(0).setText("Ello', I'm Sticky. What's yer' poison, mate?");
                stats.get(0).setVisible(true);
                backgrounds.get(2).setVisible(true);
                break;
            }
            case BUS:{
                addToMenuItemPos(new Vector(-28,46));
                stats.get(0).setText("Welcome to the bus depot. Buses leave first thing");
                stats.get(1).setText("in the morning. After that, you're outta luck.");
                stats.get(0).setVisible(true);
                stats.get(1).setVisible(true);
                backgrounds.get(3).setVisible(true);
                break;
            }
            case BANK:{
                String l = "";
                if(this.getEntityController(StickEntityController.class).player.loanday == -1)
                    l = "None";
                else
                    l = "Owe $" + NumberFormat.getNumberInstance(Locale.US).format(this.getEntityController(StickEntityController.class).player.loan) + " by day " + (this.getEntityController(StickEntityController.class).player.loanday + 15);

                addToMenuItemPos(new Vector(0,5));
                menuTitle.setText("Welcome to the bank. How may I help you?");
                stats.get(1).setText("Current Balance: $" + NumberFormat.getNumberInstance(Locale.US).format(this.getEntityController(StickEntityController.class).player.bankMoney));
                stats.get(2).setText("Going interest rate: " + Double.valueOf(new DecimalFormat("#.##").format(this.getEntityController(StickEntityController.class).player.intrate)) + "%");
                stats.get(3).setText("Current loan status: " + l);
                menuTitle.setVisible(true);
                stats.get(1).setVisible(true);
                stats.get(2).setVisible(true);
                stats.get(3).setVisible(true);
                backgrounds.get(4).setVisible(true);
                adjustStatTextForTitle(true);
                textBox.setVisible(true);
                break;
            }
            case CORP:{
                addToMenuItemPos(new Vector(0,68));
                stats.get(1).setText("Hi, Welcome to New Lines Incorporated.");
                stats.get(2).setText("How can I help you today?");
                stats.get(1).setVisible(true);
                stats.get(2).setVisible(true);
                backgrounds.get(6).setVisible(true);
                break;
            }
            case FSTORE:{
                addToMenuItemPos(new Vector(-100,36));
                stats.get(1).setText("Welcome to Fine Line Furnishings! How can we");
                stats.get(2).setText("hassle...er...help you today?");
                stats.get(1).setVisible(true);
                stats.get(2).setVisible(true);
                backgrounds.get(7).setVisible(true);
                this.getEntityController(
                        StickEntityController.class).eb.setPosition(
                            this.getEntityController(StickEntityController.class).eb.getPosition().plus(new Vector(104,-196))
                        );

                break;
            }
            case PAWN:{
                addToMenuItemPos(new Vector(0,4));
                stats.get(1).setText("Buy something or get the hell out, punk.");
                stats.get(1).setVisible(true);
                backgrounds.get(8).setVisible(true);
                break;
            }
            case OLDAPT:{
                stats.get(1).setText("Seems you don't live here anymore...");
                stats.get(1).setVisible(true);
                backgrounds.get(9).setVisible(true);
                break;
            }
            case BAPT:{
                addToMenuItemPos(new Vector(0,5));
                stats.get(1).setText("What would you like to do?");
                stats.get(1).setVisible(true);
                backgrounds.get(10).setVisible(true);
                break;
            }
            case PENTHOUSE:{
                addToMenuItemPos(new Vector(0,5));
                stats.get(1).setText("What would you like to do?");
                stats.get(1).setVisible(true);
                backgrounds.get(11).setVisible(true);
                break;
            }
            case MANSION:{
                addToMenuItemPos(new Vector(0,5));
                stats.get(1).setText("What would you like to do?");
                stats.get(1).setVisible(true);
                backgrounds.get(12).setVisible(true);
                break;
            }
            case CASTLE:{
                addToMenuItemPos(new Vector(0,5));
                stats.get(1).setText("What would you like to do?");
                stats.get(1).setVisible(true);
                if(this.getEntityController(StickEntityController.class).player.castle)
                    backgrounds.get(13).setVisible(true);
                else if(this.getEntityController(StickEntityController.class).player.mansion)
                    backgrounds.get(12).setVisible(true);
                break;
            }
            case HOME:{
                addToMenuItemPos(new Vector(0,6));
                stats.get(1).setText("What would you like to do?");
                stats.get(1).setVisible(true);
                if(this.getEntityController(StickEntityController.class).player.castle || this.getEntityController(StickEntityController.class).player.mansion)
                {
                    backgrounds.get(9).setVisible(true);
                    stats.get(1).setText("Seems you don't live here anymore...");
                }
                else if(this.getEntityController(StickEntityController.class).player.penthouse)
                    backgrounds.get(11).setVisible(true);
                else if(this.getEntityController(StickEntityController.class).player.bApt)
                    backgrounds.get(10).setVisible(true);
                else
                    backgrounds.get(15).setVisible(true);
                break;
            }
            default:{
                break;
            }
        }
        if(menuTitle.isVisible())
            adjustStatTextForTitle(true);
    }

    private void resetMenuItemPos()
    {
        menuBackground.setPosition(new Vector(
                (this.getParentEngine().getWindowSize().getWidth())
                        - (this.getParentEngine().getTextureGroup().getTexture(
                                "menuBackground"
                            ).getWidth() + 5),
                40
        ));

        for(Entity<StickEngine> e : this.getEntityController(StickEntityController.class).getAllEntities())
        {
            if(e instanceof MenuButton)
            {
                e.setPosition(((MenuButton) e).getPosForMenuSlot(((MenuButton) e).slot));
                if(((MenuButton) e).bt != null)
                    ((MenuButton) e).bt.setPosition(e.getPosition().plus(45, 10));
                if(((MenuButton) e).bt2 != null)
                    ((MenuButton) e).bt2.setPosition(e.getPosition().plus(45, 40 - 14));
            }
        }

        menuTitle.setPosition(new Vector(0,60));
        menuTitle.setParentStartPosition(menuBackground.getPosition());

        for(int i = 0; i < stats.size(); i++)
        {
            stats.get(i).setPosition(OGStatsPos.get(i));
            stats.get(i).setParentStartPosition(menuBackground.getPosition());
            stats.get(i).setColor(statsColor);
        }

        this.getEntityController(StickEntityController.class).eb.setPosition(
                this.getEntityController(StickEntityController.class).eb.getPosForMenuSlot(8)
        );


    }

    public final void showInventory()
    {
        toggleMenuBackground(DoorTypeEnum.INVENTORY);
        menuTitle.setText("Inventory");
        stats.get(0).setText(
                "Skateboard: " + this.getEntityController(StickEntityController.class).player.skateboard
                + ", Alarm: " + this.getEntityController(StickEntityController.class).player.alarm
                + ", Car: " + this.getEntityController(StickEntityController.class).player.car
        );
        stats.get(1).setText(
                "Gun: " + this.getEntityController(StickEntityController.class).player.gun
                + ", Knife: " + this.getEntityController(StickEntityController.class).player.knife
                + ", Cell: " + this.getEntityController(StickEntityController.class).player.cell
        );
        stats.get(2).setText(
                "Smokes: " + this.getEntityController(StickEntityController.class).player.smokes
                + ", Pills: " + this.getEntityController(StickEntityController.class).player.pills
                + ", Bullets:" + this.getEntityController(StickEntityController.class).player.bullets
                + ", Coke: " + this.getEntityController(StickEntityController.class).player.coke
        );
        menuTitle.setVisible(true);
        stats.get(0).setVisible(true);
        stats.get(1).setVisible(true);
        stats.get(2).setVisible(true);
        adjustStatTextForTitle(true);
    }

    public final void showStats()
    {
        toggleMenuBackground(DoorTypeEnum.STATS);
        menuTitle.setText("Stats");
        stats.get(0).setText(
                "Str " + this.getEntityController(StickEntityController.class).player.strength
                + "   |   Int " + this.getEntityController(StickEntityController.class).player.intelligence
                + "   |   Chm " + this.getEntityController(StickEntityController.class).player.charm
                + "   |   HP " + this.getEntityController(StickEntityController.class).player.health
        );
        stats.get(1).setText(
                "Day: " + this.getEntityController(StickEntityController.class).player.day
                + ", Time: " + this.getEntityController(StickEntityController.class).player.dayTime
        );
        stats.get(2).setText(
                "Money: " + this.getEntityController(StickEntityController.class).player.money
                 + ", Job: " + this.getEntityController(StickEntityController.class).player.getJobString()
        );
        menuTitle.setVisible(true);
        stats.get(0).setVisible(true);
        stats.get(1).setVisible(true);
        stats.get(2).setVisible(true);
        adjustStatTextForTitle(true);
    }

    public final void showResponse(ButtonTypeEnum button)
    {
        StickEntityController ec = this.getEntityController(StickEntityController.class);
        Player player = ec.player;

        storeLastData();
        hideStatsInv();
        ec.hideAllButtons();

        ec.ok.setVisible(true);

        textBox.setVisible(false);
        textBox.setText("0");
        textBoxFocused = false;

        switch(button)
        {
            //commodity locations
            case NY:{
                showCommodityResponse(player);
                break;
            }
            case MI:{
                showCommodityResponse(player);
                break;
            }
            case IL:{
                showCommodityResponse(player);
                break;
            }
            case CA:{
                showCommodityResponse(player);
                break;
            }
            case NJ:{
                showCommodityResponse(player);
                break;
            }
            case NV:{
                showCommodityResponse(player);
                break;
            }
            //store
            case SHAKE:{
                menuTitle.setText("+12 HP");
                stats.get(0).setText("You spill a ton of shake on your shirt.");
                stats.get(1).setText("It's okay thought because you feel a little better.");
                menuTitle.setVisible(true);
                stats.get(0).setVisible(true);
                stats.get(1).setVisible(true);
                adjustStatTextForTitle(true);
                break;
            }
            case SLUSHEE:{
                menuTitle.setText("+1 HP");
                stats.get(0).setText("You spill a ton of slushee on your shirt.");
                stats.get(1).setText("It's okay thought because you feel a little better.");
                menuTitle.setVisible(true);
                stats.get(0).setVisible(true);
                stats.get(1).setVisible(true);
                adjustStatTextForTitle(true);
                break;
            }
            case CANDY:{
                menuTitle.setText("+3 HP");
                stats.get(0).setText("CHOCOLATE!!!!");
                menuTitle.setVisible(true);
                stats.get(0).setVisible(true);
                adjustStatTextForTitle(true);
                break;
            }
            case NACHOS:{
                menuTitle.setText("+7 HP");
                stats.get(0).setText("The queso sauce drips from your chin.");
                menuTitle.setVisible(true);
                stats.get(0).setVisible(true);
                adjustStatTextForTitle(true);
                break;
            }
            case ADDSMOKES:{
                menuTitle.setText("+1 Box of Death Sticks");
                stats.get(0).setText("Hey, don't give these to that kid in town.");
                menuTitle.setVisible(true);
                stats.get(0).setVisible(true);
                adjustStatTextForTitle(true);
                break;
            }
            case PILLS:{
                menuTitle.setText("+1 Caffeine Pill");
                stats.get(0).setText("Sorry bud, we don't carry Modafinil.");
                stats.get(1).setText("No. Nor Adderall..");
                menuTitle.setVisible(true);
                stats.get(0).setVisible(true);
                stats.get(1).setVisible(true);

                break;
            }
            case ROB:{
                int rand = ThreadLocalRandom.current().nextInt(1, 11);
                int amount;
                int amount2;
                int mult;
                if(backgrounds.get(4).isVisible())
                {
                    //bank
                    amount = 1000 + (20 * rand) - player.charm;
                    amount2 = 1000 + (20 * rand) + player.charm;
                    mult = 300;
                }
                else
                {
                    //store
                    amount = 500 + (20 * rand) - player.charm;
                    amount2 = 500 + (20 * rand) + player.charm;
                    mult = 200;
                }

                if(player.bullets < 5 || player.charm + (player.karma * rand) < mult)
                {
                    player.bullets = 0;
                    player.gun = false;
                    player.knife = false;
                    player.coke = 0;
                    player.bottles = 0;
                    player.addMoney(-amount);
                    if(player.money < 0)
                        player.money = 0;
                    menuTitle.setText("Robbery failed!");
                    stats.get(1).setText("Your illegal items were confiscated.");
                    stats.get(2).setText("You have been charged $" + amount + " Dollars.");
                    stats.get(1).setVisible(true);
                    stats.get(2).setVisible(true);
                }
                else
                {
                    player.addMoney(amount2);
                    player.bullets -= 2;
                    menuTitle.setText("Robbery Successful!");
                    stats.get(1).setText("You made out with");
                    stats.get(2).setText("$" + amount + " Dollars!");
                    stats.get(1).setVisible(true);
                    stats.get(2).setVisible(true);
                }
                menuTitle.setVisible(true);
                adjustStatTextForTitle(true);
                this.getEntityController(StickEntityController.class).ok.setVisible(false);
                this.getEntityController(StickEntityController.class).eb.setVisible(true);
                break;
            }
            //People
            case GIVESMOKES:{
                menuTitle.setText("Skateboard Added!");
                stats.get(2).setText("Wow thanks mister take my skateboard.");
                menuTitle.setVisible(true);
                stats.get(2).setVisible(true);
                break;
            }
            case COINS: {
                if (!player.gCoins)
                {
                    menuTitle.setText("+6 Charm");
                    menuTitle.setVisible(true);
                }

                stats.get(2).setText("Why thank you! What a charming gesture...");
                stats.get(3).setText("*mumble*");

                stats.get(2).setVisible(true);
                stats.get(3).setVisible(true);

                break;
            }
            case COKE:{
                menuTitle.setText("+1 Cocaine");
                stats.get(0).setText("You were told you shouldn't do drugs...");
                menuTitle.setVisible(true);
                stats.get(0).setVisible(true);
                adjustStatTextForTitle(true);
                break;
            }
            //School
            case GYM:{
                menuTitle.setText("+1 Strength");
                stats.get(0).setText("You make heavy gains at the gym.");
                stats.get(1).setText("But someone saw you picking your nose :/");
                stats.get(3).setText("Strength Level: " + player.strength);
                menuTitle.setVisible(true);
                stats.get(0).setVisible(true);
                stats.get(1).setVisible(true);
                stats.get(3).setVisible(true);
                adjustStatTextForTitle(true);
                break;
            }
            case STUDY:{
                menuTitle.setText("+1 Intelligence");
                stats.get(0).setText("You fell asleep during class.");
                stats.get(3).setText("Intelligence Level: " + player.intelligence);
                menuTitle.setVisible(true);
                stats.get(0).setVisible(true);
                stats.get(3).setVisible(true);
                adjustStatTextForTitle(true);
                break;
            }
            case SCLASS:{
                menuTitle.setText("+2 Intelligence");
                stats.get(0).setText("You studied harder than normal for some reason.");
                stats.get(3).setText("Intelligence Level: " + player.intelligence);
                menuTitle.setVisible(true);
                stats.get(0).setVisible(true);
                stats.get(3).setVisible(true);
                adjustStatTextForTitle(true);
                break;
            }
            //Bar
            case DRINKB:{
                menuTitle.setText("+2 Charm");
                stats.get(0).setText("You got hella lit at the bar, yo'");
                menuTitle.setVisible(true);
                stats.get(0).setVisible(true);
                adjustStatTextForTitle(true);
                break;
            }
            case BUYB:{
                menuTitle.setText("+1 Bottle");
                stats.get(0).setText("Maybe you could sell this somewhere?");
                menuTitle.setVisible(true);
                stats.get(0).setVisible(true);
                adjustStatTextForTitle(true);
                break;
            }
            //Pawn
            case BULLETS:{
                menuTitle.setText("+5 Bullets");
                stats.get(0).setText("Use these safely.");
                menuTitle.setVisible(true);
                stats.get(0).setVisible(true);
                adjustStatTextForTitle(true);
                break;
            }
            case GUN:{
                menuTitle.setText("Gun Added!");
                stats.get(0).setText("You should think about buying ammo for this thing.");
                menuTitle.setVisible(true);
                stats.get(0).setVisible(true);
                adjustStatTextForTitle(true);
                break;
            }
            case KNIFE:{
                menuTitle.setText("Knife Added!");
                stats.get(0).setText("This should stop those thugs from raping you.");
                menuTitle.setVisible(true);
                stats.get(0).setVisible(true);
                adjustStatTextForTitle(true);
                break;
            }
            case CELL:{
                menuTitle.setText("Cell Phone Added!");
                stats.get(0).setText("Now you can know who wants to buy goods.");
                menuTitle.setVisible(true);
                stats.get(0).setVisible(true);
                adjustStatTextForTitle(true);
                break;
            }
            case ALARM:{
                menuTitle.setText("Alarm Added!");
                stats.get(0).setText("If used with pills,");
                stats.get(1).setText("You'll wake up at the crack of dawn!");
                menuTitle.setVisible(true);
                stats.get(0).setVisible(true);
                stats.get(1).setVisible(true);
                adjustStatTextForTitle(true);
                break;
            }
            //MCD
            case FRIES:{
                stats.get(1).setText("Hmm... Delicious grease..");
                stats.get(1).setVisible(true);
                break;
            }
            case TRIPLECHEESE:{
                stats.get(1).setText("Obesity is a leading cause of death these days..");
                stats.get(1).setVisible(true);
                break;
            }
            case CHEESEBURGER:{
                stats.get(1).setText("Do you want fries with that?");
                stats.get(1).setVisible(true);
                break;
            }
            //FSTORE
            case BED:{
                stats.get(1).setText("+10 Health / Sleep");
                stats.get(1).setVisible(true);
                break;
            }
            case TV:{
                menuTitle.setText("+2 Intelligence");
                menuTitle.setVisible(true);
                stats.get(1).setText("You watch the news for a couple hours.");
                stats.get(2).setText("Looks like you learned something new.");
                stats.get(1).setVisible(true);
                stats.get(2).setVisible(true);
                adjustStatTextForTitle(true);
                break;
            }
            case SATELLITE:{
                stats.get(1).setText("What would you like to watch?");
                stats.get(1).setVisible(true);
                ec.wDating.setVisible(true);
                ec.wNews.setVisible(true);
                ec.wFitness.setVisible(true);
                break;
            }
            case WNEWS:{
                menuTitle.setText("+2 Intelligence");
                menuTitle.setVisible(true);
                stats.get(1).setText("You watch the news for a couple hours.");
                stats.get(2).setText("Looks like you learned something new.");
                stats.get(1).setVisible(true);
                stats.get(2).setVisible(true);
                adjustStatTextForTitle(true);
                ec.ok.setVisible(false);
                ec.eb.setVisible(true);
                break;
            }
            case WFITNESS:{
                menuTitle.setText("+2 Strength");
                menuTitle.setVisible(true);
                stats.get(1).setText("You follow a workout video for a couple hours.");
                stats.get(2).setText("Looks like you made some gains.");
                stats.get(1).setVisible(true);
                stats.get(2).setVisible(true);
                adjustStatTextForTitle(true);
                ec.ok.setVisible(false);
                ec.eb.setVisible(true);
                break;
            }
            case WDATING:{
                menuTitle.setText("+2 Charm");
                menuTitle.setVisible(true);
                stats.get(1).setText("You feel sorry for yourself for 2 hours straight.");
                stats.get(2).setText("You reflected and made positive change!");
                stats.get(1).setVisible(true);
                stats.get(2).setVisible(true);
                adjustStatTextForTitle(true);
                ec.ok.setVisible(false);
                ec.eb.setVisible(true);
                break;
            }
            case COMPUTER:{
                stats.get(1).setText("Stock Market functionality coming soon!");
                stats.get(1).setVisible(true);
                break;
            }
            //Bank
            case LOAN:{
                deposit.setVisible(false);
                withdraw.setVisible(false);
                if(player.loanday == -1)
                {
                    stats.get(0).setText("You are allowed a loan of $1000.");
                    stats.get(1).setText("The loan must be repayed within 15 days.");
                    stats.get(2).setText(
                            "Going interest rate: "
                            + Double.valueOf(new DecimalFormat("#.##").format(
                                    this.getEntityController(StickEntityController.class).player.intrate)
                            )
                            + "%"
                    );
                    stats.get(3).setText("Would you like to accept a loan?");
                    stats.get(0).setVisible(true);
                    stats.get(1).setVisible(true);
                    stats.get(2).setVisible(true);
                    stats.get(3).setVisible(true);
                    this.getEntityController(StickEntityController.class).loanAccept.setVisible(true);
                }
                else
                {
                    stats.get(1).setText(
                            "Would you like to pay back your loan of $"
                             + NumberFormat.getNumberInstance(Locale.US).format(player.loan)
                             + "?");
                    stats.get(1).setVisible(true);
                    this.getEntityController(StickEntityController.class).loanAccept.setVisible(true);
                }

                break;
            }
            case BHOUSE:{
                deposit.setVisible(false);
                withdraw.setVisible(false);
                menuTitle.setText("Property for sale");
                menuTitle.setVisible(true);
                if(!player.bApt)
                    ec.bApt.setVisible(true);
                if(!player.penthouse)
                    ec.penthouse.setVisible(true);
                if(!player.mansion)
                    ec.mansion.setVisible(true);
                if(!player.castle)
                    ec.castle.setVisible(true);
                break;
            }
            //Home
            case SLEEP:{
                if(player.loanday != -1)
                {
                    menuTitle.setText(
                            "$"
                            + NumberFormat.getNumberInstance(Locale.US).format(player.loan)
                            + " owed in "
                            + (player.loanday + 15 - player.day)
                            + " days."
                    );
                }
                else
                    menuTitle.setText("You slept well!");
                menuTitle.setVisible(true);
                int h = 0;
                if(player.bed)
                    h += 10;
                if(player.ac)
                    h += 10;
                if(player.pills > 0)
                    h = 0;

                stats.get(0).setText("+" + h + " Health");
                stats.get(0).setVisible(true);

                if(player.treadmill)
                {
                    stats.get(1).setText("+1 Strength");
                    stats.get(1).setVisible(true);
                }
                if(player.pedia)
                {
                    stats.get(2).setText("+1 Intelligence");
                    stats.get(2).setVisible(true);
                }
                if(player.minibar)
                {
                    stats.get(3).setText("+1 Charm");
                    stats.get(3).setVisible(true);
                }
                adjustStatTextForTitle(true);
                break;
            }
            //Corp
            case APPLY:{
                adjustStatTextForTitle(true);
                if(player.corpTier == 0 && player.intelligence >= 20)
                {
                    menuTitle.setText("You're Hired!");
                    stats.get(0).setText("You start the role of Janitor immediately.");
                    menuTitle.setVisible(true);
                    stats.get(0).setVisible(true);
                }
                else if(player.corpTier == 1 && player.intelligence >= 40)
                {
                    menuTitle.setText("You've been promoted!");
                    stats.get(0).setText("You start the role of Mail Room Clerk immediately.");
                    menuTitle.setVisible(true);
                    stats.get(0).setVisible(true);
                }
                else if(player.corpTier == 2 && player.intelligence >= 80)
                {
                    menuTitle.setText("You've been promoted!");
                    stats.get(0).setText("You start the role of Sales Person immediately.");
                    menuTitle.setVisible(true);
                    stats.get(0).setVisible(true);
                }
                else if(player.corpTier == 3 && player.intelligence >= 140)
                {
                    menuTitle.setText("You've been promoted!");
                    stats.get(0).setText("You start the role of Executive immediately.");
                    menuTitle.setVisible(true);
                    stats.get(0).setVisible(true);
                }
                else if(player.corpTier == 4 && player.intelligence >= 220)
                {
                    menuTitle.setText("You've been promoted!");
                    stats.get(0).setText("You start the role of Vice President immediately.");
                    menuTitle.setVisible(true);
                    stats.get(0).setVisible(true);
                }
                else if(player.corpTier == 5 && player.intelligence >= 300)
                {
                    menuTitle.setText("You've been promoted!");
                    stats.get(0).setText("You start the role of CEO immediately.");
                    menuTitle.setVisible(true);
                    stats.get(0).setVisible(true);
                }
                else if(player.corpTier == 0 && player.intelligence < 20)
                {
                    menuTitle.setText("You've been rejected!");
                    stats.get(0).setText("Sorry, we don't hire idiots.");
                    stats.get(3).setText("(20 Intelligence Required)");
                    menuTitle.setVisible(true);
                    stats.get(0).setVisible(true);
                    stats.get(3).setVisible(true);
                }
                else if(player.corpTier == 1 && player.intelligence < 40)
                {
                    menuTitle.setText("You've been rejected!");
                    stats.get(0).setText("Thank you for applying. Unfortunately, you failed");
                    stats.get(1).setText("the aptitude test. Better luck next Time!");
                    stats.get(3).setText("(40 Intelligence Required)");
                    menuTitle.setVisible(true);
                    stats.get(0).setVisible(true);
                    stats.get(1).setVisible(true);
                    stats.get(3).setVisible(true);
                }
                else if(player.corpTier == 2 && player.intelligence < 80)
                {
                    menuTitle.setText("You've been rejected!");
                    stats.get(0).setText("Thank you for applying. Unfortunately, you failed");
                    stats.get(1).setText("the aptitude test. Better luck next Time!");
                    stats.get(3).setText("(80 Intelligence Required)");
                    menuTitle.setVisible(true);
                    stats.get(0).setVisible(true);
                    stats.get(1).setVisible(true);
                    stats.get(3).setVisible(true);
                }
                else if(player.corpTier == 3 && player.intelligence < 140)
                {
                    menuTitle.setText("You've been rejected!");
                    stats.get(0).setText("Thank you for applying. Unfortunately, you failed");
                    stats.get(1).setText("the aptitude test. Better luck next Time!");
                    stats.get(3).setText("(140 Intelligence Required)");
                    menuTitle.setVisible(true);
                    stats.get(0).setVisible(true);
                    stats.get(1).setVisible(true);
                    stats.get(3).setVisible(true);
                }
                else if(player.corpTier == 4 && player.intelligence < 220)
                {
                    menuTitle.setText("You've been rejected!");
                    stats.get(0).setText("Thank you for applying. Unfortunately, you failed");
                    stats.get(1).setText("the aptitude test. Better luck next Time!");
                    stats.get(3).setText("(220 Intelligence Required)");
                    menuTitle.setVisible(true);
                    stats.get(0).setVisible(true);
                    stats.get(1).setVisible(true);
                    stats.get(3).setVisible(true);
                }
                else if(player.corpTier == 5)
                {
                    menuTitle.setText("You've been rejected!");
                    stats.get(0).setText("Thank you for applying. Unfortunately, you failed");
                    stats.get(1).setText("the aptitude test. Better luck next Time!");
                    stats.get(3).setText("(300 Intelligence Required)");
                    menuTitle.setVisible(true);
                    stats.get(0).setVisible(true);
                    stats.get(1).setVisible(true);
                    stats.get(3).setVisible(true);
                }
                break;
            }
            case WORK:{
                if(player.corpTier == 0)
                {
                    menuTitle.setText("+36 Dollars");
                    stats.get(0).setText("You spill hot fry grease all over you and your boss.");
                    menuTitle.setVisible(true);
                    stats.get(0).setVisible(true);
                }
                else if(player.corpTier == 1)
                {
                    menuTitle.setText("+48 Dollars");
                    stats.get(0).setText("You clean up puke from last night's holiday party.");
                    menuTitle.setVisible(true);
                    stats.get(0).setVisible(true);
                }
                else if(player.corpTier == 2)
                {
                    menuTitle.setText("+60 Dollars");
                    stats.get(0).setText("You sorted that mail.");
                    menuTitle.setVisible(true);
                    stats.get(0).setVisible(true);
                }
                else if(player.corpTier == 3)
                {
                    menuTitle.setText("+90 Dollars");
                    stats.get(0).setText("Hey, you actually made a sale!");
                    menuTitle.setVisible(true);
                    stats.get(0).setVisible(true);
                }
                else if(player.corpTier == 4)
                {
                    menuTitle.setText("+150 Dollars");
                    stats.get(0).setText("You fired an intern for using the executive copier.");
                    menuTitle.setVisible(true);
                    stats.get(0).setVisible(true);
                }
                else if(player.corpTier == 5)
                {
                    menuTitle.setText("+300 Dollars");
                    stats.get(0).setText("You ran the show. Amanda in accounting even flirted with you.");
                    menuTitle.setVisible(true);
                    stats.get(0).setVisible(true);
                }
                else if(player.corpTier == 6)
                {
                    menuTitle.setText("+600 Dollars");
                    stats.get(0).setText("You ate a banana and watched your minions work.");
                    menuTitle.setVisible(true);
                    stats.get(0).setVisible(true);
                }
                adjustStatTextForTitle(true);
                break;
            }
        }
    }

    public final void hideResponse()
    {
        hideStatsInv();
        //restore saved menu state
        for(int i = 0; i < stats.size(); i++)
        {
            stats.get(i).setText(lastStats.get(i));
            stats.get(i).setVisible(lastStatsVis.get(i));
        }
        menuTitle.setText(lMt);
        menuTitle.setVisible(lMtv);

        for(MenuButton m : lastVisibleButtons)
        {
            m.setVisible(true);
        }

        this.getEntityController(StickEntityController.class).bApt.setVisible(false);
        this.getEntityController(StickEntityController.class).penthouse.setVisible(false);
        this.getEntityController(StickEntityController.class).mansion.setVisible(false);
        this.getEntityController(StickEntityController.class).castle.setVisible(false);

        this.getEntityController(StickEntityController.class).accept.setVisible(false);
        this.getEntityController(StickEntityController.class).loanAccept.setVisible(false);

        this.getEntityController(StickEntityController.class).wNews.setVisible(false);
        this.getEntityController(StickEntityController.class).wFitness.setVisible(false);
        this.getEntityController(StickEntityController.class).wDating.setVisible(false);

        backgrounds.get(14).setVisible(false);
        if(backgrounds.get(4).isVisible())
        {
            textBox.setVisible(true);
            String l = "";
            if(this.getEntityController(StickEntityController.class).player.loanday == -1)
                l = "None";
            else
                l = "Owe $"
                    + NumberFormat.getNumberInstance(Locale.US).format(
                            this.getEntityController(StickEntityController.class).player.loan
                    )
                    + " by day "
                    + (this.getEntityController(StickEntityController.class).player.loanday + 15);

            stats.get(3).setText("Current loan status: " + l);
            deposit.setVisible(true);
            withdraw.setVisible(true);
        }
        if(backgrounds.get(6).isVisible())
        {
            if(this.getEntityController(StickEntityController.class).player.corpTier > 0)
            {
                this.getEntityController(StickEntityController.class).work.setVisible(true);
                adjustStatTextForTitle(false);
            }
        }
        for(int i = 0; i < stats.size(); i++)
        {
            if(stats.get(i).getText().equals("It's Too Late! You're exhausted!"))
            {
                stats.get(i).setText("");
                stats.get(i).setColor(statsColor);
            }
        }
        if(!menuTitle.isVisible())
            adjustStatTextForTitle(false);
    }

    /* Init Functions */

    private void initBackgrounds()
    {
        backgrounds = new ArrayList<>();
        backgrounds.add(new Background(
                        2,
                        this.getParentEngine().getTextureGroup().getTexture("University"),
                        this.getParentEngine(),
                        this
                )
        );
        backgrounds.add(new Background(
                        2,
                        this.getParentEngine().getTextureGroup().getTexture("Store"),
                        this.getParentEngine(),
                        this
                )
        );
        backgrounds.add(new Background(
                        2,
                        this.getParentEngine().getTextureGroup().getTexture("Bar"),
                        this.getParentEngine(),
                        this
                )
        );
        backgrounds.add(new Background(
                        2,
                        this.getParentEngine().getTextureGroup().getTexture("Bus"),
                        this.getParentEngine(),
                        this
                )
        );
        backgrounds.add(new Background(
                        2,
                        this.getParentEngine().getTextureGroup().getTexture("Bank"),
                        this.getParentEngine(),
                        this
                )
        );
        backgrounds.add(new Background(
                        2,
                        this.getParentEngine().getTextureGroup().getTexture("MCD"),
                        this.getParentEngine(),
                        this
                )
        );
        backgrounds.add(new Background(
                        2,
                        this.getParentEngine().getTextureGroup().getTexture("Corp"),
                        this.getParentEngine(),
                        this
                )
        );
        backgrounds.add(new Background(
                        2,
                        this.getParentEngine().getTextureGroup().getTexture("FStore"),
                        this.getParentEngine(),
                        this
                )
        );
        backgrounds.add(new Background(
                        2,
                        this.getParentEngine().getTextureGroup().getTexture("Pawn"),
                        this.getParentEngine(),
                        this
                )
        );
        backgrounds.add(new Background(
                        2,
                        this.getParentEngine().getTextureGroup().getTexture("OldApt"),
                        this.getParentEngine(),
                        this
                )
        );
        backgrounds.add(new Background(
                        2,
                        this.getParentEngine().getTextureGroup().getTexture("bApt"),
                        this.getParentEngine(),
                        this
                )
        );
        backgrounds.add(new Background(
                        2,
                        this.getParentEngine().getTextureGroup().getTexture("Penthouse"),
                        this.getParentEngine(),
                        this
                )
        );
        backgrounds.add(new Background(
                        2,
                        this.getParentEngine().getTextureGroup().getTexture("Mansion"),
                        this.getParentEngine(),
                        this
                )
        );
        backgrounds.add(new Background(
                        2,
                        this.getParentEngine().getTextureGroup().getTexture("Castle"),
                        this.getParentEngine(),
                        this
                )
        );
        backgrounds.add(new Background(
                        2,
                        this.getParentEngine().getTextureGroup().getTexture("Commodity"),
                        this.getParentEngine(),
                        this
                )
        );
        backgrounds.add(new Background(
                        2,
                        this.getParentEngine().getTextureGroup().getTexture("Home"),
                        this.getParentEngine(),
                        this
                )
        );
    }

    private void initStats()
    {
        stats = new ArrayList<>();
        OGStatsPos = new ArrayList<>();
        stats.add(new Text<>(
                4,
                "",
                statsFont,
                statsColor,
                Drawable.Center.Horizontally,
                new Vector(
                        menuBackground.getPosition().getX() + 15,
                        menuBackground.getPosition().getY() + 15
                ),
                menuBackground.getPosition(),
                menuBackground.getDimensions(),
                this.getParentEngine(),
                this
        ));
        stats.add(new Text<>(
                4,
                "",
                statsFont,
                statsColor,
                Drawable.Center.Horizontally,
                new Vector(
                        menuBackground.getPosition().getX() + 15,
                        menuBackground.getPosition().getY() + 30
                ),
                menuBackground.getPosition(),
                menuBackground.getDimensions(),
                this.getParentEngine(),
                this
        ));
        stats.add(new Text<>(
                4,
                "",
                statsFont,
                statsColor,
                Drawable.Center.Horizontally,
                new Vector(
                        menuBackground.getPosition().getX() + 15,
                        menuBackground.getPosition().getY() + 45
                ),
                menuBackground.getPosition(),
                menuBackground.getDimensions(),
                this.getParentEngine(),
                this
        ));
        stats.add(new Text<>(
                4,
                "",
                statsFont,
                statsColor,
                Drawable.Center.Horizontally,
                new Vector(
                        menuBackground.getPosition().getX() + 15,
                        menuBackground.getPosition().getY() + 60
                ),
                menuBackground.getPosition(),
                menuBackground.getDimensions(),
                this.getParentEngine(),
                this
        ));
        for(Text<StickEngine> t : stats)
        {
            OGStatsPos.add(t.getPosition());
        }
    }

    private void initMenuTexts()
    {
        menuTitle = new Text<>(
                4,
                "",
                new Font(statsFont.getName(), statsFont.getStyle(), statsFont.getSize() + 2),
                statsColor,
                Drawable.Center.Horizontally,
                new Vector(
                        0,
                        menuBackground.getPosition().getY() + 15
                ),
                menuBackground.getPosition(),
                menuBackground.getDimensions(),
                this.getParentEngine(),
                this
        );
    }

    private void initOverlayTexts()
    {
        healthText = new Text<>(
                10,
                "",
                new Font("System", Font.BOLD, 12),
                Color.white,
                new Vector(45,26),
                this.getParentEngine(),
                this
        );
        moneyText = new Text<>(
                10,
                "",
                new Font("System", Font.BOLD, 18),
                new Color(247,221,0),
                new Vector(this.getParentEngine().getTextureGroup().getTexture("Health").getWidth(),26),
                this.getParentEngine(),
                this
        );
        dayText = new Text<>(
                4,
                "Day 0",
                statsFont,
                statsColor,
                new Vector(
                        this.getParentEngine().getWindowSize().getWidth() - 160,
                        25
                ),
                this.getParentEngine(),
                this
        );
        timeText = new Text<>(
                4,
                "08:00 AM",
                statsFont,
                statsColor,
                new Vector(
                        this.getParentEngine().getWindowSize().getWidth() - 250,
                        25
                ),
                this.getParentEngine(),
                this
        );
    }

    private void initTextBox()
    {
        textBox = new TextBox(
                6,
                "0",
                new Font("System", Font.BOLD, 22),
                Color.BLACK,
                new Vector(
                        menuBackground.getPosition().getX() + menuBackground.getDimensions().getWidth() / 2 - 60,
                        menuBackground.getPosition().getY() + menuBackground.getDimensions().getHeight() - 150 + 30
                ),
                this.getParentEngine(),
                this
        );

        Background textBoxBackground = new Background(
                5,
                new Vector(menuBackground.getPosition().getX() + menuBackground.getDimensions().getWidth() / 2 - 63, textBox.getPosition().getY() - 23),
                this.getParentEngine().getTextureGroup().getTexture("TextBoxBackground"),
                this.getParentEngine(),
                this
        );
        textBox.getSubDrawableGroup().addDrawable(textBoxBackground);
        Background textBoxBackgroundBorder = new Background(
                4,
                new Vector(menuBackground.getPosition().getX() + menuBackground.getDimensions().getWidth() / 2 - 63 - 2, textBox.getPosition().getY() - 23 - 2),
                this.getParentEngine().getTextureGroup().getTexture("TextBoxBackgroundBorder"),
                this.getParentEngine(),
                this
        );
        textBox.getSubDrawableGroup().addDrawable(textBoxBackgroundBorder);
    }

    /* Util Functions */

    private void showCommodityResponse(Player player)
    {
        backgrounds.get(14).setVisible(true);
        menuTitle.setText("SELL COMMODITIES");
        menuTitle.setParentStartPosition(menuBackground.getPosition());
        menuTitle.setVisible(true);

        if(player.bottles == 0 && player.coke == 0)
        {
            stats.get(0).setText("You arrive in the city with nothing to");
            stats.get(1).setText("sell. Unless you just enjoy long,");
            stats.get(2).setText("expensive bus rides, you should");
            stats.get(3).setText("probably stock up before you go again.");
        }
        else if(!player.gun && !player.knife)
        {
            player.setMoney(0);
            player.coke = 0;
            player.bottles = 0;
            stats.get(0).setText("You get mugged almost as soon as");
            stats.get(1).setText("you get on the bus. Maybe think about");
            stats.get(2).setText("investing in some protection.");
            stats.get(3).setText("(You lose your wad and your stash!)");
        }
        else if(!player.gun)
        {
            player.setMoney(0);
            player.coke = 0;
            player.bottles = 0;
            stats.get(0).setText("You brought a knife to a gun fight.");
            stats.get(1).setText("Everyone here is packing some serious heat.");
            stats.get(2).setText("You may want to purchase a firearm.");
            stats.get(3).setText("(You lose your wad and your stash!)");
        }
        else if(player.bullets == 0)
        {
            player.setMoney(0);
            player.coke = 0;
            player.bottles = 0;
            stats.get(0).setText("You wander around and a thug pulls his gun.");
            stats.get(1).setText("*Click* *Click*");
            stats.get(2).setText("You have no ammo.");
            stats.get(3).setText("(You lose your wad and your stash!)");
        }
        else if(!player.cell)
        {
            stats.get(0).setText("You have no way of knowing who wants");
            stats.get(1).setText("your product.");
            stats.get(2).setText("");
            stats.get(3).setText("Buy a cellphone.");
        }
        else
        {
            int rand = ThreadLocalRandom.current().nextInt(1, 11);
            if(player.strength + (player.karma * rand) < 200)
            {
                player.setMoney(0);
                player.coke = 0;
                player.bottles = 0;
                stats.get(0).setText("You are attacked by a pack of stray felines.");
                stats.get(1).setText("You are no match with your scrawny arms.");
                stats.get(2).setText("Someone finds your unconscious body and takes your stuff");
                stats.get(3).setText("(You are weak! You lose your wad and your stash!)");
            }
            else if(player.intelligence + (player.karma * rand) < 200)
            {
                player.setMoney(0);
                stats.get(0).setText("You are tricked into spending all your money");
                stats.get(1).setText("on the cutest plush. It's a Pikachu.");
                stats.get(2).setText("You hitch hike home as your realize your mistake.");
                stats.get(3).setText("(You are dumb! You lose your wad!)");
            }
            else if(player.charm + (player.karma * rand) < 200)
            {
                player.setMoney(0);
                player.coke = 0;
                player.bottles = 0;
                stats.get(0).setText("A lady you met at a bar invites you to her place.");
                stats.get(1).setText("She realizes your body odor and mugs you");
                stats.get(2).setText("at gunpoint, leaving you blue.");
                stats.get(3).setText("(You are smelly! You lose your wad and your stash!)");
            }
            else
            {
                //Success
                int r = ThreadLocalRandom.current().nextInt(1, 11);
                int c = 0;
                int b = 0;
                if(player.coke > 10)
                    c = 10;
                if(player.coke < 10)
                    c = player.coke;
                if(player.bottles > 10)
                    b = 10;
                if(player.bottles < 10)
                    b = player.bottles;

                int amount = 400 + (20 * r) * c;
                int a2 = 30 + (10 * r) * b;

                this.getEntityController(StickEntityController.class).accept.cAmount = amount + a2;

                stats.get(0).setText("You get an offer of");
                stats.get(1).setText("$" + amount + " for " + c + " Coke");
                stats.get(2).setText("$" + a2 + " for " + b + " Bottles");
                stats.get(3).setText("Accept?");

                this.getEntityController(StickEntityController.class).accept.setVisible(true);
                this.getEntityController(StickEntityController.class).ok.setVisible(false);
                this.getEntityController(StickEntityController.class).eb.setVisible(true);
            }
        }
        stats.get(0).setVisible(true);
        stats.get(1).setVisible(true);
        stats.get(2).setVisible(true);
        stats.get(3).setVisible(true);
        adjustStatTextForTitle(true);
    }

    private void storeLastData()
    {
        lastVisibleButtons = this.getEntityController(StickEntityController.class).getVisibleMenuButtons();

        lastStats = new ArrayList<>();
        lastStatsVis = new ArrayList<>();
        for(Text<StickEngine> t : stats)
        {
            lastStats.add(t.getText());
            lastStatsVis.add(t.isVisible());
        }
        lMt = menuTitle.getText();
        lMtv = menuTitle.isVisible();
    }

    private void adjustStatTextForTitle(boolean title)
    {
        if(title)
        {
            stats.get(0).setPosition(new Vector(
                    menuBackground.getPosition().getX() + 15,
                    menuBackground.getPosition().getY() + 45
            ));

            stats.get(1).setPosition(new Vector(
                    menuBackground.getPosition().getX() + 15,
                    menuBackground.getPosition().getY() + 60
            ));

            stats.get(2).setPosition(new Vector(
                    menuBackground.getPosition().getX() + 15,
                    menuBackground.getPosition().getY() + 75
            ));

            stats.get(3).setPosition(new Vector(
                    menuBackground.getPosition().getX() + 15,
                    menuBackground.getPosition().getY() + 90
            ));
        }
        else
        {
            stats.get(0).setPosition(new Vector(
                    menuBackground.getPosition().getX() + 15,
                    menuBackground.getPosition().getY() + 15
            ));

            stats.get(1).setPosition(new Vector(
                    menuBackground.getPosition().getX() + 15,
                    menuBackground.getPosition().getY() + 30
            ));

            stats.get(2).setPosition(new Vector(
                    menuBackground.getPosition().getX() + 15,
                    menuBackground.getPosition().getY() + 45
            ));

            stats.get(3).setPosition(new Vector(
                    menuBackground.getPosition().getX() + 15,
                    menuBackground.getPosition().getY() + 60
            ));
        }
    }

    private void addToMenuItemPos(Vector v)
    {
        menuBackground.setPosition(menuBackground.getPosition().plus(v));
        this.getEntityController(StickEntityController.class).addToMenuButtonsPos(v);
        menuTitle.setPosition(menuTitle.getPosition().plus(v));

        for(Text<StickEngine> t : stats)
        {
            t.setPosition(t.getPosition().plusY(v.getY()));
            t.setParentStartPosition(menuBackground.getPosition());
        }
    }

    public final void hideStatsInv()
    {
        menuTitle.setVisible(false);
        for(Text<StickEngine> t : stats)
            t.setVisible(false);
    }

    /* Movement Handling */

    @Override
    public final void update()
    {
        //temp
        if(this.getEntityController(StickEntityController.class).player.skateboard && this.skating)
            speed = 5;
        else if(this.getEntityController(StickEntityController.class).player.car && this.driving)
            speed = 7;
        else
            speed = 3;

        if(!(mapObject.map.getPosition().getY() + speed > 0 ))
            if(up)
                moveUp();

        if(!(mapObject.map.getPosition().getX() + speed > 0 ))
            if(left)
                moveLeft();

        if(!(mapObject.map.getPosition().getY() - speed <
                -(mapObject.map.getTexture().getHeight() - this.getParentEngine().getWindowSize().getHeight())))
            if(down)
                moveDown();

        if(!(mapObject.map.getPosition().getX() - speed <
                -(mapObject.map.getTexture().getWidth() - this.getParentEngine().getWindowSize().getWidth()) ))
            if(right)
                moveRight();
    }

    public final void moveAllTo(int x, int y)
    {
        mapObject.map.setPosition(new Vector(x,y));
        updateEntities();
    }

    private void updateEntities()
    {
        Vector mapPos = mapObject.map.getPosition();
        for(Entity<StickEngine> e : this.getEntityController().getAllEntities())
        {
            if(e instanceof Boundary)
                e.setPosition(((Boundary)e).mapPos.plus(mapPos));
            if(e instanceof hDoor)
                e.setPosition(((hDoor)e).mapPos.plus(mapPos));
            if(e instanceof vDoor)
                e.setPosition(((vDoor)e).mapPos.plus(mapPos));
            if(e instanceof Person)
                e.setPosition(((Person)e).mapPos.plus(mapPos));
        }
    }

    private void moveLeft()
    {
        if(!menuBackground.isVisible())
        {
            mapObject.map.setPosition(mapObject.map.getPosition().plus(new Vector(speed, 0)));
            updateEntities();
        }
    }

    private void moveRight()
    {
        if(!menuBackground.isVisible())
        {
            mapObject.map.setPosition(mapObject.map.getPosition().plus(new Vector(-speed, 0)));
            updateEntities();
        }
    }

    private void moveDown()
    {
        if(!menuBackground.isVisible())
        {
            mapObject.map.setPosition(mapObject.map.getPosition().plus(new Vector(0, -speed)));
            updateEntities();
        }
    }

    private void moveUp()
    {
        if(!menuBackground.isVisible())
        {
            mapObject.map.setPosition(mapObject.map.getPosition().plus(new Vector(0, speed)));
            updateEntities();
        }
    }

    private boolean up, down, left, right;

    public boolean textBoxFocused;

    @Override
    public void keyReleased(KeyEvent e)
    {
        if(textBoxFocused)
        {
            if(textBox.getText().length() < 9)
            {
                if(KeyEvent.VK_NUMPAD1 == e.getKeyCode())
                {
                    textBox.setText(textBox.getText() + "1");
                }
                if(KeyEvent.VK_NUMPAD2 == e.getKeyCode())
                {
                    textBox.setText(textBox.getText() + "2");
                }
                if(KeyEvent.VK_NUMPAD3 == e.getKeyCode())
                {
                    textBox.setText(textBox.getText() + "3");
                }
                if(KeyEvent.VK_NUMPAD4 == e.getKeyCode())
                {
                    textBox.setText(textBox.getText() + "4");
                }
                if(KeyEvent.VK_NUMPAD5 == e.getKeyCode())
                {
                    textBox.setText(textBox.getText() + "5");
                }
                if(KeyEvent.VK_NUMPAD6 == e.getKeyCode())
                {
                    textBox.setText(textBox.getText() + "6");
                }
                if(KeyEvent.VK_NUMPAD7 == e.getKeyCode())
                {
                    textBox.setText(textBox.getText() + "7");
                }
                if(KeyEvent.VK_NUMPAD8 == e.getKeyCode())
                {
                    textBox.setText(textBox.getText() + "8");
                }
                if(KeyEvent.VK_NUMPAD9 == e.getKeyCode())
                {
                    textBox.setText(textBox.getText() + "9");
                }
                if(KeyEvent.VK_NUMPAD0 == e.getKeyCode())
                {
                    textBox.setText(textBox.getText() + "0");
                }
            }
            if(KeyEvent.VK_BACK_SPACE == e.getKeyCode())
            {
                textBox.setText(textBox.getText().substring(0,textBox.getText().length()-1));
            }
            if(KeyEvent.VK_C == e.getKeyCode() && this.getEntityController(StickEntityController.class).player.car)
            {
                this.driving = !this.driving;
            }
            if(!textBox.getText().equals(""))
                textBox.setText(NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(textBox.getText().replace(",", ""))));
        }
        if(KeyEvent.VK_UP == e.getKeyCode() || KeyEvent.VK_W == e.getKeyCode())
        {
            up = false;
        }
        if(KeyEvent.VK_DOWN == e.getKeyCode() || KeyEvent.VK_S == e.getKeyCode())
        {
            down = false;
        }
        if(KeyEvent.VK_LEFT == e.getKeyCode() || KeyEvent.VK_A == e.getKeyCode())
        {
            left = false;
        }
        if(KeyEvent.VK_RIGHT == e.getKeyCode() || KeyEvent.VK_D == e.getKeyCode())
        {
            right = false;
        }
        if(KeyEvent.VK_SHIFT == e.getKeyCode()) {
            this.skating = false;
        }
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if(KeyEvent.VK_UP == e.getKeyCode() || KeyEvent.VK_W == e.getKeyCode())
        {
            up = true;
        }
        if(KeyEvent.VK_DOWN == e.getKeyCode() || KeyEvent.VK_S == e.getKeyCode())
        {
            down = true;
        }
        if(KeyEvent.VK_LEFT == e.getKeyCode() || KeyEvent.VK_A == e.getKeyCode())
        {
            left = true;
        }
        if(KeyEvent.VK_RIGHT == e.getKeyCode() || KeyEvent.VK_D == e.getKeyCode())
        {
            right = true;
        }
        if(KeyEvent.VK_SHIFT == e.getKeyCode()) {
            this.skating = true;
        }
    }
}