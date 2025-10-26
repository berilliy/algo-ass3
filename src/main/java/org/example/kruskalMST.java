package org.example;

import java.util.*;
import java.io.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class kruskalMST {

    public static class Edge implements Comparable<Edge> {
        int from;
        int to;
        int weight;

        public Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        public int compareTo(Edge other) {
            return this.weight - other.weight;
        }
    }

    public static class Result {
        public List<Edge> mstEdges;
        public int totalCost;
        public int operationsCount;
        public double executionTime;

        Result() {
            mstEdges = new ArrayList<>();
            totalCost = 0;
            operationsCount = 0;
            executionTime = 0.0;
        }
    }

    static class UnionFind {
        int[] parent;
        int[] rank;
        int operationsCount;

        UnionFind(int size) {
            parent = new int[size];
            rank = new int[size];
            operationsCount = 0;

            for (int i = 0; i < size; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        int find(int vertex) {
            operationsCount++;
            if (parent[vertex] != vertex) {
                parent[vertex] = find(parent[vertex]);
            }
            return parent[vertex];
        }

        boolean union(int vertex1, int vertex2) {
            operationsCount++;
            int root1 = find(vertex1);
            int root2 = find(vertex2);

            if (root1 == root2) {
                return false;
            }

            if (rank[root1] < rank[root2]) {
                parent[root1] = root2;
            } else if (rank[root1] > rank[root2]) {
                parent[root2] = root1;
            } else {
                parent[root2] = root1;
                rank[root1]++;
            }

            return true;
        }
    }

    public static Result findMST(int numVertices, List<Edge> edges) {
        Result result = new Result();
        long startTime = System.nanoTime();

        List<Edge> sortedEdges = new ArrayList<>(edges);
        Collections.sort(sortedEdges);
        result.operationsCount += sortedEdges.size();

        UnionFind unionFind = new UnionFind(numVertices);

        for (Edge edge : sortedEdges) {
            result.operationsCount++;

            if (unionFind.union(edge.from, edge.to)) {
                result.mstEdges.add(edge);
                result.totalCost += edge.weight;

                if (result.mstEdges.size() == numVertices - 1) {
                    break;
                }
            }
        }

        result.operationsCount += unionFind.operationsCount;

        long endTime = System.nanoTime();
        result.executionTime = (endTime - startTime) / 1_000_000.0;

        return result;
    }

    public static void main(String[] args) {
        try {
            FileReader reader = new FileReader("inputs/input1.json");
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray graphs = (JSONArray) jsonObject.get("graphs");

            JSONArray resultsArray = new JSONArray();

            for (Object graphObj : graphs) {
                JSONObject graph = (JSONObject) graphObj;
                long graphId = (long) graph.get("id");
                JSONArray nodes = (JSONArray) graph.get("nodes");
                JSONArray edgesArray = (JSONArray) graph.get("edges");

                int numVertices = nodes.size();
                List<Edge> edges = new ArrayList<>();

                Map<String, Integer> nodeMap = new HashMap<>();
                for (int i = 0; i < nodes.size(); i++) {
                    nodeMap.put((String) nodes.get(i), i);
                }

                for (Object edgeObj : edgesArray) {
                    JSONObject edgeJson = (JSONObject) edgeObj;
                    String fromNode = (String) edgeJson.get("from");
                    String toNode = (String) edgeJson.get("to");
                    long weight = (long) edgeJson.get("weight");

                    int fromIndex = nodeMap.get(fromNode);
                    int toIndex = nodeMap.get(toNode);
                    edges.add(new Edge(fromIndex, toIndex, (int) weight));
                }

                Result kruskalResult = findMST(numVertices, edges);

                JSONObject graphResult = new JSONObject();
                graphResult.put("graph_id", graphId);

                JSONObject inputStats = new JSONObject();
                inputStats.put("vertices", numVertices);
                inputStats.put("edges", edges.size());
                graphResult.put("input_stats", inputStats);

                JSONObject kruskalJson = new JSONObject();
                JSONArray mstEdgesArray = new JSONArray();

                for (Edge edge : kruskalResult.mstEdges) {
                    JSONObject edgeJson = new JSONObject();
                    edgeJson.put("from", (String) nodes.get(edge.from));
                    edgeJson.put("to", (String) nodes.get(edge.to));
                    edgeJson.put("weight", edge.weight);
                    mstEdgesArray.add(edgeJson);
                }

                kruskalJson.put("mst_edges", mstEdgesArray);
                kruskalJson.put("total_cost", kruskalResult.totalCost);
                kruskalJson.put("operations_count", kruskalResult.operationsCount);
                kruskalJson.put("execution_time_ms", Math.round(kruskalResult.executionTime * 100.0) / 100.0);

                graphResult.put("kruskal", kruskalJson);
                resultsArray.add(graphResult);
            }

            JSONObject output = new JSONObject();
            output.put("results", resultsArray);

            FileWriter file = new FileWriter("output_kruskal.json");
            file.write(output.toJSONString());
            file.flush();
            file.close();

            System.out.println("Kruskal's algorithm completed successfully!");
            System.out.println("Results saved to output_kruskal.json");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}