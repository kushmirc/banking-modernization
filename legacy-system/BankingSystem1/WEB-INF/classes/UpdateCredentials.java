

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

/**
 * Servlet implementation class UpdateCredentials
 */
public class UpdateCredentials extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateCredentials() {
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
			con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/banking_system","root","Passw0rd!");
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
		
		
		String s1=request.getParameter("newrepword");
		String s2=request.getParameter("newpword");
		Cookie c[]=request.getCookies();
		response.setContentType("text/html");
		
		int uidlen=s1.length();
		int pwdlen=s2.length();
		
		PrintWriter pw=response.getWriter();
		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		pw.print("<center><div class='post' style='float: none; width: 100%'>");
		pw.print("<div class='single-product-area'><div class='zigzag-bottom'></div><div class='container'><center><div class='row'></center><div class='bg-faded p-4 my-4'><hr class='divider'><center>");
		
		pw.println("<a href=welcome>Home</a>&nbsp; &nbsp;");
		
		pw.println("<a href=logout>Log out</a><br /><br />");
		
		pw.println("<br /><br />");
		HttpSession hs=request.getSession();
		
		String username=(String)hs.getAttribute("uname");
		String usertype=(String)hs.getAttribute("usertype");

		PreparedStatement pstmt;
		
		try {

			if(uidlen==0 && pwdlen==0){
				pw.println("<br /><h3>You have not updated Password. Login credentials remain same</h3>");
			}

			else if(!s1.equals(s2)){
				pw.println("<br /><h3> Password and Re password does not match, please try again</h3>");

			}
			else{
				pstmt=con.prepareStatement("update "+usertype+" set pword=? where USERID=?");
				pstmt.setString(1, s2);


				pstmt.setString(2, username);
				pstmt.executeUpdate();

				pw.println("<br /> <h3>Your password updated successfully</h3>");
			}




		//else

		}//try
		
		catch(Exception e){
			System.err.println(e);
		}
		
		
	}

}
