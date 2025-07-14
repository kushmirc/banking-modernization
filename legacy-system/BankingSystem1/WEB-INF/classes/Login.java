import java.io.*;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;
import javax.servlet.RequestDispatcher;

@WebServlet("/customerlogin")



	public class Login extends HttpServlet {

		ServletContext sc;

		@Override
		protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();


			// try{

			// 	String s1=request.getParameter("uname");
			// 	String s2=request.getParameter("pword");

			// 	PreparedStatement pstmt=con.prepareStatement("select * from registration where username=? and password=?",ResultSet.TYPE_SCROLL_INSENSITIVE,
			// 		ResultSet.CONCUR_UPDATABLE);
			// 	pstmt.setString(1, s1);
			// 	pstmt.setString(2, s2);

			// 	ResultSet rs=pstmt.executeQuery();

			// 	PrintWriter pw=response.getWriter();

			// 	pw.print(rs);
			// 	if(rs.next()){

			// 		HttpSession hs=request.getSession();
			// 		hs.setAttribute("uname",s1);
			// 		hs.setAttribute("pword", s2);

			// 		RequestDispatcher rd=sc.getRequestDispatcher("/welcome");
			// 		rd.forward(request, response);


			// 	}
			// 	else {

			// 		pw.println("<html> <h6> Invalid Login credential.Please try again</h6><br /></html>");
			// 		RequestDispatcher rd=sc.getRequestDispatcher("/customerlogin.html");
			// 		rd.include(request, response);

			// 	}




			// }
			// catch(Exception e){
			// 	System.err.println(e);
			// }

		/* User Information(username,password,usertype) is obtained from HttpServletRequest,
		Based on the Type of user(customer,retailer,manager) respective hashmap is called and the username and 
		password are validated and added to session variable and display Login Function is called */

		String s1=request.getParameter("uname");
		String s2=request.getParameter("pword");
		String s3 = request.getParameter("usertype");
		
		// String usertype = request.getParameter("usertype");
		int hm = 0;
		
		//user details are validated using a file 
		//if the file containts username and passoword user entered user will be directed to home page
		//else error message will be shown
		try
		{		
			hm=MySqlDataStoreUtilities.selectUser(s1,s2,s3);			
		}
		catch(Exception e)
		{

		}
		if (hm == 1) 
		{
			HttpSession hs = request.getSession(true);
			hs.setAttribute("uname",s1);
			hs.setAttribute("usertype", s3);

			response.sendRedirect("welcome");

			return;
		}
		
		displayLogin(request, response, pw, true);

	}

	@Override
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		displayLogin(request, response, pw, false);
	}

	/*  Login Screen is Displayed, Registered User specifies credentials and logins into the Game Speed Application. */
	protected void displayLogin(HttpServletRequest request,HttpServletResponse response, PrintWriter pw, boolean error)
	throws ServletException, IOException {

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		pw.print("<div class='post' style='float: none; width: 100%'>");
		//utility.addHeaderRow("Login");
		if (error)
			pw.print("<h4 class='alert alert-danger text-center' style='margin-top:20px;'>Please check your username, password and user type!</h4>");
		HttpSession session = request.getSession(true);
		if(session.getAttribute("login_msg")!=null){			
			pw.print("<h4 class='alert alert-danger text-center' style='margin-top:20px;'>"+session.getAttribute("login_msg")+"</h4>");
			session.removeAttribute("login_msg");
		}

		pw.print("<div class='bg-faded p-4 my-4'><hr class='divider'><center><div id='login-form-wrap'><form method='post' class='login collapse in ' action='customerlogin' style='margin-left: auto;margin-right: auto;display:inline-block;text-align:left;'>"
			+"<p class='form-row form-row-first'>"
			+"<label for='username'>Username <span class='required'>*</span></label>"
			+"<input type='text' id='userName' name='uname' class='input-text' required></p>"
			+"<p class='form-row form-row-wide'>"
			+"<label for='password'>Password<span class='required'>*</span></label>"
			+"<input type='password' name='pword' value='' id='userPassword' class='input-text' required></input>"
			+"<p class='form-row form-row-wide'>"
			+"<label for='usertype'>User Type<span class='required'>*</span></label></br>"
			+"<select name='usertype'>"
			+"<option value='customer'>Customer</option>"
			+"<option value='banker'>Banker</option>"
			+"<option value='administrator'>Administrator</option>"
			+"</select></p>"
			+ "<p class='form-row'><input type='submit' class='button' value='Login'></input></p>"
			+"<p class='form-row form-row-last'>"
			// + "<p class='lost_password'><strong><a class='' href='Registration'>New User? Register here!</a></strong>"
			+ "<div class='clear'></div>"
			+ "</form></div></div></center>");
		utility.printHtml("Footer.html");
	}


}
