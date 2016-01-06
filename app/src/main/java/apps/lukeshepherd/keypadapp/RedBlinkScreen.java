package apps.lukeshepherd.keypadapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.os.*;


public class RedBlinkScreen extends ActionBarActivity {

    private static final int RED_COLOR = Color.RED;
    private static final int WHITE_COLOR = Color.WHITE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the message from the intent
        Intent intent = getIntent();
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_red_blink_screen);

        View screen = findViewById(R.id.red_light);


        String blink_var = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        switch(blink_var) {
            case "solid":
                screen.setBackgroundColor(RED_COLOR);
                break;

            case "blink":
                blinkScreen(screen, 250);
                break;

            default:
                System.out.println("default");
                break;
        }

        //screen.setBackgroundColor(0xffce2029);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private static void blinkScreen(View screen, final long blinkRate) {
        final Handler handler = new Handler();
        final View newscreen = screen;
        final Runnable r = new Runnable() {
            private boolean red = true;
            public void run() {
                handler.postDelayed(this, blinkRate);
                if(red) newscreen.setBackgroundColor(WHITE_COLOR);
                else newscreen.setBackgroundColor(RED_COLOR);
                red = !red;
            }
        };
        handler.postDelayed(r, blinkRate);
    }

}
