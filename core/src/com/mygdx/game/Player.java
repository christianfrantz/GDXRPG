package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Observer.DamageObserver;
import com.mygdx.game.Observer.PlayerSubject;

/**
 * Created by imont_000 on 2/23/2017.
 */
public class Player {
    public int health = 100;
    public int damage = 5;

    public enum Direction{
        LEFT,
        RIGHT,
        DOWN,
        UP
    }

    public enum PlayerState{
        IDLE,
        ATTACKING
    }

    public Sprite sprite;
    private World world;
    public Body body;
    public Body attackBody;

    private float speed = 2;
    public Direction direction;
    public PlayerState playerState;

    private float attackTime = 1;
    private float attackCounter = 0;

    public PlayerSubject playerSubject;

    public Player(Texture texture, Vector2 position){
        this.world = MainGame.world;

        sprite = new Sprite(texture);
        sprite.setBounds(0, 0, 64 / MainGame.PPM, 64 / MainGame.PPM);
        sprite.setOrigin(32 / MainGame.PPM, 32 / MainGame.PPM);

        createBody(position);

        direction = Direction.DOWN;
        playerState = PlayerState.IDLE;

        playerSubject = new PlayerSubject();
        playerSubject.AddObserver(new DamageObserver());
    }

    private void createBody(Vector2 position){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position.x, position.y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = MainGame.world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape  shape = new PolygonShape();
        shape.setAsBox(32 / MainGame.PPM, 32 / MainGame.PPM);

        fixtureDef.shape = shape;

        body.setUserData(Statics.PLAYER_BODY);
        body.createFixture(fixtureDef).setUserData(this);

        BodyDef attackBodyDef = new BodyDef();
        attackBodyDef.position.set(position.x, (position.y - 0.5f));
        attackBodyDef.type = BodyDef.BodyType.DynamicBody;

        attackBody = MainGame.world.createBody(attackBodyDef);

        FixtureDef attackFixture = new FixtureDef();
        PolygonShape attackShape = new PolygonShape();
        attackShape.setAsBox(64 / MainGame.PPM, 18 / MainGame.PPM);
        attackFixture.isSensor = true;
        attackFixture.shape = attackShape;


        attackBody.setUserData(Statics.PLAYER_ATTACK_BODY);
        attackBody.createFixture(attackFixture).setUserData(this);
    }

    public void updatePlayer(float delta){
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);

        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            body.applyLinearImpulse(new Vector2(-speed, 0), body.getWorldCenter(), true);
            attackBody.applyLinearImpulse(new Vector2(-speed, 0), body.getWorldCenter(), true);
            direction = Direction.LEFT;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D)){
            body.applyLinearImpulse(new Vector2(speed, 0), body.getWorldCenter(), true);
            attackBody.applyLinearImpulse(new Vector2(speed, 0), body.getWorldCenter(), true);
            direction = Direction.RIGHT;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.W)){
            body.applyLinearImpulse(new Vector2(0, speed), body.getWorldCenter(), true);
            attackBody.applyLinearImpulse(new Vector2(0, speed), body.getWorldCenter(), true);
            direction = Direction.UP;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.S)){
            body.applyLinearImpulse(new Vector2(0, -speed), body.getWorldCenter(), true);
            attackBody.applyLinearImpulse(new Vector2(0, -speed), body.getWorldCenter(), true);
            direction = Direction.DOWN;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.F) && playerState == PlayerState.IDLE){
            playerState = PlayerState.ATTACKING;

        }

        if(playerState == PlayerState.ATTACKING){
            attackCounter += Gdx.graphics.getDeltaTime();
            attackBody.setActive(true);
            if(attackCounter >= attackTime){
                playerState = PlayerState.IDLE;
                attackCounter = 0;
            }
        }

        if(playerState == PlayerState.IDLE){
            attackBody.setActive(false);
        }

        body.setLinearDamping(10f);
        attackBody.setLinearDamping(10f);
        switch (direction){
            case LEFT:
                attackBody.setTransform(body.getWorldCenter().x - (48 / MainGame.PPM), body.getWorldCenter().y, 90 * MathUtils.degreesToRadians);
                break;
            case RIGHT:
                attackBody.setTransform(body.getWorldCenter().x + (48 / MainGame.PPM), body.getWorldCenter().y, 90 * MathUtils.degreesToRadians);
                break;
            case UP:
                attackBody.setTransform(body.getWorldCenter().x,  body.getWorldCenter().y + (48 / MainGame.PPM), 0);
                break;
            case DOWN:
                attackBody.setTransform(body.getWorldCenter().x,  body.getWorldCenter().y - (48 / MainGame.PPM), 0);
                break;
        }
    }
}
