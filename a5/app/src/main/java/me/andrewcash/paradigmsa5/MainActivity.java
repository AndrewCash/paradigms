package me.andrewcash.paradigmsa5;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainActivity extends AppCompatActivity
{
    Model model;
    GameView view;
    GameController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        model = new Model();
        view = new GameView(this, model);
        controller = new GameController(model, view);
        setContentView(view);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        controller.resume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        controller.pause();
    }



    static class Model
    {
        boolean flap = false;
        boolean flapped_recently = false;
        float x = 200.0f;
        float y = 200.0f;
        float yVelocity = -8.0f;

        void update()
        {
            if(flap)
            {
                yVelocity = -24.0f;
                flap = false;
            }

            // Fall
            yVelocity += 1.8f;
            y += yVelocity;

            // Bounce on the bottom of the screen
            if(y > 700.0f)
            {
                y = 700.0f;
                yVelocity = -6.0f;
            }
        }
    }




    static class GameView extends SurfaceView
    {
        SurfaceHolder ourHolder;
        Canvas canvas;
        Paint paint;
        Model model;
        GameController controller;
        Bitmap bird1;
        Bitmap bird2;

        public GameView(Context context, Model m)
        {
            super(context);
            model = m;

            // Initialize ourHolder and paint objects
            ourHolder = getHolder();
            paint = new Paint();

            // Load the images
            bird1 = BitmapFactory.decodeResource(this.getResources(),
                    R.mipmap.bird1);
            bird2 = BitmapFactory.decodeResource(this.getResources(),
                    R.mipmap.bird2);
        }

        void setController(GameController c)
        {
            controller = c;
        }

        public void update()
        {
            if (!ourHolder.getSurface().isValid())
                return;
            canvas = ourHolder.lockCanvas();

            // Draw the background color
            canvas.drawColor(Color.argb(255, 128, 200, 200));

            // Draw the score
            /*paint.setColor(Color.argb(255,  200, 128, 0));
            paint.setTextSize(45);
            canvas.drawText("Score:" + score, 25, 35, paint);*/

            // Draw the turtle
            if(model.flapped_recently)
                canvas.drawBitmap(bird2, model.x, model.y, paint);
            else
                canvas.drawBitmap(bird1, model.x, model.y, paint);

            ourHolder.unlockCanvasAndPost(canvas);
        }

        // The SurfaceView class (which GameView extends) already
        // implements onTouchListener, so we override this method
        // and pass the event to the controller.
        @Override
        public boolean onTouchEvent(MotionEvent motionEvent)
        {
            controller.onTouchEvent(motionEvent);
            return true;
        }
    }




    static class GameController implements Runnable
    {
        volatile boolean playing;
        Thread gameThread = null;
        Model model;
        GameView view;

        GameController(Model m, GameView v)
        {
            model = m;
            view = v;
            view.setController(this);
            playing = true;
        }

        void update()
        {
        }

        @Override
        public void run()
        {
            while(playing)
            {
                //long time = System.currentTimeMillis();
                this.update();
                model.update();
                view.update();

                try {
                    Thread.sleep(20);
                } catch(Exception e) {
                    Log.e("Error:", "sleeping");
                    System.exit(1);
                }
            }
        }

        void onTouchEvent(MotionEvent motionEvent)
        {
            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN: // Player touched the screen
                    model.flap = true;
                    model.flapped_recently = true;
                    break;

                case MotionEvent.ACTION_UP: // Player withdrew finger
                    model.flapped_recently = false;
                    break;
            }
        }

        // Shut down the game thread.
        public void pause() {
            playing = false;
            try {
                gameThread.join();
            } catch (InterruptedException e) {
                Log.e("Error:", "joining thread");
                System.exit(1);
            }

        }

        // Restart the game thread.
        public void resume() {
            playing = true;
            gameThread = new Thread(this);
            gameThread.start();
        }
    }
}
