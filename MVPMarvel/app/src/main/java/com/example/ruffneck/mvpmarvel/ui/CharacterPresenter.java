package com.example.ruffneck.mvpmarvel.ui;

import android.content.Context;
import android.util.Log;

import com.example.ruffneck.mvpmarvel.models.CharacterDataWrapper;
import com.example.ruffneck.mvpmarvel.network.NetworkClient;
import com.example.ruffneck.mvpmarvel.utils.CredentialsUtils;

import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

import static com.example.ruffneck.mvpmarvel.utils.CredentialsUtils.public_key;
import static com.example.ruffneck.mvpmarvel.utils.CredentialsUtils.ts;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

public class CharacterPresenter {

    private CharacterViewInterface cvi;
    private String TAG = "CharacterPresenter";
    private Subscription subscription;
    private Context context;

    public CharacterPresenter(CharacterViewInterface cvi, Context context) {
        this.cvi = cvi;
        this.context = context;
    }

    public void getCharacters(int id) {
        subscription = NetworkClient
                .getRetrofit()
                .create(NetworkClient.MarvelApiEndPoint2.class)
                .getCharacter(ts, id, public_key, CredentialsUtils.getHash())
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
                .subscribe(new Subscriber<CharacterDataWrapper>() {
                       @Override
                       public void onCompleted() {
                           Log.d(TAG, "Completed");
                       }

                       @Override
                       public void onError(Throwable e) {
                           Log.d(TAG, "Error " + e);
                           e.printStackTrace();
                           cvi.displayErrorCharacter("Error fetching Character Data");
                       }

                       @Override
                       public void onNext(CharacterDataWrapper response) {
                           Log.d(TAG, "OnNext " + String.valueOf(response.getData().getResults().size()));
                           cvi.displayCharacter(response);
                       }
                   }
                );
    }
}
