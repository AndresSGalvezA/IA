package org.asgas;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComplexConstraint {
    public void mainGraph() {
        List<String> complexVariables = List.of("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
                "S", "T", "U", "V", "W", "X", "Y", "Z", "AA");
        Map<String, List<String>> complexDomains = new HashMap<>();

        for (var variable: complexVariables) {
            complexDomains.put(variable, List.of("Cyan", "Magenta", "Yellow", "Black"));
        }

        CSP<String, String> complexProblem = new CSP<>(complexVariables, complexDomains);
        complexProblem.addConstraint(new AustraliaColoringConstraint("A", "B"));
        complexProblem.addConstraint(new AustraliaColoringConstraint("A", "V"));
        complexProblem.addConstraint(new AustraliaColoringConstraint("B", "V"));
        complexProblem.addConstraint(new AustraliaColoringConstraint("C", "D"));
        complexProblem.addConstraint(new AustraliaColoringConstraint("C", "F"));
        complexProblem.addConstraint(new AustraliaColoringConstraint("C", "H"));
        complexProblem.addConstraint(new AustraliaColoringConstraint("D", "G"));
        complexProblem.addConstraint(new AustraliaColoringConstraint("D", "H"));
        complexProblem.addConstraint(new AustraliaColoringConstraint("E", "I"));
        complexProblem.addConstraint(new AustraliaColoringConstraint("F", "K"));
        complexProblem.addConstraint(new AustraliaColoringConstraint("G", "K"));
        complexProblem.addConstraint(new AustraliaColoringConstraint("H", "V"));
        complexProblem.addConstraint(new AustraliaColoringConstraint("H", "M"));
        complexProblem.addConstraint(new AustraliaColoringConstraint("I", "M"));
        complexProblem.addConstraint(new AustraliaColoringConstraint("J", "N"));
        complexProblem.addConstraint(new AustraliaColoringConstraint("J", "K"));
        complexProblem.addConstraint(new AustraliaColoringConstraint("K", "O"));
        complexProblem.addConstraint(new AustraliaColoringConstraint("L", "P"));
        complexProblem.addConstraint(new AustraliaColoringConstraint("L", "Q"));
        complexProblem.addConstraint(new AustraliaColoringConstraint("M", "Q"));
        complexProblem.addConstraint(new AustraliaColoringConstraint("P", "S"));
        complexProblem.addConstraint(new AustraliaColoringConstraint("P", "T"));
        complexProblem.addConstraint(new AustraliaColoringConstraint("P", "U"));
        complexProblem.addConstraint(new AustraliaColoringConstraint("R", "S"));
        complexProblem.addConstraint(new AustraliaColoringConstraint("R", "W"));
        complexProblem.addConstraint(new AustraliaColoringConstraint("R", "X"));
        complexProblem.addConstraint(new AustraliaColoringConstraint("S", "X"));
        complexProblem.addConstraint(new AustraliaColoringConstraint("S", "Y"));
        complexProblem.addConstraint(new AustraliaColoringConstraint("S", "Z"));
        complexProblem.addConstraint(new AustraliaColoringConstraint("T", "U"));
        complexProblem.addConstraint(new AustraliaColoringConstraint("T", "Z"));
        complexProblem.addConstraint(new AustraliaColoringConstraint("T", "AA"));
        complexProblem.addConstraint(new AustraliaColoringConstraint("U", "AA"));
        complexProblem.addConstraint(new AustraliaColoringConstraint("V", "W"));
        complexProblem.addConstraint(new AustraliaColoringConstraint("W", "X"));
        complexProblem.addConstraint(new AustraliaColoringConstraint("X", "Y"));
        complexProblem.addConstraint(new AustraliaColoringConstraint("Y", "Z"));
        complexProblem.addConstraint(new AustraliaColoringConstraint("Z", "AA"));

        System.out.println("Complex Constraint:");
        var complexSolution = complexProblem.backtrack();
        System.out.println(complexSolution);
    }
}