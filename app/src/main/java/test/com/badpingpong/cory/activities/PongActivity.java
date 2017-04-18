package test.com.badpingpong.cory.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import test.com.badpingpong.R;
import test.com.badpingpong.cory.rendering.RenderView;
import test.com.badpingpong.cory.stages.PongStage;
import test.com.badpingpong.cory.stages.Stage;

public class PongActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pong);

        RenderView<PongStage> pongView = new RenderView<>(this,
                new Stage.StageFactory<PongStage>() {
                    @Override
                    public PongStage createStage(View view) {
                        return new PongStage(view);
                    }
                });

        FrameLayout frame = (FrameLayout) findViewById(R.id.pong_frame);
        frame.addView(pongView);
    }
}
