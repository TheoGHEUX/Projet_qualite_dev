package main.model.character.category.creature.species;

import main.enums.AgeCategory;
import main.enums.DominationRank;
import main.model.character.Character;
import main.enums.Sex;
import main.interfaces.Fighter;
import main.model.character.category.creature.Creature;
import main.model.character.tribu.WerewolfTribu;

import java.util.Random;

// Loups-garous (lycanthrope)
public class Werewolf extends Creature implements Fighter {
    private AgeCategory ageCategory;
    private DominationRank dominationRank;
    private double level;
    private WerewolfTribu tribu ;

    /**
     * Crée un nouveau loup avec plus de choix de paramètres.
     * @param name
     * @param sex
     * @param size
     * @param age
     * @param strength
     * @param stamina
     * @param health
     */
    public Werewolf(String name, Sex sex, int size, int age, int strength, int stamina, double health) {
        super(name, sex, size, age, strength, stamina, health);
        this.type = "Werewolf";
    }

    /**
     * Crée un nouveau loup
     * @param name
     * @param sex
     */
    public Werewolf(String name, Sex sex) {
        super(name, sex, randomBetween(165,180), randomBetween(5,90),randomBetween(10,50),65,100);
        this.type = "Werewolf";
        this.level = this.strength / 5 ;
        this.dominationRank = DominationRank.BETA;

        // AGE
        if(0 <  this.getAge() && this.getAge() <= 18) {
            this.ageCategory = AgeCategory.YOUNG;
            this.level += 2;
        } else if (this.getAge() <= 50) {
            this.ageCategory = AgeCategory.ADULT;
            this.level += 7;
        } else {
            this.ageCategory = AgeCategory.OLD;
            this.level += 3;
        }

        // Rang de domination aléatoire à la construction
        Random random = new Random();
        int randomRang = RANDOM.nextInt(21);
        switch (randomRang){
            case 0,1:
                this.dominationRank = DominationRank.ALPHA;
                this.level += 10;
                break;
            case 2,3,4 :
                this.dominationRank = DominationRank.BETA;
                this.level += 7;
                break;
            case 5,6,7:
                this.dominationRank = DominationRank.GAMMA;
                this.level += 5;
                break;
            case 8,9,10,11:
                this.dominationRank = DominationRank.DELTA;
                this.level += 3;
                break;
            case 12,13,14,15:
                this.dominationRank = DominationRank.EPSILON;
                this.level += 2;
                break;
            default:
                this.dominationRank = DominationRank.OMEGA;
        }
    }

    /**
     * Constructeur d'un loup nouveau avec choix du rang de domination.
     * @param name
     * @param sex
     * @param dominationRank
     */
    public Werewolf(String name, Sex sex, DominationRank dominationRank) {
        super(name, sex, randomBetween(165,180), randomBetween(5,90),randomBetween(10,50),65,100);
        this.type = "Werewolf";
        this.level = this.strength / 5 ;
        this.dominationRank = dominationRank;

        // AGE
        if(0 <  this.getAge() && this.getAge() <= 18) {
            this.ageCategory = AgeCategory.YOUNG;
            this.level += 2;
        } else if (this.getAge() <= 50) {
            this.ageCategory = AgeCategory.ADULT;
            this.level += 7;
        } else {
            this.ageCategory = AgeCategory.OLD;
            this.level += 3;
        }

        switch (dominationRank){
            case ALPHA:
                this.level += 10;
                break;
            case BETA:
                this.level += 7;
                break;
            case GAMMA:
                this.level += 5;
                break;
            case DELTA:
                this.level += 3;
                break;
            case EPSILON:
                this.level += 2;
                break;
            default:
                this.dominationRank = DominationRank.OMEGA;
        }
    }

    /**
     * Le loup combar un ennemi
     * Dents de la bête : Inflige 50 dégâts à l'ennemi contre 50 de faim et se soigne de 20
     * Coûte 50 de faim.
     * @param enemy
     */
    @Override
    public void combat(Character enemy) {
        int currentHunger = this.getHunger();
        if (currentHunger <= 50) {
            System.out.println("Werewolf " + this.name + " n'a pas assez faim pour activer \"Dents de la bête\" !");
            return;
        }
        System.out.println("Werewolf " + this.name + " utilise \"Dents de la bête\" sur " + enemy.getType() + " " + enemy.getName() + " !");
        int hungerCost = 50;
        enemy.updateHealth(-50);
        this.updateHunger(-hungerCost);
        this.updateHealth(20);
    }

    public void updateRank(DominationRank rank) throws Exception {
        if(this.dominationRank == rank) {
            return;
        } else if (rank != DominationRank.ALPHA){
            this.dominationRank = rank;

        } else if (this.tribu != null){
            if(this.tribu.getAlphaCouple().getFirst() != null && this.tribu.getAlphaCouple().getLast() != null){
                System.out.println("Ajout d'alpha impossible dans cette tribu ! Le rang a été modifié en BETA");
                this.updateRank(DominationRank.BETA);
                return;
            }

            if(this.getSex() == Sex.MALE){
                if(this.tribu.getAlphaCouple().getFirst() != null){
                    System.out.println("Ajout d'alpha mâle impossible dans cette tribu ! Le rang a été modifié en BETA");
                    this.updateRank(DominationRank.BETA);
                }
                else {
                    this.dominationRank = rank;
                    this.tribu.updateAlphaCouple(this);
                }
            } else {
                if(this.tribu.getAlphaCouple().getLast() != null){
                    System.out.println("Ajout d'alpha femelle impossible dans cette tribu ! Le rang a été modifié en BETA");
                    this.updateRank(DominationRank.BETA);
                }
                else {
                    this.dominationRank = rank;
                    this.tribu.updateAlphaCouple(this);
                }
            }
        } else {
            this.dominationRank = rank;
        }
    }

    public AgeCategory getAgeCategory() {
        return ageCategory;
    }

    public DominationRank getDominationRank() {
        return dominationRank;
    }

    public double getLevel() {
        return level;
    }

    public WerewolfTribu getWerewolfTribu() {
        return tribu;
    }

    public void setTribu(WerewolfTribu tribu) {
        this.tribu = tribu;
    }
}