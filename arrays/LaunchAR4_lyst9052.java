class Student
{
	String name;
	int age;
	
}

class Dog
{
	int cost;
}

class Fan
{
	String brand;
	
}
public class LaunchAR4 {

	public static void main(String[] args) 
	{
		
		//Student []studentArray=new Student[3];
		
		Object []studentArray=new Object[4];
		
		studentArray[0]=new Student();
		studentArray[1]=new Student();
		//studentArray[2]=new Student();
		studentArray[2]=new Dog();
		studentArray[3]=new Fan();
		
		
//		studentArray[0].name="Rohan";
//		studentArray[0].age=16;
		
	}

}
