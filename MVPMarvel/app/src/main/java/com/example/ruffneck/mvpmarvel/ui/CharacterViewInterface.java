package com.example.ruffneck.mvpmarvel.ui;
import com.example.ruffneck.mvpmarvel.models.CharacterDataWrapper;

public interface CharacterViewInterface {
    void showToastCharacter(String s);
    void displayCharacter(CharacterDataWrapper characterResponse);
    void displayErrorCharacter(String s);
}
