package org.iut.refactoring;

import java.util.List;
import java.util.Map;

public interface IServiceRapport {
    String genererRapport(List<Employe> empList, Map<String, Double> salaires, String typeRapport, String filtre);
}
