package apps.lukeshepherd.keypadapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.logging.Handler;
import java.util.logging.LogRecord;


public class MainActivity extends ActionBarActivity {

    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    public final int LIGHT_BLINK_INTERVAL = 250;
    private boolean running = false;
    boolean lightIsOn = false;
    private boolean flashOkay = true; /*getPackageManager().hasSystemFeature(
            PackageManager.FEATURE_CAMERA_FLASH);*/

    public Context context;
    public static Camera cam = Camera.open();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //if()
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    public void blinkRed(View view) {
        Intent intent = new Intent(this, RedBlinkScreen.class);
        String message = "blink";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void solidRed(View view) {
        Intent intent = new Intent(this, RedBlinkScreen.class);
        String message = "solid";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    /* Turn light on. Assumes Camera cam has been opened */
    public void lightOn(View view) {
        running = false;
        try {
            if (flashOkay) {
                Camera.Parameters p = cam.getParameters();
                p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                cam.setParameters(p);
                cam.startPreview();
            }
        } catch (Exception e) {
           // e.printStackTrace();
           // Toast.makeText(getBaseContext(), "Exception flashLightOn()",
                 //   Toast.LENGTH_SHORT).show();
        }

    }

    /* Turn light off. Assumes Camera cam has been opened */
    public void lightOff(View view) {
        running = false;
        try {
            if (flashOkay) {

                Camera.Parameters p = cam.getParameters();
                p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                cam.setParameters(p);
                cam.startPreview();
            }
        } catch (Exception e) {
          //  e.printStackTrace();
           // Toast.makeText(getBaseContext(), "Exception flashLightOff",
             //       Toast.LENGTH_SHORT).show();
        }
    }

    public void blinkLight(final View view) {
        if(running) return;
        final android.os.Handler handler = new android.os.Handler();
        final Runnable r = new Runnable() {

            @Override
            public void run() {
                if(!running) return;
                if(!lightIsOn) {
                    lightOn(view);
                    running = true;
                }
                else {
                    lightOff(view);
                    running = true;
                }
                lightIsOn = !lightIsOn;
                handler.postDelayed(this, LIGHT_BLINK_INTERVAL);
            }
        };
        running = true;
       // r.run();
        handler.postDelayed(r,LIGHT_BLINK_INTERVAL);

    }
}
