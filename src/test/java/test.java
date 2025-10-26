import org.example.kruskalMST;
import org.example.primMST;

import java.util.*;

public class test {

    public static void main(String[] args) {
        System.out.println("Starting MST Tests...\n");

        int passed = 0;
        int failed = 0;

        if (testSameTotalCost()) {
            System.out.println("PASSED: Both algorithms have same total cost");
            passed++;
        } else {
            System.out.println("FAILED: Different total costs");
            failed++;
        }

        if (testEdgeCount()) {
            System.out.println("PASSED: MST has V-1 edges");
            passed++;
        } else {
            System.out.println("FAILED: Wrong edge count");
            failed++;
        }

        if (testPositiveTime()) {
            System.out.println("PASSED: Execution time is positive");
            passed++;
        } else {
            System.out.println("FAILED: Negative execution time");
            failed++;
        }

        if (testPositiveOperations()) {
            System.out.println("PASSED: Operations count is positive");
            passed++;
        } else {
            System.out.println("FAILED: Negative operations count");
            failed++;
        }

        System.out.println("\nTotal: " + (passed + failed));
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
    }

    static boolean testSameTotalCost() {
        int numVertices = 4;

        List<primMST.Edge> primEdges = new ArrayList<>();
        primEdges.add(new primMST.Edge(0, 1, 1));
        primEdges.add(new primMST.Edge(0, 2, 4));
        primEdges.add(new primMST.Edge(1, 2, 2));
        primEdges.add(new primMST.Edge(2, 3, 3));
        primEdges.add(new primMST.Edge(1, 3, 5));

        List<kruskalMST.Edge> kruskalEdges = new ArrayList<>();
        kruskalEdges.add(new kruskalMST.Edge(0, 1, 1));
        kruskalEdges.add(new kruskalMST.Edge(0, 2, 4));
        kruskalEdges.add(new kruskalMST.Edge(1, 2, 2));
        kruskalEdges.add(new kruskalMST.Edge(2, 3, 3));
        kruskalEdges.add(new kruskalMST.Edge(1, 3, 5));

        primMST prim = new primMST();
        kruskalMST kruskal = new kruskalMST();

        primMST.Result primResult = prim.findMST(numVertices, primEdges);
        kruskalMST.Result kruskalResult = kruskal.findMST(numVertices, kruskalEdges);

        System.out.println("  Prim cost: " + primResult.totalCost);
        System.out.println("  Kruskal cost: " + kruskalResult.totalCost);

        return primResult.totalCost == kruskalResult.totalCost;
    }

    static boolean testEdgeCount() {
        int numVertices = 5;

        List<primMST.Edge> primEdges = new ArrayList<>();
        primEdges.add(new primMST.Edge(0, 1, 4));
        primEdges.add(new primMST.Edge(0, 2, 3));
        primEdges.add(new primMST.Edge(1, 2, 2));
        primEdges.add(new primMST.Edge(1, 3, 5));
        primEdges.add(new primMST.Edge(2, 3, 7));
        primEdges.add(new primMST.Edge(2, 4, 8));
        primEdges.add(new primMST.Edge(3, 4, 6));

        List<kruskalMST.Edge> kruskalEdges = new ArrayList<>();
        kruskalEdges.add(new kruskalMST.Edge(0, 1, 4));
        kruskalEdges.add(new kruskalMST.Edge(0, 2, 3));
        kruskalEdges.add(new kruskalMST.Edge(1, 2, 2));
        kruskalEdges.add(new kruskalMST.Edge(1, 3, 5));
        kruskalEdges.add(new kruskalMST.Edge(2, 3, 7));
        kruskalEdges.add(new kruskalMST.Edge(2, 4, 8));
        kruskalEdges.add(new kruskalMST.Edge(3, 4, 6));

        primMST prim = new primMST();
        kruskalMST kruskal = new kruskalMST();

        primMST.Result primResult = prim.findMST(numVertices, primEdges);
        kruskalMST.Result kruskalResult = kruskal.findMST(numVertices, kruskalEdges);

        System.out.println("  Prim edges: " + primResult.mstEdges.size() + " (expected: " + (numVertices - 1) + ")");
        System.out.println("  Kruskal edges: " + kruskalResult.mstEdges.size() + " (expected: " + (numVertices - 1) + ")");

        return primResult.mstEdges.size() == numVertices - 1 &&
                kruskalResult.mstEdges.size() == numVertices - 1;
    }

    static boolean testPositiveTime() {
        int numVertices = 4;

        List<primMST.Edge> primEdges = new ArrayList<>();
        primEdges.add(new primMST.Edge(0, 1, 1));
        primEdges.add(new primMST.Edge(0, 2, 4));
        primEdges.add(new primMST.Edge(1, 2, 2));
        primEdges.add(new primMST.Edge(2, 3, 3));

        List<kruskalMST.Edge> kruskalEdges = new ArrayList<>();
        kruskalEdges.add(new kruskalMST.Edge(0, 1, 1));
        kruskalEdges.add(new kruskalMST.Edge(0, 2, 4));
        kruskalEdges.add(new kruskalMST.Edge(1, 2, 2));
        kruskalEdges.add(new kruskalMST.Edge(2, 3, 3));

        primMST prim = new primMST();
        kruskalMST kruskal = new kruskalMST();

        primMST.Result primResult = prim.findMST(numVertices, primEdges);
        kruskalMST.Result kruskalResult = kruskal.findMST(numVertices, kruskalEdges);

        System.out.println("  Prim time: " + primResult.executionTime + " ms");
        System.out.println("  Kruskal time: " + kruskalResult.executionTime + " ms");

        return primResult.executionTime >= 0 && kruskalResult.executionTime >= 0;
    }

    static boolean testPositiveOperations() {
        int numVertices = 4;

        List<primMST.Edge> primEdges = new ArrayList<>();
        primEdges.add(new primMST.Edge(0, 1, 1));
        primEdges.add(new primMST.Edge(0, 2, 4));
        primEdges.add(new primMST.Edge(1, 2, 2));
        primEdges.add(new primMST.Edge(2, 3, 3));

        List<kruskalMST.Edge> kruskalEdges = new ArrayList<>();
        kruskalEdges.add(new kruskalMST.Edge(0, 1, 1));
        kruskalEdges.add(new kruskalMST.Edge(0, 2, 4));
        kruskalEdges.add(new kruskalMST.Edge(1, 2, 2));
        kruskalEdges.add(new kruskalMST.Edge(2, 3, 3));

        primMST prim = new primMST();
        kruskalMST kruskal = new kruskalMST();

        primMST.Result primResult = prim.findMST(numVertices, primEdges);
        kruskalMST.Result kruskalResult = kruskal.findMST(numVertices, kruskalEdges);

        System.out.println("  Prim operations: " + primResult.operationsCount);
        System.out.println("  Kruskal operations: " + kruskalResult.operationsCount);

        return primResult.operationsCount > 0 && kruskalResult.operationsCount > 0;
    }
}