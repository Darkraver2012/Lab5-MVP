package com.example.ruffneck.mvpmarvel.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.ruffneck.mvpmarvel.models.Character;
import android.widget.Toast;

import com.example.ruffneck.mvpmarvel.R;
import com.example.ruffneck.mvpmarvel.adapters.BoxAdapter;
import com.example.ruffneck.mvpmarvel.models.CharacterDataWrapper;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements MainViewInterface{

    ListView ldMain;
    private String TAG = "MainActivity";
    BoxAdapter boxAdapter;
    Context context;
    MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Type listType = new TypeToken<ArrayList<Character>>(){}.getType();
        context = this;
        ldMain = findViewById(R.id.lvMain);

        setupMVP();
        getCharacterList();
    }

    private void setupMVP() {
        mainPresenter = new MainPresenter(this, this);
    }

    private void getCharacterList() {
        mainPresenter.getCharacters();
    }

    @Override
    public void showToast(String str) {
        Toast.makeText(MainActivity.this,str,Toast.LENGTH_LONG).show();
    }

    @Override
    public void displayCharacters(CharacterDataWrapper characterResponse) {
        if(characterResponse!=null) {
            Log.d(TAG,String.valueOf(characterResponse.getData().getResults().size()));
            try{
                if(characterResponse.getData().getResults().size() > 0){
                    setAdapter(characterResponse);
                }
            } catch (NullPointerException e){
                Log.e("MainActivity", "NullPointerException  => " + e.getMessage());
            }
        }else{
            Log.d(TAG,"Characters response null");
        }
    }

    @Override
    public void displayError(String e) {
        showToast(e);
    }

    public void setAdapter (CharacterDataWrapper characterResponse) {
        ArrayList<Character> al = characterResponse.getData().getResults();
        Collections.shuffle(al);

        ArrayList<Character> al2 = new ArrayList<>();
        for (int j = 0; j < 10; j++) {
            al2.add(al.get(j));
        }


        boxAdapter = new BoxAdapter(context, al2);
        ldMain.setAdapter(boxAdapter);

        ldMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                mainPresenter.ItemClicked(id, al2);
            }
        });
    }
}