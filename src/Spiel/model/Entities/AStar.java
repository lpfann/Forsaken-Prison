package Spiel.model.Entities;

import Main.Main;
import Spiel.model.MainModel;

import java.util.*;

/**
 * @author Lukas Pfannschmidt
 *         Date: 12.03.14
 *         Time: 15:14
 */
public class AStar {
    private static final double COST_FOR_EDGE = 1;
    private PriorityQueue<Node> openList;
    private HashSet<Node> closedList;
    private Node target;
    private MainModel main;
    private Node[][] graph;

    public AStar(MainModel main) {
        this.main = main;
        graph = new Node[main.getBreite()][main.getHoehe()];


    }

    private class Node implements Comparable<Node> {
        Node previous;
        double g_value;
        double cost;
        int x,y;
        LinkedList<Node> successors;

        public Node(int x,int y) {
            this.x = x;
            this.y = y;
            successors = new LinkedList<>();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node node = (Node) o;

            if (x != node.x) return false;
            if (y != node.y) return false;

            return true;
        }


        @Override
        public int compareTo(Node o) {
            if (this.cost == o.cost) return 0;
            if (this.cost > o.cost) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    public Node findPath(Node  start, Node target) {
        this.target = target;
        this.openList = new PriorityQueue<>();
        this.closedList = new HashSet<>();
        openList.add(start);
        Node currentNode;

        while (!openList.isEmpty()) {
            currentNode = openList.poll();

            if (currentNode.equals(target)) {
                return getPath(currentNode);
            }

            closedList.add(currentNode);
            expandNode(currentNode);
        }
        return null;
    }

    private Node getPath(Node target) {
        Node successor = target.previous;
        Node previousSuccessor = target;

            while (successor.previous != null) {
                previousSuccessor = successor;
                successor = successor.previous;
            }
            return previousSuccessor;

    }

    private void expandNode(Node currentNode) {
        double calc_g;
        int x = currentNode.x;
        int y = currentNode.y;

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 0 && j >= 0 && i < main.getBreite() && j < main.getHoehe() ) {

                    Node successor = graph[i][j];
                    if (closedList.contains(successor)) continue;

                    calc_g = currentNode.g_value + COST_FOR_EDGE;
                    if (openList.contains(successor) && calc_g >= successor.g_value) continue;

                    successor.previous = currentNode;
                    successor.g_value = calc_g;

                    successor.cost = calc_g + distance_metric(successor, target);
                    if (openList.contains(successor)) {
                        openList.remove(successor);
                        openList.add(successor);
                    } else {
                        openList.add(successor);
                    }

                }
            }
        }
    }

    private double distance_metric(Node a,Node b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    public int[] getNextWaypoint(NPC a, NPC b) {
        for (int i = 0; i < main.getBreite(); i++) {
            for (int j = 0; j < main.getHoehe(); j++) {
                graph[i][j] = new Node(i, j);
            }
        }
        Node start = graph[a.getX()/main.getFIELDSIZE()][a.getY() / main.getFIELDSIZE()];
        Node target = graph[b.getX() / main.getFIELDSIZE()][b.getY() / main.getFIELDSIZE()];
        Node nextWaypoint = findPath(start,target);
        if (nextWaypoint != null) {
            return new int[]{nextWaypoint.x*main.getFIELDSIZE(), nextWaypoint.y*main.getFIELDSIZE()};
        } else {
            return null;
        }
    }
}
