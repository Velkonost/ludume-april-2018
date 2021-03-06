package com.ltc.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.ltc.Constants.PIXELS_IN_METER;

public class WallEntity extends Actor {

    /** The player texture. */
    private Texture texture;

    /** The world instance this player is in. */
    private World world;

    /** The body for this player. */
    private Body body;

    /** The fixture for this player. */
    private Fixture fixture;

    /**
     * Is the player alive? If he touches a spike, is not alive. The player will only move and
     * jump if it's alive. Otherwise it is said that the user has lost and the game is over.
     */
    private boolean alive = true;

    /**
     * Is the player jumping? If the player is jumping, then it is not possible to jump again
     * because the user cannot double jump. The flag has to be set when starting a jump and be
     * unset when touching the floor again.
     */
    private boolean jumping = false;

    /**
     * Does the player have to jump? This flag is used when the player touches the floor and the
     * user is still touching the screen, to make a double jump. Remember that we cannot add
     * a force inside a ContactListener. We have to use this flag to remember that the player
     * had to jump after the collision.
     */
    private boolean mustJump = false;

    private float size_x = 1f, size_y = 1f;

    private float plus_x, plus_y;

    public boolean isRemoved = false;

    public int hp = 5;


    public WallEntity(World world, Texture texture, Vector2 position, Float size_x, Float size_y, Float plusX, Float plusY, String name, Float box_sizex, Float box_sizey) {
        this.world = world;
        this.texture = texture;
        this.size_x = size_x;
        this.size_y = size_y;
        this.plus_x = plusX;
        this.plus_y = plusY;

        // Create the player body.
        BodyDef def = new BodyDef();                // (1) Create the body definition.
        def.position.set(position);                 // (2) Put the body in the initial position.
        def.type = BodyDef.BodyType.StaticBody;    // (3) Remember to make it dynamic.
        body = world.createBody(def);               // (4) Now create the body.

        // Give it some shape.
        PolygonShape box = new PolygonShape();      // (1) Create the shape.
        box.setAsBox(box_sizex, box_sizey);                   // (2) 1x1 meter box.
        fixture = body.createFixture(box, 1000000000);       // (3) Create the fixture.
        fixture.setUserData("wall");              // (4) Set the user data.
        box.dispose();                              // (5) Destroy the shape.

        // Set the size to a value that is big enough to be rendered on the screen.
        setSize(PIXELS_IN_METER, PIXELS_IN_METER);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (alive) {
            setPosition((body.getPosition().x) * PIXELS_IN_METER,
                    (body.getPosition().y) * PIXELS_IN_METER);
            batch.draw(texture, getX() + plus_x, getY() + plus_y, size_x, size_y);
        } else {
            setPosition(0, 0);
            batch.draw(texture, 0, 0, 0, 0);
        }
    }

    @Override
    public void act(float delta) {
        // Jump when you touch the screen.
//        if (Gdx.input.justTouched()) {
//            jump();
//        }
//
//        // Jump if we were required to jump during a collision.
//        if (mustJump) {
//            mustJump = false;
//            jump();
//        }

//        // If the player is alive, change the speed so that it moves.
//        if (alive) {
//            // Only change X speed. Do not change Y speed because if the player is jumping,
//            // this speed has to be managed by the forces applied to the player. If we modify
//            // Y speed, jumps can get very very weir.d
//            float speedY = body.getLinearVelocity().y;
//            body.setLinearVelocity(-8f, 0);
//        System.out.println(body.getPosition().x);
//        }
//
//        // If the player is jumping, apply some opposite force so that the player falls faster.
//        if (jumping) {
//            body.applyForceToCenter(0, -es.danirod.jddprototype.game.Constants.IMPULSE_JUMP * 1.15f, true);
//        }
    }

//    public void jump() {
//        // The player must not be already jumping and be alive to jump.
//        if (!jumping && alive) {
//            jumping = true;
//
//            // Apply an impulse to the player. This will make change the velocity almost
//            // at the moment unlike using forces, which gradually changes the force used
//            // during the jump. We get the position becase we have to apply the impulse
//            // at the center of mass of the body.
//            Vector2 position = body.getPosition();
//            body.applyLinearImpulse(0, es.danirod.jddprototype.game.Constants.IMPULSE_JUMP, position.x, position.y, true);
//        }
//    }

    public Body getBody() {
        return body;
    }

    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

    // Getter and setter festival below here.

    public Fixture getFixture() {
        return fixture;
    }

    public void removeFixture(){
        isRemoved = true;
    }


    public boolean isAlive() {
        return alive;
    }

    public void setDie() {
        alive = false;
    }
}
