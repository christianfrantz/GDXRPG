package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by imont_000 on 2/27/2017.
 */
public  class EnemyFactory {

    public int idNumber = 0;

    public Enemy createEnemy(Enemy.EnemyType type, Vector2 position){

        Enemy enemy = null;

        switch (type){
            case SLIME:
                String id = "slime" + idNumber;
                enemy = new Slime(position, id);
                MainGame.enemies.put(id, enemy);
                break;
        }

        idNumber++;
        return enemy;
    }
}
