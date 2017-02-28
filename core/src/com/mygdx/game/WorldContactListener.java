package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Observer.Event;
import com.mygdx.game.Observer.Observer;

/**
 * Created by imont_000 on 2/27/2017.
 */
public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        //Gdx.app.log("begin contact", "between " + fixtureA.getBody().getUserData() + " and " + fixtureB.getBody().getUserData());
        Player player;
        Enemy enemy;

        if(fixtureA == null || fixtureB == null)return;
        if(fixtureA.getBody().getUserData() == null || fixtureB.getBody().getUserData() == null)return;

        if(isPlayerAttackContact(fixtureA, fixtureB)){
            enemy = fixtureB.getBody().getUserData() instanceof Enemy ? (Enemy)fixtureB.getBody().getUserData() : (Enemy)fixtureA.getBody().getUserData();
            player = fixtureA.getBody().getUserData().equals(Statics.PLAYER_ATTACK_BODY) ? (Player)fixtureA.getUserData() : (Player)fixtureB.getUserData();

            if(player.playerState == Player.PlayerState.ATTACKING) {
                enemy.enemySubject.notify(player, enemy, Event.ENEMY_DAMAGE);
            }
        }

        if(isOtherContactPlayer(fixtureA, fixtureB)){
            player = fixtureA.getBody().getUserData().equals(Statics.PLAYER_BODY) ? (Player)fixtureA.getUserData() : (Player)fixtureB.getUserData();
            enemy = fixtureB.getBody().getUserData() instanceof Enemy ? (Enemy)fixtureB.getBody().getUserData() : (Enemy)fixtureA.getBody().getUserData();
            player.playerSubject.notify(player, Event.PLAYER_DAMAGE);
        }

    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        //Gdx.app.log("end contact", "between " + fixtureA.getBody().getUserData() + " and " + fixtureB.getBody().getUserData());
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public boolean isPlayerAttackContact(Fixture a, Fixture b){
       if (a.getBody().getUserData().equals(Statics.PLAYER_ATTACK_BODY) || b.getBody().getUserData().equals(Statics.PLAYER_ATTACK_BODY)){
            if(a.getBody().getUserData() instanceof Enemy || b.getBody().getUserData() instanceof Enemy){
               return true;
           }
        }
        return false;
    }

    public boolean isOtherContactPlayer(Fixture a, Fixture b){
        if (a.getBody().getUserData() instanceof Enemy|| b.getBody().getUserData() instanceof Enemy){
            if(a.getBody().getUserData().equals(Statics.PLAYER_BODY) || b.getBody().getUserData().equals(Statics.PLAYER_BODY)){
                return true;
            }
        }
        return false;
    }
}
