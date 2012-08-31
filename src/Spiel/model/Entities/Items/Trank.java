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
                   break;
              case MITTEL:
                  potionAuswirkung(p,50);
                   break;
              case GROß:
                  potionAuswirkung(p,100);
                   break;

          }




      }

            @Override
      public String showStat(){
        return null;


      }
      private void potionSizeRenamer() {
            if (size == Size.KLEIN) {
              setName("Kleiner "+getName());
              setDroprate(800);
          } else if (size == Size.MITTEL) {
              setName("Mittlerer "+getName());
              setDroprate(600);

            } else if (size == Size.GROß) {
                  setName("Großer "+getName());
              setDroprate(400);

            }   else {
          }


      }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }
        @Override
   public int itemPrice(){
           return 0;



   }
}
