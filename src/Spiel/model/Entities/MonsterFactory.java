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
import java.util.LinkedList;

/**
 *
 * @author Lukas
 */
public class MonsterFactory {
      private MainModel main;
      private Monster monster;
      private LinkedList<Monster> monsterlist;

      public MonsterFactory(MainModel main) {
           this.main=main;



      }

      public LinkedList populateDungeon(LinkedList<Room> rooms) {
            LinkedList monsters= new LinkedList<>();
            for (Room room : rooms) {
                  int size = room.getBreite()*room.getHoehe();
                  int anzahl= (int)(size*0.04);

                  while (monsters.size()< anzahl) {
                      double random=(double) Spiel.model.Utilites.randomizer(1, 100)/100;
                      System.err.println(random);
                      for (Monster e : monsterlist) {
                         if (e.getSpawnrate()*main.getCurrentDungeonLevel() <= random) {
                            monster = e;
                            monster.setstartposition(room.getX1()+1, room.getY1()+1, room.getBreite()-2, room.getHoehe()-2);
                         }
                     }



//                          if (random>=0 && random <=8) {
//                                    monster= new Troll(room.getX1()+1, room.getY1()+1, room.getBreite()-2, room.getHoehe()-2,main);
//                          } else if (random >8 && random<10) {
//                                    monster= new Ork(room.getX1()+1, room.getY1()+1, room.getBreite()-2, room.getHoehe()-2,main);
//
//                          } else if (random ==10) {
//                                    monster= new Knight(room.getX1()+1, room.getY1()+1, room.getBreite()-2, room.getHoehe()-2,main);
//                      }
                    room.getEntities().add(monster);
                    monster.setRoom(room);
                    monsters.add(monster);

                  }

            }
            return monsters;
      }



}
