package org.iut.refactoring;

import org.junit.jupiter.api.*;
import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GestionPersonnelTest {

    private GestionPersonnel gestion;
    private ByteArrayOutputStream sortieConsole;
    private PrintStream sortieOriginale;

    @BeforeEach
    void setUp() {
        gestion = new GestionPersonnel();

        // Capture de la sortie console
        sortieOriginale = System.out;
        sortieConsole = new ByteArrayOutputStream();
        System.setOut(new PrintStream(sortieConsole));
    }

    @AfterEach
    void tearDown() {
        // Restaure la sortie standard
        System.setOut(sortieOriginale);
    }

    // -------------------------------
    // AJOUT D'EMPLOYÉS
    // -------------------------------

    @Test
    @DisplayName("Ajout d'un développeur expérimenté (>5 ans)")
    void testAjouteDeveloppeurExperimenté() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Alice", 3000, 6, "DevTeam");
        Employe emp = gestion.employes.getFirst();
        String id = emp.getId();
        double salaireStocke = gestion.salairesEmployes.get(id);

        // 3000 * 1.2 * 1.15 = 4140
        assertEquals(4140.0, salaireStocke, 0.001);
        assertTrue(gestion.logs.getFirst().contains("Ajout de l'employé: Alice"));
    }

    @Test
    @DisplayName("Ajout d'un développeur junior (<=5 ans)")
    void testAjouteDeveloppeurJunior() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Bob", 3000, 3, "DevTeam");
        Employe emp = gestion.employes.getFirst();
        String id = emp.getId();
        double salaireStocke = gestion.salairesEmployes.get(id);

        // 3000 * 1.2 = 3600
        assertEquals(3600.0, salaireStocke, 0.001);
    }

    @Test
    @DisplayName("Ajout d'un chef de projet expérimenté (>3 ans)")
    void testAjouteChefProjetExperimente() {
        gestion.ajouteSalarie("CHEF DE PROJET", "Charlie", 4000, 4, "ProjetX");
        String id = gestion.employes.getFirst().getId();
        double salaireStocke = gestion.salairesEmployes.get(id);

        // 4000 * 1.5 * 1.1 = 6600
        assertEquals(6600.0, salaireStocke, 0.001);
    }

    @Test
    @DisplayName("Ajout d'un chef de projet junior (<=3 ans)")
    void testAjouteChefProjetJunior() {
        gestion.ajouteSalarie("CHEF DE PROJET", "Charlie", 4000, 2, "ProjetX");
        String id = gestion.employes.getFirst().getId();
        double salaireStocke = gestion.salairesEmployes.get(id);

        // 4000 * 1.5 = 6000
        assertEquals(6000.0, salaireStocke, 0.001);
    }

    @Test
    @DisplayName("Ajout d'un stagiaire")
    void testAjouteStagiaire() {
        gestion.ajouteSalarie("STAGIAIRE", "Diane", 2000, 1, "QA");
        String id = gestion.employes.getFirst().getId();
        double salaireStocke = gestion.salairesEmployes.get(id);

        // 2000 * 0.6 = 1200
        assertEquals(1200.0, salaireStocke, 0.001);
    }

    @Test
    @DisplayName("Ajout d'un type inconnu conserve le salaire de base")
    void testAjouteTypeInconnu() {
        gestion.ajouteSalarie("INCONNU", "Eric", 2500, 2, "Test");
        String id = gestion.employes.getFirst().getId();
        double salaireStocke = gestion.salairesEmployes.get(id);

        // pas de changement
        assertEquals(2500.0, salaireStocke, 0.001);
    }

    // -------------------------------
    // CALCUL SALAIRE
    // -------------------------------

    @Test
    @DisplayName("Calcul salaire développeur avec plus de 10 ans d'expérience (bonus supplémentaire)")
    void testCalculSalaireDevSenior() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Fred", 4000, 12, "DevTeam");
        String id = gestion.employes.getFirst().getId();
        double salaire = gestion.calculSalaire(id);

        // 4000 * 1.2 * 1.15 * 1.05 = 5796
        assertEquals(5796.0, salaire, 0.001);
    }

    @Test
    @DisplayName("Calcul salaire chef de projet expérimenté avec bonus fixe")
    void testCalculSalaireChefProjet() {
        gestion.ajouteSalarie("CHEF DE PROJET", "Gina", 4000, 6, "ProjetY");
        String id = gestion.employes.getFirst().getId();
        double salaire = gestion.calculSalaire(id);

        // 4000 * 1.5 * 1.1 + 5000 = 11600
        assertEquals(11600.0, salaire, 0.001);
    }

    @Test
    @DisplayName("Calcul salaire stagiaire")
    void testCalculSalaireStagiaire() {
        gestion.ajouteSalarie("STAGIAIRE", "Hugo", 2000, 1, "Support");
        String id = gestion.employes.getFirst().getId();
        double salaire = gestion.calculSalaire(id);

        // 2000 * 0.6 = 1200
        assertEquals(1200.0, salaire, 0.001);
    }

    @Test
    @DisplayName("Calcul salaire pour type inconnu")
    void testCalculSalaireTypeInconnu() {
        gestion.ajouteSalarie("INCONNU", "Ines", 3000, 5, "X");
        String id = gestion.employes.getFirst().getId();
        double salaire = gestion.calculSalaire(id);

        assertEquals(3000.0, salaire, 0.001);
    }

    @Test
    @DisplayName("Calcul salaire pour employé inexistant retourne 0 et affiche une erreur")
    void testCalculSalaireEmployeInexistant() {
        double salaire = gestion.calculSalaire("idInexistant");
        assertEquals(0.0, salaire);
        assertTrue(sortieConsole.toString().contains("ERREUR: impossible de trouver l'employé"));
    }

    // -------------------------------
    // GENERATION DE RAPPORTS
    // -------------------------------

    @Test
    @DisplayName("Rapport SALAIRE affiche les salaires de l'équipe filtrée")
    void testRapportSalaireFiltre() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Jack", 3000, 4, "Tech");
        gestion.ajouteSalarie("CHEF DE PROJET", "Kevin", 4000, 1, "Tech");
        gestion.generationRapport("SALAIRE", "Tech");

        String output = sortieConsole.toString();
        assertTrue(output.contains("=== RAPPORT: SALAIRE ==="));
        assertTrue(output.contains("Jack:"));
        assertTrue(output.contains("Kevin:"));
    }

    @Test
    @DisplayName("Rapport EXPERIENCE affiche les années d'expérience")
    void testRapportExperience() {
        gestion.ajouteSalarie("STAGIAIRE", "Laura", 1000, 1, "QA");
        gestion.generationRapport("EXPERIENCE", "");
        String output = sortieConsole.toString();

        assertTrue(output.contains("=== RAPPORT: EXPERIENCE ==="));
        assertTrue(output.contains("Laura: 1 années"));
    }

    @Test
    @DisplayName("Rapport DIVISION compte correctement les employés")
    void testRapportDivision() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Max", 2500, 3, "Backend");
        gestion.ajouteSalarie("CHEF DE PROJET", "Nina", 4500, 5, "Backend");
        gestion.ajouteSalarie("STAGIAIRE", "Oscar", 1000, 1, "Frontend");
        gestion.generationRapport("DIVISION", null);

        String output = sortieConsole.toString();
        assertTrue(output.contains("=== RAPPORT: DIVISION ==="));
        assertTrue(output.contains("Backend: 2 employés"));
        assertTrue(output.contains("Frontend: 1 employés"));
    }

    @Test
    @DisplayName("Rapport avec type inconnu ne provoque pas d'erreur")
    void testRapportTypeInconnu() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Paul", 2000, 2, "R&D");
        gestion.generationRapport("INCONNU", null);
        assertTrue(gestion.logs.getLast().contains("Rapport généré: INCONNU"));
    }

    // -------------------------------
    // AVANCEMENT
    // -------------------------------

    @Test
    @DisplayName("Avancement d'un employé met à jour le type et le salaire")
    void testAvancementEmploye() {
        gestion.ajouteSalarie("STAGIAIRE", "Quentin", 1500, 1, "QA");
        Employe emp = gestion.employes.getFirst();
        String id = emp.getId();

        gestion.avancementEmploye(id, "DEVELOPPEUR");

        assertEquals("DEVELOPPEUR", emp.getType());
        assertTrue(gestion.logs.getLast().contains("Employé promu"));
        assertTrue(sortieConsole.toString().contains("Employé promu avec succès!"));
    }

    @Test
    @DisplayName("Avancement d'un employé inexistant affiche une erreur")
    void testAvancementEmployeInexistant() {
        gestion.avancementEmploye("idFaux", "CHEF DE PROJET");
        assertTrue(sortieConsole.toString().contains("ERREUR: impossible de trouver l'employé"));
    }

    // -------------------------------
    // BONUS ANNUEL
    // -------------------------------

    @Test
    @DisplayName("Bonus développeur junior")
    void testBonusDeveloppeurJunior() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Rudy", 3000, 2, "Dev");
        String id = gestion.employes.getFirst().getId();
        double bonus = gestion.calculBonusAnnuel(id);
        // 3000 * 0.1 = 300
        assertEquals(300.0, bonus, 0.001);
    }

    @Test
    @DisplayName("Bonus développeur expérimenté (>5 ans)")
    void testBonusDeveloppeurExperimente() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Sophie", 3000, 6, "Dev");
        String id = gestion.employes.getFirst().getId();
        double bonus = gestion.calculBonusAnnuel(id);
        // 3000 * 0.1 * 1.5 = 450
        assertEquals(450.0, bonus, 0.001);
    }

    @Test
    @DisplayName("Bonus chef de projet expérimenté (>3 ans)")
    void testBonusChefProjetExperimente() {
        gestion.ajouteSalarie("CHEF DE PROJET", "Thomas", 4000, 5, "Dir");
        String id = gestion.employes.getFirst().getId();
        double bonus = gestion.calculBonusAnnuel(id);
        // 4000 * 0.2 * 1.3 = 1040
        assertEquals(1040.0, bonus, 0.001);
    }

    @Test
    @DisplayName("Bonus chef de projet junior")
    void testBonusChefProjetJunior() {
        gestion.ajouteSalarie("CHEF DE PROJET", "Ugo", 4000, 2, "Dir");
        String id = gestion.employes.getFirst().getId();
        double bonus = gestion.calculBonusAnnuel(id);
        // 4000 * 0.2 = 800
        assertEquals(800.0, bonus, 0.001);
    }

    @Test
    @DisplayName("Bonus stagiaire est toujours nul")
    void testBonusStagiaire() {
        gestion.ajouteSalarie("STAGIAIRE", "Val", 1000, 1, "QA");
        String id = gestion.employes.getFirst().getId();
        double bonus = gestion.calculBonusAnnuel(id);
        assertEquals(0.0, bonus);
    }

    @Test
    @DisplayName("Bonus d'un employé inexistant renvoie 0")
    void testBonusEmployeInexistant() {
        double bonus = gestion.calculBonusAnnuel("idFaux");
        assertEquals(0.0, bonus);
    }

    // -------------------------------
    // DIVERS
    // -------------------------------

    @Test
    @DisplayName("getEmployesParDivision retourne les bons employés")
    void testGetEmployesParDivision() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Wendy", 3000, 2, "Backend");
        gestion.ajouteSalarie("CHEF DE PROJET", "Xavier", 4000, 4, "Frontend");

        var backend = gestion.getEmployesParDivision("Backend");
        assertEquals(1, backend.size());
        assertEquals("Wendy", backend.getFirst().getNom());
    }

    @Test
    @DisplayName("getEmployesParDivision retourne vide si aucune correspondance")
    void testGetEmployesParDivisionVide() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Yvan", 3000, 2, "Backend");
        var result = gestion.getEmployesParDivision("Inconnue");
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("printLogs affiche toutes les actions effectuées")
    void testPrintLogs() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Zoé", 3200, 4, "Mobile");
        gestion.generationRapport("EXPERIENCE", null);
        sortieConsole.reset();

        gestion.printLogs();

        String output = sortieConsole.toString();
        assertTrue(output.contains("=== LOGS ==="));
        assertTrue(output.contains("Ajout de l'employé: Zoé"));
        assertTrue(output.contains("Rapport généré: EXPERIENCE"));
    }
}
