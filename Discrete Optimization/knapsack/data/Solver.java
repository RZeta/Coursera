import java.io.*;
import java.util.List;
import java.util.ArrayList;

/**
 * The class <code>Solver</code> is an implementation of a greedy algorithm to solve the knapsack problem.
 *
 */
public class Solver {
    
    /**
     * The main class
     */
    public static void main(String[] args) {
        try {
            solve(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Read the instance, solve it, and print the solution in the standard output
     */
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
        int items = Integer.parseInt(firstLine[0]);
        int capacity = Integer.parseInt(firstLine[1]);
        int[][] c=new int[items+1][capacity+1];
        int[] values = new int[items];
        int[] weights = new int[items];

        for(int i=1; i < items+1; i++){
          String line = lines.get(i);
          String[] parts = line.split("\\s+");

          values[i-1] = Integer.parseInt(parts[0]);
          weights[i-1] = Integer.parseInt(parts[1]);
        }

        // a trivial greedy algorithm for filling the knapsack
        // it takes items in-order until the knapsack is full
        int n = items;
        int k = capacity;
        int[] taken = new int[items];
		
		        for(int i=1;i<=items;i++)
            for(int j=1;j<=capacity;j++)
            {//System.out.println(i+ " "+ j);
                if(weights[i-1]<=j)
                 c[i][j]=Math.max(c[Math.abs(i-1)][j],values[i-1]+c[Math.abs(i-1)][j-weights[i-1]] );
                     else
                 c[i][j]=c[i-1][j];
                
                }    
        
        
        // prepare the solution in the specified output format
        System.out.println(c[items][capacity]+" 1");
               while(true)
        {
            if(c[n][k]==c[n-1][k])
                taken[n-1]=0;
            else
            {taken[n-1]=1;
                k=k-weights[n-1];
                                
                }
            if(c[n][k]==0)
                break;
            n=n-1;
            
            }
	   for(int i=0; i < items; i++){
            System.out.print(taken[i]+" ");
        }
        System.out.println("");        
    }
}