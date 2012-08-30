/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities;

import Spiel.model.Entities.monsterpack.EvilMonk;
import Spiel.model.Entities.monsterpack.Knight;
import Spiel.model.Entities.monsterpack.Ork;
import Spiel.model.Entities.monsterpack.Skelett;
import Spiel.model.Entities.monsterpack.Troll;
import Spiel.model.MainModel;
import Spiel.model.Room;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Lukas
 */
public class MonsterFactory {
      private MainModel main;
      private Monster monster;
      private ArrayList<Class> monsterlist=new ArrayList<>();

      public MonsterFactory(MainModel main) {
           this.main=main;




      }

      public LinkedList populateDungeon(LinkedList<Room> rooms) {
            LinkedList monsters= new LinkedList<>();
            for (Room room : rooms) {
                  int size = room.getBreite()*room.getHoehe();
                  int anzahl= (int)(Math.ceil(size*0.01*main.getCurrentDungeonLevel()));

                  for (int i = 0; i < anzahl;) {
                      double random=(double) Spiel.model.UtilFunctions.randomizer(1, 100)/100;
                      monster=null;
                      if (random < EvilMonk.spawnrate) {
                        monster=  new EvilMonk(room.getX1()+1, room.getY1()+1, room.getBreite()-2, room.getHoehe()-2,main);
                       } else if (random < Knight.spawnrate) {
                           monster= new Knight(room.getX1()+1, room.getY1()+1, room.getBreite()-2, room.getHoehe()-2,main);
                       } else if (random < Ork.spawnrate) {
                           monster= new Ork(room.getX1()+1, room.getY1()+1, room.getBreite()-2, room.getHoehe()-2,main);
                       } else if (random < Skelett.spawnrate) {
                           monster= new Skelett(room.getX1()+1, room.getY1()+1, room.getBreite()-2, room.getHoehe()-2,main);
                       } else if (random < Troll.spawnrate) {
                           monster= new Troll(room.getX1()+1, room.getY1()+1, room.getBreite()-2, room.getHoehe()-2,main);
                       }
                      if (monster!=null) {
                       room.getEntities().add(monster);
                       monster.setRoom(room);
                       monsters.add(monster);
                       i++;

                     }

                  }

            }
            return monsters;
      }



}
