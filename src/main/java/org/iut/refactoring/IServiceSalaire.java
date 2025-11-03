package org.iut.refactoring;

public interface IServiceSalaire {
    double calculerSalaire(Employe emp);

    double calculerSalaireAvecBonus(Employe emp);

    double calculerBonus(Employe emp);
}
