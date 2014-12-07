
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;


public class Solver {
   
    static int maxProfit=0;
    static int n;
    static byte[] inc;
    static byte[] incCopy;
    static  int W;
   static  List<Node> list1;
   static int[] weights;
   static int[] values;
 /*   public static void main(String[] args)throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
         W =Integer.parseInt(br.readLine());
     n=Integer.parseInt(br.readLine());
    weights=new int[n+1];
    values=new int[n+1];
    inc =new boolean[n+1];
    incCopy=new boolean[n+1];
    int total=0;
    for(int i=0;i<=n;i++)
      weights[i]=Integer.parseInt(br.readLine());//should be sorted 
      for(int i=0;i<=n;i++)
        values[i]=Integer.parseInt(br.readLine());//should be sorted 
      
    
    check(0,0,0);
   System.out.println(maxProfit);
   for(int j=1;j<=n;j++)
       System.out.println(incCopy[j]);

   }*/
 public static void main(String[] args) {
     try {
         solve(args);
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
        if(fileName == null)
            return;
        
        // read the lines out of the file
        List<String> lines = new ArrayList<String>();

        BufferedReader input =  new BufferedReader(new FileReader(fileName));
        try {
            String line = null;
            while (( line = input.readLine()) != null){
                lines.add(line);
            }
        }
        finally {
            input.close();
        }
        
        
        // parse the data in the file
        String[] firstLine = lines.get(0).split("\\s+");
         n = Integer.parseInt(firstLine[0]);
         W = Integer.parseInt(firstLine[1]);
         
        inc =new byte[n+1];
        incCopy=new byte[n+1];
        list1=new ArrayList<Node>(n+1);
        weights=new int[n+1];
        values=new int[n+1];
        for(int i=1; i < n+1; i++)
        {
          String line = lines.get(i);
         
          String[] parts = line.split("\\s+");

          int v = Integer.parseInt(parts[0]);
            values[i]=v;
            
          int w = Integer.parseInt(parts[1]);
            weights[i]=w;
            Node node=new Node(w,v,(float)v/w,0,(byte)0,i);
        //   System.out.println(node.getWv());
            list1.add(node);
        }
        
        Collections.sort(list1);
        Collections.reverse(list1);
        Node node1=new Node(0,0,0,0,(byte)0,0);
        list1.add(0,node1);
      for(int j=1;j<=n;j++)
       {
    list1.get(j).setIndex(j);       
           }
   /*     for(int j=0;j<=n;j++)
         {
             System.out.println(list1.get(j).getValue()+" "+list1.get(j).getWeight()+" "+list1.get(j).getWv()+" "+list1.get(j).getIndex());
             
             }     
     */   
        check(node1);
        System.out.println(maxProfit+ " "+1);
        
      for(int j=1;j<=n;j++)
      {Node a1=list1.get(j);
          a1.setWv(a1.getIndex2());
          }
        Collections.sort(list1);
      /* for(int j=0;j<=n;j++)
         {
             System.out.println(list1.get(j).getValue()+" "+list1.get(j).getWeight()+" "+list1.get(j).getWv()+" "+list1.get(j).getIndex());
             
             } */   
        for(int j=1;j<=n;j++)
        {
           System.out.print(incCopy[list1.get(j).getIndex()]+" ");
        }
    }
   /* public static void check(int i,int weight,int value)
    {
        if(weight<W && value>maxProfit)
            maxProfit=value;
        if(bound(i,weight,value)>=maxProfit)
     {    if(i==n)
          { //System.out.println(i+" "+weight+" "+value);
           for(int k=1;k<=n;k++)
               list1.get(k).setTf(inc[k]?(byte)1:0);
          return ;}
          else{Node a=list1.get(i+1);
          inc[i+1]=true;
          check(i+1,weight+a.getWeight(),value+a.getValue());
          inc[i+1]=false;
              check(i+1,weight,value);
          }
          
          
          }
        }*/
    
   public static void check(Node a1)
   {Stack<Node> stack =new Stack<Node>();
    stack.push(a1);
    int weight=0;
    int value=0;
    int i=0;
    while(!stack.isEmpty()){
   
        Node c=stack.pop();
        weight=c.getWeight();
        value=c.getValue();
        
        i=c.getIndex();
            inc[i]=c.getTf();
           // System.out.println(W+" "+weight+" "+" MP"+maxProfit+" "+value+" "+i);
       if(weight<W && value>maxProfit)
       {          
           //System.out.println(weight+"/---------/ "+value+" "+i);
           maxProfit=value;
       }
       if(bound(i,weight,value)>= maxProfit)
    {  
           if(i==n)
         { incCopy=Arrays.copyOf(inc,n+1);
            
         
         }
         else{
             Node b=new Node(list1.get(i+1));
            // System.out.println(b.getWeight()+" "+b.getValue()+"///// "+(i+1));
             b.setWeight(weight);
             b.setValue(value);
            
          //   System.out.println(list1.get(i+1).getWeight()+" "+list1.get(i+1).getValue()+"!!!!!!!!!!! "+(i+1));
             stack.push(b);
             Node a=new Node(list1.get(i+1));
      //       System.out.println(a.getWeight()+" "+a.getValue()+"!!!!!!!!!!! "+(i+1));
        a.setWeight(weight+a.getWeight());
             a.setValue(value+a.getValue());
             a.setTf((byte)1);
         stack.push(a);
         
         }
         
         }
        }
       }
   
    public static double bound(int i,int weight,int value)
    {float result;
     int j,k,tw;
     if(weight>=W)
         result =0;
     else{
         result=value;
         j=i+1;
         tw=weight;
         while(j<=n &&tw+list1.get(j).getWeight()<=W )
         {
             tw+=list1.get(j).getWeight();
                 result+=list1.get(j).getValue();
             j++;}
         k=j;
         if(k<=n)
             result=result+list1.get(k).getWv()*(W-tw);
         }
        return result;}
    
    
    
  /*  public static boolean isGood(int i,int weight,int total)
    {//System.out.println(i+" "+weight+" "+total);
        return (total+weight>=W) &&(weight==W||weight+weights[i+1]<=W);
        }
    */
}

class Node implements Comparable<Node> {
private int index;
    private int index2;
   private int weight;
   private int value;
   private float wv;
   private byte tf;
    public Node(int weight,int value,float wv,int i,byte tf,int index2) {
     setWeight(weight);
     setValue(value);
     setWv(wv);
   setIndex(i);
   setTf(tf);
   setIndex2(index2); }
    
    public Node(Node n)
    {
        this(n.weight,n.value,n.wv,n.index,n.tf,n.index2);
        
        
        
        
        }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setWv(float wv) {
        this.wv = wv;
    }

    public float getWv() {
        return wv;
    }


    @Override
    public int compareTo(Node o) {
        if (this.getWv()>o.getWv())
            return 1;
        else if (this.getWv()<o.getWv())
            return -1;
        return 0;
    }
    public int compare(Node o) {
        return this.index-o.index;
    }
    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setTf(byte tf) {
        this.tf = tf;
    }

    public byte getTf() {
        return tf;
    }

    public void setIndex2(int index2) {
        this.index2 = index2;
    }

    public int getIndex2() {
        return index2;
    }
}
