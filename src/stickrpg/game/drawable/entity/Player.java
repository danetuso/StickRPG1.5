package stickrpg.game.drawable.entity;

import jtwod.engine.Scene;
import jtwod.engine.drawable.Entity;
import jtwod.engine.metrics.Vector;
import stickrpg.game.core.StickEngine;
import stickrpg.game.core.StickEntityController;
import stickrpg.game.core.StickScene;
import stickrpg.game.util.DoorTypeEnum;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class Player extends Entity<StickEngine>
{
    private Vector lastKnownPos;

    //temporary stats
    public int money;
    public int strength;
    public int intelligence;
    public int charm;
    public int health;
    public int karma;

    //temporary inventory
    public boolean skateboard;
    public int smokes;
    public int pills;
    public boolean alarm;
    public int bottles;
    public boolean gun;
    public boolean knife;
    public boolean cell;
    public int coke;
    public int bullets;
    public boolean car;

    public boolean gCoins;

    //house
    public boolean bed;
    public boolean computer;
    public boolean tv;
    public boolean satellite;
    public boolean ac;
    public boolean minibar;
    public boolean pedia;
    public boolean treadmill;

    public boolean sHome;
    public boolean bApt;
    public boolean penthouse;
    public boolean mansion;
    public boolean castle;

    //job
    public int corpTier;

    //environment
    public int dayTime;
    private int dayTimeMax;
    public int day;

    //interest rate - change daily
    public double intrate;
    public int bankMoney;
    public int loan;
    public int loanday;


    public Player(Vector position, Scene<StickEngine> scene)
    {
        super(position, scene.getParentEngine().getTextureGroup().getTexture("Player"), scene);

        //temporary stats
        this.money = 1000000;
        this.strength = 200;
        this.intelligence = 200;
        this.charm = 250;
        this.health = 200;
        this.karma = 20;

        //temporary inventory
        this.smokes = 0;
        this.skateboard = false;
        this.pills = 2;
        this.alarm = false;
        this.bottles = 12;
        this.gun = false;
        this.knife = false;
        this.cell = false;
        this.coke = 6;
        this.bullets = 6;

        this.gCoins = false;

        //house
        this.bed = false;
        this.computer = false;
        this.tv = false;
        this.satellite = false;
        this.ac = false;
        this.minibar = false;
        this.treadmill = false;
        this.pedia = false;

        //home
        this.sHome = true;
        this.bApt = false;
        this.penthouse = false;
        this.mansion = false;
        this.castle = false;

        //job
        this.corpTier = 0;

        //environment
        this.dayTime = 8;
        this.dayTimeMax = 24;
        this.day = 0;

        this.intrate = 3.1;
        this.bankMoney = 0;
        this.loan = 0;
        this.loanday = -1;

        ((StickScene)this.getParentScene()).healthText.setText(this.health + " / " + this.strength);
        ((StickScene)this.getParentScene()).moneyText.setText(NumberFormat.getNumberInstance(Locale.US).format(this.money));
    }

    public final void addMoney(int amount)
    {
        this.money += amount;
        ((StickScene)this.getParentScene()).moneyText.setText(NumberFormat.getNumberInstance(Locale.US).format(this.money));
    }

    public final void setMoney(int amount)
    {
        this.money = amount;
        ((StickScene)this.getParentScene()).moneyText.setText(NumberFormat.getNumberInstance(Locale.US).format(this.money));
    }

    public final String getJobString()
    {
        if(corpTier == 0)
            return "Cook";
        else if(corpTier == 1)
            return "Janitor";
        else if(corpTier == 2)
            return "Mail Room Clerk";
        else if(corpTier == 3)
            return "Sales Person";
        else if(corpTier == 4)
            return "Executive";
        else if(corpTier == 5)
            return "Vice President";
        else if(corpTier == 6)
            return "CEO";
        else
            return "";
    }

    public final void sleep()
    {
        int h = 0;
        if(this.bed)
            h += 10;
        if(this.ac)
            h += 10;
        if(this.treadmill)
            addStrength(1);
        if(this.pedia)
            this.intelligence++;
        if(this.minibar)
            this.charm++;
        if(pills > 0)
            h = 0;
        addHealth(h);
        this.day++;
        this.dayTime = getWakeUpTime();
        ((StickScene)this.getParentScene()).dayText.setText("Day " + day);
        updateTimeText();
        this.bankMoney += this.bankMoney * this.intrate * 0.01;
        this.loan += this.loan * this.intrate * 0.01;
        double randomNum = ThreadLocalRandom.current().nextDouble(1,4);
        this.intrate = randomNum;
    }

    private int getWakeUpTime()
    {
        int start = 8;
        if(pills >= 1 && !alarm)
        {
            pills--;
            start -= 2;
        }
        if(alarm && pills < 1)
            start -=2;
        if(alarm && pills > 0)
        {
            pills--;
            start = 0;
        }
        return start;
    }

    public final int addTime(int t)
    {
        if(this.dayTime + t <= dayTimeMax)
        {
            this.dayTime += t;
            updateTimeText();
            return this.dayTime;
        }
        else
            return -1;
    }

    private void updateTimeText()
    {
        if(dayTime < 10)
            ((StickScene)this.getParentScene()).timeText.setText("0" + dayTime + ":00 AM");
        else if(dayTime < 13)
            ((StickScene)this.getParentScene()).timeText.setText(dayTime + ":00 AM");
        else if(dayTime < 22)
            ((StickScene)this.getParentScene()).timeText.setText("0" + (dayTime - 12) + ":00 PM");
        else if(dayTime < 24)
            ((StickScene)this.getParentScene()).timeText.setText((dayTime - 12) + ":00 PM");
        else if(dayTime == 24)
            ((StickScene)this.getParentScene()).timeText.setText((dayTime - 12) + ":00 AM");
    }

    public final void addStrength(int s)
    {
        this.strength += s;
        ((StickScene)this.getParentScene()).healthText.setText(this.health + " / " + this.strength);
    }

    public final void addHealth(int health)
    {
        if(this.health + health <= this.strength)
            this.health += health;
        else
            this.health = this.strength;
        ((StickScene)this.getParentScene()).healthText.setText(this.health + " / " + this.strength);
    }

    @Override
    public final void update()
    {
        Boundary b = this.getParentScene().getEntityController(StickEntityController.class).boundaries.get(0);
        if(!this.isCollidingWith(b))
            lastKnownPos = ((StickScene)this.getParentScene()).mapObject.map.getPosition();
    }

    @Override
    public final void onCollide(Entity<StickEngine> entity) {
        if (entity instanceof Boundary)
        {
            ((StickScene)this.getParentScene()).moveAllTo(lastKnownPos.getX(), lastKnownPos.getY());
        }
        else if(entity instanceof hDoor)
        {
            ((StickScene)this.getParentScene()).toggleMenuBackground(((hDoor)entity).type);
            ((StickScene)this.getParentScene()).moveAllTo(lastKnownPos.getX(), lastKnownPos.getY());
        }
        else if(entity instanceof vDoor)
        {
            if(((vDoor) entity).type == DoorTypeEnum.CASTLE || ((vDoor) entity).type == DoorTypeEnum.MANSION)
            {
                if(castle)
                {
                    ((StickScene)this.getParentScene()).toggleMenuBackground(DoorTypeEnum.CASTLE);
                }
                else if(mansion)
                {
                    ((StickScene)this.getParentScene()).toggleMenuBackground(DoorTypeEnum.MANSION);
                }
                ((StickScene)this.getParentScene()).moveAllTo(lastKnownPos.getX(), lastKnownPos.getY());
            }
            else
            {
                ((StickScene)this.getParentScene()).toggleMenuBackground(((vDoor)entity).type);
                ((StickScene)this.getParentScene()).moveAllTo(lastKnownPos.getX(), lastKnownPos.getY());
            }

        }
    }

    @Override
    protected void mouseClicked(int button, Vector location){}
}
