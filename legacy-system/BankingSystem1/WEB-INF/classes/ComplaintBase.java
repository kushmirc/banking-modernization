

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ComplaintBase
 */
public class ComplaintBase extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ComplaintBase() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
    
    Connection con;
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		

try{
			
			Class.forName("org.postgresql.Driver");
			con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/banking_system","banking_user","Passw0rd!");
			System.out.println("Database connection established successfully in retrieviing complaints from Database");
			
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
		
		
		PreparedStatement pstmt;
		response.setContentType("text/html");
		PrintWriter pw=response.getWriter();
		Utilities utility = new Utilities(request, pw);
		
			utility.printHtml("Header.html");
		pw.print("<center><div class='post' style='float: none; width: 100%'>");
		pw.print("<div class='single-product-area'><div class='zigzag-bottom'></div><div class='container'><center><div class='row'></center><div class='bg-faded p-4 my-4'><hr class='divider'><center>");
		pw.println("<strong> <a href=welcome>Home</a></strong>\t\t");
		
		pw.println("<strong><a href=logout>Log out</a></strong><br /><br />");
			pw.println("<h4>Details of Complaint :</h4><table style='width:100%; text-align:center;' border=2>");
		Cookie c[]=request.getCookies();
		
		
		try{
			
			// Use forward-only ResultSet for PostgreSQL compatibility
			pstmt=con.prepareStatement("select * from complaint where actno=?");
			pstmt.setString(1, c[2].getValue());
			
			ResultSet rs=pstmt.executeQuery();
			
			// Check if ResultSet has data
			boolean hasData = false;
			
			// First, get metadata and print headers
			ResultSetMetaData rm=rs.getMetaData();
			int colcnt=rm.getColumnCount();
			
			pw.print("<thead><tr>");
			for(int i=1;i<=colcnt;i++){
				pw.print("<th>"+rm.getColumnName(i).toLowerCase()+"</th>");
			}//for
			pw.print("</tr></thead><tbody>");
			
			// Now iterate through results
			while(rs.next()) {
				hasData = true;
				pw.print("<tr>");
				
				for(int i=1;i<=colcnt;i++){
					pw.print("<td>"+rs.getString(i)+"</td>");
				} //for
				pw.print("</tr>");
			} //while
			
			if(hasData) {
				pw.print("</tbody></table>");
			} else {
				pw.print("</tbody></table>");
				pw.println("<p>There are no complaints logged</p>");
			}
				pw.print("</td></div></div></div></div></div></div></center></center>");
			utility.printHtml("Footer.html");
		}
		
		catch(Exception e){
			System.err.println(e);
		}
	}

}
