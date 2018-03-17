package stickrpg.game.util;

import stickrpg.game.core.StickEntityController;
import stickrpg.game.drawable.entity.MenuButton;

import java.util.ArrayList;

public enum DoorTypeEnum
{
    CASTLE,
    BANK,
    HOME,
    CORP,
    STORE,
    PAWN,
    CASINO,
    BAR,
    BUS,
    FSTORE,
    SCHOOL,
    MCD,
    INVENTORY,
    STATS,
    KID,
    HOMELESS,
    DEALER,
    OLDAPT,
    BAPT,
    MANSION,
    PENTHOUSE;

    public ArrayList<MenuButton> getDoorButtons(StickEntityController ec, DoorTypeEnum type)
    {
        ArrayList<MenuButton> b2 = new ArrayList<>();
        switch (type)
        {
            case CASTLE:{
                b2.add(ec.sleep);
                b2.add(ec.phone);
                if(ec.player.computer)
                    b2.add(ec.computer);
                if(ec.player.tv)
                    b2.add(ec.tv);
                return b2;
            }
            case MANSION:{
                if(!ec.player.castle && ec.player.mansion)
                {
                    b2.add(ec.sleep);
                    b2.add(ec.phone);
                    if(ec.player.computer)
                        b2.add(ec.computer);
                    if(ec.player.tv)
                        b2.add(ec.tv);
                }
                return b2;
            }
            case STORE:{
                 b2.add(ec.buySmokes);
                 b2.add(ec.slushee);
                 b2.add(ec.candy);
                 b2.add(ec.nachos);
                 b2.add(ec.pills);
                 ec.eb.setPosition(ec.eb.getPosition().plusX(48));
                 if(ec.player.gun)
                 {
                     b2.add(ec.rob);
                     ec.rob.setPosition(ec.rob.getPosition().plusX(96));
                 }
                 return b2;
            }
            case HOME:{
                if(!ec.player.castle && !ec.player.mansion)
                {
                    b2.add(ec.sleep);
                    b2.add(ec.phone);
                    if(ec.player.computer)
                        b2.add(ec.computer);
                    if(ec.player.tv)
                        b2.add(ec.tv);
                }
                return b2;
            }
            case SCHOOL:{
                b2.add(ec.gym);
                b2.add(ec.sclass);
                b2.add(ec.study);
                return b2;
            }
            case KID:{
                b2.add(ec.giveSmokes);
                return b2;
            }
            case BAR:{
                b2.add(ec.drinkB);
                b2.add(ec.buyB);
                return b2;
            }
            case CORP:{
                b2.add(ec.apply);
                if(ec.player.corpTier > 0)
                    b2.add(ec.work);
                return b2;
            }
            case PAWN:{
                if(ec.player.gun)
                {
                    b2.add(ec.bullets);
                }
                else
                {
                    b2.add(ec.gun);
                }
                if(!ec.player.knife)
                    b2.add(ec.knife);
                if(!ec.player.alarm)
                    b2.add(ec.alarm);
                if(!ec.player.cell)
                    b2.add(ec.cell);
                return b2;
            }
            case BANK:{
                b2.add(ec.bhouse);
                ec.bhouse.setPosition(ec.bhouse.getPosition().plusX(28));
                b2.add(ec.loan);
                ec.loan.setPosition(ec.loan.getPosition().plusX(77));
                b2.add(ec.deposit);
                ec.deposit.setPosition(ec.deposit.getPosition().plusX(35));
                b2.add(ec.withdraw);
                ec.withdraw.setPosition(ec.withdraw.getPosition().plusX(48));
                ec.eb.setPosition(ec.eb.getPosition().plusX(48));
                if(ec.player.gun)
                {
                    b2.add(ec.rob);
                    ec.rob.setPosition(ec.rob.getPosition().plusX(96));
                }
                return b2;
            }
            case INVENTORY:{
                b2.add(ec.goHouse);
                return b2;
            }
            case MCD:{
                b2.add(ec.shake);
                b2.add(ec.fries);
                b2.add(ec.cheeseBurger);
                b2.add(ec.tripleCheese);
                if(ec.player.corpTier == 0)
                    b2.add(ec.work);
                return b2;
            }
            case FSTORE:{
                if(ec.player.sHome && !ec.player.bed)
                    b2.add(ec.bed);
                if((ec.player.penthouse || ec.player.mansion || ec.player.castle) && !ec.player.tv)
                    b2.add(ec.tv);
                if((ec.player.penthouse || ec.player.mansion || ec.player.castle) && !ec.player.ac)
                    b2.add(ec.ac);
                if((ec.player.bApt || ec.player.penthouse || ec.player.mansion || ec.player.castle) && !ec.player.computer)
                    b2.add(ec.computer);
                if((ec.player.mansion || ec.player.castle) && !ec.player.satellite)
                    b2.add(ec.satellite);
                if((ec.player.mansion || ec.player.castle) && !ec.player.treadmill)
                    b2.add(ec.treadmill);
                if((ec.player.mansion || ec.player.castle) && !ec.player.pedia)
                    b2.add(ec.pedia);
                if((ec.player.mansion || ec.player.castle) && !ec.player.minibar)
                    b2.add(ec.minibar);
                return b2;
            }
            case DEALER:{
                b2.add(ec.coke);
                return b2;
            }
            case HOMELESS:{
                b2.add(ec.coins);
                return b2;
            }
            case BUS:{
                b2.add(ec.NY);
                b2.add(ec.MI);
                b2.add(ec.CA);
                b2.add(ec.IL);
                b2.add(ec.NJ);
                b2.add(ec.NV);
                return b2;
            }
            default:return b2;
        }
    }
}