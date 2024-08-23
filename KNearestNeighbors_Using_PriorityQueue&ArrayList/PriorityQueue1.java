import java.util.ArrayList;
import java.util.Comparator;
@SuppressWarnings("unchecked")
public class PriorityQueue1<E> implements PriorityQueueIF<LabelledPoint>{  
    private class MyCompare implements Comparator<LabelledPoint>{ // making a nested Comparator class to sort 

        @Override
        public int compare(LabelledPoint p1, LabelledPoint p2){
            if(p1.getKey() < p2.getKey()){      ///might  need to change to oppsite < > signs
                return 1;
            }
            if(p1.getKey() == p2.getKey()){
                return 0;
            }
            return -1;
        }
    }

    int k = 0; 
    PointSet set;
    LabelledPoint Q_vector; 
    int size = 0;
    ArrayList<LabelledPoint> DistLabel_points;
    ArrayList<LabelledPoint> holder;
    
    
    public PriorityQueue1(int k){ //creates an empty queue by specifying the capicity
        this.size = 0;
        this.k = k;
        this.DistLabel_points = new ArrayList<LabelledPoint>(k+1);    
        
    }

    public PriorityQueue1(ArrayList list,int k){ //creates an queue from a specific ArrayList and the capicity
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

        if(size() == 0){
            DistLabel_points.add(e);
            size++;
            return true;
        }
        //when size of ArrayList is full, find out if e has a smaller distance than a point in the Arraylist, if so remove the largest distance and add e into a correct index
        if(size() == k){        
            for(int i = 0; i < size(); i++){
                if(e.getKey() < DistLabel_points.get(i).getKey()){
                    DistLabel_points.remove(size() - 1);
                    DistLabel_points.add(i,e);
                    return true;
                    }
                }
            return false;     
        }

        for(int j = 0; j < size(); j++){
            if(e.getKey() < DistLabel_points.get(j).getKey()){
                DistLabel_points.add(j,e);
                size++;
                return true;
            }
        }
        return false;
    }

    // Retrieves and removes the head of this queue, or returns null if this queue is empty.
    public LabelledPoint poll(){
        if(isEmpty()){
            return null;
        }

        LabelledPoint hold = DistLabel_points.get(0);
        DistLabel_points.remove(0);
        size--;

        return hold;
    }

    // Retrieves, but does not remove, the head of this queue, or returns null if this queue is empty.
    public LabelledPoint peek(){
        if(isEmpty()){
            return null;
        }
        return DistLabel_points.get(0);
    }

    // Returns the number of elements in this queue.
    public int size(){
        return size;
    }

    // Returns true if this queue contains no elements.
    public boolean isEmpty(){
        return size == 0;
    }

    public ArrayList<LabelledPoint> findKNN(LabelledPoint Q,ArrayList<LabelledPoint>points){ 
        this.DistLabel_points = new ArrayList<LabelledPoint>(k+1);
        this.size = 0;
        //becuase im re using a PQ for all Queries i reset my Arraylist that holds the non Query points and my size
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