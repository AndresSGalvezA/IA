package org.example;
import org.example.graph.BFSVersion2;
import org.example.graph.DFS;
import org.example.graph.Node;

public class App
{
    public static void main( String[] args )
    {
        var zone1 = new Node<Integer>(1);
        var zone2 = new Node<Integer>(2);
        var zone4 = new Node<Integer>(4);
        var zone5 = new Node<Integer>(5);
        var zone9 = new Node<Integer>(9);
        var zone10 = new Node<Integer>(10);
        var zone12 = new Node<Integer>(12);
        var zone13 = new Node<Integer>(13);
        var zone14 = new Node<Integer>(14);
        var zone15 = new Node<Integer>(15);
        var zone16 = new Node<Integer>(16);
        var zone21 = new Node<Integer>(21);

        zone21.connect(zone12);
        zone12.connect(zone13);
        zone12.connect(zone9);
        zone13.connect(zone14);
        zone10.connect(zone9);
        zone14.connect(zone10);
        zone10.connect(zone15);
        zone10.connect(zone16);
        zone10.connect(zone4);
        zone10.connect(zone9);
        zone9.connect(zone4);
        zone9.connect(zone1);
        zone4.connect(zone5);
        zone4.connect(zone1);
        zone1.connect(zone5);
        zone1.connect(zone2);
        zone15.connect(zone16);

        System.out.println("BFS:");
        BFSVersion2.search(16,zone21);
        System.out.println();
        System.out.println("DFS:");
        DFS.search(16,zone21);
    }
}