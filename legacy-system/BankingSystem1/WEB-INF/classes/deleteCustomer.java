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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.*;
@WebServlet("/deleteCustomer")



public class deleteCustomer extends HttpServlet {

	ServletContext sc;

	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);

		// String fname= "", lname="", dob="", userid ="",pword="",actno = "", gender="", msg = "good";
		// int balance=0;
		// fname = request.getParameter("FNAME").trim();
		// lname   = request.getParameter("LNAME").trim();
		// dob = request.getParameter("DOB").trim(); 
		// try{
		// 	Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(dob);  
		// 	dob=new SimpleDateFormat("MM/dd/yyyy").format(date1);	
		// } catch(Exception e){

		// } 
		
		// userid = request.getParameter("USERID").trim();
		// pword = request.getParameter("PASSWORD").trim();
		String actno="",msg="good";
		actno = request.getParameter("actno").trim();
		// pw.append("actno");
		 if(!actno.isEmpty()){
		 	msg = "Acconunt not found to be deleted";
			
		 }
		// pw.append("here" + actno);


		// utility.printHtml("Header.html");
		// pw.print("<div class='post' style='float: none; width: 100%'>");
		// pw.print("<div class='single-product-area'><div class='zigzag-bottom'></div><div class='container'><div class='row'>");

		// if(msg.equals("good")){
			try
			{
				msg = MySqlDataStoreUtilities.deleteUserAccount(actno);
			}
			catch(Exception e)
			{ 
				msg = "Customer cannot be deleted";
			}
			msg = "Customer has been successfully deleted";

		// } 
		// pw.append("<h4 class='alert alert-danger text-center' style='margin-top:20px;'>"+msg+"</h4>");
		// pw.print("</div></div></div></div>");
		// utility.printHtml("Footer.html");
	}

	@Override
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		askUserAccount(request, response, pw);
		// displayAddUser(request, response, pw, false);
	}
	protected void askUserAccount(HttpServletRequest request,HttpServletResponse response, PrintWriter pw) throws ServletException, IOException {

		Utilities utility = new Utilities(request, pw);
		if(!utility.isLoggedin()){
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to use functionalities");
			response.sendRedirect("customerlogin");
			return;
		}
		utility.printHtml("Header.html");
		pw.print("<div class='post' style='float: none; width: 100%'>");
		pw.print("<div id='login-form-wrap' style:'margin-bottom:0;'>");
		pw.print("<main class='container'><div class='container'><div class='bg-faded p-4 my-4'>"
			+"<hr class='divider'>"
			+"	<h2 class='text-center text-lg text-uppercase my-0'>"
			+"		<strong>Delete</strong> Customer Details"
			+"	</h2>"
			+"	<hr class='divider'>" 
			+"  <script type=\"text/javascript\">"
			+" function deleteCustomer(theForm) {"
			+"if (confirm(\"You Sure want to delete user: \" + theForm.actno.value)) {"
			+" var data = {\"actno\" : theForm.actno.value};"
			+"$.ajax({"
			+"type: \"POST\","
			+"url: \"deleteCustomer\","
			+"data: data,"
			+"success: function(response) {"
   			+ "alert('User is deleted successfully');"
   			+"window.location = 'welcome';"
   			+" return false;"
   			+ "}"
			+"});"
			+"} else {"
			+" return false;"
			+"}"
			+"return false; }</script>"
			+"	<form action=\"#\" onsubmit=\"return deleteCustomer(this);\">"
			+"		<div class='row'>"
			+"			<div class='form-group col-lg-12'>"
			+"				<label class='text-heading'>Recipient Account Number</label>"
			+"				<input type=number name=actno>"
			+"			</div>"
			+"			<div class='form-group col-lg-12'>"
			+"				<input type=submit value=\"Delete Customer details\"> &nbsp; &nbsp; &nbsp;"		
			+"				<input type=reset value= Clear>"
			+"			</div>"
			+"		</div>"
			+"	</form>"
			+"	</div>"
			+"	</div>"
			+"</main>");
		pw.print("</div></div>");
		utility.printHtml("Footer.html");
	}




}
