

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
 * Servlet implementation class FTOtherBank
 */
public class FTOtherBank extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FTOtherBank() {
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
			con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/banking_system","banking_user","Passw0rd!");
			System.out.println("Database connection established successfully in user credentials change servlet");
			
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
		
		HttpSession hs=request.getSession();
		
		String actno=(String)hs.getAttribute("AccountNoC");
		String bal=(String)hs.getAttribute("bal");
		
		response.setContentType("text/html");
		PrintWriter pw=response.getWriter();
		Utilities utility = new Utilities(request, pw);
			utility.printHtml("Header.html");
		pw.print("<center><div class='post' style='float: none; width: 100%'>");
		pw.print("<div class='single-product-area'><div class='zigzag-bottom'></div><div class='container'><center><div class='row'></center><div class='bg-faded p-4 my-4'><hr class='divider'><center>");
		
		
		pw.println("<strong><a href=welcome>Home</a></strong> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		pw.println("<strong><a href=logout>Log out</a></strong><br />");
		
		pw.println("Recipient Account number is : "+s1);
		pw.println("<br />Balance to be transferred is : "+s2);
		Cookie c[]=request.getCookies();
			
				

		PreparedStatement pstmt;
		
        ResultSet rs1;
		

		long tranid=0;
		String remark="NA",transtatus="NA",trandesc="Funds Transfer to other Bank";
		
		try {
			pstmt =con.prepareStatement("select count(*) from transaction;");
			
			rs1=pstmt.executeQuery();
			rs1.next();
			tranid=rs1.getInt(1) + 1;
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		
			if(Long.parseLong(bal)<Long.parseLong(s2)){
			
			pw.println("<br />Funds transfer can not be initiated as available balance is less than the to be transferred amount");
			remark="Funds transfer can not be initiated as available balance is less than the to be transferred amount";
			transtatus="fail";
		}
		
		else{
			
			
			
			try {
				
				// long newBal= Long.parseLong(bal)-Long.parseLong(s2);
				
				
				// pstmt = con.prepareStatement("update customer set balance=? where actno=?");

				// pstmt.setLong(1, newBal);
				// pstmt.setString(2,actno);
				
				// pstmt.executeUpdate();
				
				pw.println("<br /> Funds <strong>USD "+s2+" </strong>transfer initiated to the account number <strong> "+s1+"</strong>");
				pw.println("<br /> Transaction Id is : <strong>"+tranid+"</strong>");
				// pw.println("<br />Available balance in the account is USD <strong>"+newBal+"</strong>");
				pw.println("<br /> Your balance will be reflected only after banker approves for it.");


				remark="Funds transfer in progress";
				transtatus="progressing";
				
				pw.print("</td></div></div></div></div></div></div></center></center>");
				utility.printHtml("Footer.html");
			} 
			
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
			
		}
		
		try {

			PostgreSqlDataStoreUtilities.insertTranscationRecord(actno,s1,trandesc,transtatus,remark,Integer.parseInt(s2),"debit");

			System.out.println("Transaction table updated successfully");

			PostgreSqlDataStoreUtilities.insertholdTranscationRecord((int) tranid,actno,s1);

			System.out.println("entry done in hold transaction table");

		}
		catch(Exception e){
			
		}
	}

}
