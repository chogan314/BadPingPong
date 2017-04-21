package test.com.badpingpong.cory.input;

import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import test.com.badpingpong.cory.input.Input.TouchEvent;
import test.com.badpingpong.cory.util.Pool;
import test.com.badpingpong.cory.util.Pool.PoolObjectFactory;

/**
 * Created by Cory on 4/17/2017.
 */

public class TouchHandler implements View.OnTouchListener {
    private final boolean[] isTouched = new boolean[20];
    private final int[] touchX = new int[20];
    private final int[] touchY = new int[20];
    private final Pool<TouchEvent> touchEventPool;
    private final List<TouchEvent> touchEvents = new ArrayList<>();
    private final List<TouchEvent> touchEventsBuffer = new ArrayList<>();

    public TouchHandler(View view) {
        PoolObjectFactory<TouchEvent> factory = new PoolObjectFactory<TouchEvent>() {
            @Override
            public TouchEvent createObject() {
                return new TouchEvent();
            }
        };
        touchEventPool = new Pool<>(factory, 100);
        view.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        v.performClick();
        synchronized (this) {
            int action = event.getAction() & MotionEvent.ACTION_MASK;
            int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >>
                    MotionEvent.ACTION_POINTER_INDEX_SHIFT;
            int pointerId = event.getPointerId(pointerIndex);
            TouchEvent touchEvent;

            switch (action) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:
                    touchEvent = touchEventPool.newObject();
                    touchEvent.type = TouchEvent.TOUCH_DOWN;
                    touchEvent.pointer = pointerId;
                    touchEvent.x = touchX[pointerId] = (int) event.getX(pointerIndex);
                    touchEvent.y = touchY[pointerId] = (int) event.getY(pointerIndex);
                    isTouched[pointerId] = true;
                    touchEventsBuffer.add(touchEvent);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                case MotionEvent.ACTION_CANCEL:
                    touchEvent = touchEventPool.newObject();
                    touchEvent.type = TouchEvent.TOUCH_UP;
                    touchEvent.pointer = pointerId;
                    touchEvent.x = touchX[pointerId] = (int) event.getX(pointerIndex);
                    touchEvent.y = touchY[pointerId] = (int) event.getY(pointerIndex);
                    isTouched[pointerId] = false;
                    touchEventsBuffer.add(touchEvent);
                    break;
                case MotionEvent.ACTION_MOVE:
                    int pointerCount = event.getPointerCount();
                    for (int i = 0; i < pointerCount; i++) {
                        pointerIndex = i;
                        pointerId = event.getPointerId(pointerIndex);

                        touchEvent = touchEventPool.newObject();
                        touchEvent.type = TouchEvent.TOUCH_DRAGGED;
                        touchEvent.pointer = pointerId;
                        touchEvent.x = touchX[pointerId] = (int) (event.getX(pointerIndex));
                        touchEvent.y = touchY[pointerId] = (int) (event.getY(pointerIndex));
                        touchEventsBuffer.add(touchEvent);
                    }
                    break;
            }

            return true;
        }
    }

    public boolean isTouchDown(int pointer) {
        synchronized (this) {
            return !(pointer < 0 || pointer >= 20) && isTouched[pointer];
        }
    }

    public int getTouchX(int pointer) {
        synchronized (this) {
            if (pointer < 0 || pointer >= 20) {
                return 0;
            } else {
                return touchX[pointer];
            }
        }
    }

    public int getTouchY(int pointer) {
        synchronized (this) {
            if (pointer < 0 || pointer >= 20) {
                return 0;
            } else {
                return touchY[pointer];
            }
        }
    }

    public List<TouchEvent> getTouchEvents() {
        synchronized (this) {
            int len = touchEvents.size();
            for (int i = 0; i < len; i++) {
                touchEventPool.free(touchEvents.get(i));
            }
            touchEvents.clear();
            touchEvents.addAll(touchEventsBuffer);
            touchEventsBuffer.clear();
            return touchEvents;
        }
    }
}
