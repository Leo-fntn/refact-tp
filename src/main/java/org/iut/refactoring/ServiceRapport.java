package org.iut.refactoring;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceRapport {
    public String genererRapport(List<Employe> empList, Map<String, Double> salaires, String typeRapport, String filtre) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== RAPPORT: ").append(typeRapport).append(" ===\n");

        switch (typeRapport) {
            case "SALAIRE" -> {
                for (Employe emp : empList) {
                    if (filtre == null || filtre.isEmpty() || emp.getEquipe().equals(filtre)) {
                        String id = emp.getId();
                        String nom = emp.getNom();
                        double salaire = salaires.get(id);
                        sb.append(nom).append(": ").append(salaire).append(" €\n");
                    }
                }
            }
            case "EXPERIENCE" -> {
                for (Employe emp : empList) {
                    if (filtre == null || filtre.isEmpty() || emp.getEquipe().equals(filtre)) {
                        String nom = emp.getNom();
                        int exp = emp.getExperience();
                        sb.append(nom).append(": ").append(exp).append(" années\n");
                    }
                }
            }
            case "DIVISION" -> {
                HashMap<String, Integer> compteurDivisions = new HashMap<>();
                for (Employe emp : empList) {
                    String div = emp.getEquipe();
                    compteurDivisions.put(div, compteurDivisions.getOrDefault(div, 0) + 1);
                }
                for (Map.Entry<String, Integer> entry : compteurDivisions.entrySet()) {
                    sb.append(entry.getKey()).append(": ").append(entry.getValue()).append(" employés\n");
                }
            }
        }
        
        return sb.toString();
    }
}
