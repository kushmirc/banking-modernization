

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.*;
import java.util.*;
import java.text.SimpleDateFormat;

/**
 * Servlet implementation class Complaint
 */
public class Complaint extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
 */
    
    public Complaint() {
    	super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */

	Connection con;
	long complNo;
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		

		super.init(config);

		try{
			
			Class.forName("org.postgresql.Driver");
			con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/banking_system","banking_user","Passw0rd!");
			System.out.println("Database connection established successfully in registering complaint servlet");
			
			complNo=1010;
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
		
		
		String s1=request.getParameter("subject");
		String s2=request.getParameter("desc");
		
		Cookie c[]=request.getCookies();
		
		response.setContentType("text/html");
		PreparedStatement pstmt;

		try{
			
			String actno = null;
			PreparedStatement pst=con.prepareStatement("select count(*) from complaint;");
			ResultSet rs1=pst.executeQuery();
			rs1.next();
			// complNo = rs1.getInt(1) + 1010;
			complNo = 1013;
			for ( int i=0; i<c.length; i++) {
				Cookie cookie = c[i];
				if ("AccountNoC".equals(cookie.getName())){
					actno = cookie.getValue();

				}

			}
			
			pstmt=con.prepareStatement("insert into complaint(complaint_no,actno,COMPLAINT_DATE,subject,description,status,CLOSED) values(?,?,?,?,?,?,?)");
			


			try{

				java.util.Date utilDate = new java.util.Date();
				java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
				pstmt.setLong(1, complNo);
				pstmt.setString(2, c[2].getValue());
				pstmt.setDate(3, sqlDate);
				pstmt.setString(4, s1);
				pstmt.setString(5, s2);
				pstmt.setString(6, "Open");
				pstmt.setString(7, "False");
				pstmt.executeUpdate();	
			 // Complaint_POJO complaints = new Complaint_POJO(); 
				//  complaints.setComplaint_no(complNo);
				// complaints.setActno(c[2].getValue());
				// complaints.setCompalaint_date(sqlDate);
				// complaints.setSubject(s1);
				// complaints.setDescription(s2);
				// complaints.setStatus("Open");
				// complaints.setClosed("False"); 
				System.out.println("complaint No is :"+complNo);
				String date1;
				date1 = new SimpleDateFormat("MM/dd/yyyy").format(utilDate);

				MongoDBDataStoreUtilities.insertComplaint(Long.toString(complNo),c[2].getValue(),date1 ,s1,s2,"OPEN","false");

			} catch(Exception e){
				System.out.print(e);
			}


			
			PrintWriter pw=response.getWriter();
			Utilities utility = new Utilities(request, pw);
			utility.printHtml("Header.html");
			pw.print("<center><div class='post' style='float: none; width: 100%'>");
			pw.print("<div class='single-product-area'><div class='zigzag-bottom'></div><div class='container'><center><div class='row'></center><div class='bg-faded p-4 my-4'><hr class='divider'><center>");

			
			pw.println("<a href=welcome>Home</a>\t\t");
			
			pw.println("<a href=logout>Log out</a><br /><br />");
			

			pw.println("<br />Your complaint is registered successfully and the complaint number is "+ complNo);
			//pw.print("<h4 class='alert alert-danger text-center' style='margin-top:20px;'>"+msg+"</h4>");
			pw.print("</div></div></div></div></div></div></center></center>");
			utility.printHtml("Footer.html");
			
		}
		
		catch(Exception e){
			System.err.println(e);
		}
		
		
	}

}
