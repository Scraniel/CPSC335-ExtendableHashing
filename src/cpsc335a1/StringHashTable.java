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
		this.bucketSize = bucketSize;
		buckets.add(new Bucket(bucketSize, globalDepth));
		directory.add(0);
		
	}
	
	private int findBucket(String toAdd)
	{
		int hash = toAdd.hashCode();
		
		// shifts the bit to get a power of 2, then subtracts 1 to get all 1s
		int bitmask = (1 << globalDepth) - 1;
		int directoryIndex = hash & bitmask;
		int bucketIndex = directory.get(directoryIndex);
		
		return bucketIndex;
	}
	
	public int linearSearch(String toFind)
	{
		int bucketIndex = findBucket(toFind);
		Bucket bucket = buckets.get(bucketIndex);
		
		return bucket.linearSearch(toFind)[1];
	}
	
	public int binarySearch(String toFind)
	{
		int bucketIndex = findBucket(toFind);
		Bucket bucket = buckets.get(bucketIndex);
		return bucket.binarySearch(toFind);
	}
	
	public void add(String toAdd)
	{
//		int hash = toAdd.hashCode();
//		
//		// shifts the bit to get a power of 2, then subtracts 1 to get all 1s
//		int bitmask = (1 << globalDepth) - 1;
//		int directoryIndex = hash & bitmask;
		int bucketIndex = findBucket(toAdd);
		Bucket bucket = buckets.get(bucketIndex);
		
		// Add the word. If the bucket is full, we need to split the bucket and add a new one
		if(!bucket.add(toAdd))
		{
			bucket.incrementLocalDepth();
			Bucket newBucket = new Bucket(bucketSize, bucket.getLocalDepth());
			buckets.add(newBucket);
			
			// Only double the directory when local depth exceeds global depth
			if(bucket.getLocalDepth() > globalDepth)
			{
				doubleDirectory();
			}
			
			fixPointers(bucketIndex);
			
			// redistribute keys
			String keys[] = bucket.empty();
			
			for(String key : keys)
				add(key);
			
			add(toAdd);
		}
		
	}
	
	private void doubleDirectory()
	{
		globalDepth++;
		int newSize = directory.size();
		
		// Add new entries into directory
		for(int i = 0; i < newSize; i ++)
		{
			int biggestDepth = 0;
			int newIndex = -1;
			
			// Find new pointer for this entry
			for(int j = 0; j < newSize; j++)
			{
				Bucket current = buckets.get(directory.get(j));
				if(current.getLocalDepth() <= biggestDepth)
					continue;
				
				int masked = directory.size() & ((1 << current.getLocalDepth()) - 1);
				if(masked == j)
				{
					biggestDepth = current.getLocalDepth();
					newIndex = directory.get(j);
				}
					
			}
			
			if(newIndex == -1)
				directory.add(null);
			else
				directory.add(newIndex);
		}
	}
	
	private void fixPointers(int bucketIndex)
	{
		for(int i = 0; i < directory.size(); i++)
		{
			Integer pointer = directory.get(i);
			
			
			if(pointer == null)
			{
				directory.set(i, buckets.size()-1);
				continue;
			}
			if(pointer == bucketIndex)
			{
				int bitSet = (i & (1 << buckets.get(bucketIndex).getLocalDepth()-1));
				
				if(bitSet > 0)
				{
					directory.set(i, buckets.size()-1);
				}
			}
		}
	}
	
	public String viewTable()
	{
		String output = "";
		boolean visualized[] = new boolean[buckets.size()];
		output += "GLOBAL DEPTH: " + globalDepth;
		
		for(int i = 0; i < directory.size(); i++)
		{
			output += (String.format("%32s", Integer.toBinaryString(i)).replace(" ", "0") + " points to b" + directory.get(i) + " which has LD " + buckets.get(directory.get(i)).getLocalDepth());
			if(!visualized[directory.get(i)])
			{
				visualized[directory.get(i)] = true;
				output += ("\t{");
				for(String s : buckets.get(directory.get(i)).view())
					output += (s + ", ");
				output += ("}");
			}
			
			output += ("\n");
		}
		
		return output;
	}
	
}
