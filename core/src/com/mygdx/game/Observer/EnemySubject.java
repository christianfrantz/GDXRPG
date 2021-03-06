package com.mygdx.game.Observer;

import com.mygdx.game.Enemy;
import com.mygdx.game.Player;

/**
 * Created by imont_000 on 2/28/2017.
 */
public class EnemySubject extends Subject{

    public void notify(Enemy enemy, Event event){
        for(int i = 0; i < numOfObservers; i++){
            observers.get(i).onNotify(enemy, event);
            System.out.println(i);
        }
    }

    public void notify(Player player, Enemy enemy, Event event){
        for(int i = 0; i < numOfObservers; i++){
            observers.get(i).onNotify(player, enemy, event);
        }
    }
}
