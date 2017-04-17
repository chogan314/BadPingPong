package test.com.badpingpong.cory.input;

import android.content.Context;
import android.view.View;

import java.util.List;

/**
 * Created by Cory on 4/17/2017.
 */

public class AndroidInput implements Input {
    private final TouchHandler touchHandler;

    public AndroidInput(Context context, View view) {
        touchHandler = new TouchHandler(view);
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
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public float getAccelY() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public float getAccelZ() {
        // TODO Auto-generated method stub
        return 0;
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
}
