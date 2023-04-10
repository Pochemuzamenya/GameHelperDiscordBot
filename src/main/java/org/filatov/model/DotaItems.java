package org.filatov.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class DotaItems {
    Map<String, Integer> start_game_items = new HashMap<>();
    Map<String, Integer> early_game_items = new HashMap<>();
    Map<String, Integer> mid_game_items = new HashMap<>();
    Map<String, Integer> late_game_items = new HashMap<>();

    @Override
    public String toString() {
        return "DotaItems{" +
                "start_game_items=" + start_game_items +
                ", early_game_items=" + early_game_items +
                ", mid_game_items=" + mid_game_items +
                ", late_game_items=" + late_game_items +
                '}';
    }
}
