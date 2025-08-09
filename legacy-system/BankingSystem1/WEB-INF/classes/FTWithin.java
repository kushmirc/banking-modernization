

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.*;

/**
 * Servlet implementation class FTWithin
 */
public class FTWithin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FTWithin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
    Connection con;
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		
		super.init(config);
		
try{
			
			Class.forName("org.postgresql.Driver");
			con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/banking_system","kushmirchandani","");
			System.out.println("Database connection established successfully in Funds transfer With in bank servlet");
			
		}
		
		catch(Exception e){
			System.err.println(e);
			
		}

	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String s1=request.getParameter("actno");
		String s2=request.getParameter("amt");
		
		response.setContentType("text/html");
		PrintWriter pw=response.getWriter();
		Utilities utility = new Utilities(request, pw);
			utility.printHtml("Header.html");
		pw.print("<center><div class='post' style='float: none; width: 100%'>");
		pw.print("<div class='single-product-area'><div class='zigzag-bottom'></div><div class='container'><center><div class='row'></center><div class='bg-faded p-4 my-4'><hr class='divider'><center>");
		
		
		pw.println("<a href=welcome>Home</a>\t");
		
		pw.println("<a href=logout>Log out</a><br /><br />");
		
		pw.println("<h3>Funds Transfer to Other Account With in Bank :</h3> ");
		pw.println("Recipient Account number is : "+s1+"<br />");
		pw.println("Balance to be transferred is : "+s2);
		
		Cookie c[]=request.getCookies();
		PreparedStatement pstmt;
		ResultSet rs1;
		HttpSession hs=request.getSession();
		
		String actno=(String)hs.getAttribute("AccountNoC");
		String bal=(String)hs.getAttribute("bal");
		long tranid=0;
		String remark="NA",transtatus="NA",trandesc="Funds Transfer With in the Bank";

		try {
			pstmt =con.prepareStatement(" select count(*) from transaction;");
			
			rs1=pstmt.executeQuery();
			rs1.next();
			tranid=rs1.getInt(1) + 1;
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		if(Integer.parseInt(bal)<Integer.parseInt(s2)){
			
			pw.println("<br /><br /><strong> Funds transfer can not be initiated as available balance is less than the to be transferred amount</strong>");
			remark="Funds transfer can not be initiated as available balance is less than the to be transferred amount";
			transtatus="fail";
		}
		
		else{
			
			
			
			try {
				
				pstmt=con.prepareStatement("select * from customer where actno=?");
				
				pstmt.setString(1, s1);
				
				ResultSet rs=pstmt.executeQuery();
				
				if(rs.next()){
				
				int newRecptBal=Integer.parseInt(rs.getString(8));
				newRecptBal=newRecptBal+Integer.parseInt(s2);
				
				pstmt=con.prepareStatement("update customer set balance=? where actno=?");
				pstmt.setLong(1, newRecptBal);
				pstmt.setString(2, s1);
				pstmt.executeUpdate();
				
				int newBal= Integer.parseInt(bal)-Integer.parseInt(s2);
				
				
				pstmt = con.prepareStatement("update customer set balance=? where actno=?");
							
				pstmt.setLong(1, newBal);
				pstmt.setString(2,actno);
				
				pstmt.executeUpdate();
				
				pw.println("<br /> <br />Funds <strong> USD "+s2+" </strong> transferred successfully to the account number <strong>"+s1+"</strong>");
				pw.println("<br /> Transaction Id is : <strong>"+tranid+"</strong>");
				
				pw.println("<br /><br />Available balance in the account is USD <strong>"+newBal+"</strong");
				
				remark="Funds transferred successfully";
				System.out.println("New balance is updated successfully in database");
				transtatus="pass";
				
			
				}
				
				else {
					
					pw.println("<br /><br /><strong>Recipient account number is incorrect. Please check</strong>");
					remark="Recipient account number is incorrect";
					transtatus="fail";
					pw.println("There are no Transactions happened in this account");
				pw.print("</td></div></div></div></div></div></div></center></center>");
		utility.printHtml("Footer.html");
				}
				
				
			} 
			
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}//else
		
		try {
			
			
			System.out.println("New balance is updated successfully in database");
			PostgreSqlDataStoreUtilities.insertTranscationRecord(actno,s1,trandesc,transtatus,remark,Integer.parseInt(s2),"debit");
			PostgreSqlDataStoreUtilities.insertTranscationRecord(s1,actno,trandesc,transtatus,remark,Integer.parseInt(s2),"credit");
			
			System.out.println("Transaction table updated successfully");
			
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}

}
