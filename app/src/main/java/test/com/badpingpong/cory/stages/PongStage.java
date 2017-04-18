package test.com.badpingpong.cory.stages;

import android.view.View;

import test.com.badpingpong.cory.math.Vec2;

/**
 * Created by Cory on 4/17/2017.
 */

public class PongStage extends Stage {
    private static final String STAGE_NAME = "Pong";

    public PongStage(View view) {
        super(view, STAGE_NAME);
        frameBuffer.setAntiAlias(true);
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void present() {
        frameBuffer.clear(255, 0, 0);
        frameBuffer.setBrushColor(0, 0, 255);
        frameBuffer.drawRectangle(new Vec2(0, 0), 400, 400);
    }
}
