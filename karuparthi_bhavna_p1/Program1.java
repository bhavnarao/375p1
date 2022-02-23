package karuparthi_bhavna_p1;

import java.util.*;
import java.io.*;
import javafx.util.Pair;

public class Program1 {
 public static Pair<Integer, ArrayList<String>> ComputeMaximumProfit(int maximumAmount, ArrayList<Pair<String, Integer>> itemlists, ArrayList<Pair<String, Integer>> marketpricelist){
        int maximumProfit = 0;
        int amount = 0;
        //iterate through the price list file to get the total amount
        for (Pair <String,Integer> temp: itemlists)
        {
            
            int val = temp.getValue();
            amount+=val;
        }
        //create a maximum array to contain the max values
        ArrayList<String> maximum = new ArrayList<>();
        if(amount > maximumAmount) {
        int itemsize = itemlists.size();
        ArrayList<Pair<String, Integer>> a = new ArrayList<>();
        amount = 0;
        //subsets are generated using bit masking in the pricelists

        for(int i = 0; i < (1 << itemsize); i++) {
            for(int bit = 0; bit < itemsize; bit++) {
                if( (i & (1 << bit)) > 0) 
                {
                    a.add(itemlists.get(bit));
                }
            }
             //get the total amount of the substrings
             for(Pair<String, Integer> temp : a)
            {
                int val = temp.getValue();
                amount+=val;
            }
            //check for the current profit
            if(amount <= maximumAmount) {
                //Calculate profit of current subset
                int currentprofit = 0;
                for(int x = 0; x < a.size(); x++) {
                    for(int y = 0; y < marketpricelist.size(); y++) {
                        if(a.get(x).getKey().equals(marketpricelist.get(y).getKey())) {
                             currentprofit += marketpricelist.get(y).getValue();
                            break;
                        }
                    }
                }

                 currentprofit -= amount;

                //Check for a new max profit
                if( currentprofit > maximumProfit) {
                    maximumProfit =  currentprofit;
                    maximum = new ArrayList<String>();
                    for(int x = 0; x < a.size(); x++) {
                        maximum.add(a.get(x).getKey());
                    }
                }
            }

            //Reset for next subset
            amount = 0;
            a = new ArrayList<Pair<String, Integer>>();
        }

        return new Pair<Integer, ArrayList<String>>(maximumProfit, maximum);
    
        }
        //case when the amount is <= maximumAmount
        else{
              for(Pair<String, Integer> temp : itemlists)
            {
		maximum.add(temp.getKey());
	    }

            //Calculate income of purchasing cards using market prices
            for(int i = 0; i < maximum.size(); i++) {
                for(int j = 0; j < marketpricelist.size(); j++) {
                    if(maximum.get(i).equals(marketpricelist.get(j).getKey())) {
                        maximumProfit += marketpricelist.get(j).getValue();
                        break;
                    }
                }
            }

            maximumProfit -= amount;
            return new Pair<Integer, ArrayList<String>>(maximumProfit, maximum);
        }
       
         
     }
    public static void main(String[] args) throws FileNotFoundException {       
        String marketpricefile = "";
        String pricelistfile = "";
        //Parsing input from command arguments 
        PrintStream output = new PrintStream(new File("output.txt"));
        System.setOut(output);
         for(int i = 0; i < args.length; i++) {
            if(args[i].equals("-m")) {
                if(i < args.length-1 && args[i+1].charAt(0) != '-') {
                    marketpricefile = args[++i];
                } else {
                    return;
                }
            }

            if(args[i].equals("-p")) {
                if(i < args.length-1 && args[i+1].charAt(0) != '-') {
                    marketpricefile = args[++i];
                } else {
                    return;
                }
            }
        }
         //arraylist for containing marketpricefile
        ArrayList<Pair<String, Integer>> marketpricev = new ArrayList<Pair<String, Integer>>(); 
         //arraylist for containing pricelistcards
        ArrayList<Pair<String, Integer>> pricelistcards = new ArrayList<Pair<String, Integer>>(); 
        boolean fl = true;
        int size = 0;
        //Read from the Market Price File
        try {
            String st = "";
            File mpfFile = new File(marketpricefile);           
            BufferedReader br1 = new BufferedReader(new FileReader(mpfFile));
            while((st = br1.readLine()) != null) {
                if(fl) {
                    size = Integer.parseInt(st);
                    fl = false;
                    continue;
                }
                String str = st.substring(0, st.indexOf(' '));
                int x = Integer.parseInt(st.substring(st.indexOf(' ')+1, st.length()));
                marketpricev.add(new Pair<String, Integer>(str, x));
            }
        } catch(IOException e) {
            System.out.println(e.getMessage());
            return;
        }

        fl = true;
        int numofcards = 0;
        int amount = 0;
        int index = 0;
        
     
        //Read from the  Price List File
        try {
            String st = "";
            File plfFile = new File(pricelistfile);
            BufferedReader br2 = new BufferedReader(new FileReader(plfFile));
            long startTime = System.nanoTime();
            while((st = br2.readLine()) != null) {
                if(index != 0 && index == numofcards) {
                    Pair<Integer, ArrayList<String>> op = ComputeMaximumProfit(amount,pricelistcards, marketpricev);
                    System.out.print(op.getKey() + " ");
                    System.out.print(op.getValue().size() + " ");
                    pricelistcards = new ArrayList<Pair<String, Integer>>();
                    fl = true;
                    index = 0;
                    // execution time of the function
                    long endTime = System.nanoTime();
                    long duration = (long) ((endTime - startTime));
                    System.out.print(((duration)/1000000000.) + "\n");
                    startTime = System.nanoTime();
                    
                    for(int i = 0; i < op.getValue().size(); i++)
                    {
                        System.out.println(op.getValue().get(i));
                    }
                    System.out.println();
                }

               
                if(fl) {
                    numofcards = Integer.parseInt(st.substring(0, st.indexOf(' ')));
                    if(numofcards == 0) {
                        long endTime = System.nanoTime();
                         long duration = (long) ((endTime - startTime)/1000000000.);
                        System.out.println("0 0 0 " + (duration) + "\n");
                        startTime = System.nanoTime();
                        System.out.println();
                        continue;
                    }
                    System.out.print(numofcards + " ");
                    amount = Integer.parseInt(st.substring(st.indexOf(' ')+1, st.length()));
                    fl = false;
                    continue;
                }

                
                String str = st.substring(0, st.indexOf(' '));
                int x = Integer.parseInt(st.substring(st.indexOf(' ')+1, st.length()));
                pricelistcards.add(new Pair<String, Integer>(str, x));
                index++;
            }

            
            if(numofcards != 0) {
                 Pair<Integer, ArrayList<String>> op = ComputeMaximumProfit(amount,pricelistcards, marketpricev);
                System.out.print(op.getKey() + " ");
                System.out.print(op.getValue().size() + " ");
                ///time 
                long endTime = System.nanoTime();
                long duration = ((endTime - startTime));
                System.out.print(((duration)/1000000000.) + "\n");
                
                for(int i = 0; i < op.getValue().size(); i++){
                    System.out.println(op.getValue().get(i));
                }
                System.out.println();
            }

            
        } catch(IOException e) {
            System.out.println(e.getMessage());
            return;
        }
    }
    
}

