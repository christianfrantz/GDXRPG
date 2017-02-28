package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Observer.DamageObserver;
import com.mygdx.game.Observer.EnemySubject;

/**
 * Created by imont_000 on 2/27/2017.
 */
public abstract class Enemy {

    public enum EnemyType{
        SLIME
    };

    public int health;

    public Sprite sprite;
    public Body body;
    public boolean flaggedForDelete = false;

    public EnemyType enemyType;

    public String id;

    public EnemySubject enemySubject;

    public Enemy(){
        enemySubject = new EnemySubject();
        enemySubject.AddObserver(new DamageObserver());
    }

    public void GenerateBody(Vector2 position, Texture texture){
        sprite.setBounds(0, 0, texture.getWidth() / MainGame.PPM, texture.getHeight() / MainGame.PPM);
        sprite.setOrigin(32 / MainGame.PPM, 32 / MainGame.PPM);

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position.x, position.y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = MainGame.world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(texture.getWidth() / 2 / MainGame.PPM, texture.getHeight() / 2 / MainGame.PPM);

        body.setUserData(this);
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
    }

    public void UpdateEnemy(){
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);

        checkIfAlive();
    }

    public void checkIfAlive(){
        if(health <= 0) {
            MainGame.enemies.values().remove(this);
            flaggedForDelete = true;
        }

    }
}
