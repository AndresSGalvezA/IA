package org.example.graph;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

public class BFSVersion2
{
    public static<T> Optional<Node<T>> search(T value, Node<T> start )
    {
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(start);
        String route = "Ruta: ";
        Node<T> currentNode = null;
        Set<Node<T>> closed = new HashSet<>();

        while(!queue.isEmpty())
        {
            currentNode = queue.remove();
            System.out.println("Visitando el nodo: " + currentNode.getValue());

            //If I reach the goal:
            if(currentNode.getValue().equals(value))
            {
                route += currentNode.getValue();
                System.out.println(route);
                return Optional.of(currentNode);
            }
            else
            {
                if (!closed.contains(currentNode))
                {
                    closed.add(currentNode);
                    queue.addAll(currentNode.getNeighbors());
                }

                queue.removeAll(closed);
            }
        }

        return Optional.empty();
    }
}