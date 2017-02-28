package com.mygdx.game.Observer;

import com.mygdx.game.Enemy;
import com.mygdx.game.Player;

/**
 * Created by imont_000 on 2/28/2017.
 */
public interface Observer {
    public void onNotify(Enemy enemy, Event event);

    public void onNotify(Player player, Event event);

    public void onNotify(Player player, Enemy enemy, Event event);
}
