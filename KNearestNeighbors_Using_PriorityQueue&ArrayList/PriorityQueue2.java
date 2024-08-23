import java.util.ArrayList;
import java.util.Comparator;
@SuppressWarnings("unchecked")
public class PriorityQueue2<E> implements PriorityQueueIF<LabelledPoint>{

    private class MyCompare implements Comparator<LabelledPoint>{ // making a nested Comparator class to sort 
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
    ArrayList<LabelledPoint> DistLabel_points;
   

    public PriorityQueue2(int k){ //creates an empty queue by specifying the capicity
        this.size = 0;
        this.k = k;
        this.DistLabel_points = new ArrayList<LabelledPoint>(k+1);   
    }

    public PriorityQueue2(ArrayList list,int k){ //creates an queue from a specific ArrayList and the capicity
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
        //when size of ArrayList is full, find out if e has a smaller distance than a point in the Arraylist, if so remove the largest distance and add e into a correct index
        if(size() == k){
            if((DistLabel_points.get(0)).getKey() > e.getKey()){    //compare with last postion and choose which is lower distance 
                LabelledPoint emptying = poll();
                DistLabel_points.add(e);
                int location = size()-1;
                while(location > 0 ){    
                    int parent_index = ((location)/2);


                    if(((DistLabel_points.get(location)).getKey()) <= ((DistLabel_points.get(parent_index)).getKey())){
                        break;
                    } 

                    LabelledPoint hold = DistLabel_points.get(location);
                    LabelledPoint hold1 = DistLabel_points.get(parent_index);

                    DistLabel_points.set(location,hold1);
                    DistLabel_points.set(parent_index,hold);

                    location = parent_index;
                }
                size++;
                return true;
            }
            else{
                return false;
            }                 
        }

        if(size() == 0){
            DistLabel_points.add(e);
            size++;
            return true;
        }
    
        DistLabel_points.add(e);

        int location = size()-1;
         while(location > 0 ){          //using same idea as lab
            int parent_index = ((location)/2);


            if(((DistLabel_points.get(location)).getKey()) <= ((DistLabel_points.get(parent_index)).getKey())){
                break;
            } 

            LabelledPoint hold = DistLabel_points.get(location);
            LabelledPoint hold1 = DistLabel_points.get(parent_index);

            DistLabel_points.set(location,hold1);
            DistLabel_points.set(parent_index,hold);

            location = parent_index;
        }

        size++;
        return true;

    }

    // Retrieves and removes the head of this queue, or returns null if this queue is empty.
    public LabelledPoint poll(){
        if(isEmpty()){
            return null;
        }

        LabelledPoint hold = DistLabel_points.get(0);
        DistLabel_points.set(0,DistLabel_points.get(size()-1));
        DistLabel_points.set(size()-1,hold);
        DistLabel_points.remove(size()-1);
        size--;

        //using lab 4 idea
        int location = 0;
        while(((location * 2) + 1) < size()){

            int left_Index = (location * 2) + 1;
            int smaller_ChildIndex = left_Index;

            if(((location * 2) + 2) < size()){

                int right_Index = (location * 2) + 2;
                if(((DistLabel_points.get(left_Index)).getKey()) < (DistLabel_points.get(right_Index)).getKey()){
                    smaller_ChildIndex = right_Index;
                }

                if(((DistLabel_points.get(smaller_ChildIndex)).getKey()) <= (DistLabel_points.get(location)).getKey()){
                    break;
                }

                LabelledPoint holdings = DistLabel_points.get(location);
                LabelledPoint hold1 = DistLabel_points.get(smaller_ChildIndex);

                DistLabel_points.set(location,hold1);
                DistLabel_points.set(smaller_ChildIndex,holdings);

                location = smaller_ChildIndex;
            }
        }
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
        this.size = 0;
        this.DistLabel_points = new ArrayList<LabelledPoint>(k+1);  
        //becuase im re using a PQ for all Queries i reset my Arraylist that holds the non Query points and my size
        for(int i = 0; i < points.size(); i++){                          
            LabelledPoint holder = points.get(i);
            double dis = Q.distanceTo(holder);
            holder.setKey(dis);
            offer(holder);
        }
        DistLabel_points.sort(new MyCompare());
        return DistLabel_points;
    }
}