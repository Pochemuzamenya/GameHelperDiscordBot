package org.filatov.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DotaItemsDTO {
    private List<String> startGame;
    private List<String> earlyGame;
    private List<String> midGame;
    private List<String> lateGame;

    @Override
    public String toString() {
        return "Start: " + startGame + "\n" +
                "Early: " + earlyGame + "\n" +
                "Mid: " + midGame + "\n" +
                "Late: " + lateGame;
    }
}
