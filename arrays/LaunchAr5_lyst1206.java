import java.util.Arrays;

public class LaunchAr5 
{

	public static void main(String[] args) 
	{
//		int ar[]= {10,20,30};
		
//		for(int i=0;i<ar.length;i++)
//		{
//			System.out.println(ar[i]);
//			
//		}
		
		//for each loop, enhanced loop, advanced loop java 5
		
//		for(variable to store array data:array Name)
//		{
//			
//			
//		}
		
//		for(int elem:ar)
//		{
//			System.out.println(elem);
//			
//		}
		
		int ar[][]= {{10,20,30},{1,2,3}};
		
//		for(int a[]:ar)
//		{
//			for(int element:a)
//			{
//				System.out.print(element + " ");
//				
//			}
//			System.out.println();
//			
//		}
		
		System.out.println(ar.getClass().getName());
		
		int a[]=new int[3];
		
		System.out.println(a.getClass().getName());
		
		//int aa[]=new int[1234532445];
		
		int aaa[]= {2,3,5,1,6,0};
		System.out.println("Before sorting");
		for(int ab:aaa)
		{
			
			System.out.print(ab + " ");
		}
		System.out.println();
		Arrays.sort(aaa);
		System.out.println("After sorting");
		for(int ab:aaa)
		{
			
			System.out.print(ab  + " ");
		}
		

	}

}
