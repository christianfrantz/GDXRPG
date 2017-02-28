package com.mygdx.game.Observer;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by imont_000 on 2/28/2017.
 */
public abstract class Subject {
        public LinkedList<Observer> observers = new LinkedList<Observer>();
        int numOfObservers;

        public void AddObserver(Observer ob) {
            if(!observers.contains(ob)){
                observers.add(ob);
                numOfObservers++;
            }
        }

        public void RemoveObserver(Observer ob){
            if(observers.contains(ob)){
                observers.remove(ob);
                numOfObservers--;
            }
        }

}
