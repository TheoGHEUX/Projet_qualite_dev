package com.example.TD3.characters.roman;

import com.example.TD3.characters.Character;
import com.example.TD3.enums.Sex;

import java.util.Random;

public abstract class Roman extends Character {

    // Variable utile à la génération de l'âge et la taille aléatoire
    protected static final Random RANDOM = new Random();

    public Roman(String name, Sex sex, int size, int age, int strength, int stamina, int health) {
        super(name, sex, size, age, strength, stamina, health);
    }

    // Méthode pour générer aléatoirement un nombre dans un intervalle (utilisée pour générer la taille et l'âge)
    protected static int randomBetween(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }
}
