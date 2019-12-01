//Sabahat Rahat
//Cisc 3810
import java.sql.*;
import java.util.Scanner;

public class jdbcconn {

	
	//MAIN CLASS PRINTS A LIST OF OPTIONS FOR THE USER
	//USING THE REGISTRAR DATABASE
	//USER SELECTS AN OPTION FROM 1-4 AND UPON EACH OPTION ENTERED
	//DIFFERENT FUNCTIONS ARE CALLED TO REPORT THE RESULTS
	public static void main(String[] args) {
		try {
	Class.forName("oracle.jdbc.driver.OracleDriver");
	Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "sabahat", "");
	jdbcconn db= new jdbcconn();
	int option;
	int emp;
	int courseNum;
	int preReqNum;
	
	Scanner obj= new Scanner(System.in);
	System.out.println("Select an option: ");
	System.out.println("1: Names of all the students");
	System.out.println("2: Information on a certain student");
	System.out.println("3: Names of the students who are"
			+ " required to take a certain course");
	System.out.println("4: Names of students who took a"
			+ " specified pre-req course");
	
	option=obj.nextInt();
	
	switch(option) {
	case 1:
		db.getStudentNames();
		break;
	
		//CASE 2 ASKS USER TO TYPE IN THE EMPLID AND CALLS THE FUNCTION
		//TO SHOW THE DATA FOR THAT SPECIFIC EMPID
	case 2:
		System.out.println("Type in student's emplId: ");
		emp = obj.nextInt();
		db.getStudentInfo(emp);
		break;
		
		//CASE 3 ASKS THE USER TO TYPE IN THE COURSEID THEY WANT 
		//INFORMATION ON AND THEN PASSES THE FUNCTION THAT ID NUMBER
		//WHICH THEN PRINTS OUT THE REQUESTED INFORMATION ON
		//THAT SPECIFIC COURSEID
	case 3:
		System.out.println("Type in the courseID"
				+ " you wish to see data on: ");
		courseNum = obj.nextInt();
		db.studCourseRequired(courseNum);
		break;
		
		
		//CASE 4 ASKS THE USER TO TYPE IN THE PRE-REQ COURSEID
		//THEY WANT INFORMATION ON, AND THEN SENDS THAT TO THE FUNCTION
		//WHICH PRINTS OUT THE REQUESTED INFORMATION ON THAT SPECIFIC
		//PRE-REQ COURSEID
	case 4:
		System.out.println("Type in the pre-req courseID "
				+ "you wish to see data on: ");
		preReqNum = obj.nextInt();
		db.studCourseTaken(preReqNum);
		break;
		
	default: 
		System.out.println("Invalid option");
	}
	
	obj.close();
	con.close();

	}
		catch(Exception e)
		{
			System.out.println(e);
		}
		

}
	//PRINTS THE NAME OF ALL THE STUDENTS
	public void getStudentNames() {
		try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "sabahat", "");
		Statement st=con.createStatement(
				ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		ResultSet rs= st.executeQuery("select lastName, "
				+ "firstName from studentInfo");
		rs.last();
		int numRows=rs.getRow();
		rs.first();
		
		String[] firstNameData = new String[numRows];
		String[] lastNameData = new String[numRows];
		
		for(int index = 0; index < numRows; index++) {
			
			lastNameData[index]= rs.getString(1);
			firstNameData[index]= rs.getString(2);
			System.out.println(firstNameData[index]+" "
			+lastNameData[index]);
			
			rs.next();
		}
		con.close();
		st.close();
		
		
		}
		
		catch(Exception e)
		{
			System.out.println(e);
			
		}
		
		
	}
	
	
	//PRINTS THE NAME OF STUDENTS WHO ARE REQUIRED TO TAKE A CERTAIN COURSE
	
	public void studCourseRequired(int course) {
		try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "sabahat", "");
		Statement st=con.createStatement(
				ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		ResultSet rs= st.executeQuery("select lastName, firstName from studentInfo where courseID ="+ course);
		rs.last();
		int numRows=rs.getRow();
		int countStudents = 0;
		rs.first();
		
		String[] firstNameData = new String[numRows];
		String[] lastNameData = new String[numRows];
		
		for(int index = 0; index < numRows; index++) {
			
			lastNameData[index]= rs.getString(1);
			firstNameData[index]= rs.getString(2);
			System.out.println(firstNameData[index]+" "+lastNameData[index]);
			
			countStudents++;
			rs.next();
		}
		System.out.println(countStudents + " Students are required to take courseID " + course);
		con.close();
		st.close();
		
		
		}
		
		catch(Exception e)
		{
			System.out.println(e);
			
		}
		
		
	}
	
	//PRINTS THE NAMES OF STUDENTS WHO HAVE TAKEN A CERTAIN PRE-REQ COURSE
	public void studCourseTaken(int course) {
		try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "sabahat", "");
		Statement st=con.createStatement(
				ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		ResultSet rs= st.executeQuery("select studentInfo.lastName, studentInfo.firstName "
				+ "from studentInfo"
				+ " join courseInfo on (studentInfo.courseID=courseInfo.courseID) "
				+ "where courseInfo.preReqCourse=" + course);
		rs.last();
		int numRows=rs.getRow();
		rs.first();
		int countStudents = 0;
		
		String[] firstNameData = new String[numRows];
		String[] lastNameData = new String[numRows];
		
		for(int index = 0; index < numRows; index++) {
			
			lastNameData[index]= rs.getString(1);
			firstNameData[index]= rs.getString(2);
			System.out.println(firstNameData[index]+" "+lastNameData[index]);
			
			countStudents++;
			rs.next();
		}
		
		System.out.println(countStudents + " Students have taken the course " + course);
		con.close();
		st.close();
		
		
		}
		
		catch(Exception e)
		{
			System.out.println(e);
			
		}
		
		
	}
	
	//PRINTS ALL THE INFORMATION FOR A SPECIFIC STUDENT
	public void getStudentInfo(int empid) {
		try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "sabahat", "");
		Statement st=con.createStatement(
				ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		ResultSet rs= st.executeQuery("select * from studentInfo where empID=" + empid);
		if(rs.next()) {
			System.out.println("EmpID: " + rs.getInt(1));
			System.out.println("Last Name: " + rs.getString(2));
			System.out.println("First Name: " + rs.getString(3));
			System.out.println("CourseID for required course: " + rs.getInt(4));
			System.out.println("Term Course was Taken: " + rs.getString(5));
			System.out.println("Year course was taken: " + rs.getInt(6));
			System.out.println("Grade Received: " + rs.getString(7));
			System.out.println("GPA: " + rs.getDouble(8));
		}
		con.close();
		st.close();
		
		
		}
		
		catch(Exception e)
		{
			System.out.println(e);
			
		}
		
		
	}
	
	
	
	                                                           
}
