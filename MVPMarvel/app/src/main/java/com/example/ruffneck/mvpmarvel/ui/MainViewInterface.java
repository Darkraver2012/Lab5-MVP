package com.example.ruffneck.mvpmarvel.ui;
import com.example.ruffneck.mvpmarvel.models.CharacterDataWrapper;

public interface MainViewInterface {
    void showToast(String s);
    void displayCharacters(CharacterDataWrapper charactersResponse);
    void displayError(String s);
}
