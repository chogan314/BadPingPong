package test.com.badpingpong.cory.input;

/**
 * Created by Cory on 4/18/2017.
 */

public interface InputHandler {
    void onExternalEvent(Input.ExternalEvent event);

    void onTouchEvent(Input.TouchEvent event);

    void onKeyEvent(Input.KeyEvent event);
}
