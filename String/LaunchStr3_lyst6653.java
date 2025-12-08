
public class LaunchStr3 
{

	public static void main(String[] args) 
	{
		String str="RajaRamMohanRoy";
		
		System.out.println(str);//RajaRamMohanRoy
		System.out.println(str.toUpperCase());// RAJARAMMOHANROY
		System.out.println(str.toLowerCase());//rajarammohanroy
		System.out.println(str.charAt(2));// j
		System.out.println(str.substring(2, 8));//jaRamM
		System.out.println(str.substring(2));//jaRamMohanRoy
		System.out.println(str.contains("R"));//true
		System.out.println(str.contains("H"));//false
		System.out.println(str.indexOf("a"));//1
		System.out.println(str.lastIndexOf("a"));//10
		System.out.println(str.length());//15
		System.out.println(str.startsWith("R"));//true
		System.out.println(str.startsWith("A"));//false
		System.out.println(str.endsWith("y"));//true
		
		char ch[]=str.toCharArray();
		
		for(char c:ch)
		{
			System.out.println(c);
		}
		
		String s[]=str.split("a");
		
		for(String st:s)
		{
			System.out.print(st );
		}
		
		
		

	}

}
