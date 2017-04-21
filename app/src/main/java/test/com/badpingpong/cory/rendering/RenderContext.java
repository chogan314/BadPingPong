package test.com.badpingpong.cory.rendering;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import test.com.badpingpong.cory.math.Rect;
import test.com.badpingpong.cory.math.Vec2;

/**
 * Created by Cory on 4/17/2017.
 */

public class RenderContext {
    private final Bitmap source;
    private final Canvas canvas;
    private final Paint paint;
    private final android.graphics.Rect bounds;

    public RenderContext(int width, int height, Bitmap.Config config) {
        source = Bitmap.createBitmap(width, height, config);
        canvas = new Canvas(source);
        paint = new Paint();
        bounds = new android.graphics.Rect();
    }

    private RenderContext(RenderContext parent) {
        source = parent.source.copy(parent.source.getConfig(), true);
        canvas = new Canvas(source);
        paint = new Paint(parent.paint);
        bounds = new android.graphics.Rect(parent.bounds);
    }

    public RenderContext copy() {
        return new RenderContext(this);
    }

    public void setAntiAlias(boolean antiAlias) {
        paint.setAntiAlias(antiAlias);
    }

    public void clear(int r, int g, int b) {
        canvas.drawRGB(r, g, b);
    }

    public void setBrushColor(int r, int g, int b) {
        paint.setARGB(255, r, g, b);
    }

    public void drawRectangle(Vec2 position, float width, float height) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(position.getX(), position.getY(), position.getX() + width,
                position.getY() + height, paint);
    }

    public void drawRectangle(Rect rect) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(rect.getPosition().getX(), rect.getPosition().getY(),
                rect.getPosition().getX() + rect.getWidth(),
                rect.getPosition().getY() + rect.getHeight(), paint);
    }

    public void drawCircle(Vec2 position, float radius) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(position.getX(), position.getY(), radius, paint);
    }

    public void drawBitmap(Bitmap bitmap) {
        canvas.getClipBounds(bounds);
        canvas.drawBitmap(bitmap, null, bounds, null);
    }

    public void drawBitmap(Bitmap bitmap, float xOffset, float yOffset) {
        canvas.drawBitmap(bitmap, xOffset, yOffset, null);
    }

    public void dispose() {
        source.recycle();
    }

    public Bitmap getSource() {
        return source;
    }
}
