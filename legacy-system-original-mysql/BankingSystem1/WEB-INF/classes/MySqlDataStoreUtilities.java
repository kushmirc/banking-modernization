import java.sql.*;
import java.util.*;

public class MySqlDataStoreUtilities
{
	static Connection conn = null;
	static String message;
	public static String getConnection()
	{

		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bns","root","Passw0rd!");							
			message="Successfull";
			return message;
		}
		catch(SQLException e)
		{
			message="unsuccessful";
			return message;
		}
		catch(Exception e)
		{
			message=e.getMessage();
			return message;
		}
	}


	/* Use: Function To get customer data */
	public static ResultSet getUserData(String un, String usertype ){

		ResultSet rs = null;

		try{

			getConnection();
			PreparedStatement pstmt=conn.prepareStatement("select * from "+usertype+" where userid=?");

			if( ! un.equals(null))
			{
				pstmt.setString(1, un);
			}

			rs=pstmt.executeQuery();	
		} catch(Exception e){

		}


		return rs;

	}


	/* Use: Function To check if user exsist or not*/
	public static int selectUser(String username, String password, String usertype)
	{	
		//getConnection();

		int msg = -1;
		try 
		{
			getConnection();
			PreparedStatement pstmt=conn.prepareStatement("select * from "+usertype+" where userid=? and pword=?",ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next())
			{	
				msg = 1;
			}
		}
		catch(Exception e)
		{
		}
		return msg;			
	}

	/* Use: Function To check if user exsist or not*/
	public static int checkAccountExsist(String accountno)
	{	
		//getConnection();

		int msg = -1;
		try 
		{
			getConnection();
			PreparedStatement pstmt=conn.prepareStatement("select * from customer where actno=?",ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
			pstmt.setString(1, accountno);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next())
			{	
				msg = 1;
			}
		}
		catch(Exception e)
		{
		}
		return msg;			
	}

	/* Use: Function To check get details from account number*/
	public static ResultSet getUserDetailAccount(String accountno)
	{	
		//getConnection();

		ResultSet rs = null;

		try{

			getConnection();
			PreparedStatement pstmt=conn.prepareStatement("select * from customer where actno=?");

			if( ! accountno.equals(null))
			{
				pstmt.setString(1, accountno);
			}

			rs=pstmt.executeQuery();	
		} catch(Exception e){

		}
		return rs;		
	}

	/* Use: Function To check if user exsist or not*/
	public static int checkUserName(String username)
	{	
		//getConnection();

		int msg = -1;
		try 
		{
			getConnection();
			PreparedStatement pstmt=conn.prepareStatement("select * from customer where userid=?",ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
			pstmt.setString(1, username);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next())
			{	
				msg = 1;
			}
		}
		catch(Exception e)
		{
		}
		return msg;			
	}

	public static String addCustomer(String fname,String lname,String dob,String userid, String pword,String actno ,String gender,int balance, String addressline1, String addressline2, String city, String state, int zip){

		String msg = "not okay";
		try{
			getConnection();
			String insertProductQurey = "INSERT INTO  customer(fname, lname, dob, userid,pword,actno ,gender, balance,addressline1,addressline2,city,state,zip)" +
			"VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);";

			PreparedStatement pst = conn.prepareStatement(insertProductQurey);
			pst.setString(1,fname);
			pst.setString(2,lname);
			pst.setString(3,dob);
			pst.setString(4,userid);
			pst.setString(5,pword);
			pst.setString(6,actno);
			pst.setString(7,gender);
			pst.setInt(8,balance);
			pst.setString(9,addressline1);
			pst.setString(10,addressline2);
			pst.setString(11,city);
			pst.setString(12,state);
			pst.setInt(13,zip);




			pst.executeUpdate();	
			msg = "okay";
		} catch(Exception e){

		} 
		return msg;
		

	}

	public static String updateCustomer(String fname,String lname,String dob,String userid, String pword,String actno ,String gender,int balance, String addressline1, String addressline2, String city, String state, int zip){

		String msg = "not okay";
		try{
			getConnection();
			String insertProductQurey = "Update customer set fname=?, lname=?, dob=?, userid=?,pword=?,gender=?, balance=? , addressline1=?,addressline2=?,city=?,state=?,zip=? where actno=?" ;
			

			PreparedStatement pst = conn.prepareStatement(insertProductQurey);
			pst.setString(1,fname);
			pst.setString(2,lname);
			pst.setString(3,dob);
			pst.setString(4,userid);
			pst.setString(5,pword);
			pst.setString(6,gender);
			pst.setInt(7,balance);
			pst.setString(8,addressline1);
			pst.setString(9,addressline2);
			pst.setString(10,city);
			pst.setString(11,state);
			pst.setInt(12,zip);
			pst.setString(13,actno);

			pst.executeUpdate();	
			msg = "okay";
		} catch(Exception e){

		} 
		return msg;
		

	}

	public static String deleteUserAccount(String actno){
		String msg = "not okay";
		try{

			getConnection();
			PreparedStatement pstmt=conn.prepareStatement("delete from customer where actno=?");
			pstmt.setString(1, actno);
			pstmt.executeUpdate();	
			msg = "okay";
		} catch(Exception e){

		}
		return msg;
	}

	public static String UpdateBalance(String actno ,int balance){

		String msg = "not okay";
		try{
			getConnection();
			String insertProductQurey = "Update customer set balance=? where actno=? " ;
			

			PreparedStatement pst = conn.prepareStatement(insertProductQurey);
			pst.setInt(1,balance);
			pst.setString(2,actno);

			pst.executeUpdate();	
			msg = "okay";
		} catch(Exception e){

		} 
		return msg;
		

	}

	public static String insertTranscationRecord(String fromactno, String toactno,String trandesc,String transtatus,String remark, int amount, String amountaction){

		String msg = "not okay";
		try{
			getConnection();
			PreparedStatement pstmt=conn.prepareStatement("insert into transaction(fromactno,toactno,trandate,trandesc,transtatus,remark,amount,amountaction) values(?,?,Now(),?,?,?,?,?)");

			pstmt.setString(1, fromactno);
			pstmt.setString(2, toactno);
			pstmt.setString(3, trandesc);
			pstmt.setString(4, transtatus);
			pstmt.setString(5, remark);
			pstmt.setInt(6, amount);
			pstmt.setString(7, amountaction);

			pstmt.executeUpdate();
			
			msg = "okay";

		} catch(Exception e){
			System.out.println("insertTranscationRecord: " +e);
		} 
		return msg;
		

	}

	public static String insertholdTranscationRecord(int tranid, String fromactno, String toactno){
		String msg = "not okay";
		try{
			getConnection();
			PreparedStatement pstmt=conn.prepareStatement("insert into holdTranscations(tranid,fromactno,toactno,requesttime,approveStatus) values(?,?,?,Now(),'NotApproved')");

			pstmt.setInt(1, tranid);
			pstmt.setString(2, fromactno);
			pstmt.setString(3, toactno);
			
			

			pstmt.executeUpdate();
			
			msg = "okay";
		} catch(Exception e){

		} 
		return msg;
		

	}

	public static ResultSet getAllholdTranscationRecord(){
		ResultSet rs = null;

		try{

			getConnection();
			PreparedStatement pstmt=conn.prepareStatement("select h.*,c.fname as firstname,t.amount from holdTranscations h join transaction t on h.TRANID = t.TRANID join customer c on c.actno=h.fromactno");
			rs=pstmt.executeQuery();	
		} catch(Exception e){

		}
		return rs;		
	}

	public static String approveTranscation(String actno, String action, String tranid,String un){

		String msg = "not okay";
		try{
			getConnection();
			PreparedStatement pstmt=conn.prepareStatement("update transaction set transtatus=?, remark=? where TRANID = ? ");
			String remark = "transaction is " + action + "  by  " + un;
			String status = (action.equals("Approved")) ? "pass" : "fail" ;
			System.out.println("remark" + remark);
			pstmt.setString(1, status);
			pstmt.setString(2, remark);
			pstmt.setString(3, tranid);
			
			pstmt.executeUpdate();
			
			String insertProductQurey = "Update holdTranscations set approveStatus=?,approvetime=Now(),aprrovedby=? where holdtranid=? " ;

			PreparedStatement pst = conn.prepareStatement(insertProductQurey);
			pst.setString(1,action);
			pst.setString(2,un);
			pst.setString(3,actno);

			pst.executeUpdate();	
			msg = "okay";
			
			if(action.equals("Approved")){

				System.out.print("innnnn inin");
				PreparedStatement p=conn.prepareStatement("select amount,fromactno from transaction where TRANID = ? ");
				p.setString(1, tranid);
				ResultSet rs = p.executeQuery();

				rs.next();
				

				int amountChange = rs.getInt(1);
				String customeract = rs.getString(2);

				PreparedStatement p1=conn.prepareStatement("select BALANCE from customer where ACTNO =? ");
				p1.setString(1, customeract);
				ResultSet rs1 = p1.executeQuery();

				rs1.next();
				
				int balance = rs1.getInt(1);

				
				
				msg = MySqlDataStoreUtilities.UpdateBalance(customeract, (balance - amountChange));
				System.out.println("final amt" +  (balance - amountChange));
				System.out.println("msg" + msg);
				
			}
			

		} catch(Exception e){
			System.out.println("Approved" + e);
		} 
		return msg;
		

	}

	public static ResultSet getAllTransactions(){

		ResultSet rs = null;

		try{

			getConnection();
			PreparedStatement pstmt=conn.prepareStatement("select * from transaction");

			
			rs=pstmt.executeQuery();	
		} catch(Exception e){
			System.out.println("getAllTransactions ; " + e);
		}


		return rs;

	}
	
	
	public static ResultSet getParticularTransactions(String actno){

		ResultSet rs = null;

		try{
			getConnection();
			PreparedStatement pstmt=conn.prepareStatement("select * from transaction where fromactno LIKE '%"+actno+"%' or toactno LIKE '%"+actno+"%' ");
			
			rs=pstmt.executeQuery();
		} catch(Exception e){

		}


		return rs;

	}

	public static ResultSet getComplaints(){
		ResultSet rs = null;

		try{
			getConnection();
			PreparedStatement pstmt=conn.prepareStatement("select * from complaint ");
			
			rs=pstmt.executeQuery();
		} catch(Exception e){

		}


		return rs;
	}



}	