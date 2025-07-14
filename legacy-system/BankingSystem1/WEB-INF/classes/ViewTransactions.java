import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;

/**
 * Servlet implementation class ViewTransactions
 */
public class ViewTransactions extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewTransactions() {
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
			
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/BNS","root","Passw0rd!");
			System.out.println("Database connection established successfully");
			
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
		
pw.println("<strong><a href=welcome>Home</a></strong>\t\t");
		
		pw.println("<strong><a href=logout>Log out</a></strong><br /><br />");
		
		pw.println("<h4>Details of Transactions :</h4><table style='width:100%; text-align:center;' border=2>");
		
		Cookie c[]=request.getCookies();
		HttpSession hs=request.getSession();
		
		String actno=(String)hs.getAttribute("AccountNoC");
		
		try{
			
			pstmt=con.prepareStatement("select * from transaction where fromactno=?",ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
			pstmt.setString(1, actno);
			
			ResultSet rs=pstmt.executeQuery();
			
		
			if(rs.absolute(1))
			{
			
			
						
						
						ResultSetMetaData rm=rs.getMetaData();
						
						int colcnt=rm.getColumnCount();
						
						pw.print("<thead><tr>");
						
						for(int i=1;i<=colcnt;i++){
							
							pw.print("<th>"+rm.getColumnName(i).toLowerCase()+"</th>");
							
						}//for
						pw.print("</tr></thead><tbody>");
						rs.first();
						do {
							pw.print("<tr>");
							
							
							
							for(int i=1;i<=colcnt;i++){
								
								pw.print("<td>"+rs.getString(i)+"</td>");
								
							} //for
							pw.print("</tr>");
										
						} while(rs.next());//while
						pw.print("</tbody></table>");
			
			}//if
			
			else{
				pw.println("There are no Transactions happened in this account");

			}
			pw.print("</td></div></div></div></div></div></div></center></center>");
			utility.printHtml("Footer.html");
		}
		
		catch(Exception e){
			System.err.println(e);
		}

	}

}
