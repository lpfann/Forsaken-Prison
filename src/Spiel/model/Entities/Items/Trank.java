/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities.Items;

import Spiel.model.Entities.Player;

/**
 *
 * @author Lukas
 */
public abstract class Trank extends Item {
      public enum Size { KLEIN,MITTEL,GROß};
      private Size size;
      
      Trank(String name,Size s){
          setName(name);
          this.size=s;
          potionSizeRenamer();
          
          
          
      }
      
      //Methode die jeder Trank brauch. Die Auswirkung wenn der Player den Trank nimmt.
      public abstract void potionAuswirkung(Player p, int i);
      
              @Override
        public void useItem() {
                    usepotion(getPlayer());  
                      
        }
      
      public  void usepotion(Player p) {
          switch (getSize()) {
              case KLEIN:
                  potionAuswirkung(p,20);
              case MITTEL:
                  potionAuswirkung(p,50);
                  
              case GROß:
                  potionAuswirkung(p,100);
                  
          }

          
          
          
      }
      
      
      private void potionSizeRenamer() {
            if (size == Size.KLEIN) {
              setName("Kleiner "+getName());
          } else if (size == Size.MITTEL) {
              setName("Mittlerer "+getName());
              
            } else if (size == Size.GROß) {
                  setName("Großer "+getName());

            }   else {
          }
      

      }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }
      
}
