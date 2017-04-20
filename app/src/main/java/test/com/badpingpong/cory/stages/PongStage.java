package test.com.badpingpong.cory.stages;

import android.view.View;

import test.com.badpingpong.cory.input.ExternalEventHandler.ExternalEventSource;
import test.com.badpingpong.cory.input.Input;
import test.com.badpingpong.cory.input.Input.ExternalEvent;
import test.com.badpingpong.cory.input.InputHandler;
import test.com.badpingpong.cory.math.Rect;
import test.com.badpingpong.cory.math.Vec2;

/**
 * Created by Cory on 4/17/2017.
 */

public class PongStage extends Stage implements InputHandler {
    private static final String STAGE_NAME = "Pong";
    private final float PADDLE_SPEED;
    private final float BALL_SPEED;

    private Rect fieldBounds;
    private Rect playerPaddle;
    private Rect cpuPaddle;
    private Rect ball;
    private Vec2 ballVelocity;
    float velocity;

    public PongStage(View view, ExternalEventSource source) {
        super(view, source, STAGE_NAME);
        PADDLE_SPEED = convertHeight(0.4f);
        BALL_SPEED = convertWidth(0.6f);

        frameBuffer.setAntiAlias(true);
        registerInputHandler(this);

        fieldBounds = new Rect(width, height);

        playerPaddle = new Rect(new Vec2(convertWidth(0.05f), 0),
                convertWidth(0.05f), convertHeight(0.25f));
        playerPaddle.centerYWith(fieldBounds);

        cpuPaddle = new Rect(convertWidth(0.05f), convertHeight(0.25f));
        cpuPaddle.alignRightWith(fieldBounds);
        cpuPaddle.getPosition().sub(convertWidth(0.05f), 0);
        cpuPaddle.centerYWith(fieldBounds);

        ball = new Rect(convertWidth(0.05f), convertWidth(0.05f));
        ball.centerWith(fieldBounds);
        ballVelocity = new Vec2();
        randomizeBallVelocity();
    }

    private void randomizeBallVelocity() {
        float rand = (float) Math.random();
        ballVelocity.set(rand, rand * (float) Math.random()).normalize().scl(BALL_SPEED);
    }

    private void doPaddleRebound(float toCenter) {
        ballVelocity.reflectX();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void update(float delta) {
        playerPaddle.getPosition().add(0, delta * velocity);

        if (playerPaddle.extendsAbove(fieldBounds)) {
            playerPaddle.alignTopWith(fieldBounds);
        } else if (playerPaddle.extendsBelow(fieldBounds)) {
            playerPaddle.alignBottomWith(fieldBounds);
        }

        ball.getPosition().add(ballVelocity.cpy().scl(delta));
        if (ball.extendsAbove(fieldBounds)) {
            ball.alignTopWith(fieldBounds);
            ballVelocity.reflectY();
        } else if (ball.extendsBelow(fieldBounds)) {
            ball.alignBottomWith(fieldBounds);
            ballVelocity.reflectY();
        } else if (ball.extendsLeftOf(fieldBounds)) {
            ball.centerWith(fieldBounds);
            randomizeBallVelocity();
        } else if (ball.extendsRightOF(fieldBounds)) {
            ball.centerWith(fieldBounds);
            randomizeBallVelocity();
        }

        if (ball.overlaps(playerPaddle)) {
            ball.alignLeftWith(playerPaddle);
            ball.getPosition().add(playerPaddle.getWidth(), 0);
            doPaddleRebound(1);
        } else if (ball.overlaps(cpuPaddle)) {
            ball.alignRightWith(cpuPaddle);
            ball.getPosition().sub(cpuPaddle.getWidth(), 0);
            doPaddleRebound(-1);
        }

        float cpuDirection = 0;

        if (ball.getCenter().getY() > cpuPaddle.getCenter().getY()) {
            cpuDirection = 1;
        } else if (ball.getCenter().getY() < cpuPaddle.getCenter().getY()) {
            cpuDirection = -1;
        }

        cpuPaddle.getPosition().add(0, delta * cpuDirection * PADDLE_SPEED);

        if (cpuPaddle.extendsAbove(fieldBounds)) {
            cpuPaddle.alignTopWith(fieldBounds);
        } else if (cpuPaddle.extendsBelow(fieldBounds)) {
            cpuPaddle.alignBottomWith(fieldBounds);
        }

//        Log.d("BallPosition", ball.getPosition().toString());
    }

    @Override
    public void present() {
        frameBuffer.clear(0, 0, 0);
        frameBuffer.setBrushColor(255, 255, 255);
        frameBuffer.drawRectangle(playerPaddle);
        frameBuffer.drawRectangle(cpuPaddle);
        frameBuffer.drawRectangle(ball);
    }

    @Override
    public void onExternalEvent(ExternalEvent event) {
        switch (event.tag) {
            case "UP_PRESS":
                velocity -= PADDLE_SPEED;
                break;
            case "UP_RELEASE":
                velocity += PADDLE_SPEED;
                break;
            case "DOWN_PRESS":
                velocity += PADDLE_SPEED;
                break;
            case "DOWN_RELEASE":
                velocity -= PADDLE_SPEED;
                break;
        }
    }

    @Override
    public void onTouchEvent(Input.TouchEvent event) {
        playerPaddle.getPosition().set(event.x, event.y);
    }

    @Override
    public void onKeyEvent(Input.KeyEvent event) {

    }
}
