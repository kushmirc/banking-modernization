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
import javax.servlet.http.Cookie;
import java.sql.*;
@WebServlet("/addtransactions")



public class addtransactions extends HttpServlet {

	ServletContext sc;

	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);

		String amt= "", actno="", option="", balance="";
		String remark="NA",transtatus="NA",trandesc="Add Customer Transcation";
		
		actno = request.getParameter("actno").trim();
		amt = request.getParameter("amt").trim();
		option = request.getParameter("option").trim();
		
		
		try{
			ResultSet rs =  MySqlDataStoreUtilities.getUserDetailAccount(actno);
			rs.next();
			balance = rs.getString(8);
		}catch(Exception e){

		}
		
		if(option.equals("debit") && Integer.parseInt(balance)<Integer.parseInt(amt)){
			
			pw.println("<br /><br /><strong> Funds transfer can not be initiated as available balance is less than the to be transferred amount</strong>");
			remark="Funds transfer can not be initiated as available balance is less than the to be transferred amount";
			transtatus="fail";
		}
		int Finalamt = Integer.parseInt(amt);
		

		if(option.equals("debit")) {
			Finalamt  = Integer.parseInt(balance)-Integer.parseInt(amt);
		} else {
			Finalamt  = Integer.parseInt(balance)+Integer.parseInt(amt);
		}

		utility.printHtml("Header.html");
		pw.print("<div class='post' style='float: none; width: 100%'>");
		pw.print("<div class='single-product-area'><div class='zigzag-bottom'></div><div class='container'><div class='row'>");

		// if(remark.equals("good")){
		try
		{
			remark = MySqlDataStoreUtilities.UpdateBalance(actno, Finalamt);
			transtatus="pass";
			MySqlDataStoreUtilities.insertTranscationRecord(actno,actno,trandesc,transtatus,remark,Integer.parseInt(amt),option);
		}
		catch(Exception e)
		{ 

		}
		remark = "Transcation is done successfully";

		// } 
		pw.print("<h4 class='alert alert-danger text-center' style='margin-top:20px;'>"+remark+"</h4>");
		pw.print("</div></div></div></div>");
		utility.printHtml("Footer.html");
	}

	@Override
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		addCustomerTranscation(request,response,pw);
		
	}
	protected void addCustomerTranscation(HttpServletRequest request,HttpServletResponse response, PrintWriter pw) throws ServletException, IOException {

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
			+"		<strong>Add</strong> Customer Transcation"
			+"	</h2>"
			+"	<hr class='divider'>"
			+"	<form action=addtransactions method=post>"
			+"		<div class='row'>"
			+"			<div class='form-group col-lg-12'>"
			+"				<label class='text-heading'>Recipient Account Number</label>"
			+"				<input type=number name='actno' />"
			+"			</div>"
			+"			<div class='form-group col-lg-12'>"
			+"				<label class='text-heading'>Amount to be processed</label>"
			+"				<input type=number name='amt' />"
			+"			</div>"
			+"			<div class='form-group col-lg-12'>"
			+"				<label class='text-heading'>Option</label>"
			+"				<input type=radio name=option value=debit> Debit </input>"
			+"				<input type=radio name=option value=credit> Credit </input>"
			+"			</div>"
			+"			<div class='form-group col-lg-12'>"
			+"				<input type=submit value= Add Customer Transcation> &nbsp; &nbsp; &nbsp;"		
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
