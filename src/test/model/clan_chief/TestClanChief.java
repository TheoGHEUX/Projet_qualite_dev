package test.model.clan_chief;

import main.enums.CharacterType;
import main.enums.FoodType;
import main.enums.Sex;
import main.model.character.category.roman.job.General;
import main.model.clan_chief.ClanChief;
import main.model.character.Character;
import main.model.items.food.Food;
import main.model.items.food.FoodStats;
import main.model.place.category.place_with_clan_chief.RomanTown;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TestClanChief {

    private ClanChief cc;
    private RomanTown rome;

    @BeforeEach
    void setUp() {
        cc = new ClanChief("César", Sex.MALE);
        rome = new RomanTown("Rome", 1285000000, null);
    }

    @Test
    void testConstructorAndGetters() {
        Assertions.assertEquals("César", cc.getName());
        Assertions.assertTrue(cc.getAge() >= 30 && cc.getAge() <= 90);
        Assertions.assertNull(cc.getPlace());
        Assertions.assertEquals(Sex.MALE, cc.getSex());
    }

    @Test
    void testClearSetPlace(){

        cc.setPlace(rome);
        Assertions.assertEquals(rome, cc.getPlace());

        cc.clearPlace();
        Assertions.assertNull(cc.getPlace());
    }

    @Test
    void testCreateCharacter() {

        cc.setPlace(rome);
        cc.createCharacter("Auguste", Sex.MALE, CharacterType.GENERAL);
        cc.createCharacter("Vercingétorix", Sex.MALE, CharacterType.DRUID);

        List<String> romePeople = new ArrayList<>();
        romePeople.add("Auguste");

        Assertions.assertEquals(romePeople,rome.showPresentCharactersNames());
    }

    @Test
    void testFeedCharacters() {
        cc.setPlace(rome);

        Food mistletoe = FoodStats.newFood(FoodType.MISTLETOE);
        rome.addFood(mistletoe);
        General auguste = new General("Auguste", Sex.MALE);
        double oldHeatlh = auguste.getHealth();
        auguste.updateHealth(-50);

        rome.addCharacter(auguste);
        cc.feedCharacters();

        Assertions.assertEquals(oldHeatlh - 50 + mistletoe.getHealthEffect(), auguste.getHealth());
    }

}
