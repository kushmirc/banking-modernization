

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class CustomerLogin
 */
public class CustomerLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
    
    ServletContext sc;
    Connection con;
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		
		try{
			
			sc=getServletContext();
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/BNS","root","Passw0rd!");
			System.out.println("Database connection established successfully in validating Login credential servlet");
			
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
		
		
		try{
			
			String s1=request.getParameter("uname");
			String s2=request.getParameter("pword");
			
			PreparedStatement pstmt=con.prepareStatement("select * from customer where userid=? and pword=?",ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
			pstmt.setString(1, s1);
			pstmt.setString(2, s2);
			
			ResultSet rs=pstmt.executeQuery();
			
			PrintWriter pw=response.getWriter();
			
			
			if(rs.next()){
				
				HttpSession hs=request.getSession();
				hs.setAttribute("uname",s1);
				hs.setAttribute("pword", s2);
				
				RequestDispatcher rd=sc.getRequestDispatcher("/welcome");
				rd.forward(request, response);
				
				
			}
			else {
				
				pw.println("<html> <h6> Invalid Login credential.Please try again</h6><br /></html>");
				RequestDispatcher rd=sc.getRequestDispatcher("/customerlogin.html");
				rd.include(request, response);
								
			}
			
			
			
					
		}
		catch(Exception e){
			System.err.println(e);
		}
	}

}
