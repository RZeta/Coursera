

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.lang.System;

import java.util.ArrayList;
import java.util.Collections;


public class Solver {
           

static double objF=0.0;
static ArrayList<Integer> Tour ;
   static float[][] Distance; 
    public static void main(String[] args) 
    throws IOException
    {try {
         solve(args); // calling solve with the command-line argument
     } catch (IOException e) {
         e.printStackTrace();
     }
    
    }
    public static void solve(String[] args) throws IOException {
            
            String fileName = null;
            
            // get the temp file name
            for(String arg : args){
                if(arg.startsWith("-file=")){
                    fileName = arg.substring(6);
                } 
            }
            
			// reading data from the file .
            if(fileName == null)
            return;
        BufferedReader input =  new BufferedReader(new FileReader(fileName));
        String line = null;
        
            line =input.readLine();
            String[] firstLine ;
            int N=Integer.parseInt(line);
            float[][] dis=new float[N][2];
             Distance=new float[N][N]; // array to store distance between two points
       ArrayList<ArrayList<SortList>> DistSort=new ArrayList<ArrayList<SortList>>(N);
   //    for(int i=0;i<N;i++)
     //      DistSort.add(new ArrayList<SortList>(N));
       
       // storing the input co-ordinates in a 2-d array
            try {
                        String line1 = null;
                        for(int i=0;i<N;i++){
                            
                            line1 =input.readLine();
                       firstLine=line1.split("\\s+");
                       dis[i][0]=Float.parseFloat(firstLine[0]);
                            dis[i][1]=Float.parseFloat(firstLine[1]);
                        }
                    }
                    finally {
                        input.close();
                    }
            SortList a; // declaring instance of SortList class
            
            ArrayList<SortList> List;
        for(int j=0;j<N;j++)
        {List=new ArrayList<SortList>(N);
            for(int k=0;k<N;k++)
            {
                Distance[j][k]=Dist(dis[j][0],dis[j][1],dis[k][0],dis[k][1]);//using dist function to calculate distance between point j and k
                a=new SortList();
                a.setDistance( Distance[j][k]);
                a.setVertex(k);
    List.add(k,a);
                       
            }
         Collections.sort(List);
       DistSort.add(j,List);
       /*debug print statements
         //for(int i=0;i<N;i++)
         //System.out.println(DistSort.get(j).get(i).getDistance()+" "+DistSort.get(j).get(i).getVertex());
        
    //  System.out.println(Distance[j][j]);   */
     }
        
        
           /*    for(int l=0;l<N;l++)
    System.out.println(DistSort.get(0).get(l).getDistance()+" "+DistSort.get(0).get(l).getVertex()+" "+
                       Distance[0][DistSort.get(0).get(l).getVertex()]);
           */
        
        
            Tour=new ArrayList<Integer>(N);
     // System.out.println("Tour-size"+Tour.size());
       
       Tour.add(0,0); // Tour starts from node 0 
            double objV=0.0;
            
            // creating a greedy Tour choosing the minimum distance index at each step.
       for(int i=1;i<N;i++)
       {
           int v=Tour.get(i-1);
           int ind=0;
           float min=Float.MAX_VALUE;
           for(int j=0;j<N;j++)
           {
               float c=Distance[v][j];
               if(v!=j && !Tour.contains(j))
               {
                   if(c<min)
                   {  min=c;
                   ind=j;
                   }
               }               
               }
           Tour.add(i,ind);
           objV=objV+(double)min;
           }
           objV=objV+Distance[0][Tour.get(N-1)];// final value of the distance using greedy algorithm
            System.out.println(objV+" "+0);
         //prints the greedy algo hamiltonian cycle
            for(int o=0;o<N;o++)
                System.out.print(Tour.get(o)+" ");
     double objFin=objV;
           
            boolean c=true;
            ArrayList<Integer> TourI=new ArrayList<Integer>(N);
           
int c1=0;

//start of simulated annealing 
     float Temp=120f;
     double objF1=0.0f;
     double objF2=0.0f;
     float cT=0.0f;
     float rate=0.0001f;
     ArrayList<Integer> Exclude=new ArrayList<Integer>(N);
       while(Temp>0)
       {
           int v1=0,v2=0,i1,i2;
           while(v1==v2){
           v1= (int)(Math.random()*N);
                   if(Temp<50)
                               {  i2=(int)(Math.random()*N*0.25)+1; // limiting the scope of the neighbourhood
                           
                            v2=DistSort.get(v1).get(i2).getVertex(); 
                             }
                                         else if(Temp<4)
                                           {  i2=(int)(Math.random()*N*0.05)+1;// limiting the scope of the neighbourhood
                           
                            v2=DistSort.get(v1).get(i2).getVertex(); 
                             }
                       else           {  i2=(int)(Math.random()*N*0.5)+1;
                           
                           v2=DistSort.get(v1).get(i2).getVertex();
                           } 
                                                   
  
               }
           
           
            i1=Tour.indexOf(v1);
            i2=Tour.indexOf(v2);
           
          if(i1<i2)
          {
        TourI= new ArrayList<Integer>(swap(i1,i2,Tour));
          }
         else
          {
                  TourI= new ArrayList<Integer>(swap(i2,i1,Tour));
          }
           if(objFin>objF)
         {
                Tour= new ArrayList<Integer>(TourI);  
          //   cT=Temp;
         objFin=objF;   
      c1++;   }
           else // even if objective value is not reduced , swapping in case the acceptance probability criteria is satisfied- used to avoid local minima
         {
              //calculating acceptance probability
             double prob=Math.exp(-1*(objF-objFin)/Temp);
                 if(Math.random()<=prob)
                 {       
                        
                     Tour= new ArrayList<Integer>(TourI);               
                        objFin=objF;                        
                     }             
                     
               
               }
           
               TourI.removeAll(TourI);             
               objF=0.0;
               if(Temp>10)
                            rate=0.0001f;
                             
                        else
                            rate =0.000003f;
                      if(Temp<2)
                              rate=0.000001f;
               Temp=Temp-rate;      
           }
     
            System.out.println("swaps"+c1);
             System.out.println("\n"+objFin+" "+0);

           for(int o=0;o<N;o++)
               System.out.print(Tour.get(o)+" ");   
          
           
           
        }
        /*
     * function to swap two indices in a tour and return the newly formed tour
     */
    public static ArrayList<Integer> swap(int i1,int i2,ArrayList<Integer> Tour1)
    
    {int n=Tour.size();
        ArrayList<Integer> TourI=new ArrayList<Integer>(n);
            for(int i=0;i<i1-1;i++)
            {
                objF=objF+Distance[Tour1.get(i)][Tour1.get(i+1)];                   
            TourI.add(i,Tour1.get(i));
            }
            if(i1!=0){
            TourI.add(i1-1,Tour1.get(i1-1));
             objF=objF+Distance[Tour1.get(i1-1)][Tour1.get(i2)];
            }
             for(int j=i2;j>i1;j--)
             {
                     
                         objF=objF+Distance[Tour1.get(j)][Tour1.get(j-1)];                   
                     TourI.add(Tour1.get(j));
                     
                 }    
               TourI.add(Tour1.get(i1));
               
               //System.out.println(Tour.get(i2)+"  "+Tour.get(i1+1));
               if(i2!=n-1)
                objF=objF+Distance[Tour1.get(i1)][Tour1.get(i2+1)];
                for(int k=i2+1;k<n-1;k++)
                {
                        objF=objF+Distance[Tour1.get(k)][Tour1.get(k+1)];                   
                        TourI.add(Tour1.get(k));             
                    }
                if(TourI.size()!=n)
                    TourI.add(Tour1.get(n-1));
               objF=objF+Distance[TourI.get(0)][TourI.get(n-1)];
            
        return TourI;
        
        }
    
    
    public static float Dist(float x1,float y1,float x2,float y2)
    {
        
        return (float)Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
        
        }
    
}

/*
This class will be used to create a list of vertices sorted by their distance from a point . 
*/
 class SortList implements Comparable<SortList>{
    
    private float Distance;// indicates the distance of the vertex from the point being referred to by the instance
    private int vertex;
    public SortList(){}

public SortList(float Distance,int vertex1)
{
    setDistance(Distance);
    setVertex(vertex1);
    }
    public void setDistance(float Distance) {
        this.Distance = Distance;
    }

    public float getDistance() {
        return Distance;
    }

    public void setVertex(int vertex) {
        this.vertex = vertex;
    }

    public int getVertex() {
        return vertex;
    }

    @Override
    public int compareTo(SortList o) {
        if(this.getDistance()>o.getDistance())
        return 1;
        else 
            return -1;
    }
}
