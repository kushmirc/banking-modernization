import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.text.*;
 
public class BankRate extends HttpServlet{
    public static void readAndDisplayFile(HttpServletRequest req, HttpServletResponse res,ServletContext context) throws ServletException, IOException {
        //get Output Stream
        PrintWriter out = res.getWriter();
        //set content type for writing the output
        res.setContentType("text/html");
        out.println("<html><head></head><body>");
        try {
/* String actualPath = context.getRealPath(virtualPath);
System.out.println(actualPath); */
            File file = new File("C:\\tommy\\tomcat-7.0.34-preconfigured\\apache-tomcat-7.0.34\\webapps\\BankingSystem\\BankRate.txt");
			/* BufferedReader reader = new BufferedReader(new FileReader (new File("C:\\tommy\\tomcat-7.0.34-preconfigured\\apache-tomcat-7.0.34\\webapps\\SmartPortables\\DealMatches.txt"))); */
            FileReader fileReader = new FileReader(file);
            BufferedReader buffReader = new BufferedReader(fileReader);
            String buffer = new String();
            while( (buffer = buffReader.readLine() ) != null)
                out.println(buffer);
            buffReader.close();
        } catch(IOException e){
            out.println("Error reading file: " + e.getMessage());
        }
        out.println("</body></html>");
        out.close();
    }
}