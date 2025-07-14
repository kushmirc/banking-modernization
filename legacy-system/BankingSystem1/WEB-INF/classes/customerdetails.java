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
@WebServlet("/customerdetails")

public class customerdetails extends HttpServlet {

	ServletContext sc;

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		showcustomerdetails(request, response, pw, false);
		// displayAddUser(request, response, pw, false);
	}


	public void showcustomerdetails(HttpServletRequest request,HttpServletResponse response, PrintWriter pw, boolean error)
	throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		Utilities utility = new Utilities(request, pw);
		if(!utility.isLoggedin()){
			session.setAttribute("login_msg", "Please Login to use functionalities");
			response.sendRedirect("customerlogin");
			return;
		}


		String un = request.getParameter("actno").trim();

		utility.printHtml("Header.html");
		pw.print("<div class='post' style='float: none; width: 100%'>");
				pw.print("<div class='single-product-area'><div class='zigzag-bottom'></div><div class='container'><center><div class='row'></center><div class='bg-faded p-4 my-4'><hr class='divider'><center>");

		//utility.addHeaderRow("Login");
		if (error)
			pw.print("<h4 class='alert alert-danger text-center' style='margin-top:20px;'>Please check your username, password and user type!</h4>");
		if(session.getAttribute("login_msg")!=null){			
			pw.print("<h4 class='alert alert-danger text-center' style='margin-top:20px;'>"+session.getAttribute("login_msg")+"</h4>");
			session.removeAttribute("login_msg");
		}

		
		try{
			ResultSet rs= MySqlDataStoreUtilities.getUserDetailAccount(un);
			

			if(rs !=null){


				rs.next();

				String dob = rs.getString(3);
				try{
					Date date1=new SimpleDateFormat("MM/dd/yyyy").parse(dob);  
					dob=new SimpleDateFormat("yyyy-MM-dd").format(date1);	
				} catch(Exception e){

				}
				pw.print("<div id='login-form-wrap' style:'margin-bottom:0;'>");
				pw.print("<form method='post' action='updateCustomer' class='login collapse in ' style='margin-left: auto;width: 20%;margin-right: auto;display:inline-block;text-align:left;'>"
			+ "<p class='form-row form-row-wide address-field update_totals_on_change validate-required woocommerce-validated'><label>Customer First Name</label><input type='text' name='FNAME' value=' "+rs.getString(1)+"'class='input input-text ' required></input></p>"
			+ "<p class='form-row form-row-wide address-field update_totals_on_change validate-required woocommerce-validated'><label>Customer Last Name</label><input type='text' name='LNAME' value='"+rs.getString(2)+"' class='input input-text ' required></input></p>"
			+ "<p class='form-row form-row-wide address-field update_totals_on_change validate-required woocommerce-validated'><label>Customer Date of Birth</label><input type='date' name='DOB' value='"+dob+"' class='input input-text ' required></input></p>"
			+ "<p class='form-row form-row-wide address-field update_totals_on_change validate-required woocommerce-validated'><label>User ID</label><input type='text' name='USERID' value='"+rs.getString(4)+"' class='input input-text ' required></input></p>"
			+ "<p class='form-row form-row-wide address-field update_totals_on_change validate-required woocommerce-validated'><label>Password</label><input type='password' name='PASSWORD' placeholder='Leave it Blank if you do not wish to change password ' value='' class='input input-text '></input></p>"
			+ "<p class='form-row form-row-wide address-field update_totals_on_change validate-required woocommerce-validated'><label>Account number</label><input type='text' name='ACTNO' value='"+rs.getString(6)+"' class='input input-text ' required></input></p>"

			+ "<p class='form-row form-row-wide address-field update_totals_on_change validate-required woocommerce-validated'><label>Gender</label>"
					+" <input type='radio' name='gender' value='MALE'> Male<br>"
					+ "<input type='radio' name='gender' value='FEMALE'> Female<br>"
					+ "<input type='radio' name='gender' value='OTHER'> Other</p>"
			+ "<p class='form-row form-row-wide address-field update_totals_on_change validate-required woocommerce-validated'><label>Balance</label><input type='number' step='any' placeholder='please enter numeric data' name='balance' id='balance' value='"+rs.getString(8)+"' class='input input-text ' required></input></p>"
			+ "<p class='form-row form-row-wide address-field update_totals_on_change validate-required woocommerce-validated'><label>Address Line 1</label><input type='text' name='addressline1' value='"+rs.getString(9)+"' class='input input-text ' required></input></p>"
			+ "<p class='form-row form-row-wide address-field update_totals_on_change validate-required woocommerce-validated'><label>Address Line 2</label><input type='text' name='addressline2' value='"+rs.getString(10)+"' class='input input-text ' required></input></p>"
			+ "<p class='form-row form-row-wide address-field update_totals_on_change validate-required woocommerce-validated'><label>City</label><input type='text' name='city' value='"+rs.getString(11)+"' class='input input-text ' required></input></p>"
			+ "<p class='form-row form-row-wide address-field update_totals_on_change validate-required woocommerce-validated'><label>State</label><input type='text' name='state' value='"+rs.getString(12)+"' class='input input-text ' required></input></p>"
			+ "<p class='form-row form-row-wide address-field update_totals_on_change validate-required woocommerce-validated'><label>Zip</label><input type='number' name='zip' value='"+rs.getString(13)+"' class='input input-text ' required></input></p>"
			
			+ "<input type='submit' class='btnbuy' name='button' value='Update'></input>"
			+ "<div class='clear'></div>"
			+ "</form><script type='text/javascript'>x = document.getElementsByName('gender');for(var k in x){ if(x[k].value && x[k].value==\""+rs.getString(7)+"\"){x[k].checked = 'checked';}}"
			+ "</script></div></div>");


			} else{
				pw.print("User Not found");
			}
		} catch(Exception e){

		}
		utility.printHtml("Footer.html");
	}

}