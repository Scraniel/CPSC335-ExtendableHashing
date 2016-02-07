package cpsc335a1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class as1 {

	public static void main(String[] args) {
		
		if(args.length != 3)
		{
			System.err.println("Usage: as1 <file_name> <number_keys> <bucket_size>");
			return;
		}
		
		StringHashTable tester = new StringHashTable(Integer.parseInt(args[2]));
		
		 // The name of the file to open.
        String fileName = args[0];

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                tester.add(line);
            }   

            // Always close files.
            bufferedReader.close(); 
            
            // Re-read the file and find each word, computing average probes
            fileReader = 
                new FileReader(fileName);

            bufferedReader = 
                new BufferedReader(fileReader);

            int totalWords = 0;
            int totalBinaryProbes = 0;
            int totalLinearProbes = 0;
            while((line = bufferedReader.readLine()) != null) {
                totalWords++;
                int prober = tester.binarySearch(line);
                if(prober < 0)
                	System.out.println(line);
                
                totalBinaryProbes += prober;
                totalLinearProbes += tester.linearSearch(line);
            }  
            
            double averageBinaryProbes = (double)totalBinaryProbes/(double)totalWords;
            double averageLinearProbes = (double)totalLinearProbes/(double)totalWords;
            double percentage = ((averageLinearProbes - averageBinaryProbes) / averageLinearProbes) * 100;
            
            System.out.println("It took an average of " + averageBinaryProbes + " probes to find each word using a binary search.\n"
            		+ "By contrast, it took an average of " + averageLinearProbes + " probes to find each word using a linear search.\n"
            				+ "This means the binary search was approximately " + percentage + "% more efficient than linear search.");
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
		
	}

}
