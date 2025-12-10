package test.model.character;

import main.enums.PotionEffect;
import main.enums.Sex;
import main.enums.FoodType;
import main.model.character.category.gaul.job.*;
import main.model.character.category.roman.job.*;
import main.model.character.category.creature.species.Werewolf;
import main.model.items.food.Food;
import main.model.items.food.FoodStats;
import main.model.character.Character;
import main.model.items.potion.Potion;
import main.model.place.Place;
import main.model.place.category.place_with_clan_chief.GallicVillage;
import main.model.place.category.place_with_clan_chief.GalloRomanSettlement;
import main.model.place.category.place_with_clan_chief.RomanFortifiedCamp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
class CharacterTest {

    private Druid druid;
    private Blacksmith blacksmith;
    private Werewolf werewolf;
    private Legionary legionary;
    private Place place;

    @BeforeEach
    void setUp() {
        place = new GalloRomanSettlement("Village", 50000, null);
        druid = new Druid("Panoramix", Sex.MALE);
        blacksmith = new Blacksmith("Forge", Sex.MALE);
        werewolf = new Werewolf("Loup", Sex.MALE);
        legionary = new Legionary("Marcus", Sex.MALE);

        druid.modifyCurrentPlace(place);
        blacksmith.modifyCurrentPlace(place);
    }

    @Test
    void testCharacterCreation() {
        assertEquals("Panoramix", druid.getName());
        assertEquals(Sex.MALE, druid.getSex());
        assertEquals("Gaul", druid.getNationality());
        assertEquals("Druid", druid.getType());
        assertTrue(druid.isAlive());
        assertEquals(100, druid.getHunger());
    }

    @Test
    void testFight() {
        double initialHealth = legionary.getHealth();
        double druidStrength = druid.getStrength();
        int initialStamina = druid.getStamina();

        druid.fight(legionary);

        assertEquals(initialHealth - druidStrength, legionary.getHealth());
        assertEquals(initialStamina - 10, druid.getStamina());
    }

    @Test
    void testFightWithoutEnoughStamina() {
        druid.updateStamina(-60);
        double initialHealth = legionary.getHealth();

        druid.fight(legionary);

        assertEquals(initialHealth, legionary.getHealth());
    }

    @Test
    void testUpdateHealth() {
        double initialHealth = druid.getHealth();
        druid.updateHealth(20);
        assertEquals(initialHealth, druid.getHealth());

        druid.updateHealth(-30);
        assertEquals(initialHealth - 30, druid.getHealth());
    }

    @Test
    void testUpdateHealthBeyondMax() {
        druid.updateHealth(1000);
        assertEquals(druid.getMaxHealth(), druid.getHealth());
    }

    @Test
    void testDeathByHealthZero() {
        druid.updateHealth(-druid.getHealth());
        assertFalse(druid.isAlive());
    }

    @Test
    void testUpdateHunger() {
        druid.updateHunger(-20);
        assertEquals(80, druid.getHunger());

        druid.updateHunger(30);
        assertEquals(100, druid.getHunger());
    }

    @Test
    void testDeathByHungerZero() {
        druid.updateHunger(-100);
        assertFalse(druid.isAlive());
    }

    @Test
    void testUpdateStamina() {
        druid.updateStamina(-20);
        assertEquals(45, druid.getStamina());

        druid.updateStamina(50);
        assertEquals(druid.getMaxStamina(), druid.getStamina());
    }

    @Test
    void testEatFood() {
        Food food = FoodStats.newFood(FoodType.WILD_BOAR);
        int initialHunger = druid.getHunger();
        double initialHealth = druid.getHealth();

        druid.updateHunger(-50);
        druid.eat(food);

        assertTrue(druid.getHunger() > initialHunger - 50);
    }

    @Test
    void testEatTwoVegetablesInRow() {
        Food carrot1 = FoodStats.newFood(FoodType.CARROT);
        Food carrot2 = FoodStats.newFood(FoodType.CARROT);

        double healthAfterFirst = druid.getHealth();
        druid.eat(carrot1);
        assertTrue(druid.getHealth() >= healthAfterFirst);

        double healthAfterSecond = druid.getHealth();
        druid.eat(carrot2);
        assertTrue(druid.getHealth() < healthAfterSecond);
    }

    @Test
    void testGainStrength() {
        double initialStrength = druid.getStrength();
        druid.gainStrength(10);
        assertEquals(initialStrength + 10, druid.getStrength());
    }

    @Test
    void testModifyCurrentPlace() {
        Place newPlace = new RomanFortifiedCamp("Camp", 3, null);
        druid.modifyCurrentPlace(newPlace);
        assertEquals(newPlace, druid.getPlace());
    }

    @Test
    void testTransformStatue() {
        druid.transformStatue();
        assertTrue(druid.isStatue());
        assertFalse(druid.isAlive());
    }
}

class DruidTest {

    private Druid druid;
    private Legionary enemy;
    private Place place;

    @BeforeEach
    void setUp() {
        place = new GalloRomanSettlement("Village", 50000, null);
        druid = new Druid("Panoramix", Sex.MALE);
        enemy = new Legionary("Marcus", Sex.MALE);
        druid.modifyCurrentPlace(place);
        enemy.modifyCurrentPlace(place);
        place.addCharacter(druid);
        place.addCharacter(enemy);
    }

    @Test
    void testCombatTransfusion() {
        double initialDruidHealth = druid.getHealth();
        double initialEnemyHealth = enemy.getHealth();
        int initialStamina = druid.getStamina();

        druid.combat(enemy);

        assertTrue(enemy.getHealth() < initialEnemyHealth);
        assertTrue(druid.getHealth() == initialDruidHealth);
        assertEquals(initialStamina - 35, druid.getStamina());
    }

    @Test
    void testCombatWithoutEnoughStamina() {
        druid.updateStamina(-40);
        double initialEnemyHealth = enemy.getHealth();

        druid.combat(enemy);

        assertEquals(initialEnemyHealth, enemy.getHealth());
    }

    @Test
    void testLeadCallOfStar() {
        Blacksmith bm = new Blacksmith("Forgerix", Sex.MALE);
        List<Character> followers = new ArrayList<>();
        followers.add(druid);
        followers.add(bm);
        double bmHealthBefore = bm.getHealth();
        bm.updateHealth(-30);
        int druidStaminaBefore = druid.getStamina();

        druid.lead(followers);

        assertEquals(bmHealthBefore, bm.getHealth());
        assertEquals(druidStaminaBefore - 50, druid.getStamina());
    }
}

class BlacksmithTest {

    private Blacksmith blacksmith;
    private Druid druid;
    private Place place;

    @BeforeEach
    void setUp() {
        place = new GalloRomanSettlement("Village", 50000, null);
        blacksmith = new Blacksmith("Forge", Sex.MALE);
        druid = new Druid("Panoramix", Sex.MALE);
        blacksmith.modifyCurrentPlace(place);
        druid.modifyCurrentPlace(place);
        place.addCharacter(blacksmith);
        place.addCharacter(druid);
    }

    @Test
    void testWork() {
        double druidStrengthBefore = druid.getStrength();
        int blacksmithStaminaBefore = blacksmith.getStamina();

        blacksmith.work();

        assertEquals(druidStrengthBefore + 5, druid.getStrength());
        assertEquals(blacksmithStaminaBefore - 10, blacksmith.getStamina());
    }

    @Test
    void testWorkWithoutPlace() {
        blacksmith.modifyCurrentPlace(null);
        double druidStrengthBefore = druid.getStrength();

        blacksmith.work();

        assertEquals(druidStrengthBefore, druid.getStrength());
    }

    @Test
    void testWorkWithoutEnoughStamina() {
        blacksmith.updateStamina(-60);
        double druidStrengthBefore = druid.getStrength();

        blacksmith.work();

        assertEquals(druidStrengthBefore, druid.getStrength());
    }
}

class WerewolfTest {

    private Werewolf werewolf;
    private Legionary enemy;

    @BeforeEach
    void setUp() {
        werewolf = new Werewolf("Loup", Sex.MALE);
        enemy = new Legionary("Marcus", Sex.MALE);
    }

    @Test
    void testCombatBeastTeeth() {
        double initialWerewolfHealth = werewolf.getHealth();
        double initialEnemyHealth = enemy.getHealth();
        int initialHunger = werewolf.getHunger();

        werewolf.combat(enemy);

        assertEquals(initialEnemyHealth - 50, enemy.getHealth());
        assertEquals(initialWerewolfHealth, werewolf.getHealth());
        assertEquals(initialHunger - 50, werewolf.getHunger());
    }

    @Test
    void testCombatWithoutEnoughHunger() {
        werewolf.updateHunger(-60);
        double initialEnemyHealth = enemy.getHealth();

        werewolf.combat(enemy);

        assertEquals(initialEnemyHealth, enemy.getHealth());
    }

    @Test
    void testWerewolfCreation() {
        assertEquals("Creature", werewolf.getNationality());
        assertEquals("Werewolf", werewolf.getType());
    }
}

class LegionaryTest {

    private Legionary legionary;
    private Druid enemy;

    @BeforeEach
    void setUp() {
        legionary = new Legionary("Marcus", Sex.MALE);
        enemy = new Druid("Panoramix", Sex.MALE);
    }

    @Test
    void testCombatDecisiveStrike() {
        double initialEnemyHealth = enemy.getHealth();
        int initialStamina = legionary.getStamina();
        double expectedDamage = 30 + (legionary.getStrength() / 4);

        legionary.combat(enemy);

        assertEquals(initialEnemyHealth - expectedDamage, enemy.getHealth());
        assertEquals(initialStamina - 40, legionary.getStamina());
    }

    @Test
    void testLegionaryCreation() {
        assertEquals("Roman", legionary.getNationality());
        assertEquals("Legionary", legionary.getType());
    }
}

class GeneralTest {

    private General general;
    private List<Character> followers;
    private Legionary legionary;
    private Place place;

    @BeforeEach
    void setUp() {
        place = new RomanFortifiedCamp("Camp", 50000, null);
        general = new General("Caesar", Sex.MALE);
        legionary = new Legionary("Marcus", Sex.MALE);
        general.modifyCurrentPlace(place);
        legionary.modifyCurrentPlace(place);

        followers = new ArrayList<>();
        followers.add(general);
        followers.add(legionary);
    }

    @Test
    void testLeadWill() {
        double legionaryStrengthBefore = legionary.getStrength();
        int generalStaminaBefore = general.getStamina();

        general.lead(followers);

        assertEquals(legionaryStrengthBefore + 10, legionary.getStrength());
        assertEquals(generalStaminaBefore - 35, general.getStamina());
    }

    @Test
    void testCombatJustice() {
        Druid enemy = new Druid("Panoramix", Sex.MALE);
        double initialEnemyHealth = enemy.getHealth();
        int generalStaminaBefore = general.getStamina();

        general.combat(enemy);

        assertEquals(initialEnemyHealth / 2, enemy.getHealth());
        assertEquals(generalStaminaBefore - 45, general.getStamina());
    }
}