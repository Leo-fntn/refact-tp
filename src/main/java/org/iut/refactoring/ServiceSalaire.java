package org.iut.refactoring;

public class ServiceSalaire {
    public double calculerSalaire(Employe emp){
        String type = emp.getType();
        int experience = emp.getExperience();
        double salaireBase = emp.getSalaireBase();

        double salaireFinal;
        switch (type) {
            case "DEVELOPPEUR" -> {
                salaireFinal = salaireBase * 1.2;
                if (experience > 5) {
                    salaireFinal = salaireFinal * 1.15;
                }
            }
            case "CHEF DE PROJET" -> {
                salaireFinal = salaireBase * 1.5;
                if (experience > 3) {
                    salaireFinal = salaireFinal * 1.1;
                }
            }
            case "STAGIAIRE" -> salaireFinal = salaireBase * 0.6;
            default -> salaireFinal = salaireBase;
        }
        return salaireFinal;
    }

    public double calculerSalaireAvecBonus(Employe emp){
        String type = emp.getType();
        double salaireDeBase = emp.getSalaireBase();
        int experience = emp.getExperience();

        double salaireFinal;
        switch (type) {
            case "DEVELOPPEUR" -> {
                salaireFinal = salaireDeBase * 1.2;
                if (experience > 5) {
                    salaireFinal = salaireFinal * 1.15;
                }
                if (experience > 10) {
                    salaireFinal = salaireFinal * 1.05; // bonus
                }
            }
            case "CHEF DE PROJET" -> {
                salaireFinal = salaireDeBase * 1.5;
                if (experience > 3) {
                    salaireFinal = salaireFinal * 1.1;
                }
                salaireFinal = salaireFinal + 5000; // bonus
            }
            case "STAGIAIRE" -> salaireFinal = salaireDeBase * 0.6;

            default -> salaireFinal = salaireDeBase;
        }
        return salaireFinal;
    }

    public double calculerBonus(Employe emp){
        String type = emp.getType();
        int experience = emp.getExperience();
        double salaireDeBase = emp.getSalaireBase();

        double bonus = 0;
        switch (type) {
            case "DEVELOPPEUR" -> {
                bonus = salaireDeBase * 0.1;
                if (experience > 5) {
                    bonus = bonus * 1.5;
                }
            }
            case "CHEF DE PROJET" -> {
                bonus = salaireDeBase * 0.2;
                if (experience > 3) {
                    bonus = bonus * 1.3;
                }
            }
            case "STAGIAIRE" -> bonus = 0; // Pas de bonus
        }
        return bonus;
    }
}
