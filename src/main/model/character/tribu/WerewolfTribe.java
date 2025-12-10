package main.model.character.tribu;

import main.enums.DominationRank;
import main.enums.Sex;
import main.model.character.category.creature.species.Werewolf;
import java.util.*;

public class WerewolfTribe {
    private List<Werewolf> tribeMembers;
    private List<Werewolf> alphaCouple; // indice 0 : MALE - indice 1 : FEMALE

    /**
     * Création d'une nouvelle tribu à partir d'une liste
     * @param werewolves
     * @throws Exception
     */
    public WerewolfTribe(List<Werewolf> werewolves) throws Exception {
        List<Werewolf> tribeMembers = new ArrayList<>();
        LinkedList<Werewolf> alphaCouple = new LinkedList<>(); // Utilisation d'une LinkedList

        alphaCouple.add(null);
        alphaCouple.add(null);

        boolean hasAlphaFemale = false;
        boolean hasAlphaMale = false;

        for (Werewolf w : werewolves) {
            if (w.getDominationRank() != DominationRank.ALPHA) {
                tribeMembers.add(w);
                w.setTribu(this);
            } else {
                if (w.getSex() == Sex.MALE) {
                    if (!hasAlphaMale) {
                        tribeMembers.add(w);
                        w.setTribu(this);
                        hasAlphaMale = true;
                        alphaCouple.set(0, w);
                    } else {
                        if (alphaCouple.getFirst().getLevel() >= w.getLevel()) {
                            w.updateRank(DominationRank.BETA);
                            tribeMembers.add(w);
                            w.setTribu(this);
                        } else {
                            alphaCouple.getFirst().updateRank(DominationRank.BETA);
                            w.updateRank(DominationRank.ALPHA);
                            alphaCouple.set(0, w);
                            tribeMembers.add(w);
                            w.setTribu(this);
                        }
                    }
                } else {
                    if (!hasAlphaFemale) {
                        tribeMembers.add(w);
                        w.setTribu(this);
                        hasAlphaFemale = true;
                        alphaCouple.set(1, w);
                    } else {
                        if (alphaCouple.getLast().getLevel() >= w.getLevel()) {
                            w.updateRank(DominationRank.BETA);
                            tribeMembers.add(w);
                            w.setTribu(this);
                        } else {
                            alphaCouple.getLast().updateRank(DominationRank.BETA);
                            w.updateRank(DominationRank.ALPHA);
                            alphaCouple.set(1, w);
                            tribeMembers.add(w);
                            w.setTribu(this);
                        }
                    }
                }
            }
        }

        if (hasAlphaFemale && hasAlphaMale) {
            this.tribeMembers = tribeMembers;
            this.alphaCouple = alphaCouple;
            System.out.println("Création de la meute réalisée avec succès !");
        } else {
            System.out.println("Création de la meute impossible ! Il faut un alpha femelle et un alpha mâle !");
        }
    }


    /**
     * Mise à jour du couple alpha (ne pas utiliser en dehors de la méthode updateRank() de Werewolf)
     * @param w
     */
    public void updateAlphaCouple(Werewolf w){
        if(w.getSex()==Sex.MALE) {
            alphaCouple.removeFirst();
            alphaCouple.addFirst(w);
        } else {
            alphaCouple.removeLast();
            alphaCouple.addLast(w);
        }
    }

    public void showTribuMembers(){
        for (Werewolf w : tribeMembers){
            System.out.println(w.getName() + " " + w.getAgeCategory() + " " + w.getDominationRank());
        }
    }

    public void showCoupleMembers(){
        for (Werewolf w : alphaCouple){
            System.out.println(w.getName() + " " + w.getAgeCategory() + " " + w.getDominationRank());
        }
    }

    public List<Werewolf> getWerewolves() {
        return tribeMembers;
    }

    public List<Werewolf> getAlphaCouple() {
        return alphaCouple;
    }
}
