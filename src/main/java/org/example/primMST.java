package org.example;

import java.util.*;
import java.io.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class primMST {

     public static class Edge {
        int from;
        int to;
        int weight;

        public Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
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

    public static Result findMST(int numVertices, List<Edge> edges) {
        Result result = new Result();
        long startTime = System.nanoTime();

        int[][] adjacencyMatrix = new int[numVertices][numVertices];

        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                adjacencyMatrix[i][j] = Integer.MAX_VALUE;
            }
        }

        for (Edge edge : edges) {
            adjacencyMatrix[edge.from][edge.to] = edge.weight;
            adjacencyMatrix[edge.to][edge.from] = edge.weight;
            result.operationsCount++;
        }

        boolean[] visited = new boolean[numVertices];
        int[] minWeight = new int[numVertices];
        int[] parent = new int[numVertices];

        for (int i = 0; i < numVertices; i++) {
            minWeight[i] = Integer.MAX_VALUE;
            parent[i] = -1;
        }

        minWeight[0] = 0;

        for (int count = 0; count < numVertices; count++) {
            int minVertex = -1;

            for (int v = 0; v < numVertices; v++) {
                result.operationsCount++;
                if (!visited[v] && (minVertex == -1 || minWeight[v] < minWeight[minVertex])) {
                    minVertex = v;
                }
            }

            visited[minVertex] = true;

            if (parent[minVertex] != -1) {
                result.mstEdges.add(new Edge(parent[minVertex], minVertex, minWeight[minVertex]));
                result.totalCost += minWeight[minVertex];
            }

            for (int v = 0; v < numVertices; v++) {
                result.operationsCount++;
                if (adjacencyMatrix[minVertex][v] != Integer.MAX_VALUE && !visited[v]) {
                    if (adjacencyMatrix[minVertex][v] < minWeight[v]) {
                        minWeight[v] = adjacencyMatrix[minVertex][v];
                        parent[v] = minVertex;
                    }
                }
            }
        }

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

                Result primResult = findMST(numVertices, edges);

                JSONObject graphResult = new JSONObject();
                graphResult.put("graph_id", graphId);

                JSONObject inputStats = new JSONObject();
                inputStats.put("vertices", numVertices);
                inputStats.put("edges", edges.size());
                graphResult.put("input_stats", inputStats);

                JSONObject primJson = new JSONObject();
                JSONArray mstEdgesArray = new JSONArray();

                for (Edge edge : primResult.mstEdges) {
                    JSONObject edgeJson = new JSONObject();
                    edgeJson.put("from", (String) nodes.get(edge.from));
                    edgeJson.put("to", (String) nodes.get(edge.to));
                    edgeJson.put("weight", edge.weight);
                    mstEdgesArray.add(edgeJson);
                }

                primJson.put("mst_edges", mstEdgesArray);
                primJson.put("total_cost", primResult.totalCost);
                primJson.put("operations_count", primResult.operationsCount);
                primJson.put("execution_time_ms", Math.round(primResult.executionTime * 100.0) / 100.0);

                graphResult.put("prim", primJson);
                resultsArray.add(graphResult);
            }

            JSONObject output = new JSONObject();
            output.put("results", resultsArray);

            FileWriter file = new FileWriter("output_prim.json");
            file.write(output.toJSONString());
            file.flush();
            file.close();

            System.out.println("Prim's algorithm completed successfully!");
            System.out.println("Results saved to output_prim.json");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}