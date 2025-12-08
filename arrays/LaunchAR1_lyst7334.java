import java.util.Scanner;

public class LaunchAR1 {

	public static void main(String[] args) 
	{
		
		//int a[]= {10,20,30,40};
		
		int []ar=new int[5];
		
		Scanner scan=new Scanner(System.in);
		
		for(int i=0;i<ar.length;i++)
		{
			System.out.println("Kindly enter the marks of Student " + i);
			ar[i]=scan.nextInt();
		}
		System.out.println("Marks are as follows");
		

		for(int i=0;i<ar.length;i++)
		{
//			System.out.println("The Marks of student : "+ i + " is " + ar[i]);
			System.out.print( ar[i]);
			
		}
		
		
		

	}

}
