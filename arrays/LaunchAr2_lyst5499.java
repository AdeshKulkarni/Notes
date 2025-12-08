import java.util.Scanner;

public class LaunchAr2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//int ar[][]= {{12, 12,11,12},{12, 12,11,12},{12, 12,11,12}};
		
//		int [][]arr=new int[3][5]; //  2 D regular array
		
		int [][]arr=new int[3][];// 2 D Jagged array
		arr[0]=new int[4];
		arr[1]=new int[5];
		arr[2]=new int[3];
		
		Scanner sc=new Scanner(System.in);
		
		for(int i=0;i<arr.length;i++)
		{
			for(int j=0;j<arr[i].length;j++)
			{
				System.out.println("Enter the marks of class "+ i + " Student "+ j);
				arr[i][j]=sc.nextInt();
			}
			
		}
		
		System.out.println("Marks of studenst are as follows : ");
		
		for(int i=0;i<arr.length;i++)
		{
			for(int j=0;j<arr[i].length;j++)
			{
				System.out.print(arr[i][j] +" ");
				
			}
			System.out.println();
			
		}

	}

}
