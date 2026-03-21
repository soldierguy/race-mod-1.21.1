package net.un2rws1.racemod.client;

import net.un2rws1.racemod.classsystem.PlayerClass;
public class ClientClassState {
    private static PlayerClass playerClass = null;
    public static void setPlayerClass(PlayerClass newClass) {
        System.out.println("Client race set to " + newClass);
        playerClass = newClass;
    }
    public static PlayerClass getPlayerClass() {
        return playerClass;
    }
}