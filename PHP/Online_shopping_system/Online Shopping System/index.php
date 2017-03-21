	<?php 
	require_once "inc/util.php";
	require_once "mail/mail.class.php";
	$msg = "";
    $alert = "";
    
    if (isset($_POST['enter']))
    {
        //always initialized variables to be used
        $uname = "";
        $pwd = "";
        $unameok = false;

        //take the information submitted and verify inputs
        $uname =  trim($_POST['userName']);
        $pwd = trim($_POST['pwd']);		

        //check the form of email
        if (!spamcheck($uname))							
            $msg = $msg . '<b>Email is not valid.</b>';
        else $unameok = true;

        //check whether the password is empty
        if ( $pwd=="" ){
			$msg = $msg.'<br/><b>Please enter your password.</b>';
			$unameok = false;
		}
		//check if the username is over max length
		if ((strlen($uname) > 39) or (strlen($pwd) > 29)){
			$msg = $msg.'<br/><b>Your account or password is too long.</b>';
			$unameok = false;
		}
		if(strlen($pwd) < 4 and ($pwd != "")){
			$msg = 	$msg.'<br/><b>Your password is too short.<b/>';
			$unameok = false;
		}

		
        //if you login successfully, your username will be delivered to another page called "My account"
        if ($unameok) { 										
            $alert;
            $servername = "localhost";
            $usernameDB = "admin";
            $passwordDB = "";
            $dbName = "test";
            // Create connection
            $mysqli = new mysqli($servername, $usernameDB, $passwordDB, $dbName);
            // Check connection
            if ($mysqli->connect_error) {
                die("Connection failed: " . $mysqli->connect_error);
            } else {
                //echo "Database Connected<br>";
            }
            
            $sql = "SELECT * FROM login WHERE Email = '$uname' AND Password = '$pwd'";
            
            //mysqli_num_rows($result) == 0
            if ($result = $mysqli->query($sql)) {
                $row_cnt = $result->num_rows;
                if ($row_cnt == 0) {
                    $msg = $msg.'<br/><b>Username or Password is wrong!</b>';
                }
                else {
                    $row = $result->fetch_assoc();
                    if($row['Membership'] == "seller"){
                        Header("Location: http://cs.iupui.edu/~caozh/CSCI_45200/project/Fangbing/sellerPage.php?uid=".$row['ID']);
                        die();
                    } elseif ($row['Membership'] == "buyer"){
                        
                        Header("Location: http://cs.iupui.edu/~caozh/CSCI_45200/project/Fangbing/shop.php?uid=".$row['ID']);
                        $msg = $msg . '<br/><b>buyer.</b>';
                    } else if ($row['Membership'] == "admin"){
                        Header("Location: http://cs.iupui.edu/~caozh/CSCI_45200/project/Fangbing/adminHome.php?uid=".$row['ID']);
                        $msg = $msg . '<br/><b>buyer.</b>';
                    } elseif ($row['Membership'] == "administrator"){
                        $msg = $msg . '<br/><b>administrator.</b>';
                    } else $msg = $msg . '<br/><b>Membership error.</b>';
                }
            }else {
                echo "Error: " . $sql . "<br>" . $mysqli->error;
            } //end if 
            $uname="";
            $pwd="";
            $mysqli->close(); 
        }
    }

    if( isset( $_POST['forget'] ) )
    {
        $uname = "";
        $unameok = false;			
        //take the information submitted and verify inputs
        $uname =  trim($_POST['userName']);
		$msg = "";
        //check the form of email
        if (!spamcheck($uname))	{
            echo 'alery(message successfully sent)';
            $msg = $msg . '<br/><b>Email is not valid.</b>';
        }
        else $unameok = true;
        
        if ($unameok) 
        {										
            $code = randomCodeGenerator(8);
            $subject = "Email Activation";						
            $body = 'Your password is '.$code;
            $mailer = new Mail();
            //if your email is correct, you will receive a email for your new password
            if (($mailer->sendMail($uname, 'customer', $subject, $body))==true)
            {
            	    $uname =  trim($_POST['userName']);
        		$servername = "localhost";
       		 	$usernameDB = "admin";
        		$passwordDB = "";
        		$dbName = "test";
          		$mysqli = new mysqli($servername, $usernameDB, $passwordDB, $dbName);
    	 		$sql = "UPDATE login SET Password = '$code' WHERE Email = '$uname' ";
           		 $mysqli->query($sql);  	
            	$msg = "<b>please check your email for the new password.</b>";
            }
            else $msg = "Email not sent. ";
        }
    }
    
    function phpAlert($alert) {
        echo '<script type="text/javascript">alert("' . $alert . '")</script>';
    }
?>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>eElectronics - HTML eCommerce Template</title>
    
    <!-- Google Fonts -->
    <link href='http://fonts.googleapis.com/css?family=Titillium+Web:400,200,300,700,600' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Roboto+Condensed:400,700,300' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Raleway:400,100' rel='stylesheet' type='text/css'>
    
    <!-- Bootstrap -->
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
    
    <!-- Font Awesome -->
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
    
    <!-- Custom CSS -->
    <link rel="stylesheet" href="css/owl.carousel.css">
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="css/responsive.css">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
   
    <div class="header-area">
        <div class="container">
            <div class="row">
                <div class="col-md-8">
                    <div class="user-menu">
                        <ul>
						<!-- leaving here so can be updated later if needed -->
                        </ul>
                    </div>
                </div>
                    <div class="header-right">
                        <ul class="list-unstyled list-inline">
                            <li class="dropdown dropdown-small">
                                <a data-toggle="dropdown" data-hover="dropdown" class="dropdown-toggle" href="#"><span class="key">currency :</span><span class="value">USD </span><b class="caret"></b></a>
                                <ul class="dropdown-menu">
                                    <li><a href="#">USD</a></li>
                                    <li><a href="#">INR</a></li>
                                    <li><a href="#">GBP</a></li>
                                </ul>
                            </li>

                            <li class="dropdown dropdown-small">
                                <a data-toggle="dropdown" data-hover="dropdown" class="dropdown-toggle" href="#"><span class="key">language :</span><span class="value">English </span><b class="caret"></b></a>
                                <ul class="dropdown-menu">
                                    <li><a href="#">English</a></li>
                                    <li><a href="#">French</a></li>
                                    <li><a href="#">German</a></li>
                                </ul>
                            </li>
                        </ul>
                    </div>                

            </div>
        </div>
    </div> <!-- End header area -->
    
    <div class="site-branding-area">
        <div class="container">
            <div class="row">
                <div class="col-sm-6">
                    <div class="logo">
                        <h1><a href="#"><span>Online Shopping System</span></a></h1>
                    </div>
                </div>
                
                <div class="col-sm-6">
                </div>
            </div>
        </div>
    </div> <!-- End site branding area -->
    
    <div class="mainmenu-area">
        <div class="container">
            <div class="row">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                </div> 
                <div class="navbar-collapse collapse">
                    <ul class="nav navbar-nav">
                        <li class="active"><a href="index.php">Home</a></li>
                        <li><a href="contact.php">Contact</a></li>
                    </ul>
                </div>  
            </div>
        </div>
    </div> <!-- End mainmenu area -->
    
    <div class="slider-area">
        <div class="zigzag-bottom"></div>
        <div id="slide-list" class="carousel carousel-fade slide" data-ride="carousel">
            
            <div class="slide-bulletz">
                <div class="container">
                    <div class="row">
                        <div class="col-md-12">
                            <ol class="carousel-indicators slide-indicators">
                                <li data-target="#slide-list" data-slide-to="0" class="active"></li>
                                <li data-target="#slide-list" data-slide-to="1"></li>
                                <li data-target="#slide-list" data-slide-to="2"></li>
                            </ol>                            
                        </div>
                    </div>
                </div>
            </div>

           
     <div class="slider-area">
        <div class="zigzag-bottom"></div>
        <div id="slide-list" class="carousel carousel-fade slide" data-ride="carousel">
            
            <div class="slide-bulletz">
                <div class="container">
                    <div class="row">
                        <div class="col-md-12">
                            <ol class="carousel-indicators slide-indicators">
                                <li data-target="#slide-list" data-slide-to="0" class="active"></li>
                                <li data-target="#slide-list" data-slide-to="1"></li>
                                <li data-target="#slide-list" data-slide-to="2"></li>
                            </ol>                            
                        </div>
                    </div>
                </div>
            </div>

            <div class="carousel-inner" role="listbox">
                <div class="item active">
                    <div class="single-slide">
                        <div class="slide-bg slide-one"></div>
                        <div class="slide-text-wrapper">
                            <div class="slide-text">
                                <div class="container">
                                    <div class="row">
                                        <div class="col-md-6 col-md-offset-6">
                                            <div class="slide-content">     
                                               <form action="index.php" method="post">
												<h3>Login</h3>
<?php
	print $msg;
	$msg = "";
?>
													<br>
													Email : <br><input type="email" name="userName" id="userName" size="35" autofocus /> <br>
													Password : <br><input type="password" name="pwd" id="pwd" width="200"  size="35" /> <br><br>
													<input name="enter" class="btn" type="submit" value="Submit" />
													<input name="forget" class="btn" type="submit" value="forget password" /> 
													<br>
													<a href="register.php"> Register an account!</a>
												</form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
               
            </div>

        </div>        
    </div> <!-- End slider area -->
    



    
 
 
  </body>
</html>