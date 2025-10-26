# Minimum Spanning Tree Algorithm Analysis Report

## 1. Summary of Input Data and Algorithm Results

### Graph 1: Small Graph (4 vertices, 5 edges)
- **Prim's Algorithm:**
  - Total Cost: 6
  - Execution Time: 0.02 ms
  - Operations Count: 37
  
- **Kruskal's Algorithm:**
  - Total Cost: 6
  - Execution Time: 1.21 ms
  - Operations Count: 19

### Graph 2: Small Graph (5 vertices, 7 edges)
- **Prim's Algorithm:**
  - Total Cost: 16
  - Execution Time: 0.01 ms
  - Operations Count: 57
  
- **Kruskal's Algorithm:**
  - Total Cost: 16
  - Execution Time: 0.03 ms
  - Operations Count: 30

### Graph 3: Small Graph (6 vertices, 9 edges)
- **Prim's Algorithm:**
  - Total Cost: 15
  - Execution Time: 0.01 ms
  - Operations Count: 81
  
- **Kruskal's Algorithm:**
  - Total Cost: 15
  - Execution Time: 0.02 ms
  - Operations Count: 31

### Graph 4: Medium Graph (10 vertices, 16 edges)
- **Prim's Algorithm:**
  - Total Cost: 25
  - Execution Time: 0.03 ms
  - Operations Count: 216
  
- **Kruskal's Algorithm:**
  - Total Cost: 25
  - Execution Time: 0.03 ms
  - Operations Count: 70

### Graph 5: Medium Graph (15 vertices, 24 edges)
- **Prim's Algorithm:**
  - Total Cost: 44
  - Execution Time: 0.05 ms
  - Operations Count: 474
  
- **Kruskal's Algorithm:**
  - Total Cost: 44
  - Execution Time: 0.04 ms
  - Operations Count: 92

### Graph 6: Large Graph (20 vertices, 33 edges)
- **Prim's Algorithm:**
  - Total Cost: 60
  - Execution Time: 0.64 ms
  - Operations Count: 833
  
- **Kruskal's Algorithm:**
  - Total Cost: 60
  - Execution Time: 0.09 ms
  - Operations Count: 150

### Graph 7: Large Dense Graph (30 vertices, 51 edges)
- **Prim's Algorithm:**
  - Total Cost: 90
  - Execution Time: 0.2 ms
  - Operations Count: 1851
  
- **Kruskal's Algorithm:**
  - Total Cost: 90
  - Execution Time: 0.08 ms
  - Operations Count: 239

---

## 2. Comparison Between Prim's and Kruskal's Algorithms

### Theoretical Analysis

**Prim's Algorithm:**
- Time Complexity: O(V²) with adjacency matrix implementation
- Space Complexity: O(V²) for adjacency matrix
- Works by growing a single tree from a starting vertex
- Best for dense graphs where E ≈ V²

**Kruskal's Algorithm:**
- Time Complexity: O(E log E) for sorting edges
- Space Complexity: O(E + V) for edge list and union-find
- Works by sorting all edges and adding them if they don't create a cycle
- Best for sparse graphs where E << V²

### Practical Performance Comparison

| Graph Size | Vertices | Edges | Prim Operations | Kruskal Operations | Prim Time (ms) | Kruskal Time (ms) |
|------------|----------|-------|-----------------|--------------------| ---------------|-------------------|
| Small      | 4        | 5     | 37              | 19                 | 0.02           | 1.21              |
| Small      | 5        | 7     | 57              | 30                 | 0.01           | 0.03              |
| Small      | 6        | 9     | 81              | 31                 | 0.01           | 0.02              |
| Medium     | 10       | 16    | 216             | 70                 | 0.03           | 0.03              |
| Medium     | 15       | 24    | 474             | 92                 | 0.05           | 0.04              |
| Large      | 20       | 33    | 833             | 150                | 0.64           | 0.09              |
| Large      | 30       | 51    | 1851            | 239                | 0.2            | 0.08              |

### Key Observations

**Operation Count:**
- Kruskal's algorithm consistently performs fewer operations than Prim's
- As graph size increases, the gap widens significantly
- For the 30-vertex graph, Prim performs 7.7x more operations than Kruskal

**Execution Time:**
- For very small graphs (4 vertices), Prim is faster despite more operations
- As graphs grow larger, Kruskal becomes consistently faster
- For the 20-vertex graph, Kruskal is 7x faster than Prim
- For the 30-vertex graph, Kruskal is 2.5x faster than Prim

**Correctness:**
- Both algorithms produce identical MST costs for all test cases
- Both algorithms produce valid MSTs with V-1 edges

---

## 3. Conclusions

### When to Use Prim's Algorithm:
1. **Dense graphs** - When the number of edges is close to V², Prim's O(V²) complexity becomes competitive
2. **Adjacency matrix representation** - If the graph is already stored as an adjacency matrix
3. **Starting from specific vertex** - When you need the MST rooted at a particular vertex
4. **Very small graphs** - Overhead of sorting in Kruskal may not be worth it

### When to Use Kruskal's Algorithm:
1. **Sparse graphs** - When E << V², Kruskal's O(E log E) is much better than Prim's O(V²)
2. **Edge list representation** - If the graph is stored as a list of edges
3. **Large graphs** - Kruskal scales better as shown in our tests
4. **Distributed systems** - Easier to parallelize edge sorting and processing

### Overall Recommendation:

Based on our experimental results:

- **For small graphs (< 10 vertices):** Either algorithm works well, performance difference is negligible
- **For medium to large graphs:** Kruskal's algorithm is significantly more efficient in terms of both operations and execution time
- **For very dense graphs:** Prim's algorithm with a priority queue implementation (not used in this study) would be more competitive

The key factor is graph density. Our test cases represent relatively sparse graphs (edges ≈ 2-3 times vertices), which is typical for real-world transportation networks. In such scenarios, Kruskal's algorithm demonstrates clear superiority.

### Implementation Notes:

- Our Prim's implementation uses adjacency matrix, which contributes to higher operation counts
- Kruskal's implementation uses Union-Find with path compression, which is highly efficient
- For production systems handling large transportation networks, Kruskal's algorithm is recommended

---

References:

https://www.geeksforgeeks.org/dsa/kruskals-minimum-spanning-tree-algorithm-greedy-algo-2/
https://www.geeksforgeeks.org/dsa/prims-minimum-spanning-tree-mst-greedy-algo-5/
