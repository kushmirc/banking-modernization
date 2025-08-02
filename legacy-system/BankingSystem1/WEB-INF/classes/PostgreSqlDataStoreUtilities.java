import java.sql.*;
import java.util.*;

/**
 * PostgreSQL Data Store Utilities for Banking System
 * Converted from MySqlDataStoreUtilities.java for PostgreSQL compatibility
 * 
 * Key Changes from MySQL version:
 * - Updated JDBC driver to PostgreSQL
 * - Changed connection string format
 * - Updated SQL syntax for PostgreSQL compatibility
 * - Changed NOW() to CURRENT_TIMESTAMP for PostgreSQL
 * - Updated database name to lowercase 'banking_system'
 */
public class PostgreSqlDataStoreUtilities
{
	static Connection conn = null;
	static String message;
	
	public static String getConnection()
	{
		try
		{
			// PostgreSQL JDBC driver
			Class.forName("org.postgresql.Driver").newInstance();
			
			// PostgreSQL connection string format
			// Using lowercase database name for PostgreSQL best practices
			conn = DriverManager.getConnection(
				"jdbc:postgresql://localhost:5432/banking_system",
				"postgres", 
				"Passw0rd!"
			);							
			message="Successful";
			return message;
		}
		catch(SQLException e)
		{
			message="unsuccessful: " + e.getMessage();
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
			System.out.println("getUserData error: " + e.getMessage());
		}

		return rs;
	}

	/* Use: Function To check if user exists or not*/
	public static int selectUser(String username, String password, String usertype)
	{	
		int msg = -1;
		try 
		{
			getConnection();
			PreparedStatement pstmt=conn.prepareStatement(
				"select * from "+usertype+" where userid=? and pword=?",
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE
			);
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
			System.out.println("selectUser error: " + e.getMessage());
		}
		return msg;			
	}

	/* Use: Function To check if account exists or not*/
	public static int checkAccountExsist(String accountno)
	{	
		int msg = -1;
		try 
		{
			getConnection();
			PreparedStatement pstmt=conn.prepareStatement(
				"select * from customer where actno=?",
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE
			);
			pstmt.setString(1, accountno);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next())
			{	
				msg = 1;
			}
		}
		catch(Exception e)
		{
			System.out.println("checkAccountExsist error: " + e.getMessage());
		}
		return msg;			
	}

	/* Use: Function To get details from account number*/
	public static ResultSet getUserDetailAccount(String accountno)
	{	
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
			System.out.println("getUserDetailAccount error: " + e.getMessage());
		}
		return rs;		
	}

	/* Use: Function To check if username exists or not*/
	public static int checkUserName(String username)
	{	
		int msg = -1;
		try 
		{
			getConnection();
			PreparedStatement pstmt=conn.prepareStatement(
				"select * from customer where userid=?",
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE
			);
			pstmt.setString(1, username);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next())
			{	
				msg = 1;
			}
		}
		catch(Exception e)
		{
			System.out.println("checkUserName error: " + e.getMessage());
		}
		return msg;			
	}

	public static String addCustomer(String fname, String lname, String dob, String userid, 
									String pword, String actno, String gender, int balance, 
									String addressline1, String addressline2, String city, 
									String state, int zip){
		String msg = "not okay";
		try{
			getConnection();
			String insertProductQurey = "INSERT INTO customer(fname, lname, dob, userid, pword, actno, gender, balance, addressline1, addressline2, city, state, zip)" +
			"VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

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
			System.out.println("addCustomer error: " + e.getMessage());
		} 
		return msg;
	}

	public static String updateCustomer(String fname, String lname, String dob, String userid, 
									   String pword, String actno, String gender, int balance, 
									   String addressline1, String addressline2, String city, 
									   String state, int zip){
		String msg = "not okay";
		try{
			getConnection();
			String insertProductQurey = "UPDATE customer SET fname=?, lname=?, dob=?, userid=?, pword=?, gender=?, balance=?, addressline1=?, addressline2=?, city=?, state=?, zip=? WHERE actno=?";

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
			System.out.println("updateCustomer error: " + e.getMessage());
		} 
		return msg;
	}

	public static String deleteUserAccount(String actno){
		String msg = "not okay";
		try{
			getConnection();
			PreparedStatement pstmt=conn.prepareStatement("DELETE FROM customer WHERE actno=?");
			pstmt.setString(1, actno);
			pstmt.executeUpdate();	
			msg = "okay";
		} catch(Exception e){
			System.out.println("deleteUserAccount error: " + e.getMessage());
		}
		return msg;
	}

	public static String UpdateBalance(String actno, int balance){
		String msg = "not okay";
		try{
			getConnection();
			String insertProductQurey = "UPDATE customer SET balance=? WHERE actno=?";

			PreparedStatement pst = conn.prepareStatement(insertProductQurey);
			pst.setInt(1,balance);
			pst.setString(2,actno);

			pst.executeUpdate();	
			msg = "okay";
		} catch(Exception e){
			System.out.println("UpdateBalance error: " + e.getMessage());
		} 
		return msg;
	}

	public static String insertTranscationRecord(String fromactno, String toactno, String trandesc, 
												String transtatus, String remark, int amount, 
												String amountaction){
		String msg = "not okay";
		try{
			getConnection();
			// PostgreSQL uses CURRENT_TIMESTAMP instead of Now()
			PreparedStatement pstmt=conn.prepareStatement(
				"INSERT INTO transaction(fromactno,toactno,trandate,trandesc,transtatus,remark,amount,amountaction) VALUES(?,?,CURRENT_TIMESTAMP,?,?,?,?,?)"
			);

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
			System.out.println("insertTranscationRecord: " + e.getMessage());
		} 
		return msg;
	}

	public static String insertholdTranscationRecord(int tranid, String fromactno, String toactno){
		String msg = "not okay";
		try{
			getConnection();
			// PostgreSQL uses CURRENT_TIMESTAMP instead of Now()
			PreparedStatement pstmt=conn.prepareStatement(
				"INSERT INTO holdtranscations(tranid,fromactno,toactno,requesttime,approvestatus) VALUES(?,?,?,CURRENT_TIMESTAMP,'NotApproved')"
			);

			pstmt.setInt(1, tranid);
			pstmt.setString(2, fromactno);
			pstmt.setString(3, toactno);

			pstmt.executeUpdate();
			msg = "okay";
		} catch(Exception e){
			System.out.println("insertholdTranscationRecord error: " + e.getMessage());
		} 
		return msg;
	}

	public static ResultSet getAllholdTranscationRecord(){
		ResultSet rs = null;

		try{
			getConnection();
			PreparedStatement pstmt=conn.prepareStatement(
				"SELECT h.*, c.fname as firstname, t.amount FROM holdtranscations h " +
				"JOIN transaction t ON h.tranid = t.tranid " +
				"JOIN customer c ON c.actno = h.fromactno"
			);
			rs=pstmt.executeQuery();	
		} catch(Exception e){
			System.out.println("getAllholdTranscationRecord error: " + e.getMessage());
		}
		return rs;		
	}

	public static String approveTranscation(String actno, String action, String tranid, String un){
		String msg = "not okay";
		try{
			getConnection();
			PreparedStatement pstmt=conn.prepareStatement(
				"UPDATE transaction SET transtatus=?, remark=? WHERE tranid = ?"
			);
			String remark = "transaction is " + action + " by " + un;
			String status = (action.equals("Approved")) ? "pass" : "fail";
			System.out.println("remark: " + remark);
			pstmt.setString(1, status);
			pstmt.setString(2, remark);
			pstmt.setString(3, tranid);
			
			pstmt.executeUpdate();
			
			// PostgreSQL uses CURRENT_TIMESTAMP instead of Now()
			String insertProductQurey = "UPDATE holdtranscations SET approvestatus=?, approvetime=CURRENT_TIMESTAMP, aprrovedby=? WHERE holdtranid=?";

			PreparedStatement pst = conn.prepareStatement(insertProductQurey);
			pst.setString(1,action);
			pst.setString(2,un);
			pst.setString(3,actno);

			pst.executeUpdate();	
			msg = "okay";
			
			if(action.equals("Approved")){
				System.out.print("Processing approved transaction...");
				PreparedStatement p=conn.prepareStatement(
					"SELECT amount, fromactno FROM transaction WHERE tranid = ?"
				);
				p.setString(1, tranid);
				ResultSet rs = p.executeQuery();

				rs.next();
				
				int amountChange = rs.getInt(1);
				String customeract = rs.getString(2);

				PreparedStatement p1=conn.prepareStatement(
					"SELECT balance FROM customer WHERE actno = ?"
				);
				p1.setString(1, customeract);
				ResultSet rs1 = p1.executeQuery();

				rs1.next();
				
				int balance = rs1.getInt(1);
				
				msg = PostgreSqlDataStoreUtilities.UpdateBalance(customeract, (balance - amountChange));
				System.out.println("final amount: " + (balance - amountChange));
				System.out.println("msg: " + msg);
			}

		} catch(Exception e){
			System.out.println("approveTranscation error: " + e.getMessage());
		} 
		return msg;
	}

	public static ResultSet getAllTransactions(){
		ResultSet rs = null;

		try{
			getConnection();
			PreparedStatement pstmt=conn.prepareStatement("SELECT * FROM transaction");
			rs=pstmt.executeQuery();	
		} catch(Exception e){
			System.out.println("getAllTransactions error: " + e.getMessage());
		}

		return rs;
	}
	
	public static ResultSet getParticularTransactions(String actno){
		ResultSet rs = null;

		try{
			getConnection();
			// PostgreSQL uses ILIKE for case-insensitive pattern matching
			PreparedStatement pstmt=conn.prepareStatement(
				"SELECT * FROM transaction WHERE fromactno ILIKE ? OR toactno ILIKE ?"
			);
			String pattern = "%" + actno + "%";
			pstmt.setString(1, pattern);
			pstmt.setString(2, pattern);
			
			rs=pstmt.executeQuery();
		} catch(Exception e){
			System.out.println("getParticularTransactions error: " + e.getMessage());
		}

		return rs;
	}

	public static ResultSet getComplaints(){
		ResultSet rs = null;

		try{
			getConnection();
			PreparedStatement pstmt=conn.prepareStatement("SELECT * FROM complaint");
			rs=pstmt.executeQuery();
		} catch(Exception e){
			System.out.println("getComplaints error: " + e.getMessage());
		}

		return rs;
	}
}