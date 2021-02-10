package org.example.graph;
import java.util.HashSet;
import java.util.Optional;
import java.util.Stack;
import java.util.Set;

public class DFS
{
    public static<T> Optional<Node<T>> search(T value, Node<T> start)
    {
        Stack<Node> stack = new Stack<>();
        stack.add(start);
        Node<T> currentNode = null;
        Set<Node<T>> closed = new HashSet<>();

        while(!stack.isEmpty())
        {
            currentNode = stack.pop();
            System.out.println("Visitando el nodo: " + currentNode.getValue());

            if(currentNode.getValue().equals(value))
                return Optional.of(currentNode);
            else
            {
                if (!closed.contains(currentNode))
                {
                    closed.add(currentNode);
                    stack.addAll(currentNode.getNeighbors());
                }

                stack.removeAll(closed);
            }
        }

        return Optional.empty();
    }
}