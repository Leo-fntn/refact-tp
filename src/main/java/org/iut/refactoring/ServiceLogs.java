package org.iut.refactoring;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ServiceLogs {
    private List<String> logs = new ArrayList<>();

    public void ajouter(String message){
        String log = LocalDateTime.now() + " - " + message;
        logs.add(log);
    }

    public List<String> getLogs(){
        return logs;
    }

    public void printLogs() {
        System.out.println("=== LOGS ===");
        for (String log : logs) {
            System.out.println(log);
        }
    }
}
