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
    private Rect fieldBounds;
    private Rect playerPaddle;
    float velocity;
    private final float PADDLE_SPEED;

    public PongStage(View view, ExternalEventSource source) {
        super(view, source, STAGE_NAME);
        frameBuffer.setAntiAlias(true);
        registerInputHandler(this);
        fieldBounds = new Rect(width, height);
        playerPaddle = new Rect(new Vec2(convertWidth(0.05f), 0),
                convertWidth(0.05f), convertHeight(0.25f));
        playerPaddle.centerYWith(fieldBounds);
        PADDLE_SPEED = convertHeight(0.3f);
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
    }

    @Override
    public void present() {
        frameBuffer.clear(0, 0, 0);
        frameBuffer.setBrushColor(255, 255, 255);
        frameBuffer.drawRectangle(playerPaddle.getPosition(),
                playerPaddle.getWidth(), playerPaddle.getHeight());
    }

    @Override
    public void onExternalEvent(ExternalEvent event) {
        if (event.tag.equals("UP_PRESS")) {
            velocity -= PADDLE_SPEED;
        } else if (event.tag.equals("UP_RELEASE")) {
            velocity += PADDLE_SPEED;
        } else if (event.tag.equals("DOWN_PRESS")) {
            velocity += PADDLE_SPEED;
        } else if (event.tag.equals("DOWN_RELEASE")) {
            velocity -= PADDLE_SPEED;
        }
    }

    @Override
    public void onTouchEvent(Input.TouchEvent event) {

    }

    @Override
    public void onKeyEvent(Input.KeyEvent event) {

    }
}
