package com.example.ruffneck.mvpmarvel.ui;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.example.ruffneck.mvpmarvel.models.CharacterDataWrapper;
import com.example.ruffneck.mvpmarvel.network.NetworkClient;
import com.example.ruffneck.mvpmarvel.utils.CredentialsUtils;

import java.util.ArrayList;

import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

import static com.example.ruffneck.mvpmarvel.utils.CredentialsUtils.offset;
import static com.example.ruffneck.mvpmarvel.utils.CredentialsUtils.public_key;
import static com.example.ruffneck.mvpmarvel.utils.CredentialsUtils.limit;
import static com.example.ruffneck.mvpmarvel.utils.CredentialsUtils.ts;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

import com.example.ruffneck.mvpmarvel.models.Character;

public class MainPresenter {

    private MainViewInterface mvi;
    private String TAG = "MainPresenter";
    private Subscription subscription;
    private Context context;

    public MainPresenter(MainViewInterface mvi, Context context) {
        this.mvi = mvi;
        this.context = context;

    }

    public void getCharacters() {
        subscription = NetworkClient
                .getRetrofit()
                .create(NetworkClient.MarvelApiEndPoint.class)
                .getCharacters(ts, limit, offset, public_key, CredentialsUtils.getHash())
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
                .subscribe(new Subscriber<CharacterDataWrapper>() {
                       @Override
                       public void onCompleted() {
                           Log.d(TAG,"Completed");
                       }

                       @Override
                       public void onError(Throwable e) {
                           Log.d(TAG,"Error "+e);
                           e.printStackTrace();
                           mvi.displayError("Error fetching Characters Data");
                       }

                       @Override
                       public void onNext(CharacterDataWrapper response) {
                           Log.d(TAG,"OnNext "+String.valueOf(response.getData().getResults().size()));
                           mvi.displayCharacters(response);
                       }
                   }
                );
    }

    public void ItemClicked(long id, ArrayList arr) {
       int p_id = (int) id;
       Character p = (Character) arr.get(p_id);
        int characterId = p.getId();

        Intent intent = new Intent(context, CharacterDetail.class);
        intent.putExtra("Character", characterId);
        context.startActivity(intent);
    }
}