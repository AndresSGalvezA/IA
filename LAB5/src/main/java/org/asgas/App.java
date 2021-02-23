package org.asgas;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App
{
    public static void main( String[] args ) {
        List<String> variables = List.of("Western Australia", "Northern Territory",
                "Queensland", "South Australia", "New South Wales", "Victoria", "Tasmania");
        Map<String, List<String>> domains = new HashMap<>();

        for (var variable: variables) {
            domains.put(variable, List.of("rojo", "verde", "azul"));
        }

        System.out.println("Filtering:");
        CSP<String, String> problem = new CSP<>(variables, domains);
        problem.addConstraint(new AustraliaColoringConstraint("Western Australia", "Northern Territory"));
        problem.addConstraint(new AustraliaColoringConstraint("Western Australia", "South Australia"));
        problem.addConstraint(new AustraliaColoringConstraint("Northern Territory", "South Australia"));
        problem.addConstraint(new AustraliaColoringConstraint("Northern Territory", "Queensland"));
        problem.addConstraint(new AustraliaColoringConstraint("South Australia", "Queensland"));
        problem.addConstraint(new AustraliaColoringConstraint("South Australia", "New South Wales"));
        problem.addConstraint(new AustraliaColoringConstraint("South Australia", "Victoria"));
        problem.addConstraint(new AustraliaColoringConstraint("Queensland", "New South Wales"));
        problem.addConstraint(new AustraliaColoringConstraint("New South Wales", "Victoria"));
        problem.addConstraint(new AustraliaColoringConstraint("Tasmania", "Victoria")); //Hipotético.

        var solution = problem.backtrack();
        System.out.println(solution + "\n");

        //Solving the complex graph:
        var complex = new ComplexConstraint();
        complex.mainGraph();
    }
}