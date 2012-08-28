/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.View;

import Spiel.model.MainModel;
import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author Lukas
 */
public class Sounds implements Observer {
private HashMap<String, AudioClip> sounds;
private Vector<AudioClip> loopingsounds;
private int counter=1;
private long delay;




public Sounds(){
   sounds = new HashMap<String, AudioClip>();
   loopingsounds = new Vector<AudioClip>();

   loadSound("enemyhit", "/resources/sounds/enemyhit.wav");
   loadSound("dooropen", "/resources/sounds/dooropen.wav");
   loadSound("levelup", "/resources/sounds/levelup.wav");
   loadSound("enemydead", "/resources/sounds/enemydead.wav");
   loadSound("chestopen", "/resources/sounds/chestopen.wav");
   loadSound("playerhit", "/resources/sounds/playerhit.wav");
   loadWalkingSound("walking", "/resources/sounds/step_lth");

}




public void loadSound(String name, String path){
   if (sounds.containsKey(name)) {
   return;
   }

   URL soundpath = getClass().getResource(path);
   sounds.put(name, (AudioClip)Applet.newAudioClip(soundpath));

}
public void loadWalkingSound(String name, String path){
   if (sounds.containsKey(name)) {
   return;
   }
   for (int i = 1; i <= 4; i++) {
   URL soundpath = getClass().getResource(path+i+".wav");
   sounds.put(name+i, (AudioClip)Applet.newAudioClip(soundpath));

   }

}

public void playWalkingSound(){
   if (delay > 250) {
      delay=0;

   if (counter>4) {
      counter=1;
   }
   AudioClip sound= sounds.get("walking"+counter);
   sound.play();
   counter++;

   }


}

public void playSound(String name){
   AudioClip sound = sounds.get(name);

   sound.play();


}

public void loopSound(String name){
   AudioClip sound = sounds.get(name);
   loopingsounds.add(sound);
   sound.loop();


}
public void stoploopingSound(){
      for (AudioClip audioClip : loopingsounds) {
     audioClip.stop();
   }
}

   @Override
   public void update(transEnum enu, MainModel mm) {
   }

   @Override
   public void update(char[][] map) {
   }

   @Override
   public void update(sounds s, long delta) {
      delay+=delta/1e6;
      switch (s) {
         case enemyhit:
               playSound("enemyhit");
            break;
         case playerhit:
               playSound("playerhit");
            break;
         case enemydead:
               playSound("enemydead");
            break;
         case levelup:
               playSound("levelup");
            break;
         case dooropen:
               playSound("dooropen");
            break;
         case chestopen:
               playSound("chestopen");
            break;
         case walkingon:
               playWalkingSound();
            break;



      }

   }



}
