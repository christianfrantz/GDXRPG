package com.mygdx.game.Observer;

import com.mygdx.game.Enemy;
import com.mygdx.game.Player;
/**
 * Created by imont_000 on 2/28/2017.
 */
public class DamageObserver implements Observer {

    @Override
    public void onNotify(Enemy enemy, Event event) {
        switch (event){
            case ENEMY_DAMAGE:{
                enemy.health -= 10;
                System.out.println("ENEMY_DAMAGE : " + enemy.health + " " + enemy.id);
                break;
            }
        }
    }

    @Override
    public void onNotify(Player player, Event event) {
        switch (event){
            case PLAYER_DAMAGE:{
                player.health -= 10;
                System.out.println("PLAYER_DAMAGE : " + player.health);
                break;
            }
        }
    }

    @Override
    public void onNotify(Player player, Enemy enemy, Event event) {
        switch (event) {
            case ENEMY_DAMAGE:
                enemy.health -= player.damage;
                System.out.println("ENEMY_DAMAGE : " + enemy.health);
                break;
        }
    }


}
