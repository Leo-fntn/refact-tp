package org.iut.refactoring;

import java.util.List;

public interface IServiceLogs {
    void ajouter(String message);

    List<String> getLogs();

    void printLogs();
}
