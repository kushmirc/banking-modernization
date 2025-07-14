import java.io.*;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Cookie;
import java.sql.*;


@WebServlet("/welcome")

/* 
	Home class uses the printHtml Function of Utilities class and prints the Header,LeftNavigationBar,
	Content,Footer of Game Speed Application.

*/

	public class WelcomeServlet extends HttpServlet {
		private static final long serialVersionUID = 1L;




		protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


			HttpSession hs=request.getSession();

			String un=(String)hs.getAttribute("uname");
			String usertype=(String)hs.getAttribute("usertype");

			try{
			// PreparedStatement pstmt=con.prepareStatement("select * from customer where userid=? and pword=?");

			// if( ! un.equals(null) && ! pwd.equals(null))
			// {
			// 	pstmt.setString(1, un);
			// 	pstmt.setString(2, pwd);
			// }


				PrintWriter pw=response.getWriter();
				ResultSet rs= MySqlDataStoreUtilities.getUserData(un, usertype);

				Utilities utility = new Utilities(request, pw);
				if(!utility.isLoggedin()){
					HttpSession session = request.getSession(true);				
					session.setAttribute("login_msg", "Please Login to use functions");
					response.sendRedirect("customerlogin");
					return;
				}

				if(rs !=null){


					rs.next();

					response.setContentType("text/html");

					Cookie c1=new Cookie("lname",rs.getString(2));
					response.addCookie(c1);
					utility.printHtml("Header.html");
					pw.println("<div class='bg-faded p-4 my-4'><hr class='divider'><center>");
					pw.println("<strong><a href=changecredentials.html>Change User Credentials</a></strong>&nbsp;&nbsp;&nbsp;&nbsp;");
					pw.println("<strong><a href=logout>Log out</a></strong> <br />");


					pw.println("<hr />");
					pw.println("<html> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<h4>Welcome <em>"+rs.getString(2)+"</em></h4>");

					if(usertype.equals("customer")){

						hs.setAttribute("AccountNoC",rs.getString(6));
						hs.setAttribute("bal", rs.getString(8));

						Cookie c2=new Cookie("AccountNoC",rs.getString(6));
						Cookie c3=new Cookie("bal",rs.getString(8));


						response.addCookie(c2);
						response.addCookie(c3);

						pw.println("Account Number  : &nbsp;<strong>"+rs.getString(6)+"</strong>");
						pw.println("<br />Your current Balance  : &nbsp;<strong> USD &nbsp; "+rs.getString(8)+"</strong>");



						pw.println("<h3>Menu</h3>");
						pw.println("<a href=FTWithin.html>Funds Transfer With in Bank</a>");
						pw.println("<br />  <a href=Ftother.html>Funds Transfer to Other Bank</a>");
						pw.println("<br /> <a href=complaint.html>Register a Complaint</a>");
						pw.println("<br /> <a href=viewcomplaint>View Complaints' status</a>");
						pw.println("<br /> <a href=viewtransactions>View Transactions</a>");
						pw.println("<br /> <a href=changecredentials.html >Change User Credentials</a>");
						pw.println("<br /> <a href=logout>Log out</a>");	

					} else if(usertype.equals("banker")){

						pw.println("<h3>Menu</h3>");
						pw.println(" <a href=createCustomer>Create New customer</a>");
						pw.println("<br /> <a href=updateCustomer>Update a Customer</a>");
						pw.println("<br /> <a href=deleteCustomer>Delete a Customer</a>");
						pw.println("<br /><a href=complaint.html>Register a Complaint</a>");
						pw.println("<br /><a href=viewcomplaint>View Complaints' status</a>");
						pw.println("<br /><a href=addtransactions>Add Customer Transactions</a>");
						pw.println("<br /><a href=viewalltransactions>View all customer Transactions</a>");
						pw.println("<br /><a href=moneytransferrequest>Money Transfer Request</a>");
						pw.println("<br /><a href=changecredentials.html >Change User Credentials</a>");
						pw.println("<br /><a href=logout>Log out</a>");	
						pw.println("</div></center>");

					} else{
			// 			pw.print(	+"  <script type=\"text/javascript\">"
			// +" function changeStatus(id,this) {"
			// // +"if (confirm(\"You Sure want to delete user: \" + theForm.actno.value)) {"
			// +" var data = {\"COMPLAINT_NO\" : id , value};"
			// +"$.ajax({"
			// +"type: \"POST\","
			// +"url: \"deleteCustomer\","
			// +"data: data,"
			// +"success: function(response) {"
   // 			+ "alert('User is deleted successfully');"
   // 			+"window.location = 'welcome';"
   // 			+" return false;"
   // 			+ "}"
			// +"});"
			// // +"} else {"
			// // +" return false;"
			// // +"}"
			// +"return false; }</script>");
						pw.println("<h4>List of Complaints :</h4><table style='width:100%; text-align:center;' border=2>");
						ResultSet r = MySqlDataStoreUtilities.getComplaints();
						pw.print("<thead><tr>");
						if(r.absolute(1))
						{
							ResultSetMetaData rm=r.getMetaData();
							int colcnt=rm.getColumnCount();
							for(int i=1;i<=colcnt;i++){
								if (i==8){
									continue;
								}
								pw.print("<th>"+rm.getColumnName(i).toLowerCase()+"</th>");
							}
							pw.print("</tr></thead><tbody>");
							r.first();
							String id;
							do {
								id="";
								pw.print("<tr>");
								for(int i=1;i<=colcnt;i++){
									if (i==8){
										continue;
									}
									String s = r.getString(i);
									if(i==1){
										id = s;
									}
									if(i==6 || i==7){
										if(i==6){
											s = "<select onchange='changeStatus("+id+",this);'><option value='Open'>Open</option><option value='Ongoing'>Ongoing</option><option value='Closed'>Closed</option></select>";
										} else{
											s = "<select onchange='changeClosed("+id+",this);'><option value='True'>True</option><option value='False'>False</option></select>";
										}
										
									} 
									pw.print("<td>"+s+"</td>");
									
								}
								pw.print("</tr>");
							} while(r.next());
							pw.print("</tbody></table>");
						} else{
							pw.println("<h4>There are no Complaints</h4>");
							pw.print("</table></div></div></div></div></div></div></center></center>");
							utility.printHtml("Footer.html");
						}
					}


					utility.printHtml("Footer.html");

				} else{
					HttpSession session = request.getSession(true);				
					session.setAttribute("login_msg", "No User found");
					response.sendRedirect("customerlogin");
					return;
				}


			}
			catch(Exception e){
				System.err.println(e);
			}

		}

	}
