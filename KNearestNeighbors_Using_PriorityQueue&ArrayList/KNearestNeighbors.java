/* ---------------------------------------------------------------------------------
Input: 
- A query point Q (with coordinates in N-dimensional space)
- A set of points P (each point also has N-dimensional coordinates)
- The number of neighbors to find, k

Output:
- The k-nearest neighbors of point Q from set P

(c) Robert Laganiere, CSI2510 2023
------------------------------------------------------------------------------------*/

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class KNearestNeighbors {
	// finds the k nearest neighbors of query point in list of points
    public static List<Point> findKNearestNeighbors(Point query, List<Point> points, int k) {
		
		// maxHeap to contain the neighbors
        PriorityQueue<Point> maxHeap = new PriorityQueue<>(
		       (a, b) -> Double.compare(b.distanceTo(query), a.distanceTo(query))); // comparator used in Heap
			 

        for (Point p : points) {
            maxHeap.offer(p);          // insert p in Heap
            

            if (maxHeap.size() > k) {  // if Heap is full
                maxHeap.poll();        // removeMax from Heap
            }
        }
		// transfer neighbors to a list in order
        List<Point> neighbors = new ArrayList<>();
        while (!maxHeap.isEmpty()) {
            neighbors.add(maxHeap.poll());
        }
        return neighbors;
    }

    public static void main(String[] args) {
        // Example usage:
        Point query = new Point(0, 0, 0); // Replace with your query point
        List<Point> points = new ArrayList<>(); // Replace with your set of points
        int k = 3; // Number of nearest neighbors to find

        List<Point> nearestNeighbors = findKNearestNeighbors(query, points, k);
        System.out.println("K Nearest Neighbors:");
        for (Point neighbor : nearestNeighbors) {
            System.out.println(neighbor);
        }
    }
}

class Point {
    double[] coordinates;

    public Point(double... coordinates) {
        this.coordinates = coordinates;
    }

    public double distanceTo(Point other) {
        double sum = 0;
        for (int i = 0; i < coordinates.length; i++) {
            double diff = coordinates[i] - other.coordinates[i];
            sum += diff * diff;
        }
        return Math.sqrt(sum);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        for (double coordinate : coordinates) {
            sb.append(coordinate).append(", ");
        }
        sb.setLength(sb.length() - 2); 
        sb.append(")");
        return sb.toString();
    }
}
