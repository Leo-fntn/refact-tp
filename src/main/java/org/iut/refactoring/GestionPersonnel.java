package org.iut.refactoring;

import java.util.*;
import java.time.*;

public class GestionPersonnel {

    public ArrayList<Employe> employes = new ArrayList<>();
    public HashMap<String, Double> salairesEmployes = new HashMap<>();
    private final IServiceSalaire serviceSalaire;
    private final IServiceRapport serviceRapport;
    private final IServiceLogs serviceLogs;

    public GestionPersonnel(IServiceSalaire serviceSalaire, IServiceRapport serviceRapport, IServiceLogs serviceLogs) {
        this.serviceSalaire = serviceSalaire;
        this.serviceRapport = serviceRapport;
        this.serviceLogs = serviceLogs;
    }

    public void ajouteSalarie(String type, String nom, double salaireDeBase, int experience, String equipe) {
        Employe emp = new Employe(type, nom, salaireDeBase, experience, equipe);

        employes.add(emp);

        double salaireFinal = serviceSalaire.calculerSalaire(emp);

        salairesEmployes.put(emp.getId(), salaireFinal);

        serviceLogs.ajouter("Ajout de l'employé: " + nom);
    }

    public double calculSalaire(String employeId) {
        Employe emp = null;
        for (Employe e : employes) {
            if (e.getId().equals(employeId)) {
                emp = e;
                break;
            }
        }
        if (emp == null) {
            System.out.println("ERREUR: impossible de trouver l'employé");
            return 0;
        }

        return serviceSalaire.calculerSalaireAvecBonus(emp);
    }

    public String generationRapport(String typeRapport, String filtre) {
        String rapport = serviceRapport.genererRapport(employes, salairesEmployes, typeRapport, filtre);
        serviceLogs.ajouter("Rapport généré: " + typeRapport);
        return rapport;
    }

    public void avancementEmploye(String employeId, String newType) {
        for (Employe emp : employes) {
            if (emp.getId().equals(employeId)) {
                emp.setType(newType);

                double nouveauSalaire = calculSalaire(employeId);
                salairesEmployes.put(employeId, nouveauSalaire);

                serviceLogs.ajouter("Employé promu: " + emp.getNom());
                System.out.println("Employé promu avec succès!");
                return;
            }
        }
        System.out.println("ERREUR: impossible de trouver l'employé");
    }

    public ArrayList<Employe> getEmployesParDivision(String division) {
        ArrayList<Employe> resultat = new ArrayList<>();
        for (Employe emp : employes) {
            if (emp.getEquipe().equals(division)) {
                resultat.add(emp);
            }
        }
        return resultat;
    }

    public void printLogs() {
        serviceLogs.printLogs();
    }

    public List<String> getLogs() {
        return serviceLogs.getLogs();
    }

    public double calculBonusAnnuel(String employeId) {
        Employe emp = null;
        for (Employe e : employes) {
            if (e.getId().equals(employeId)) {
                emp = e;
                break;
            }
        }
        if (emp == null) return 0;

        return serviceSalaire.calculerBonus(emp);
    }
}



