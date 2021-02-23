package org.asgas;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CSP <V, D> {
    private List<V> variables;
    private Map<V, List<D>> domains;
    private Map<V, List<Constraint<V, D>>> constraints = new HashMap<>();

    public CSP(List<V> variables, Map<V, List<D>> domains) {
        this.variables = variables;
        this.domains = domains;

        for (V variable: variables) {
            constraints.put(variable, new ArrayList<>());

            if (!domains.containsKey(variable))
                throw new IllegalArgumentException("La variable " + variable + " no contiene un dominio.");
        }
    }

    public void addConstraint (Constraint<V, D> constraint) {
        for (V variable: constraint.variables) {
            if (!this.variables.contains(variable))
                throw new IllegalArgumentException("La variable " + variable + " no se encuentra en el CSP.");

            constraints.get(variable).add(constraint);
        }
    }

    public boolean consistent(V variable, Map<V, D> assignment) {
        for (Constraint<V, D> constraint: this.constraints.get(variable)) {
            if (!constraint.satisfied(assignment))
                return false;
        }

        return true;
    }

    public Map<V, D> backtrack() {
        return backtrack(new HashMap<>(), domains);
    }

    public Map<V, D> backtrack(final Map<V, D> assignment, Map<V, List<D>> domains) {
        if (variables.size() == assignment.size())
            return assignment;

        V unassigned = variables.stream()
                .filter(v -> !assignment.containsKey(v))
                .findFirst().get();

        for (D value: domains.get(unassigned)) {
            System.out.println("Variable: " + unassigned + " valor: " + value);
            Map<V, D> localAssignment = new HashMap<>(assignment);
            Map<V, List<D>> mainDomains = new HashMap<>(domains);
            localAssignment.put(unassigned, value);

            if (consistent(unassigned, localAssignment)) {
                Map<V, D> result = backtrack(localAssignment, filtering(unassigned, value, mainDomains));

                if (result != null || filtering(unassigned, value, mainDomains) != null)
                    return result;
            }
        }

        return null;
    }

    public Map<V, List<D>>  filtering(V value, D valueDomain , Map<V, List<D>> mainDomains) {
        var fixedDomains = mainDomains;
        var fixedConstraints = constraints.get(value);
        List<V> nextNodes = new ArrayList<>();

        for (var nodes: fixedConstraints) {
            nextNodes.add(nodes.variables.stream()
                    .filter(x -> !x.equals(value))
                    .findFirst().get());
        }

        var newDomains = fixedDomains.get(value).stream()
                .filter(x->x.equals(valueDomain))
                .collect(Collectors.toList());

        fixedDomains.replace(value,newDomains);

        for (var tempNightbord : nextNodes) {
            var newDomain = fixedDomains.get(tempNightbord).stream()
                    .filter(x -> !x.equals(valueDomain))
                    .collect(Collectors.toList());

            fixedDomains.replace(tempNightbord,newDomain);
        }

        return fixedDomains;
    }
}