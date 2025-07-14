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
@WebServlet("/createCustomer")



public class createCustomer extends HttpServlet {

	ServletContext sc;

	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);

		String fname= "", lname="", dob="", userid ="",pword="",actno = "", gender="", msg = "good",addressline1="",addressline2="",city="",state="";
		int balance=0, zip;
		fname = request.getParameter("FNAME").trim();
		lname   = request.getParameter("LNAME").trim();
		dob = request.getParameter("DOB").trim(); 
		try{
			Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(dob);  
			dob=new SimpleDateFormat("MM/dd/yyyy").format(date1);	
		} catch(Exception e){

		} 
		
		userid = request.getParameter("USERID").trim();
		pword = request.getParameter("PASSWORD").trim();
		actno = request.getParameter("ACTNO").trim();
		gender = request.getParameter("gender");
		balance = Integer.parseInt(request.getParameter("balance").trim());
		addressline1 = request.getParameter("addressline1");
		addressline2 = request.getParameter("addressline2");
		city = request.getParameter("city");
		state = request.getParameter("state");
		zip = Integer.parseInt(request.getParameter("zip").trim());

		if(MySqlDataStoreUtilities.checkAccountExsist(actno) == 1){
			msg = "Account number already taken";

		}
		if(MySqlDataStoreUtilities.checkUserName(userid) == 1){
			msg = "UserID already taken";

		}
		utility.printHtml("Header.html");
		pw.print("<div class='post' style='float: none; width: 100%'>");
		pw.print("<div class='single-product-area'><div class='zigzag-bottom'></div><div class='container'><center><div class='row'></center><div class='bg-faded p-4 my-4'><hr class='divider'><center>");
		if(msg.equals("good")){
			try
			{
				msg = MySqlDataStoreUtilities.addCustomer(fname, lname, dob, userid,pword,actno ,gender, balance,addressline1,addressline2,city,state,zip);
			}
			catch(Exception e)
			{ 
				msg = "Customer cannot be inserted";
			}
			msg = "Customer has been successfully added";

		} 
		pw.print("<h4 class='alert alert-danger text-center' style='margin-top:20px;'>"+msg+"</h4>");
		pw.print("</div></div></div></div></div></div></center>");
		utility.printHtml("Footer.html");
		//displayAddUser(request, response, pw, false);
	}

	@Override
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		displayAddUser(request, response, pw, false);
	}

	protected void displayAddUser(HttpServletRequest request,HttpServletResponse response, PrintWriter pw, boolean error)
	throws ServletException, IOException {

		Utilities utility = new Utilities(request, pw);
		if(!utility.isLoggedin()){
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to use functionalities");
			response.sendRedirect("customerlogin");
			return;
		}
		utility.printHtml("Header.html");
		pw.print("<div class='post' style='float: none; width: 100%'>");
		pw.print("<div class='single-product-area'><div class='zigzag-bottom'></div><div class='container'><center><div class='row'></center><div class='bg-faded p-4 my-4'><hr class='divider'><center>");
		//utility.addHeaderRow("Login");
		if (error)
			pw.print("<h4 class='alert alert-danger text-center' style='margin-top:20px;'>Please check your username, password and user type!</h4>");
		HttpSession session = request.getSession(true);
		if(session.getAttribute("login_msg")!=null){			
			pw.print("<h4 class='alert alert-danger text-center' style='margin-top:20px;'>"+session.getAttribute("login_msg")+"</h4>");
			session.removeAttribute("login_msg");
		}

		pw.print("<div id='login-form-wrap' style:'margin-bottom:0;'>");



		pw.print("<form method='post' action='createCustomer' class='login collapse in ' style='margin-left: auto;width: 20%;margin-right: auto;display:inline-block;text-align:left;'>"
			+ "<p class='form-row form-row-wide address-field update_totals_on_change validate-required woocommerce-validated'><label>Customer First Name</label><input type='text' name='FNAME' value='' class='input input-text ' required></input></p>"
			+ "<p class='form-row form-row-wide address-field update_totals_on_change validate-required woocommerce-validated'><label>Customer Last Name</label><input type='text' name='LNAME' value='' class='input input-text ' required></input></p>"
			+ "<p class='form-row form-row-wide address-field update_totals_on_change validate-required woocommerce-validated'><label>Customer Date of Birth</label><input type='date' name='DOB' value='' class='input input-text ' required></input></p>"
			+ "<p class='form-row form-row-wide address-field update_totals_on_change validate-required woocommerce-validated'><label>Gender</label><input type='radio' name='gender' value='MALE'> Male<br>"
			+ "<input type='radio' name='gender' value='FEMALE'> Female<br>"
			+ "<input type='radio' name='gender' value='OTHER'> Other</p>"
			+ "<p class='form-row form-row-wide address-field update_totals_on_change validate-required woocommerce-validated'><label>Address Line 1</label><input type='text' name='addressline1' value='' class='input input-text ' required></input></p>"
			+ "<p class='form-row form-row-wide address-field update_totals_on_change validate-required woocommerce-validated'><label>Address Line 2</label><input type='text' name='addressline2' value='' class='input input-text ' required></input></p>"
			+ "<p class='form-row form-row-wide address-field update_totals_on_change validate-required woocommerce-validated'><label>City</label><input type='text' name='city' value='' class='input input-text ' required></input></p>"
			+ "<p class='form-row form-row-wide address-field update_totals_on_change validate-required woocommerce-validated'><label>State</label><input type='text' name='state' value='' class='input input-text ' required></input></p>"
			+ "<p class='form-row form-row-wide address-field update_totals_on_change validate-required woocommerce-validated'><label>Zip</label><input type='number' name='zip' value='' class='input input-text ' required></input></p>"
			+ "<p class='form-row form-row-wide address-field update_totals_on_change validate-required woocommerce-validated'><label>Account number</label><input type='text' name='ACTNO' value='' class='input input-text ' required></input></p>"
			+ "<p class='form-row form-row-wide address-field update_totals_on_change validate-required woocommerce-validated'><label>Balance</label><input type='number' step='any' placeholder='please enter numeric data' name='balance' id='balance' value='' class='input input-text ' required></input></p>"
			+ "<p class='form-row form-row-wide address-field update_totals_on_change validate-required woocommerce-validated'><label>User ID</label><input type='text' name='USERID' value='' class='input input-text ' required></input></p>"
			+ "<p class='form-row form-row-wide address-field update_totals_on_change validate-required woocommerce-validated'><label>Password</label><input type='password' name='PASSWORD' value='' class='input input-text ' required></input></p>"
			+ "<input type='submit' class='btnbuy' name='button' value='Add'></input>"
			+ "<div class='clear'></div>"
			+ "</form>" + "</div></div>");


		pw.print("</div></div></div></div></div></div></center>");
		utility.printHtml("Footer.html");
	}


}
