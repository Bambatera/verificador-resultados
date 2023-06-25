package br.com.loteria.controller;

import br.com.loteria.model.Concurso;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProbabilityCalculation {

    private String filePath;

    public ProbabilityCalculation() {
    }

    public ProbabilityCalculation(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void startProbabilityCalculation(List<Concurso> concursos) {
        try {
            List<Byte> allNumbers = new ArrayList<>();

            concursos.stream().forEach(concurso -> concurso.getDezenas().stream().forEach(dezena -> allNumbers.add(dezena)));

            Map<Byte, Integer> numberOccurrences = new HashMap<>();
            for (Byte number : allNumbers) {
                numberOccurrences.put(number, numberOccurrences.getOrDefault(number, 0) + 1);
            }

            List<Byte> group1 = selectNumbersWithProbability(numberOccurrences, 0.7, 1.0);
            List<Byte> group2 = selectNumbersWithProbability(numberOccurrences, 0.3, 0.69);
            List<Byte> group3 = selectNumbersWithProbability(numberOccurrences, 0.15, 0.29);
            System.out.println("Group 1: " + group1);
            System.out.println("Group 2: " + group2);
            System.out.println("Group 3: " + group3);
        } catch (Exception ex) {
            Logger.getLogger(ProbabilityCalculation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private List<Byte> selectNumbersWithProbability(Map<Byte, Integer> numberOccurrences,
            double minProbability, double maxProbability) {
        List<Byte> selectedNumbers = new ArrayList<>();
        for (Map.Entry<Byte, Integer> entry : numberOccurrences.entrySet()) {
            double probability = (double) entry.getValue() / (double) numberOccurrences.size();
            if (probability >= minProbability && probability <= maxProbability) {
                selectedNumbers.add(entry.getKey());
            }
        }
        return selectedNumbers;
    }
}
