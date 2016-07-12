package com.victor.xo;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Віктор on 05.05.2015.
 */
public class GameActivity extends ActionBarActivity implements View.OnClickListener {

    public static final String USED_LIST = "used_list";

    private String playersMode;
    private String aiMode;
    private ArrayList<View> field;
    private ArrayList<View> used;
    private String[] usedArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        field = new ArrayList<>();
        used = new ArrayList<>();
        usedArray = new String[]{"", "", "", "", "", "", "", "", ""};
        Button b;
        Intent intent = getIntent();
        playersMode = intent.getStringExtra("game_mode");
        if (playersMode == null) playersMode = randomeMode();
        aiMode = "X".equalsIgnoreCase(playersMode) ? "O" : "X";
        int[] idArray = {R.id.cell_1_1, R.id.cell_1_2, R.id.cell_1_3,
                R.id.cell_2_1, R.id.cell_2_2, R.id.cell_2_3,
                R.id.cell_3_1, R.id.cell_3_2, R.id.cell_3_3};
        for (int i : idArray) {
            b = (Button) findViewById(i);
            b.setOnClickListener(this);
            b.setText("");
            field.add(b);
            used.add(b);
        }
        System.out.println(savedInstanceState);
        if (savedInstanceState != null) {
            usedArray = savedInstanceState.getStringArray(USED_LIST);
        } else {
            if ("X".equalsIgnoreCase(aiMode)) aiMove();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        TextView text = (TextView) findViewById(R.id.players_name_view);
        text.setText(MainActivity.player);
    }

    @Override
    public void onClick(View v) {
        Button b = (Button) v;
        b.setText(playersMode);
        usedArray[field.indexOf(v)] = b.getText().toString();
        field.remove(b);
        if (!isWinner(playersMode)) {
            aiMove();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArray(USED_LIST, usedArray);
    }

    private String randomeMode() {
        return ((int) Math.random()) == 1 ? "X" : "O";
    }

    private void aiMove() {
        int index = (int) (Math.random() * (field.size() - 1));
        Button b = (Button) field.get(index);
        b.setText(aiMode);
        field.remove(b);
        isWinner(aiMode);
    }

    private boolean isWinner(String player) {
        boolean winnFlag = false;
        Button b1;
        Button b2;
        Button b3;
        for (View v : used) {
            b1 = (Button) v;
            b2 = (Button) v;
            b3 = (Button) v;
            if (player.equalsIgnoreCase(b1.getText().toString())) {
                switch (used.indexOf(v)) {
                    case 0:
                        b2 = (Button) used.get(used.indexOf(v) + 4);
                        b3 = (Button) used.get(used.indexOf(v) + 8);
                        if (player.equalsIgnoreCase(b2.getText().toString())
                                && player.equalsIgnoreCase(b3.getText().toString())) {
                            winnFlag = true;
                            break;
                        }
                        b2 = (Button) used.get(used.indexOf(v) + 3);
                        b3 = (Button) used.get(used.indexOf(v) + 6);
                        if (player.equalsIgnoreCase(b2.getText().toString())
                                && player.equalsIgnoreCase(b3.getText().toString())) {
                            winnFlag = true;
                            break;
                        }
                        b2 = (Button) used.get(used.indexOf(v) + 1);
                        b3 = (Button) used.get(used.indexOf(v) + 2);
                        if (player.equalsIgnoreCase(b2.getText().toString())
                                && player.equalsIgnoreCase(b3.getText().toString())) {
                            winnFlag = true;
                        }
                        break;
                    case 1:
                        b2 = (Button) used.get(used.indexOf(v) + 3);
                        b3 = (Button) used.get(used.indexOf(v) + 6);
                        if (player.equalsIgnoreCase(b2.getText().toString())
                                && player.equalsIgnoreCase(b3.getText().toString())) {
                            winnFlag = true;
                        }
                        break;
                    case 2:
                        b2 = (Button) used.get(used.indexOf(v) + 3);
                        b3 = (Button) used.get(used.indexOf(v) + 6);
                        if (player.equalsIgnoreCase(b2.getText().toString())
                                && player.equalsIgnoreCase(b3.getText().toString())) {
                            winnFlag = true;
                            break;
                        }
                        b2 = (Button) used.get(used.indexOf(v) + 2);
                        b3 = (Button) used.get(used.indexOf(v) + 4);
                        if (player.equalsIgnoreCase(b2.getText().toString())
                                && player.equalsIgnoreCase(b3.getText().toString())) {
                            winnFlag = true;
                        }
                        break;
                    case 3:
                    case 6:
                        b2 = (Button) used.get(used.indexOf(v) + 1);
                        b3 = (Button) used.get(used.indexOf(v) + 2);
                        if (player.equalsIgnoreCase(b2.getText().toString())
                                && player.equalsIgnoreCase(b3.getText().toString())) {
                            winnFlag = true;
                            break;
                        }
                    default:
                        if (field.isEmpty()) gameEnd("No winner");
                }
            }
            if (winnFlag) {
                firework(b1, b2, b3);
                String message = player.equalsIgnoreCase(playersMode)
                        ? "Congratulations! You won!" : "You lost!";
                gameEnd(message);
                break;
            }
        }
        return winnFlag;
    }

    private void firework(Button b1, Button b2, Button b3) {
        b1.setTextColor(getResources().getColor(R.color.second_theme));
        b2.setTextColor(getResources().getColor(R.color.second_theme));
        b3.setTextColor(getResources().getColor(R.color.second_theme));
    }

    private void gameEnd(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Intent backIntent = new Intent(this, MainActivity.class);
        startActivity(backIntent);
    }
}
