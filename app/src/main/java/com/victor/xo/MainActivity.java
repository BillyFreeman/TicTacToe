package com.victor.xo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    public static String player;

    private String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int[] idArray = {R.id.x_mode, R.id.o_mode, R.id.new_game, R.id.settings, R.id.statistics, R.id.exit};
        for (int i : idArray) {
            findViewById(i).setOnClickListener(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        player = sp.getString("playersName", "Player");

//        if("NULL".equalsIgnoreCase(player)){
//            startActivity(new Intent(this, PrefActivity.class));
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_settings){
            startActivity(new Intent(this, PrefActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.x_mode:
            case R.id.o_mode:
                mode = ((Button) v).getText().toString();
                Toast.makeText(this, "You will play for " + mode, Toast.LENGTH_SHORT).show();
                break;
            case R.id.new_game:
                Intent gameIntent = new Intent(this, GameActivity.class);
                gameIntent.putExtra("game_mode", mode);
                startActivity(gameIntent);
                break;
            case R.id.settings:
                startActivity(new Intent(this, PrefActivity.class));
                break;
            case R.id.statistics:
                break;
            case R.id.exit:
                finish();
                break;
        }
    }
}
