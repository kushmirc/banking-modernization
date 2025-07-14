

<%@page import="java.io.File"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.net.URL"%>
<%@page import="java.io.FileReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="ISO-8859-1">
        <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>
        <script src="js/bootstrap.min.js"></script>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="css/style.css">
        <!-- <link rel="stylesheet" href="css/bootstrap.min.css"> -->
        <title>Banking System</title>
    </head>
    <body>
        <!-- <header>
            <nav class="navbar navbar-dark navbar-expand-lg sticky-top main-navbar">
                <a class="navbar-brand" href="bankhomepage.html">Bank</a>
                <button type="button" name="navbarButton" class="navbar-toggler" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <div class="navbar-nav">
                        <a class="nav-item active nav-link" href="#">Home
                            <span class="sr-only">(current)</span>
                        </a>
                        <a href=aboutus.html class="nav-item nav-link">About Us</a>
                        <a href=BankRate.jsp.html class="nav-item nav-link">Bank News & Deals</a>
                        <a href=careers.html class="nav-item nav-link">Careers</a>
                        <a href=contactus.html class="nav-item nav-link">Contact Us</a>
                        <a href=customerlogin.html class="nav-item nav-link">Login</a>
                    </div>

                </div>
            </nav>
        </header> -->
        <div class="tagline-upper text-center text-heading text-shadow text-white mt-5 d-none d-lg-block" id="top">Chicago Bank</div>
        
        <br>
        <div class="tagline-lower text-center text-expanded text-shadow text-uppercase text-white mb-5 d-none d-lg-block">10 West 35th Street | Chicago, IL | 60616</div>

        <!-- Navigation -->
        <nav class="navbar navbar-expand-lg navbar-light bg-faded py-lg-4">
            <div class="container">
                <a class="navbar-brand text-uppercase text-expanded font-weight-bold d-lg-none" href="#">Chicago Bank</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarResponsive">
                    <ul class="navbar-nav mx-auto">
                        <li class="nav-item active px-lg-4" id="Home">
                            <a class="nav-link text-uppercase text-expanded" href="Home" >Home
                                <span class="sr-only">(current)</span>
                            </a>
                        </li>
                        <li class="nav-item px-lg-4" id="aboutus">
                            <a class="nav-link text-uppercase text-expanded" href="aboutus">About</a>
                        </li>
                        <li class="nav-item px-lg-4" id="products">
                            <a class="nav-link text-uppercase text-expanded" href="BankRate.jsp">Bank News & Deals</a>
                        </li>
                        <li class="nav-item px-lg-4" id="contactus">
                            <a class="nav-link text-uppercase text-expanded" href="contactus">Contact</a>
                        </li>
                        <li class="nav-item px-lg-4" id="customerlogin">
                            <a class="nav-link text-uppercase text-expanded" href="customerlogin">Login</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>

    <%
    String jspPath = session.getServletContext().getRealPath("C:/tomcat/apache-tomcat-7.0.34/webapps/BankingSystem1");
    String txtFilePath ="C:/tomcat/apache-tomcat-7.0.34/webapps/BankingSystem1/BankRate.txt";
    BufferedReader reader = new BufferedReader(new FileReader(txtFilePath));
    StringBuilder sb = new StringBuilder();

    String line;
        sb.append("<main><div class=\"container\">");
    while((line = reader.readLine())!= null){
    //sb.append(line);
    
    int lastClose = line.lastIndexOf("...");
    int lastOpen = line.lastIndexOf(":", lastClose);
    String title=line.substring(0, lastOpen).trim() ;
    String desc= line.substring(lastOpen + 1, lastClose).trim() ;
    String link=line.substring(lastClose + 3).trim();

    sb.append("<div class=\"bg-faded p-4 my-4\" style='height: 350px;'><div class=\"card card-inverse\"><img class=\"card-img img-fluid w-100\" src=\"img/p1.jpg\" style='height:300px;' alt=><div class=\"card-img-overlay bg-overlay\"><h2 class=\"card-title text-shadow text-white text-uppercase mb-0\">");
    sb.append(title+"</h2><p class=\"lead card-text text-shadow text-white w-50 d-none d-lg-block\">");
    sb.append(desc+"</p><a href="+link+" class=\"btn btn-secondary\" target='_blank'>Read More</a></div></div></div>");  
}


out.println(sb.toString());
%>

<footer class="bg-faded text-center py-5">
    <div class="container">
        <a href="#top">Back to Top</a>
    </div>
    <div class="container">
        <p class="m-0">Copyright &copy; Banking System, 2019</p>
    </div>
</footer>

<!-- Bootstrap core JavaScript -->
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.bundle.min.js"></script>

</body>
</html>

</body>
</html>