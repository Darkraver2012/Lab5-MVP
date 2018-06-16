package com.example.ruffneck.mvpmarvel.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ruffneck.mvpmarvel.R;
import com.example.ruffneck.mvpmarvel.models.CharacterDataWrapper;

import com.squareup.picasso.Picasso;

public class CharacterDetail extends AppCompatActivity implements CharacterViewInterface{

    TextView Name, Description;
    ImageView Image;
    Context context;
    CharacterPresenter characterPresenter;
    private String TAG = "CharacterDetail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_detail);

        context = this;

        Name = findViewById(R.id.detail_name);
        Description = findViewById(R.id.detail_description);
        Image = findViewById(R.id.detail_image);

        Intent intent = getIntent();
        int p = (int) intent.getSerializableExtra("Character");

        setupCVP();
        getCharacterList(p);
    }

    private void setupCVP() {
        characterPresenter = new CharacterPresenter(this, this);
    }

    private void getCharacterList(int p) {
        characterPresenter.getCharacters(p);
    }

    @Override
    public void showToastCharacter(String str) {
        Toast.makeText(CharacterDetail.this,str,Toast.LENGTH_LONG).show();
    }

    @Override
    public void displayCharacter(CharacterDataWrapper characterResponse) {
        if(characterResponse!=null) {
            Log.d(TAG, String.valueOf(characterResponse.getData().getResults().size()));
            try{
                if(characterResponse.getData().getResults().size() > 0){
                    setAdapter(characterResponse);
                }
            } catch (NullPointerException e){
                Log.e("CharacterDetail", "NullPointerException  => " + e.getMessage());
            }
        }else{
            Log.d(TAG,"Characters response null");
        }
    }

    @Override
    public void displayErrorCharacter(String e) {
        showToastCharacter(e);
    }

    public void setAdapter (CharacterDataWrapper response) {
        Name.setText(response.getData().getResults().get(0).getName());
        Description.setText(response.getData().getResults().get(0).getDescription());

        String imagePath = response.getData().getResults().get(0).getThumbnail().getPath()+ "/standard_xlarge" + ".";
        String imageExtension =  response.getData().getResults().get(0).getThumbnail().getExtension();
        String imageUrl = imagePath + imageExtension;
        Picasso.get().load(imageUrl).into(Image);
    }
}
