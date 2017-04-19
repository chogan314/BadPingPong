package test.com.badpingpong.cory.stages;

import android.graphics.Bitmap;
import android.view.View;

import java.util.List;

import test.com.badpingpong.cory.input.AndroidInput;
import test.com.badpingpong.cory.input.ExternalEventHandler.ExternalEventSource;
import test.com.badpingpong.cory.input.Input;
import test.com.badpingpong.cory.input.InputHandler;
import test.com.badpingpong.cory.math.Vec2;
import test.com.badpingpong.cory.rendering.RenderContext;


/**
 * Created by Cory on 4/17/2017.
 */

public abstract class Stage {
    public interface StageFactory<T extends Stage> {
        T createStage(View view, ExternalEventSource source);
    }

    protected RenderContext frameBuffer;
    protected final int width, height;
    protected final Vec2 topCenter, centerRight, bottomCenter, centerLeft;
    protected Input input;
    private final String name;
    private InputHandler inputHandler;

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public Stage(View view, ExternalEventSource source, String name) {
        this.width = view.getWidth();
        this.height = view.getHeight();

        topCenter = new Vec2(width / 2, 0);
        bottomCenter = new Vec2(width / 2, height);
        centerLeft = new Vec2(0, height / 2);
        centerRight = new Vec2(width, height / 2);

        this.name = name;

        input = new AndroidInput(view, source);

        frameBuffer = new RenderContext(width, height, Bitmap.Config.ARGB_8888);
    }

    public void registerInputHandler(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    public void handleInput() {
        if (inputHandler != null) {
            List<Input.ExternalEvent> externalEvents = input.getExternalEvents();
            List<Input.TouchEvent> touchEvents = input.getTouchEvents();
            List<Input.KeyEvent> keyEvents = input.getKeyEvents();

            int len = externalEvents.size();
            for (int i = 0; i < len; i++) {
                inputHandler.onExternalEvent(externalEvents.get(i));
            }

            len = touchEvents.size();
            for (int i = 0; i < len; i++) {
                inputHandler.onTouchEvent(touchEvents.get(i));
            }

//            len = keyEvents.size();
//            for (int i = 0; i < len; i++) {
//                inputHandler.onKeyEvent(keyEvents.get(i));
//            }
        }
    }

    protected float convertWidth(float x) {
        return width * x;
    }

    protected float convertHeight(float y) {
        return height * y;
    }

    public abstract void resume();

    public abstract void pause();

    public abstract void update(float delta);

    public abstract void present();

    public void dispose() {
        frameBuffer.dispose();
    }

    public RenderContext getFrameBuffer() {
        return frameBuffer;
    }

    public String getName() {
        return name;
    }
}
