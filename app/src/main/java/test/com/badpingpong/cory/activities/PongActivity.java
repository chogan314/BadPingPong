package test.com.badpingpong.cory.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import test.com.badpingpong.R;
import test.com.badpingpong.cory.input.ExternalEventHandler;
import test.com.badpingpong.cory.input.ExternalEventHandler.ExternalEventSource;
import test.com.badpingpong.cory.rendering.RenderView;
import test.com.badpingpong.cory.stages.PongStage;
import test.com.badpingpong.cory.stages.Stage;

public class PongActivity extends Activity implements ExternalEventSource {
    private ExternalEventHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pong);

        RenderView<PongStage> pongView = new RenderView<>(this, this,
                new Stage.StageFactory<PongStage>() {
                    @Override
                    public PongStage createStage(View view, ExternalEventSource source) {
                        return new PongStage(view, source);
                    }
                });

        FrameLayout frame = (FrameLayout) findViewById(R.id.pong_frame);
        frame.addView(pongView);

        Button upButton = (Button) findViewById(R.id.up_button);
        upButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    handler.queueEvent(handler.createEvent("UP_PRESS"));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    handler.queueEvent(handler.createEvent("UP_RELEASE"));
                }
                return false;
            }
        });

        Button downButton = (Button) findViewById(R.id.down_buton);
        downButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    handler.queueEvent(handler.createEvent("DOWN_PRESS"));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    handler.queueEvent(handler.createEvent("DOWN_RELEASE"));
                }
                return false;
            }
        });
    }

    @Override
    public void registerListener(ExternalEventHandler handler) {
        this.handler = handler;
    }
}
