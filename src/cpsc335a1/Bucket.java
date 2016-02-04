package cpsc335a1;

import java.util.ArrayList;

public class Bucket {

	ArrayList<Integer> indices;
	char slots[];
	int localDepth;
	int numCharsLeft;
	int firstOpenSlot = 0;
	static final int DEFAULT_BUCKET_SIZE = 128;
	
	public Bucket(int size, int depth)
	{
		indices = new ArrayList<Integer>();
		if(size % 2 == 0)
			slots = new char[size/2];
		else
		{
			System.err.println("The number of bytes requested is not a power of 2. Setting bucket size to default (128 bytes).");
			slots = new char[DEFAULT_BUCKET_SIZE];
		}
		
		numCharsLeft = slots.length;
		localDepth = depth;
	}
	
	// Returns the index of the start of the string, or -1 if the string isn't present.
	// TODO: Right now just does a linear search, once the indices are sorted I can change
	// 		 to a binary search.
	public int search(String toFind)
	{
		for(int index : indices)
		{
			int length = slots[index];
			if(length != toFind.length())
				continue;
			
			int i = index + 1;
			int end = i + length;
			for(int j = 0; i < end; i++, j++)
			{
				if(toFind.charAt(j) != slots[i])
					break;
			}
			
			if(i == end + 1)
				return index;
		}
		
		return -1;
	}
	
	// Inserts a new index in the correct sorted order.
	// TODO: Finish implementing!
	private void insert(int index)
	{
		if(indices.isEmpty())
			indices.add(index);
		
		int current = indices.get(indices.size() / 2);
		
		StringBuilder builder = new StringBuilder();
		
		int length = slots[current];
		int end = current + 1 + length;
		
		for(int i = current + 1; i < end; i++)
			builder.append(slots[i]);
		
		
		
	}
	
	
	// Adds the string to the bucket. If the bucket isn't big enough to add the string,
	// returns false. At this point the hash table should go about doing the doubling 
	// process.
	// NOTE: due to the length being stored as a 16-bit char, the length of the word
	// 		 being added cannot be longer than 65535 characters no matter what the
	// 		 size of the bucket is.
	public boolean add(String toAdd)
	{
		if(numCharsLeft < toAdd.length() + 1)
			return false;
		
		
		
		slots[firstOpenSlot] = (char)toAdd.length();
		for(int i = firstOpenSlot + 1; i <= toAdd.length(); i++)
			slots[i] = toAdd.charAt(i);
		
		firstOpenSlot += toAdd.length() + 1;
		numCharsLeft -= toAdd.length() + 1;
		
		return true;
	}
	
	// Deletes the requested word from the bucket. Returns false if the word could not
	// be found. Automatically rearranges the words so the empty spaces are at the end
	// of the bucket.
	// TODO: Implement if necessary. Right now all we really need is a clear function,
	// 		 as elements are only going to be added.
//	public boolean delete(String toDelete)
//	{
//		int index = search(toDelete);
//		if(index == -1)
//			return false;
//		
//		
//		
//		return true;
//	}
	
	
}
