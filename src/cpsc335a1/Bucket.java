package cpsc335a1;

import java.util.ArrayList;

public class Bucket {

	ArrayList<Integer> indices;
	char slots[];
	int localDepth;
	int numCharsLeft;
	int firstOpenSlot = 0;
	public static final int DEFAULT_BUCKET_SIZE = 128;
	StringBuilder builder;
	
	public Bucket(int size, int depth)
	{
		indices = new ArrayList<Integer>();
		if(size % 2 == 0)
			slots = new char[size/2];
		else
		{
			System.err.println("The number of bytes requested is not a multiple of 2. Setting bucket size to default (128 bytes).");
			slots = new char[DEFAULT_BUCKET_SIZE];
		}
		
		numCharsLeft = slots.length;
		localDepth = depth;
		builder = new StringBuilder();
	}
	
	// Returns the index of the start of the string, or -1 if the string isn't present.
	// TODO: Right now just does a linear search. After testing the insert to make sure
	// 		 elements are inserted in order, should update this to do binary search.
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
	private void insert(int index, String adding)
	{
		if(indices.isEmpty())
		{
			indices.add(index);
			return;
		}

		boolean added = false;
		
		for(int i = 0; i < indices.size(); i++)
		{	
			String comparing = constructString(indices.get(i));
			
			if(comparing.compareTo(adding) > 0)
			{
				indices.add(i, index);
				added = true;
				break;
			}
				
		}
		
		if(!added)
			indices.add(index);
		
	}
	
	// Returns the string starting at index i in the bucket.
	private String constructString(int index)
	{	
		int length = slots[index];
		int end = index + 1 + length;
		
		for(int j = index + 1; j < end; j++)
			builder.append(slots[j]);
		
		String constructed = builder.toString();
		builder.setLength(0);
		
		return constructed;
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
		
		insert(firstOpenSlot, toAdd);
		
		slots[firstOpenSlot++] = (char)toAdd.length();
		int end = firstOpenSlot + 1 + toAdd.length();
		for(int i = 0; i < toAdd.length(); i++)
			slots[firstOpenSlot++] = toAdd.charAt(i);
		
		//firstOpenSlot += toAdd.length() + 1;
		numCharsLeft -= toAdd.length() + 1;
		
		return true;
	}
	
	public String[] empty()
	{
		String words[] = new String[indices.size()];
		
		int current = 0;
		for(int i : indices)
		{
			words[current] = constructString(i);
			current++;
		}
		
		numCharsLeft = slots.length;
		firstOpenSlot = 0;
		indices.clear();
		
		return words;
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
