import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
@SuppressWarnings("unchecked")
public class KNN{
    static int k;
    static int version;
    public void KNN(){
        // do this in each PQ class
    }
    void SetK(int k){
        this.k = k;
    }
    // for k being 1. 10, 50, 100, 500, 2000
    public static void main(String[] args){
        double startingTest = System.currentTimeMillis();

        version = Integer.parseInt(args[0]);
        k = Integer.parseInt(args[1]);

        PointSet pointsSet = new PointSet(PointSet.read_ANN_SIFT(args[2]));
        ArrayList<LabelledPoint> pointList = pointsSet.getPointsList();

        PointSet Queries = new PointSet(PointSet.read_ANN_SIFT(args[3]));
        ArrayList<LabelledPoint> Q_list = Queries.getPointsList();

        ArrayList<LabelledPoint> nearestN;
        int num_queries = 100;
        Double finalTime = 0.0;
        try{
            FileWriter f = new FileWriter("knn " + version + " " + " " + k + " " + num_queries + " 1000000.txt"); //name of txt?

            if(version == 1){
                PriorityQueue1<LabelledPoint> PQ1 = new PriorityQueue1(k);
                
                for(int i = 0; i < 100; i++){
                    double begin = System.currentTimeMillis();
                    nearestN = PQ1.findKNN(Q_list.get(i), pointList);
                    double end = (System.currentTimeMillis()) - begin;
                    finalTime = finalTime + end;

                    String Write_this = (i + ": ");
                    for(int p = 0; p < nearestN.size(); p++){
                        Write_this = Write_this + nearestN.get(p).getLabel();
                        if(p != nearestN.size() - 1){
                            Write_this = Write_this + ", ";
                        }
                    }
                    System.out.println(Write_this);
                    f.write(Write_this);
                    f.write("\n");
                }
                System.out.println("" + finalTime);
                f.write("" + finalTime);
                f.close();
            }

            if(version == 2){
                PriorityQueue2<LabelledPoint> PQ2 = new PriorityQueue2(k);
                
                for(int i = 0; i < 100; i++){
                    double begin = System.currentTimeMillis();
                    nearestN = PQ2.findKNN(Q_list.get(i), pointList);
                    double end = (System.currentTimeMillis()) - begin;
                    finalTime = finalTime + end;

                    String Write_this = (i + ": ") ;
                    for(int p = 0; p < nearestN.size(); p++){
                        Write_this = Write_this + nearestN.get(p).getLabel();
                        if(p != nearestN.size() - 1){
                            Write_this = Write_this + ", ";
                        }
                    }
                    System.out.println(Write_this);
                    f.write(Write_this);
                    f.write("\n");
                }
                System.out.println("" + finalTime);
                f.write("" + finalTime);
                f.close();
            }

            if(version == 3){
                PriorityQueue3<LabelledPoint> PQ3 = new PriorityQueue3(k);
                
                for(int i = 0; i < 100; i++){
                    double begin = System.currentTimeMillis();
                    nearestN = PQ3.findKNN(Q_list.get(i), pointList);
                    double end = (System.currentTimeMillis()) - begin;
                    finalTime = finalTime + end;

                    String Write_this = (i + ": ");
                    for(int p = 0; p < nearestN.size(); p++){
                        Write_this = Write_this + nearestN.get(p).getLabel();
                        if(p != nearestN.size() - 1){
                            Write_this = Write_this + ", ";
                        }
                    }
                    System.out.println(Write_this);
                    f.write(Write_this);
                    f.write("\n");
                }
                System.out.println("" + finalTime);
                f.write("" + finalTime);
                f.close();
            }
        
        }
        catch(IOException e){
            System.err.println("IOExeption has occured: " + e.getMessage());
            e.printStackTrace();
        }
    }
}