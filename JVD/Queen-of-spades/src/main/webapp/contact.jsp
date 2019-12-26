<!DOCTYPE html>

<html>
<head>
    <meta charset="utf-8">
    <title>Queen Of Spades</title>
    
    <script src="http://cdn.jquerytools.org/1.2.7/full/jquery.tools.min.js"></script>
    

     <style><%@include file='/css/reset.css' %>
               <%@include file='/css/style.css' %>

            </style>
        
    <link href='http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,400,600,700&subset=latin,latin-ext' rel='stylesheet' type='text/css'>

</head>

<body>
    
    <!-- *********  Header  ********** -->
    
     <div id="header">
            <div id="header_in">

            <h1><a href="index.jsp"><b>Queen Of Spades</b> </a></h1>

            <div id="menu">
             <ul>
                <li><a href="index.jsp">Home</a></li>

                <li><a href="blog.jsp">Blog</a></li>
                <li><a href="contact.jsp"class="active">Contact</a></li>
             </ul>
            </div>

            </div>
        </div>
    
    <!-- *********  Main part – headline ********** -->
    
        
          <div id="main_part_inner">
                  <div id="main_part_inner_in">

                  <h2>Blog</h2>

                  <div class="button_main">
                      <a href="\play.jsp" class="button_dark">PLAY NOW</a>
                  </div>

                  </div>

              </div>
        
        
        <!-- *********  Content  ********** -->
        
        <div id="content_inner">
            
            <!-- *** contact form *** -->
            
           <h3>Leave a message</h3>
           
           <form action="#" method="post" class="formit">
                <input type="text" name="name" placeholder="YOUR NAME"/>
                <input type="text" name="email" placeholder="EMAIL"/>
                <input type="text" name="url" placeholder="WEBSITE (OPTIONAL)"/>
                <textarea name="message" placeholder="Leave your message here..."></textarea>
                <input type="submit" class="button_submit" value="SEND A MESSAGE">
            </form>

            <div class="cara"></div>
            
            <h3>Contact information</h3>
            
            <div class="contactinfo">
                <span class="ico_mapmark"><b>address</b></span>
            </div>
            
            <div class="contactinfo">
                <span class="ico_message"><b>email@att.com</b></span>
            </div>
            
            <div class="contactinfo">
                <span class="ico_iphone"><b>(+123) 456 789 012</b></span>    
            </div>
            
            <hr class="cleanit">

        </div>
        
    
    
    <!-- *********  Footer  ********** -->
    
    <hr class="cleanit">
    
     <div id="footer">
        <div id="footer_in">
            
           <p>standard policy, I'm not good at it</p>
           <span>Author: Egor Krasovski and despair</span>

        
        </div>
    </div>
</body>
</html>
