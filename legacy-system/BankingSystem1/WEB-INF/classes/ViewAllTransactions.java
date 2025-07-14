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
@WebServlet("/viewalltransaction")



public class ViewAllTransactions extends HttpServlet {

	ServletContext sc;

	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);

		String amt= "", actno="", option="", balance="";
		// String remark="NA",transtatus="NA",trandesc="Add Customer Transcation";
		
		actno = request.getParameter("actno").trim();
		
			utility.printHtml("Header.html");
		pw.print("<center><div class='post' style='float: none; width: 100%'>");
		pw.print("<div class='single-product-area'><div class='zigzag-bottom'></div><div class='container'><center><div class='row'></center><div class='bg-faded p-4 my-4'><hr class='divider'><center>");
		
pw.println("<strong><a href=welcome>Home</a></strong>\t\t");
		
		pw.println("<strong><a href=logout>Log out</a></strong><br /><br />");
		showAllTranscations(request,response,pw);
		pw.println("<h4>Details of Transactions :</h4><table style='width:100%; text-align:center;' border=2>");
		
		
		// amt = request.getParameter("amt").trim();
		// option = request.getParameter("option").trim();
		try{
		if (actno.equals(""))	
		{
			
			ResultSet rs = MySqlDataStoreUtilities.getAllTransactions();
			
			
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
			
			}else{
			 System.out.println("Not fetching");
			}
				
				
		}else{
			System.out.println("entering");
			ResultSet rs = MySqlDataStoreUtilities.getParticularTransactions(actno);
			System.out.println("fetched");
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
			
			}
			else{
				pw.println("There are no Transactions happened in this account");
				
			}
			pw.print("</td></div></div></div></div></div></div></center></center>");
		utility.printHtml("Footer.html");
			
		}
		}catch(Exception e){
			System.out.print("View all Trans: "+e);
		}
	}
		
		// try{
		// 	ResultSet rs =  MySqlDataStoreUtilities.getUserDetailAccount(actno);
		// 	rs.next();
		// 	balance = rs.getString(8);
		// }catch(Exception e){

		// }
		
		// if(option.equals("debit") && Integer.parseInt(balance)<Integer.parseInt(amt)){
			
		// 	pw.println("<br /><br /><strong> Funds transfer can not be initiated as available balance is less than the to be transferred amount</strong>");
		// 	remark="Funds transfer can not be initiated as available balance is less than the to be transferred amount";
		// 	transtatus="fail";
		// }
		// int Finalamt = Integer.parseInt(amt);
		

		// if(option.equals("debit")) {
		// 	Finalamt  = Integer.parseInt(balance)-Integer.parseInt(amt);
		// } else {
		// 	Finalamt  = Integer.parseInt(balance)+Integer.parseInt(amt);
		// }

		// utility.printHtml("Header.html");
		
		// pw.print("<div class='post' style='float: none; width: 100%'>");
		// pw.print("<div class='single-product-area'><div class='zigzag-bottom'></div><div class='container'><div class='row'>");

		// // if(remark.equals("good")){
		// try
		// {
		// 	remark = MySqlDataStoreUtilities.UpdateBalance(actno, Finalamt);
		// 	transtatus="pass";
		// 	MySqlDataStoreUtilities.insertTranscationRecord(actno,trandesc,transtatus,remark);
		// }
		// catch(Exception e)
		// { 

		// }
		// remark = "Transcation is done successfully";

		// // } 
		// pw.print("<h4 class='alert alert-danger text-center' style='margin-top:20px;'>"+remark+"</h4>");
		// pw.print("</div></div></div></div>");
		// utility.printHtml("Footer.html");
	

	@Override
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		if(!utility.isLoggedin()){
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to use functionalities");
			response.sendRedirect("customerlogin");
			return;
		}
		utility.printHtml("Header.html");
		pw.print("<div class='post' style='float: none; width: 100%'>");
		pw.print("<div id='login-form-wrap' style:'margin-bottom:0;'><main class='container'><div class='container'><div class='bg-faded p-4 my-4'>");
		pw.println("<center><strong><a href=welcome>Home</a></strong>\t\t");
		
		pw.println("<strong><a href=logout>Log out</a></strong><br /><br /></center>");
		showAllTranscations(request,response,pw);
		pw.print("</div></div></main></div></div>");
		utility.printHtml("Footer.html");

	}
	protected void showAllTranscations(HttpServletRequest request,HttpServletResponse response, PrintWriter pw) throws ServletException, IOException {

		
		
		pw.print(""
			+"<hr class='divider'>"
			+"	<h2 class='text-center text-lg text-uppercase my-0'>"
			+"		<strong>All</strong> Customer Transcation"
			+"	</h2>"
			+"	<hr class='divider'>"
			+"	<form action=viewalltransactions method=post>"
			+"		<div class='row' style='text-align: center;'>"
			+"			<div class='form-group col-lg-12'>"
			+"				<label class='text-heading'>Recipient Account Number</label>"
			+"				<input type=number name='actno' />"
			+"			</div>"
			+"			<div class='form-group col-lg-12'>"
			+"				<input type=submit value= View Customer Transcation> &nbsp; &nbsp; &nbsp;"		
			+"				<input type=reset value= Clear>"
			+"			</div>"
			+"		</div>"
			+"	</form>");
		
	}




}
