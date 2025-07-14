import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

@WebServlet("/Utilities")

/* 
	Utilities class contains class variables of type HttpServletRequest, PrintWriter,String and HttpSession.

	Utilities class has a constructor with  HttpServletRequest, PrintWriter variables.
	  
*/

	public class Utilities extends HttpServlet{
		HttpServletRequest req;
		PrintWriter pw;
		String url;
		HttpSession session; 
		public Utilities(HttpServletRequest req, PrintWriter pw) {
			this.req = req;
			this.pw = pw;
			this.url = this.getFullURL();
			this.session = req.getSession(true);
		}


		public void printHtml(String file) {
			String result = HtmlToString(file);
			String urlName = req.getServletPath().replace("/","");
			result = result + "<script type='text/javascript'>";
			if (file == "Header.html") {

				
				if (session.getAttribute("uname")!=null){
					
					// String username = session.getAttribute("uname").toString();
					// username = Character.toUpperCase(username.charAt(0)) + username.substring(1);
					result = result + "document.getElementById('customerlogin').childNodes[1].text = 'Logout'; ";
					result = result + "document.getElementById('customerlogin').childNodes[1].href = 'logout' ;";
					result = result + "c= document.getElementById('customerlogin').cloneNode(true) ;";
					result = result + "document.getElementById('navbarResponsive').childNodes[1].append(c) ;";
					result = result + "document.getElementById('customerlogin').childNodes[1].text = 'profile'; ";
					result = result + "document.getElementById('customerlogin').childNodes[1].href = 'welcome' ;";
					
				}
				result = result + "document.getElementById('navbarResponsive').getElementsByClassName('active')[0].classList.remove('active'); " ;
				result = result + "document.getElementById('"+urlName+"') ? document.getElementById('"+ urlName + "').classList.add('active') : '';  " ;
			}
			
			result = result + "</script>";
			pw.print(result);
		}
		/*  getFullURL Function - Reconstructs the URL user request  */

		public String getFullURL() {
			String scheme = req.getScheme();
			String serverName = req.getServerName();
			int serverPort = req.getServerPort();
			String contextPath = req.getContextPath();
			StringBuffer url = new StringBuffer();
			url.append(scheme).append("://").append(serverName);

			if ((serverPort != 80) && (serverPort != 443)) {
				url.append(":").append(serverPort);
			}
			url.append(contextPath);
			url.append("/");
			return url.toString();
		}


		/*  HtmlToString - Gets the Html file and Converts into String and returns the String.*/
		public String HtmlToString(String file) {
			String result = null;
			try {
				String webPage = url + file;
				URL url = new URL(webPage);
				URLConnection urlConnection = url.openConnection();
				InputStream is = urlConnection.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);

				int numCharsRead;
				char[] charArray = new char[1024];
				StringBuffer sb = new StringBuffer();
				while ((numCharsRead = isr.read(charArray)) > 0) {
					sb.append(charArray, 0, numCharsRead);
				}
				result = sb.toString();
			} 
			catch (Exception e) {
			}
			return result;
		} 


		public boolean isLoggedin(){
			if (session.getAttribute("uname")==null)
				return false;
			return true;
		}

	}

