package cpsc335a1;

import java.util.ArrayList;

public class StringHashTable {

	ArrayList<Integer> directory;
	ArrayList<Bucket> buckets;
	int globalDepth = 0;
	int bucketSize;
	
	public StringHashTable(int bucketSize)
	{
		directory = new ArrayList<Integer>();
		buckets = new ArrayList<Bucket>();
	
		buckets.add(new Bucket(bucketSize, globalDepth));
		directory.add(0);
		
	}
	
	public void add(String toAdd)
	{
		int hash = toAdd.hashCode();
		
		// shifts the bit to get a power of 2, then subtracts 1 to get all 1s
		int bitmask = (1 << globalDepth) - 1;
		int directoryIndex = hash & bitmask;
		int bucketIndex = directory.get(directoryIndex);
		
		
		// Add the word. If the bucket is full, we need to double the directory
		if(!buckets.get(bucketIndex).add(toAdd))
		{
			// TODO: Double directory size, add a bucket
		}
		
	}
	
}
