package cpsc335a1;

public class Driver {

	public static void main(String[] args) {
		
		Bucket tester = new Bucket(5, 1);
		
		tester.add("AAABC");
		tester.add("AAA");
		tester.add("AAAB");
		tester.add("AA");
		tester.add("A");
		tester.add("AAAAA");
		tester.add("AAAABB");
		tester.add("B");
		
		
		for(String s : tester.empty())
			System.out.println(s);

		
	}

}
