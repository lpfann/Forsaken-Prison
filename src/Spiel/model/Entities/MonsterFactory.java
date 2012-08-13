/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities;

import Spiel.model.Entities.monsterpack.Ork;
import Spiel.model.Entities.monsterpack.Troll;
import Spiel.model.MainModel;
import Spiel.model.Room;
import java.util.LinkedList;

/**
 *
 * @author Lukas
 */
public class MonsterFactory {
      private LinkedList monsterlist;
      private MainModel main;
      private Monster monster;
      public MonsterFactory(MainModel main) {
           this.main=main;
      
      }
      
      
      public LinkedList createMonsters(int anzahl,int x,int y, int w, int h) {
            LinkedList monsters= new LinkedList<>();
            
              for (int i = 0; i < anzahl; i++) {
                    Monster monster= new Troll(x,y,w,h,main);
                    
                    monsters.add(monster);
                    
                    
              }
              return monsters;
      }
      
      public LinkedList populateDungeon(LinkedList<Room> rooms) {
            LinkedList monsters= new LinkedList<>();
            for (Room room : rooms) {
                  int size = room.getBreite()*room.getHoehe();
                  int anzahl= (int)(size*0.04);
                  
                  for (int i = 0; i < anzahl; i++) {
                      int random= Spiel.model.Utilites.randomizer(0, 4);
                          if (random==0) {
                                    monster= new Troll(room.getX1()+1, room.getY1()+1, room.getBreite()-2, room.getHoehe()-2,main);
                                    monster.setRoom(room);
                                    monsters.add(monster);   
                          } else if (random >0) {
                                    monster= new Ork(room.getX1()+1, room.getY1()+1, room.getBreite()-2, room.getHoehe()-2,main);
                                    monster.setRoom(room);
                                    monsters.add(monster);   
                      }
                  
                  }
                  
            }
            return monsters;
      }

      public LinkedList getMonsterlist() {
            return monsterlist;
      }

      public void setMonsterlist(LinkedList monsterlist) {
            this.monsterlist = monsterlist;
      }
      
}
