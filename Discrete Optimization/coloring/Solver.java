import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;

    import java.util.Random;

public class Solver {
    
    public static void main(String[] args) 
    throws IOException
    {try {
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
        BufferedReader input =  new BufferedReader(new FileReader(fileName));
        String line = null;
line =input.readLine();
        String[] firstLine = line.split("\\s+");
int N=Integer.parseInt(firstLine[0]);//#Nodes
int E=Integer.parseInt(firstLine[1]);//#Edges
Graph2 G=new Graph2(N); // creating an object of Graph2 class

        List<String> lines = new ArrayList<String>(E);

        try {
                    String line1 = null;
                    while (( line1 = input.readLine()) != null){
                   firstLine=line1.split("\\s+");
                   G.addEdge(Integer.parseInt(firstLine[0]),Integer.parseInt(firstLine[1]));
                    }
                }
                finally {
                    input.close();
                }
        ArrayList<VertexDegree> ListFin=new ArrayList<VertexDegree>(N); // Creating a list of instances of VertexDegree class
        
          for(int i=0;i<N;i++)
          {VertexDegree v=new VertexDegree(i,G.adj(i)); // initialising an instance of VertexDegree class
              ListFin.add(v);             
              }   
                      
          ArrayList<ArrayList<Integer>> Coloring=new ArrayList<ArrayList<Integer>>(); // A List of Arraylist where in each sub array-list represents the vertices having same color 
          Coloring.add(new ArrayList<Integer>());
        Coloring.add(new ArrayList<Integer>());
        int s=1,c=0;
        ArrayList<Integer> exclude=new ArrayList<Integer>();
        for(int j=0;j<N;j++) 
        {   
          VertexDegree a;
            int k,maxIndex=0,maxSDeg=0,maxDeg=0;
            a=ListFin.get(0);
            for(int z=0;z<N;z++) // finds the vertex with max degree among those not colored 
            {
                if(exclude.contains(z))
                    continue ;
                else{
                VertexDegree V1=ListFin.get(z);
                if(V1.getSaturationDegree()>=maxSDeg)
                {maxSDeg=V1.getSaturationDegree();
                    a=V1;
                    }
                }
                }
           exclude.add(a.getVertex());
            ArrayList<Integer> X=a.getColors();//list of colors aroud vertex a 
            for( k=1;k<s;k++) // loop finds the smallest color that can be given to a vertex 
                if(X.contains(k))
                    continue;
            else
                    break;
            a.setColor(k);
       Coloring.get(k).add(a.getVertex());      //adding the vertex a to color group k
            if(s==k)
            {  s++;
            
                Coloring.add(new ArrayList<Integer>());
            }
            ArrayList<Integer> C=G.adjList(a.getVertex()); // list of vertices adjacent to a
            int loop=0;
            while(loop<C.size())
            {
                VertexDegree b=ListFin.get(C.get(loop)); //getting a vertex adjacent to a
                    ArrayList<Integer> Y=b.getColors();
                if(!Y.contains(k))
                {Y.add(k); //adding k to the colors around vertex b
                 b.setSaturationDegree(b.getSaturationDegree()+1000);
                    
                    
                    }
                loop++;
                
                }
                
            }
        
   
        
           for(int i=0;i<N;i++)
   {
       VertexDegree a =ListFin.get(i);
       a.setColor(0);
       a.getColors().removeAll(a.getColors());
       }
   Coloring.remove(Coloring.size()-1);
   //     for(int i=0;i<Coloring.size();i++)
     //       System.out.println(Coloring.get(i));
 //  for(int i=0;i<N;i++)     
  // System.out.println(ListFin.get(i).getColors());
 // System.out.println(Coloring.size());
        
   int col=s-1;int rand[]=new int[Coloring.size()-1];
   for(int iter=1;iter<=10000;iter++)
   {
       if(iter%10==4||iter%10==8) 
       {
       rand=new int[Coloring.size()-1];
       for(int f=0;f<rand.length;f++)
           rand[f]=f;
          // System.out.println("random");
       StdRandom.shuffle(rand);
       }
   else if(iter%10==2||iter%10==5||iter%10==7||iter%10==0)
       { Collections.reverse((List)Coloring);
         //  System.out.println("reverse");
               Coloring.set(Coloring.size()-1,Coloring.get(0));
 }
       else{
           rand=new int[Coloring.size()-1];
           for(int v=1;v<Coloring.size();v++)
               rand[v-1]=Coloring.get(v).size()*1000+v;
            //   System.out.println("Largest");
           Arrays.sort(rand);
           
           
           }
       
        
       
       
   int k=1;                                
        for(int i=1;i<Coloring.size();i++)
        {
            
            int loop=0; 
            //System.out.println("in");
            ArrayList<Integer> List;
            if(iter%10==4||iter%10==8)
            {//System.out.println("random");
             List=Coloring.get(rand[i-1]+1);
            }
            else if(iter%10==2||iter%10==5||iter%10==7||iter%10==0)
            {
              //      System.out.println("reverse");   
                List=Coloring.get(i);
                }
            
            else
            {
                //System.out.println("Largest");
                    List=Coloring.get(rand[Coloring.size()-1-i]%1000);
                
                
                }
            
     //   System.out.println("----"+List);
            while(loop<List.size())
            {
                int g;
                VertexDegree a =ListFin.get(List.get(loop));
       //         System.out.println(a.getVertex()+"NColor"+a.getColors());
              for( g=1;g<k;g++)
              if(a.getColors().contains(g))
                  continue;
                else 
                  break;
//System.out.println(a.getVertex()+"color"+g);
                a.setColor(g);
            if(g==k)
                k++;
                ArrayList<Integer> add1=G.adjList(a.getVertex());
  //              System.out.println(a.getVertex()+"nl"+add1);
            int c2=0;
            while(c2<add1.size())
            {                
                    VertexDegree b=ListFin.get(add1.get(c2));
                        ArrayList<Integer> Y=b.getColors();
                    Y.add(g);                                                                    
                    c2++;                                                                }
                loop++;            
            }                             
        }
  k--;
    //System.out.println(k);
  while((col-k)>0)
  {
      Coloring.remove(k);
       col--;
     // System.out.println("in");
  }   
       col=k;
       for(int rc=1;rc<Coloring.size();rc++)
       Coloring.get(rc).removeAll(Coloring.get(rc));
        //   System.out.println(Coloring.get(17));
           
       for(int node=0;node<N;node++)
       {
           VertexDegree n1=ListFin.get(node);
           Coloring.get(n1.getColor()).add(n1.getVertex());
                      
           }
       if(iter<=9999)
       { for(int i=0;i<N;i++)
       {
           VertexDegree a =ListFin.get(i);
           a.setColor(0);
           a.getColors().removeAll(a.getColors());
           }
      
       }
        }
   System.out.println(col+" "+0);
        for(int i=0;i<N;i++)
            System.out.print(ListFin.get(i).getColor()+" ");
   
   
   
        
    }
    
}
/* This class is used to 
*
*/
class VertexDegree implements Comparable<VertexDegree> {

private int vertex;
private int degree; // indicates the degree of a vertex
private int color; // color of the vertex
private int SaturationDegree;
private ArrayList<Integer> Colors; //list of colors around the vertex .
public VertexDegree(int v,int d)
{setVertex(v);
 setDegree(d);
 setSaturationDegree(d);   
 setColor(0);
 Colors =new ArrayList<Integer>();
    
    }


    public void setVertex(int vertex) {
        this.vertex = vertex;
    }

    public int getVertex() {
        return vertex;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public int getDegree() {
        return degree;
    }
    @Override
    public int compareTo(VertexDegree o) {
      int c;
      c=this.getSaturationDegree()>o.getSaturationDegree()?1:(this.getSaturationDegree()==o.getSaturationDegree()?0:-1);
       if(c==0)
           c=this.getDegree()>o.getDegree()?1:(this.getDegree()==o.getDegree()?0:-1);
       return c;
    }
    
    


    public void setSaturationDegree(int SaturationDegree) {
        this.SaturationDegree = SaturationDegree;
    }

    public int getSaturationDegree() {
        return SaturationDegree;
    }

    public ArrayList<Integer> getColors() {
        return Colors;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }
}



/*************************************************************************
 *  Compilation:  javac Graph2.java
 *  Execution:    java Graph2 input.txt
 *  Dependencies: Bag.java In.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/41undirected/tinyG.txt
 *
 *  A Graph2, implemented using an array of sets.
 *  Parallel edges and self-loops allowed.
 *
 *  % java Graph2 tinyG.txt
 *  13 vertices, 13 edges
 *  0: 6 2 1 5
 *  1: 0
 *  2: 0
 *  3: 5 4
 *  4: 5 6 3
 *  5: 3 4 0
 *  6: 0 4
 *  7: 8
 *  8: 7
 *  9: 11 10 12
 *  10: 9
 *  11: 9 12
 *  12: 11 9
 *
 *  % java Graph2 mediumG.txt
 *  250 vertices, 1273 edges
 *  0: 225 222 211 209 204 202 191 176 163 160 149 114 97 80 68 59 58 49 44 24 15
 *  1: 220 203 200 194 189 164 150 130 107 72
 *  2: 141 110 108 86 79 51 42 18 14
 *  ...
 *
 *************************************************************************/


/**
 *  The <tt>Graph2</tt> class represents an undirected Graph2 of vertices
 *  named 0 through <em>V</em> - 1.
 *  It supports the following two primary operations: add an edge to the Graph2,
 *  iterate over all of the vertices adjacent to a vertex. It also provides
 *  methods for returning the number of vertices <em>V</em> and the number
 *  of edges <em>E</em>. Parallel edges and self-loops are permitted.
 *  <p>
 *  This implementation uses an adjacency-lists representation, which 
 *  is a vertex-indexed array of {@link Bag} objects.
 *  All operations take constant time (in the worst case) except
 *  iterating over the vertices adjacent to a given vertex, which takes
 *  time proportional to the number of such vertices.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/41undirected">Section 4.1</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
 class Graph2 {
    private final int V;
    private int E;
    private ArrayList<Integer>[] adj;
    
    /**
     * Initializes an empty Graph2 with <tt>V</tt> vertices and 0 edges.
     * param V the number of vertices
     * @throws java.lang.IllegalArgumentException if <tt>V</tt> < 0
     */
    public Graph2(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
        this.V = V;
        this.E = 0;
        adj = (ArrayList<Integer>[]) new ArrayList[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new ArrayList<Integer>();
        }
    }

    /**  
     * Initializes a Graph2 from an input stream.
     * The format is the number of vertices <em>V</em>,
     * followed by the number of edges <em>E</em>,
     * followed by <em>E</em> pairs of vertices, with each entry separated by whitespace.
     * @param in the input stream
     * @throws java.lang.IndexOutOfBoundsException if the endpoints of any edge are not in prescribed range
     * @throws java.lang.IllegalArgumentException if the number of vertices or edges is negative
     */
   /* public Graph2(In in) {
        this(in.readInt());
        int E = in.readInt();
        if (E < 0) throw new IllegalArgumentException("Number of edges must be nonnegative");
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            addEdge(v, w);
        }
    }
*/
    /**
     * Initializes a new Graph2 that is a deep copy of <tt>G</tt>.
     * @param G the Graph2 to copy
     */
    /*public Graph2(Graph2 G) {
        this(G.V());
        this.E = G.E();
        for (int v = 0; v < G.V(); v++) {
            // reverse so that adjacency list is in same order as original
            Stack<Integer> reverse = new Stack<Integer>();
            for (int w : G.adj[v]) {
                reverse.push(w);
            }
            for (int w : reverse) {
                adj[v].add(w);
            }
        }
    }
*/
    /**
     * Returns the number of vertices in the Graph2.
     * @return the number of vertices in the Graph2
     */
    public int V() {
        return V;
    }

    /**
     * Returns the number of edges in the Graph2.
     * @return the number of edges in the Graph2
     */
    public int E() {
        return E;
    }

    /**
     * Adds the undirected edge v-w to the Graph2.
     * @param v one vertex in the edge
     * @param w the other vertex in the edge
     * @throws java.lang.IndexOutOfBoundsException unless both 0 <= v < V and 0 <= w < V
     */
    public void addEdge(int v, int w) {
        if (v < 0 || v >= V) throw new IndexOutOfBoundsException();
        if (w < 0 || w >= V) throw new IndexOutOfBoundsException();
        E++;
        adj[v].add(w);
        adj[w].add(v);
    }


    /**
     * Returns the vertices adjacent to vertex <tt>v</tt>.
     * @return the vertices adjacent to vertex <tt>v</tt> as an Iterable
     * @param v the vertex
     * @throws java.lang.IndexOutOfBoundsException unless 0 <= v < V
     */
    public int adj(int v) {
        if (v < 0 || v >= V) throw new IndexOutOfBoundsException();
        return adj[v].size();
    }
	    public ArrayList<Integer> adjList(int v) {
        if (v < 0 || v >= V) throw new IndexOutOfBoundsException();
        return adj[v];
    }
	public boolean isConnected(int u,int v)
	{//for(int i=0;i<adj[u].size();i++)
      //      System.out.println(u+" "+v);
	return adj[u].contains(v);
            
	
	}


    /**
     * Returns a string representation of the Graph2.
     * This method takes time proportional to <em>E</em> + <em>V</em>.
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
     *    followed by the <em>V</em> adjacency lists
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        String NEWLINE = System.getProperty("line.separator");
        s.append(V + " vertices, " + E + " edges " + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (int w : adj[v]) {
                s.append(w + " ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }


    /**
     * Unit tests the <tt>Graph2</tt> data type.
     */
    public static void main(String[] args) {
       // In in = new In(args[0]);
      //  Graph2 G = new Graph2(in);
      //  StdOut.println(G);
    }

}


/*************************************************************************
 *  Copyright 2002-2012, Robert Sedgewick and Kevin Wayne.
 *
 *  This file is part of algs4-package.jar, which accompanies the textbook
 *
 *      Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
 *      Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
 *      http://algs4.cs.princeton.edu
 *
 *
 *  algs4-package.jar is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  algs4-package.jar is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License
 *  along with algs4-package.jar.  If not, see http://www.gnu.org/licenses.
 *************************************************************************/


 


/**
 *  <i>Standard random</i>. This class provides methods for generating
 *  random number from various distributions.
 *  <p>
 *  For additional documentation, see <a href="http://introcs.cs.princeton.edu/22library">Section 2.2</a> of
 *  <i>Introduction to Programming in Java: An Interdisciplinary Approach</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
 final class StdRandom {

    private static Random random;    // pseudo-random number generator
    private static long seed;        // pseudo-random number generator seed

    // static initializer
    static {
        // this is how the seed was set in Java 1.4
        seed = System.currentTimeMillis();
        random = new Random(seed);
    }

    // don't instantiate
    private StdRandom() { }

    /**
     * Sets the seed of the psedurandom number generator.
     */
    public static void setSeed(long s) {
        seed   = s;
        random = new Random(seed);
    }

    /**
     * Returns the seed of the psedurandom number generator.
     */
    public static long getSeed() {
        return seed;
    }

    /**
     * Return real number uniformly in [0, 1).
     */
    public static double uniform() {
        return random.nextDouble();
    }

    /**
     * Returns an integer uniformly between 0 (inclusive) and N (exclusive).
     * @throws IllegalArgumentException if <tt>N <= 0</tt>
     */
    public static int uniform(int N) {
        if (N <= 0) throw new IllegalArgumentException("Parameter N must be positive");
        return random.nextInt(N);
    }

    ///////////////////////////////////////////////////////////////////////////
    //  STATIC METHODS BELOW RELY ON JAVA.UTIL.RANDOM ONLY INDIRECTLY VIA
    //  THE STATIC METHODS ABOVE.
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Returns a real number uniformly in [0, 1).
     * @deprecated clearer to use {@link #uniform()}
     */
    public static double random() {
        return uniform();
    }

    /**
     * Returns an integer uniformly in [a, b).
     * @throws IllegalArgumentException if <tt>b <= a</tt>
     * @throws IllegalArgumentException if <tt>b - a >= Integer.MAX_VALUE</tt>
     */
    public static int uniform(int a, int b) {
        if (b <= a) throw new IllegalArgumentException("Invalid range");
        if ((long) b - a >= Integer.MAX_VALUE) throw new IllegalArgumentException("Invalid range");
        return a + uniform(b - a);
    }

    /**
     * Returns a real number uniformly in [a, b).
     * @throws IllegalArgumentException if <tt>b <= a</tt>
     */
    public static double uniform(double a, double b) {
        if (b <= a) throw new IllegalArgumentException("Invalid range");
        return a + uniform() * (b-a);
    }

    /**
     * Returns a boolean, which is true with probability p, and false otherwise.
     * @throws IllegalArgumentException if either <tt>p < 0.0</tt> or <tt>p > 1.0</tt>
     */
    public static boolean bernoulli(double p) {
        if (p < 0.0 || p > 1.0)
            throw new IllegalArgumentException("Probability must be between 0.0 and 1.0");
        return uniform() < p;
    }

    /**
     * Returns a boolean, which is true with probability .5, and false otherwise.
     */
    public static boolean bernoulli() {
        return bernoulli(0.5);
    }

    /**
     * Returns a real number with a standard Gaussian distribution.
     */
    public static double gaussian() {
        // use the polar form of the Box-Muller transform
        double r, x, y;
        do {
            x = uniform(-1.0, 1.0);
            y = uniform(-1.0, 1.0);
            r = x*x + y*y;
        } while (r >= 1 || r == 0);
        return x * Math.sqrt(-2 * Math.log(r) / r);

        // Remark:  y * Math.sqrt(-2 * Math.log(r) / r)
        // is an independent random gaussian
    }

    /**
     * Returns a real number from a gaussian distribution with given mean and stddev
     */
    public static double gaussian(double mean, double stddev) {
        return mean + stddev * gaussian();
    }

    /**
     * Returns an integer with a geometric distribution with mean 1/p.
     * @throws IllegalArgumentException if either <tt>p < 0.0</tt> or <tt>p > 1.0</tt>
     */
    public static int geometric(double p) {
        if (p < 0.0 || p > 1.0)
            throw new IllegalArgumentException("Probability must be between 0.0 and 1.0");
        // using algorithm given by Knuth
        return (int) Math.ceil(Math.log(uniform()) / Math.log(1.0 - p));
    }

    /**
     * Return an integer with a Poisson distribution with mean lambda.
     * @throws IllegalArgumentException if  <tt>lambda <= 0.0</tt>
     */
    public static int poisson(double lambda) {
        if (lambda <= 0.0)
            throw new IllegalArgumentException("Parameter lambda must be positive");
        // using algorithm given by Knuth
        // see http://en.wikipedia.org/wiki/Poisson_distribution
        int k = 0;
        double p = 1.0;
        double L = Math.exp(-lambda);
        do {
            k++;
            p *= uniform();
        } while (p >= L);
        return k-1;
    }

    /**
     * Returns a real number with a Pareto distribution with parameter alpha.
     * @throws IllegalArgumentException if <tt>alpha <= 0.0</tt>
     */
    public static double pareto(double alpha) {
        if (alpha <= 0.0)
            throw new IllegalArgumentException("Shape parameter alpha must be positive");
        return Math.pow(1 - uniform(), -1.0/alpha) - 1.0;
    }

    /**
     * Returns a real number with a Cauchy distribution.
     */
    public static double cauchy() {
        return Math.tan(Math.PI * (uniform() - 0.5));
    }

    /**
     * Returns a number from a discrete distribution: i with probability a[i].
     * throws IllegalArgumentException if sum of array entries is not (very nearly) equal to <tt>1.0</tt>
     * throws IllegalArgumentException if <tt>a[i] < 0.0</tt> for any index <tt>i</tt>
     */
    public static int discrete(double[] a) {
        double EPSILON = 1E-14;
        double sum = 0.0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] < 0.0) throw new IllegalArgumentException("array entry " + i + " is negative: " + a[i]);
            sum = sum + a[i];
        }
        if (sum > 1.0 + EPSILON || sum < 1.0 - EPSILON)
            throw new IllegalArgumentException("sum of array entries not equal to one: " + sum);

        // the for loop may not return a value when both r is (nearly) 1.0 and when the
        // cumulative sum is less than 1.0 (as a result of floating-point roundoff error)
        while (true) {
            double r = uniform();
            sum = 0.0;
            for (int i = 0; i < a.length; i++) {
                sum = sum + a[i];
                if (sum > r) return i;
            }
        }
    }

    /**
     * Returns a real number from an exponential distribution with rate lambda.
     * @throws IllegalArgumentException if <tt>lambda <= 0.0</tt>
     */
    public static double exp(double lambda) {
        if (lambda <= 0.0)
            throw new IllegalArgumentException("Rate lambda must be positive");
        return -Math.log(1 - uniform()) / lambda;
    }

    /**
     * Rearrange the elements of an array in random order.
     */
    public static void shuffle(Object[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int r = i + uniform(N-i);     // between i and N-1
            Object temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Rearrange the elements of a double array in random order.
     */
    public static void shuffle(double[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int r = i + uniform(N-i);     // between i and N-1
            double temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Rearrange the elements of an int array in random order.
     */
    public static void shuffle(int[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int r = i + uniform(N-i);     // between i and N-1
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }


    /**
     * Rearrange the elements of the subarray a[lo..hi] in random order.
     */
    public static void shuffle(Object[] a, int lo, int hi) {
        if (lo < 0 || lo > hi || hi >= a.length) {
            throw new IndexOutOfBoundsException("Illegal subarray range");
        }
        for (int i = lo; i <= hi; i++) {
            int r = i + uniform(hi-i+1);     // between i and hi
            Object temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Rearrange the elements of the subarray a[lo..hi] in random order.
     */
    public static void shuffle(double[] a, int lo, int hi) {
        if (lo < 0 || lo > hi || hi >= a.length) {
            throw new IndexOutOfBoundsException("Illegal subarray range");
        }
        for (int i = lo; i <= hi; i++) {
            int r = i + uniform(hi-i+1);     // between i and hi
            double temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Rearrange the elements of the subarray a[lo..hi] in random order.
     */
    public static void shuffle(int[] a, int lo, int hi) {
        if (lo < 0 || lo > hi || hi >= a.length) {
            throw new IndexOutOfBoundsException("Illegal subarray range");
        }
        for (int i = lo; i <= hi; i++) {
            int r = i + uniform(hi-i+1);     // between i and hi
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Unit test.
     */
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        if (args.length == 2) StdRandom.setSeed(Long.parseLong(args[1]));
        double[] t = { .5, .3, .1, .1 };
/*
        StdOut.println("seed = " + StdRandom.getSeed());
        for (int i = 0; i < N; i++) {
            StdOut.printf("%2d "  , uniform(100));
            StdOut.printf("%8.5f ", uniform(10.0, 99.0));
            StdOut.printf("%5b "  , bernoulli(.5));
            StdOut.printf("%7.5f ", gaussian(9.0, .2));
            StdOut.printf("%2d "  , discrete(t));
            StdOut.println();
        }

        String[] a = "A B C D E F G".split(" ");
        for (String s : a)
            StdOut.print(s + " ");
        StdOut.println();
*/    }

}


/*************************************************************************
 *  Copyright 2002-2012, Robert Sedgewick and Kevin Wayne.
 *
 *  This file is part of stdlib-package.jar, which accompanies the textbook
 *
 *      Introduction to Programming in Java: An Interdisciplinary Approach
 *      by R. Sedgewick and K. Wayne, Addison-Wesley, 2007. ISBN 0-321-49805-4.
 *
 *      http://introcs.cs.princeton.edu
 *
 *
 *  stdlib-package.jar is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  stdlib-package.jar is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License
 *  along with stdlib-package.jar.  If not, see http://www.gnu.org/licenses.
 *************************************************************************/


    


