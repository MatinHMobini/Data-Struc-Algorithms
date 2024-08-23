# 🛠️ KNearestNeighbors_Using_PriorityQueue & ArrayList and Paris_Metro_Using_Graphs

Welcome to the repository containing two projects that explore data structures and algorithms:

1. **KNearestNeighbors_Using_PriorityQueue & ArrayList**
2. **Paris_Metro_Using_Graphs**

---

## KNearestNeighbors_Using_PriorityQueue & ArrayList

### ⏲️ Performance Analysis
- **Priority Queue 1**: 
  - **Structure**: ArrayList (Ascending Order)
  - **Complexity**: `O(K)`
  - **Performance**: Slowest

- **Priority Queue 2**: 
  - **Structure**: ArrayList (Descending Order)
  - **Complexity**: `O(log(K))`
  - **Performance**: Second fastest

- **Priority Queue 3**: 
  - **Structure**: Java's PriorityQueue (Descending Order)
  - **Complexity**: `O(K)`
  - **Performance**: Fastest

### 📊 Summary
- **PQ1**: Slower due to `O(K)` insertion in ArrayList.
- **PQ2**: Faster with `O(log(K))`, uses descending order.
- **PQ3**: Fastest with Java’s PriorityQueue, also using descending order.

---

## 🗼 Paris_Metro_Using_Graphs

### 🗂️ Data Structure
- **AdjacencyMapGraph**: 
  - **Vertices & Edges**: Managed using `ConcurrentHashMap` and `LinkedList`.
  - **InnerVertex & InnerEdge**: Subclasses to manage stations and connections.

### 📈 Algorithms
- **DFS** (Depth-First Search): 
  - **Purpose**: Identify all stations on the same subway line.
- **Dijkstra’s Algorithm**: 
  - **Purpose**: Find the shortest path between stations.

### 🛤️ Examples
1. **Stations on the Same Line**: 
   - **Output**: Lists all stations on the same line as the input station.
2. **Shortest Path**: 
   - **Output**: Calculates the shortest path and time between two stations.
3. **Shortest Path with Non-Working Line**: 
   - **Output**: Computes paths avoiding specified non-working lines.


Thank you for visiting!

