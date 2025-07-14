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
@WebServlet("/moneytransferrequest")



public class moneytransferrequest extends HttpServlet {


	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		String holdID="",action="",tranid="",msg="good";
		holdID = request.getParameter("holdID").trim();
		action = request.getParameter("action").trim();
		tranid = request.getParameter("tranid").trim();


		HttpSession hs=request.getSession();
		
		String un=(String)hs.getAttribute("uname");
		
		
		if(!holdID.isEmpty()){
			msg = "Transaction not found";
			
		}
		try
		{
			msg = MySqlDataStoreUtilities.approveTranscation(holdID,action,tranid, un);
		}
		catch(Exception e)
		{ 
			msg = "Transaction cannot be approved";
		}
		msg = "Transaction approved successfully";

	}
	@Override
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		displayTrasnferData(request, response, pw);
	}

	protected void displayTrasnferData(HttpServletRequest request,HttpServletResponse response, PrintWriter pw)
	throws ServletException, IOException {


		Utilities utility = new Utilities(request, pw);
		if(!utility.isLoggedin()){
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to use functionalities");
			response.sendRedirect("customerlogin");
			return;
		}

		utility.printHtml("Header.html");
		pw.print("<center><div class='post' style='float: none; width: 100%'>");
		pw.print("<div class='single-product-area'><div class='zigzag-bottom'></div><div class='container'><center><div class='row'></center><div class='bg-faded p-4 my-4'><hr class='divider'><center>");
		
		pw.println("<strong><a href=welcome>Home</a></strong>\t\t");
		
		pw.println("<strong><a href=logout>Log out</a></strong><br /><br />");
		pw.println("  <script type=\"text/javascript\">"
			+" function AprroveTransaction(holdID, tranid, action) {"
			+"if (confirm(\"You Sure want to \"+action+\" the Transaction \")) {"
			+" var data = {\"holdID\" : holdID , \"tranid\" : tranid , \"action\" : action };"
			+"$.ajax({"
			+"type: \"POST\","
			+"url: \"moneytransferrequest\","
			+"data: data,"
			+"success: function(response) {"
			+ "alert('Transaction '+action+' successfully');"
			+"window.location = 'moneytransferrequest';"
			+" return false;"
			+ "}"
			+"});"
			+"} else {"
			+" return false;"
			+"}"
			+"return false; }</script>");
		
		pw.println("<h4>List of Transactions Needs to be approved :</h4><table style='width:100%; text-align:center;' border=2>");
		



		try{

			ResultSet rs =MySqlDataStoreUtilities.getAllholdTranscationRecord();
			if(rs.absolute(1))
			{
				ResultSetMetaData rm=rs.getMetaData();				
				int colcnt=rm.getColumnCount();
				pw.print("<thead><tr>");
				
				for(int i=1;i<=colcnt;i++){
					pw.print("<th>"+rm.getColumnName(i).toLowerCase()+"</th>");
					
						}//for
						pw.print("<th></th></tr></thead><tbody>");
						rs.first();
						do {
							pw.print("<tr>");
							
							for(int i=1;i<=colcnt;i++){

								String s = rs.getString(i);		
								pw.print("<td>"+s+"</td>");
								
								
							} //for
							if(rs.getString(8).equals("NotApproved")){
								//\""+rs.getString(1).,trim()+"\",\"Approved\"
								pw.print("<td><button onclick='return AprroveTransaction(\""+rs.getString(1).trim()+"\", \""+rs.getString(2).trim()+"\",\"Approved\")';>Approve</button><button onclick='return AprroveTransaction(\""+rs.getString(1).trim()+"\",\""+rs.getString(2).trim()+"\",\"DisApproved\")';>DisApprove</button></td>");
							} else{
								pw.print("<td>"+rs.getString(8)+"</td>");
							}
							pw.print("</tr>");
							
						} while(rs.next());//while
						pw.print("</tbody>");
						
			}//if
			
			else{
				pw.println("<h4>There are no Transactions happened in this account</h4>");
				pw.print("</table></div></div></div></div></div></div></center></center>");
			utility.printHtml("Footer.html");
			}
			

		}catch(Exception e){
			System.out.println(e);
		}


	}


	



}
