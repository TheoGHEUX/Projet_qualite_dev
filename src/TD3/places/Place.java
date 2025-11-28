package TD3.places;

import TD3.characters.Character;
import java.util.ArrayList;
import java.util.List;

//lieu
public class Place {
    protected String name;
    protected int surface; //superficie
    protected List<Character> the_characters_present; // à modifier
    protected String[] the_aliments_present; // à modifier

    public Place(String name, int surface) {
        this.name = name;
        this.surface = surface;
        this.the_characters_present = new ArrayList<>();
    }

    public boolean canAccept(Character character) {
        return false;
    }

    public String getName() {
        return name;
    }
    public int getSurface() {
        return surface;
    }


    public List<Character> getThe_characters_present() {
        return the_characters_present;
    }
    public String[] getThe_aliments_present() {
        return the_aliments_present;
    }

    public void showPlaceInfos(){
        System.out.println(this.getName() + " | Surface: " + this.getSurface() + " | liste des personnages: " + this.getThe_characters_present() + " | Liste des aliments: " + this.getThe_aliments_present());
    }



}
