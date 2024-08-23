import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class PriorityQueue3<E> implements PriorityQueueIF<LabelledPoint>{
    private class MyCompare implements Comparator<LabelledPoint>{  // making a nested Comparator class to sort 
        @Override
        public int compare(LabelledPoint p1, LabelledPoint p2){
            if(p1.getKey() < p2.getKey()){     
                return 1;
            }
            if(p1.getKey() == p2.getKey()){
                return 0;
            }
            return -1;
        }
    }

    int k; 
    PointSet set;
    LabelledPoint Q_vector; 
    int size = 0;
    PriorityQueue<LabelledPoint> DistLabel_points;
    
    
    public PriorityQueue3(int k){ //creates an empty queue by specifying the capicity
        this.size = 0;
        this.k = k;
        this.DistLabel_points = new PriorityQueue<LabelledPoint>(k+1, new MyCompare());  
        
    }

    public PriorityQueue3(PriorityQueue<LabelledPoint> list,int k){ //creates an queue from a specific ArrayList and the capicity
        this.size = 0;
        this.k = k;
        this.DistLabel_points = list;
    }

   // Inserts the specified element into this queue if it is possible to do so immediately 
	// without violating capacity restrictions.
    public boolean offer(LabelledPoint e){
        if(e == null){
            throw new NullPointerException("The entered Element is not allowed, it is Null");
        }

        //when size of PQ is full, find out if e has a smaller distance than a point in the PQ, if so remove the largest distance and add e into a correct index
        if(size() == k){
            if((DistLabel_points.peek()).getKey() > e.getKey()){     
                DistLabel_points.poll();
                offer(e);
                return true;
            }
            else{
                return false;
            }                 
        }
        return DistLabel_points.offer(e);
    }

    // Retrieves and removes the head of this queue, or returns null if this queue is empty.
    public LabelledPoint poll(){
        return DistLabel_points.poll();
    }

    // Retrieves, but does not remove, the head of this queue, or returns null if this queue is empty.
    public LabelledPoint peek(){
      
        return DistLabel_points.peek();
    }

    // Returns the number of elements in this queue.
    public int size(){
        return DistLabel_points.size();
    }

    // Returns true if this queue contains no elements.
    public boolean isEmpty(){
        return DistLabel_points.isEmpty();
    }

    public ArrayList<LabelledPoint> findKNN(LabelledPoint Q,ArrayList<LabelledPoint>points){ 
        this.size = 0;
        this.DistLabel_points = new PriorityQueue<LabelledPoint>(k+1, new MyCompare());
        //becuase im re using PQ for all Queries i reset my Arraylist that holds the non Query points
        for(int i = 0; i < points.size(); i++){               
            LabelledPoint holder = points.get(i);
            double dis = Q.distanceTo(holder);
            holder.setKey(dis);
            offer(holder);
        }
        ArrayList<LabelledPoint> holding = new ArrayList<LabelledPoint>(DistLabel_points);
        holding.sort(new MyCompare());
        return holding;

    }
}