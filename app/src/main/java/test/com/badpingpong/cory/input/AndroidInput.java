package test.com.badpingpong.cory.input;

import android.view.View;

import java.util.List;

import test.com.badpingpong.cory.input.ExternalEventHandler.ExternalEventSource;

/**
 * Created by Cory on 4/17/2017.
 */

public class AndroidInput implements Input {
    private final TouchHandler touchHandler;
    private final AccelHandler accelHandler;
    private final ExternalEventHandler externalEventHandler;

    public AndroidInput(View view, ExternalEventSource source) {
        touchHandler = new TouchHandler(view);
        accelHandler = new AccelHandler(view);
        externalEventHandler = new ExternalEventHandler();
        source.registerListener(externalEventHandler);
    }

    @Override
    public boolean isKeyPressed(int keyCode) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isTouchDown(int pointer) {
        return touchHandler.isTouchDown(pointer);
    }

    @Override
    public int getTouchX(int pointer) {
        return touchHandler.getTouchX(pointer);
    }

    @Override
    public int getTouchY(int pointer) {
        return touchHandler.getTouchY(pointer);
    }

    @Override
    public float getAccelX() {
        return accelHandler.getAccelX();
    }

    @Override
    public float getAccelY() {
        return accelHandler.getAccelY();
    }

    @Override
    public float getAccelZ() {
        return accelHandler.getAccelZ();
    }

    @Override
    public List<KeyEvent> getKeyEvents() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<TouchEvent> getTouchEvents() {
        return touchHandler.getTouchEvents();
    }

    @Override
    public List<ExternalEvent> getExternalEvents() {
        return externalEventHandler.getExternalEvents();
    }
}
