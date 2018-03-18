package stickrpg.game.core;

import jtwod.engine.EntityController;
import jtwod.engine.Scene;
import jtwod.engine.drawable.Entity;
import jtwod.engine.metrics.Vector;
import stickrpg.game.util.ButtonTypeEnum;
import stickrpg.game.util.DoorTypeEnum;
import stickrpg.game.drawable.StickBasicMap;
import stickrpg.game.drawable.entity.*;

import java.util.ArrayList;

public class StickEntityController extends EntityController<StickEngine>
{
    public Player player;

    private ArrayList<Person> people;
    private ArrayList<hDoor> hDoors;
    public ArrayList<vDoor> vDoors;
    public ArrayList<Boundary> boundaries;

    //Top Layer Buttons
    public TopLayerButton inventory;
    public TopLayerButton stats;

    public Person mansionBuilding;
    public Person castleBuilding;

    //Menu Buttons
    //exit
    public MenuButton eb;
    public MenuButton ok;
    //store
    public MenuButton pills;
    public MenuButton nachos;
    public MenuButton candy;
    public MenuButton slushee;
    public MenuButton buySmokes;
    //kid
    public MenuButton giveSmokes;
    //school
    public MenuButton gym;
    public MenuButton study;
    public MenuButton sclass;
    //home
    public MenuButton sleep;
    public MenuButton phone;
    //bar
    public MenuButton drinkB;
    public MenuButton buyB;
    //corp
    public MenuButton work;
    public MenuButton apply;
    //homeless guy
    public MenuButton coins;
    //pawn
    public MenuButton gun;
    public MenuButton knife;
    public MenuButton alarm;
    public MenuButton cell;
    public MenuButton bullets;
    //bank
    public MenuButton bhouse;
    public MenuButton loanAccept;
    public MenuButton deposit;
    public MenuButton withdraw;
    public MenuButton loan;
    public MenuButton bApt;
    public MenuButton penthouse;
    public MenuButton mansion;
    public MenuButton castle;
    //coke dealer
    public MenuButton coke;
    //mcd
    public MenuButton shake;
    public MenuButton fries;
    public MenuButton cheeseBurger;
    public MenuButton tripleCheese;
    //fstore
    public MenuButton bed;
    public MenuButton computer;
    public MenuButton tv;
    public MenuButton satellite;
    public MenuButton ac;
    public MenuButton treadmill;
    public MenuButton minibar;
    public MenuButton pedia;
    //BUS
    public MenuButton NY;
    public MenuButton MI;
    public MenuButton CA;
    public MenuButton IL;
    public MenuButton NJ;
    public MenuButton NV;
    public MenuButton accept;

    public MenuButton wNews;
    public MenuButton wFitness;
    public MenuButton wDating;

    public MenuButton rob;

    //Inventory
    public MenuButton goHouse;

    public StickEntityController(Scene<StickEngine> scene)
    {
        super(scene);

        player = new Player(new Vector(this.getParentEngine().getWindowSize().getWidth() / 2
                - (this.getParentEngine().playerWidth / 2),
                    this.getParentEngine().getWindowSize().getHeight() / 2
                        - (this.getParentEngine().playerWidth / 2)),
                scene);
        this.spawnEntity(player);
        player.setVisible(false);

        initPeople();
        initDoors();
        initBoundaries();
        initButtons();
        mansionBuilding = new Person(
                mapToScreenCoords(786,135),
                this.getParentEngine().getTextureGroup().getTexture("MansionBuilding"),
                this.getParentScene(),
                new Vector(786,135),
                DoorTypeEnum.MANSION
        );
        this.spawnEntity(mansionBuilding);
        mansionBuilding.setVisible(false);

        castleBuilding = new Person(
                mapToScreenCoords(654,17),
                this.getParentEngine().getTextureGroup().getTexture("CastleBuilding"),
                this.getParentScene(),
                new Vector(654,17),
                DoorTypeEnum.CASTLE
        );
        this.spawnEntity(castleBuilding);
        castleBuilding.setVisible(false);
    }

    public final void addToMenuButtonsPos(Vector v)
    {
        for(Entity<StickEngine> e : getAllEntities())
            if(e instanceof MenuButton)
            {
                e.setPosition(e.getPosition().plus(v));
                if(((MenuButton) e).bt != null)
                    ((MenuButton) e).bt.setPosition(((MenuButton) e).bt.getPosition().plus(v).plusY(5));
                if(((MenuButton) e).bt2 != null)
                    ((MenuButton) e).bt2.setPosition(((MenuButton) e).bt2.getPosition().plus(v).plusY(5));
            }
    }

    public final void displayButtonsForDoor(DoorTypeEnum type)
    {
        for(MenuButton b : type.getDoorButtons(this, type))
            b.setVisible(true);
    }

    private void initPeople()
    {
        people = new ArrayList<>();
        people.add(new Person(
                mapToScreenCoords(966,354),
                this.getParentEngine().getTextureGroup().getTexture("Person"),
                this.getParentScene(),
                new Vector(966,354),
                DoorTypeEnum.KID
        ));
        people.add(new Person(
                mapToScreenCoords(950,1007),
                this.getParentEngine().getTextureGroup().getTexture("Person"),
                this.getParentScene(),
                new Vector(950,1007),
                DoorTypeEnum.HOMELESS
        ));
        people.add(new Person(
                mapToScreenCoords(1361,1561),
                this.getParentEngine().getTextureGroup().getTexture("Person"),
                this.getParentScene(),
                new Vector(1361,1561),
                DoorTypeEnum.DEALER
        ));
        for(Person p : people)
            this.spawnEntity(p);
    }

    private void initButtons()
    {
        inventory = new TopLayerButton(
                new Vector(this.getParentEngine().getWindowSize().getWidth()
                        - this.getParentEngine().getTextureGroup().getTexture("Stats").getWidth()
                        - this.getParentEngine().getTextureGroup().getTexture("Inventory").getWidth() - 20, 0),
                this.getParentEngine().getTextureGroup().getTexture("Inventory"),
                this.getParentScene(),
                0
        );
        this.spawnEntity(inventory);
        inventory.setVisible(false);

        stats = new TopLayerButton(
                new Vector(this.getParentEngine().getWindowSize().getWidth()
                        - this.getParentEngine().getTextureGroup().getTexture("Stats").getWidth() - 10, 0),
                this.getParentEngine().getTextureGroup().getTexture("Stats"),
                this.getParentScene(),
                1
        );
        this.spawnEntity(stats);
        stats.setVisible(false);

        rob = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(11,3),
                this.getParentEngine().spriteSheet.getTexture(12,3),
                this.getParentScene(),
                ButtonTypeEnum.ROB,
                8,
                8
        );
        this.spawnEntity(rob);

        //exit button
        eb = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(1,1),
                this.getParentEngine().spriteSheet.getTexture(2,1),
                this.getParentScene(),
                ButtonTypeEnum.EXIT,
                8
        );
        this.spawnEntity(eb);

        ok = new MenuButton(
                this.getParentEngine().getTextureGroup().getTexture("OK"),
                this.getParentEngine().getTextureGroup().getTexture("OK"),
                this.getParentScene(),
                ButtonTypeEnum.OK,
                8
        );
        ok.setPosition(new Vector(
                ((StickScene)this.getParentScene()).menuBackground.getPosition().getX()
                        + ((StickScene)this.getParentScene()).menuBackground.getDimensions().getWidth() / 2 + 65,
                ((StickScene)this.getParentScene()).menuBackground.getPosition().getY()
                        + ((StickScene)this.getParentScene()).menuBackground.getDimensions().getHeight() - 50
        ));
        this.spawnEntity(ok);
        //bus
        accept = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(11,2),
                this.getParentEngine().spriteSheet.getTexture(12,2),
                this.getParentScene(),
                ButtonTypeEnum.ACCEPT,
                4
        );
        this.spawnEntity(accept);
        NY = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(3,6),
                this.getParentEngine().spriteSheet.getTexture(4,6),
                this.getParentScene(),
                ButtonTypeEnum.NY,
                1,
                24,
                "Sell Commodities",
                "$115 Brooklyn, NY"
        );
        this.spawnEntity(NY);
        MI = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(3,6),
                this.getParentEngine().spriteSheet.getTexture(4,6),
                this.getParentScene(),
                ButtonTypeEnum.MI,
                2,
                24,
                "Sell Commodities",
                "$100 Detroit, MI"
        );
        this.spawnEntity(MI);
        CA = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(3,6),
                this.getParentEngine().spriteSheet.getTexture(4,6),
                this.getParentScene(),
                ButtonTypeEnum.CA,
                3,
                24,
                "Sell Commodities",
                "$100 Los Angeles, CA"
        );
        this.spawnEntity(CA);
        IL = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(3,6),
                this.getParentEngine().spriteSheet.getTexture(4,6),
                this.getParentScene(),
                ButtonTypeEnum.IL,
                5,
                24,
                "Sell Commodities",
                "$115 Chicago, IL"
        );
        this.spawnEntity(IL);
        NJ = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(3,6),
                this.getParentEngine().spriteSheet.getTexture(4,6),
                this.getParentScene(),
                ButtonTypeEnum.NJ,
                6,
                24,
                "Sell Commodities",
                "$130 Camden, NJ"
        );
        this.spawnEntity(NJ);
        NV = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(3,6),
                this.getParentEngine().spriteSheet.getTexture(4,6),
                this.getParentScene(),
                ButtonTypeEnum.NV,
                7,
                24,
                "Sell Commodities",
                "$130 Las Vegas, NV"
        );
        this.spawnEntity(NV);
        //mcd
        shake = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(1,3),
                this.getParentEngine().spriteSheet.getTexture(2,3),
                this.getParentScene(),
                ButtonTypeEnum.SHAKE,
                1,
                "Shake",
                "$8 (+12 HP)"
        );
        this.spawnEntity(shake);

        fries = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(3,3),
                this.getParentEngine().spriteSheet.getTexture(4,3),
                this.getParentScene(),
                ButtonTypeEnum.FRIES,
                2,
                "Fries",
                "$12 (+20 HP)"
        );
        this.spawnEntity(fries);

        cheeseBurger = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(5,3),
                this.getParentEngine().spriteSheet.getTexture(6,3),
                this.getParentScene(),
                ButtonTypeEnum.CHEESEBURGER,
                3,
                "Cheese Burger",
                "$25 (+40 HP)"
        );
        this.spawnEntity(cheeseBurger);

        tripleCheese = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(7,3),
                this.getParentEngine().spriteSheet.getTexture(8,3),
                this.getParentScene(),
                ButtonTypeEnum.TRIPLECHEESE,
                4,
                "Triple Burger",
                "$50 (+80 HP)"
        );
        this.spawnEntity(tripleCheese);

        //fstore
        bed = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(11,5),
                this.getParentEngine().spriteSheet.getTexture(12,5),
                this.getParentScene(),
                ButtonTypeEnum.BED,
                1,
                "BED",
                "$500"
        );
        this.spawnEntity(bed);

        computer = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(9,6),
                this.getParentEngine().spriteSheet.getTexture(10,6),
                this.getParentScene(),
                ButtonTypeEnum.COMPUTER,
                2,
                "Computer",
                "$2,000"
        );
        this.spawnEntity(computer);

        tv = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(7,6),
                this.getParentEngine().spriteSheet.getTexture(8,6),
                this.getParentScene(),
                ButtonTypeEnum.TV,
                3,
                2,
                "TV",
                "$2,500 (+2 INT)"
        );
        this.spawnEntity(tv);

        wNews = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(7,6),
                this.getParentEngine().spriteSheet.getTexture(8,6),
                this.getParentScene(),
                ButtonTypeEnum.WNEWS,
                5,
                2,
                "News Channel"
        );
        this.spawnEntity(wNews);

        wFitness = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(7,6),
                this.getParentEngine().spriteSheet.getTexture(8,6),
                this.getParentScene(),
                ButtonTypeEnum.WFITNESS,
                6,
                2,
                "Fitness channel"
        );
        this.spawnEntity(wFitness);

        wDating = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(7,6),
                this.getParentEngine().spriteSheet.getTexture(8,6),
                this.getParentScene(),
                ButtonTypeEnum.WDATING,
                7,
                2,
                "Dating Channel"
        );
        this.spawnEntity(wDating);

        satellite = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(5,5),
                this.getParentEngine().spriteSheet.getTexture(6,5),
                this.getParentScene(),
                ButtonTypeEnum.SATELLITE,
                4,
                "Satellite TV",
                "$3,000"
        );
        this.spawnEntity(satellite);

        pedia = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(3,5),
                this.getParentEngine().spriteSheet.getTexture(4,5),
                this.getParentScene(),
                ButtonTypeEnum.PEDIA,
                5,
                "Stick-O-Pedia",
                "$2,000 (+1 INT/Sleep)"
        );
        this.spawnEntity(pedia);

        treadmill = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(7,5),
                this.getParentEngine().spriteSheet.getTexture(8,5),
                this.getParentScene(),
                ButtonTypeEnum.TREADMILL,
                6,
                "A/C",
                "$3,500 (+11 STR/Sleep)"
        );
        this.spawnEntity(treadmill);

        ac = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(9,5),
                this.getParentEngine().spriteSheet.getTexture(10,5),
                this.getParentScene(),
                ButtonTypeEnum.AC,
                7,
                "A/C",
                "$2,500 (+10 HP/Sleep)"
        );
        this.spawnEntity(ac);

        minibar = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(1,5),
                this.getParentEngine().spriteSheet.getTexture(2,5),
                this.getParentScene(),
                ButtonTypeEnum.MINIBAR,
                8,
                "MiniBar",
                "$2,000 (+1 Chm/Sleep)"
        );
        this.spawnEntity(minibar);

        //bank
        loanAccept = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(11,2),
                this.getParentEngine().spriteSheet.getTexture(12,2),
                this.getParentScene(),
                ButtonTypeEnum.LOANACCEPT,
                4,
                "Accept"
        );
        this.spawnEntity(loanAccept);

        bhouse = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(5,7),
                this.getParentEngine().spriteSheet.getTexture(6,7),
                this.getParentScene(),
                ButtonTypeEnum.BHOUSE,
                4
        );
        this.spawnEntity(bhouse);

        deposit = new MenuButton(
                this.getParentEngine().getTextureGroup().getTexture("Deposit"),
                this.getParentEngine().getTextureGroup().getTexture("Deposit"),
                this.getParentScene(),
                ButtonTypeEnum.DEPOSIT,
                2
        );
        this.spawnEntity(deposit);

        withdraw = new MenuButton(
                this.getParentEngine().getTextureGroup().getTexture("Withdraw"),
                this.getParentEngine().getTextureGroup().getTexture("Withdraw"),
                this.getParentScene(),
                ButtonTypeEnum.WITHDRAW,
                6
        );
        this.spawnEntity(withdraw);

        loan = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(11,6),
                this.getParentEngine().spriteSheet.getTexture(12,6),
                this.getParentScene(),
                ButtonTypeEnum.LOAN,
                4
        );
        this.spawnEntity(loan);

        bApt = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(3,6),
                this.getParentEngine().spriteSheet.getTexture(4,6),
                this.getParentScene(),
                ButtonTypeEnum.BAPT,
                2,
                "Bigger Apt",
                "$25,000"
        );
        this.spawnEntity(bApt);

        penthouse = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(3,6),
                this.getParentEngine().spriteSheet.getTexture(4,6),
                this.getParentScene(),
                ButtonTypeEnum.PENTHOUSE,
                3,
                "Penthouse",
                "$50,000"
        );
        this.spawnEntity(penthouse);

        mansion = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(3,6),
                this.getParentEngine().spriteSheet.getTexture(4,6),
                this.getParentScene(),
                ButtonTypeEnum.MANSION,
                6,
                "Mansion",
                "$100,000"
        );
        this.spawnEntity(mansion);

        castle = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(3,6),
                this.getParentEngine().spriteSheet.getTexture(4,6),
                this.getParentScene(),
                ButtonTypeEnum.CASTLE,
                7,
                "Castle",
                "$500,000"
        );
        this.spawnEntity(castle);

        //inventory
        goHouse = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(3,6),
                this.getParentEngine().spriteSheet.getTexture(4,6),
                this.getParentScene(),
                ButtonTypeEnum.GOHOUSE,
                4,
                "Go Home"
        );
        this.spawnEntity(goHouse);

        //bar
        drinkB = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(7,2),
                this.getParentEngine().spriteSheet.getTexture(8,2),
                this.getParentScene(),
                ButtonTypeEnum.DRINKB,
                1,
                2,
                "Drink Beer",
                "$20 (+2 Charm)"
        );
        this.spawnEntity(drinkB);

        buyB = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(9,2),
                this.getParentEngine().spriteSheet.getTexture(10,2),
                this.getParentScene(),
                ButtonTypeEnum.BUYB,
                5,
                "Buy Bottle",
                "$30"
        );
        this.spawnEntity(buyB);

        //home
        phone = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(3,7),
                this.getParentEngine().spriteSheet.getTexture(4,7),
                this.getParentScene(),
                ButtonTypeEnum.PHONE,
                1,
                "Check Messages"
        );
        this.spawnEntity(phone);

        sleep = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(1,7),
                this.getParentEngine().spriteSheet.getTexture(2,7),
                this.getParentScene(),
                ButtonTypeEnum.SLEEP,
                7,
                "Sleep"
        );
        this.spawnEntity(sleep);

        //corp
        apply = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(5,6),
                this.getParentEngine().spriteSheet.getTexture(6,6),
                this.getParentScene(),
                ButtonTypeEnum.APPLY,
                5,
                "Apply",
                ""
        );
        this.spawnEntity(apply);

        work = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(11,2),
                this.getParentEngine().spriteSheet.getTexture(12,2),
                this.getParentScene(),
                ButtonTypeEnum.WORK,
                6,
                6,
                "Work",
                ""
        );
        this.spawnEntity(work);

        //homeless guy
        coins = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(11,4),
                this.getParentEngine().spriteSheet.getTexture(12,4),
                this.getParentScene(),
                ButtonTypeEnum.COINS,
                4,
                "Give Coins"
        );
        this.spawnEntity(coins);

        //pawn
        gun = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(11,3),
                this.getParentEngine().spriteSheet.getTexture(12,3),
                this.getParentScene(),
                ButtonTypeEnum.GUN,
                2,
                "Hand Gun",
                "$400"
        );
        this.spawnEntity(gun);

        knife = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(7,4),
                this.getParentEngine().spriteSheet.getTexture(8,4),
                this.getParentScene(),
                ButtonTypeEnum.KNIFE,
                3,
                "Knife",
                "$100"
        );
        this.spawnEntity(knife);

        alarm = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(9,4),
                this.getParentEngine().spriteSheet.getTexture(10,4),
                this.getParentScene(),
                ButtonTypeEnum.ALARM,
                4,
                "Alarm Clock",
                "$200"
        );
        this.spawnEntity(alarm);

        cell = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(3,4),
                this.getParentEngine().spriteSheet.getTexture(4,4),
                this.getParentScene(),
                ButtonTypeEnum.CELL,
                5,
                "Cell Phone",
                "$200"
        );
        this.spawnEntity(cell);

        bullets = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(1,4),
                this.getParentEngine().spriteSheet.getTexture(2,4),
                this.getParentScene(),
                ButtonTypeEnum.BULLETS,
                1,
                "Ammo",
                "$10 (+5 Bullets)"

        );
        this.spawnEntity(bullets);

        //coke dealer
        coke = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(1,6),
                this.getParentEngine().spriteSheet.getTexture(2,6),
                this.getParentScene(),
                ButtonTypeEnum.COKE,
                4,
                "Buy Coke",
                "$400"
        );
        this.spawnEntity(coke);

        //kid
        giveSmokes = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(7,1),
                this.getParentEngine().spriteSheet.getTexture(8,1),
                this.getParentScene(),
                ButtonTypeEnum.GIVESMOKES,
                1,
                "Give Smokes",
                "(+1 Skateboard)"
        );
        this.spawnEntity(giveSmokes);

        //school
        gym = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(5,2),
                this.getParentEngine().spriteSheet.getTexture(6,2),
                this.getParentScene(),
                ButtonTypeEnum.GYM,
                5,
                1,
                "Gym",
                "(+1 Strength)"
        );
        this.spawnEntity(gym);

        sclass = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(3,2),
                this.getParentEngine().spriteSheet.getTexture(4,2),
                this.getParentScene(),
                ButtonTypeEnum.SCLASS,
                2,
                2,
                "Class",
                "$2 (+2 Intelligence)"
        );
        this.spawnEntity(sclass);

        study = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(1,2),
                this.getParentEngine().spriteSheet.getTexture(2,2),
                this.getParentScene(),
                ButtonTypeEnum.STUDY,
                1,
                1,
                "Study",
                "(+1 Intelligence)"
        );
        this.spawnEntity(study);

        //store
        buySmokes = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(7,1),
                this.getParentEngine().spriteSheet.getTexture(8,1),
                this.getParentScene(),
                ButtonTypeEnum.ADDSMOKES,
                6,
                "Smokes",
                "$10"
        );
        this.spawnEntity(buySmokes);

        pills = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(9,1),
                this.getParentEngine().spriteSheet.getTexture(10,1),
                this.getParentScene(),
                ButtonTypeEnum.PILLS,
                7,
                "Caffeine Pills",
                "$45"

        );
        this.spawnEntity(pills);

        nachos = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(5,1),
                this.getParentEngine().spriteSheet.getTexture(6,1),
                this.getParentScene(),
                ButtonTypeEnum.NACHOS,
                4,
                "Nachos",
                "$4 (+7 HP)"
        );
        this.spawnEntity(nachos);

        candy = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(11,1),
                this.getParentEngine().spriteSheet.getTexture(12,1),
                this.getParentScene(),
                ButtonTypeEnum.CANDY,
                3,
                "Candy Bar",
                "$3 (+3 HP)"
        );
        this.spawnEntity(candy);

        slushee = new MenuButton(
                this.getParentEngine().spriteSheet.getTexture(3,1),
                this.getParentEngine().spriteSheet.getTexture(4,1),
                this.getParentScene(),
                ButtonTypeEnum.SLUSHEE,
                2,
                "Slushee",
                "$1 (+1 HP)"
        );
        this.spawnEntity(slushee);
    }

    private void initDoors()
    {
        hDoors = new ArrayList<>();
        vDoors = new ArrayList<>();
        hDoors.add(new hDoor(mapToScreenCoords(
                387,332), this.getParentScene(),
                new Vector(387,332),
                DoorTypeEnum.HOME
        ));
        hDoors.add(new hDoor(mapToScreenCoords(
                1881,803), this.getParentScene(),
                new Vector(1871,803),
                DoorTypeEnum.SCHOOL
        ));
        hDoors.add(new hDoor(mapToScreenCoords(
                1955,1183), this.getParentScene(),
                new Vector(1955, 1183),
                DoorTypeEnum.BUS
        ));
        hDoors.add(new hDoor(mapToScreenCoords(
                537,772), this.getParentScene(),
                new Vector(537,772),
                DoorTypeEnum.FSTORE
        ));
        vDoors.add(new vDoor(mapToScreenCoords(
                1003,238), this.getParentScene(),
                new Vector(1003,238),
                DoorTypeEnum.CASTLE
        ));
        vDoors.add(new vDoor(mapToScreenCoords(
                1357,374), this.getParentScene(),
                new Vector(1357,374),
                DoorTypeEnum.BANK
        ));
        vDoors.add(new vDoor(mapToScreenCoords(
                1357,630), this.getParentScene(),
                new Vector(1357,630),
                DoorTypeEnum.CORP
        ));
        vDoors.add(new vDoor(mapToScreenCoords(
                1357,1332), this.getParentScene(),
                new Vector(1357,1332),
                DoorTypeEnum.STORE
        ));
        vDoors.add(new vDoor(mapToScreenCoords(
                1357,1656), this.getParentScene(),
                new Vector(1357,1656),
                DoorTypeEnum.PAWN
        ));
        vDoors.add(new vDoor(mapToScreenCoords(
                932,1628), this.getParentScene(),
                new Vector(932,1628),
                DoorTypeEnum.CASINO
        ));
        vDoors.add(new vDoor(mapToScreenCoords(
                950,1063), this.getParentScene(),
                new Vector(950,1063),
                DoorTypeEnum.BAR
        ));
        vDoors.add(new vDoor(
                mapToScreenCoords(997,807),
                this.getParentScene(),
                new Vector(997,812),
                DoorTypeEnum.MCD
        ));
        for(vDoor vd : vDoors)
        {
            vd.setPositionConstraint(null,null);
            this.spawnEntity(vd);
        }
        for(hDoor hd : hDoors)
        {
            hd.setPositionConstraint(null,null);
            this.spawnEntity(hd);
        }
    }

    private void initBoundaries()
    {
        boundaries = new ArrayList<>();
        boundaries.add(new Boundary(
                mapToScreenCoords(1357,418),
                this.getParentEngine().getTextureGroup().getTexture("b1"),
                this.getParentScene(),
                new Vector(1357,418)
        ));
        boundaries.add(new Boundary(
                mapToScreenCoords(230,141),
                this.getParentEngine().getTextureGroup().getTexture("b2"),
                this.getParentScene(),
                new Vector(230,141)
        ));
        boundaries.add(new Boundary(
                mapToScreenCoords(429,280),
                this.getParentEngine().getTextureGroup().getTexture("b3"),
                this.getParentScene(),
                new Vector(429,280)
        ));
        boundaries.add(new Boundary(
                mapToScreenCoords(1897,802),
                this.getParentEngine().getTextureGroup().getTexture("b4"),
                this.getParentScene(),
                new Vector(1897,802)
        ));
        boundaries.add(new Boundary(
                mapToScreenCoords(1357,686),
                this.getParentEngine().getTextureGroup().getTexture("b5"),
                this.getParentScene(),
                new Vector(1357,686)
        ));
        boundaries.add(new Boundary(
                mapToScreenCoords(1359,1177),
                this.getParentEngine().getTextureGroup().getTexture("b6"),
                this.getParentScene(),
                new Vector(1359,1177)
        ));
        boundaries.add(new Boundary(
                mapToScreenCoords(1358,1403),
                this.getParentEngine().getTextureGroup().getTexture("b7"),
                this.getParentScene(),
                new Vector(1358,1403)
        ));
        boundaries.add(new Boundary(
                mapToScreenCoords(1359,1701),
                this.getParentEngine().getTextureGroup().getTexture("b8"),
                this.getParentScene(),
                new Vector(1359,1701)
        ));
        boundaries.add(new Boundary(
                mapToScreenCoords(228,747),
                this.getParentEngine().getTextureGroup().getTexture("b9"),
                this.getParentScene(),
                new Vector(228,747)
        ));
        boundaries.add(new Boundary(
                mapToScreenCoords(586,748),
                this.getParentEngine().getTextureGroup().getTexture("b10"),
                this.getParentScene(),
                new Vector(586,748)
        ));
        boundaries.add(new Boundary(
                mapToScreenCoords(921,867),
                this.getParentEngine().getTextureGroup().getTexture("b11"),
                this.getParentScene(),
                new Vector(921,867)
        ));
        boundaries.add(new Boundary(
                mapToScreenCoords(932,1106),
                this.getParentEngine().getTextureGroup().getTexture("b12"),
                this.getParentScene(),
                new Vector(932,1106)
        ));
        boundaries.add(new Boundary(
                mapToScreenCoords(932,1683),
                this.getParentEngine().getTextureGroup().getTexture("b13"),
                this.getParentScene(),
                new Vector(932,1683)
        ));
        boundaries.add(new Boundary(
                mapToScreenCoords(915,140),
                this.getParentEngine().getTextureGroup().getTexture("b14"),
                this.getParentScene(),
                new Vector(915,140)
        ));
        boundaries.add(new Boundary(
                mapToScreenCoords(1357,141),
                this.getParentEngine().getTextureGroup().getTexture("b15"),
                this.getParentScene(),
                new Vector(1357,141)
        ));
        boundaries.add(new Boundary(
                mapToScreenCoords(2014,1176),
                this.getParentEngine().getTextureGroup().getTexture("b16"),
                this.getParentScene(),
                new Vector(2014,1176)
        ));
        for(Boundary b : boundaries)
        {
            b.setPositionConstraint(null,null);
            this.spawnEntity(b);
        }
    }

    public final void hideAllButtons()
    {
        for(Entity e : this.getAllEntities())
            if(e instanceof MenuButton)
                e.setVisible(false);
    }

    public final ArrayList<MenuButton> getVisibleMenuButtons()
    {
        ArrayList<MenuButton> a = new ArrayList<>();
        for(Entity e : this.getAllEntities())
            if(e instanceof MenuButton)
                if(e.isVisible())
                    a.add((MenuButton)e);
        return a;
    }

    public Vector mapToScreenCoords(int x, int y)
    {
        StickBasicMap m = ((StickScene)this.getParentScene()).mapObject;
        return new Vector(m.mapStartX + x, m.mapStartY + y);
    }

    @Override
    public final void runControlUpdate(){}

    @Override
    public final void  iterateEntityPerControlUpdate(Entity<StickEngine> entity){}
}