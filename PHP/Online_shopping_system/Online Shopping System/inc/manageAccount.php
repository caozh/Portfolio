<?php
    $msg = "";
    $userID = "";
    $productID = "";

    //run functions
    checkPost();
  
    
    //check if user id and product id are passed to this page
    function checkPost() {
        global $userID,$productID,$getAmount;
        
        //if(!empty($_GET['uid'])) {
        if(isset($_GET['uid'])) {    
            $userID = $_GET['uid'];
            //echo 'User ID: '.$userID;
        } else {
            echo 'You should login or append "?uid=1" to header.';
            Header("Location: http://cs.iupui.edu/~caozh/CSCI_45200/project/Fangbing/index.php");
        }
        
    }    
    

    //load user information
    function loadUserInfo() {
     $msg = "";
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
            $msg = $msg.'<br/><b>Database Connected</b>';
        }           
        
        $sql = "SELECT * FROM login";
        if ($result = $mysqli->query($sql)) {
            //fetch associative array
            while ($row = $result->fetch_assoc()){
            	if($row['Membership'] != 'admin')
            	{
            		$id = $row['ID'];
                echo '
							<h3>User ID:'.$row['ID'].'</h3>
								                              
												First Name: 
                                                <input type="text" value="'.$row['FirstName'].'" placeholder="" id="firstname" name="firstname" class="input-text ">
                                         
                                              Last Name:
                                                <input type="text" value="'.$row['LastName'].'" placeholder="" id="lastname" name="lastname" class="input-text ">
                                        
                                			  Address 1:
                                                <input type="text" value="'.$row['Address1'].'" placeholder="Street address" id="address1" name="address1" class="input-text ">
                                              Address 2:
                                                <input type="text" value="'.$row['Address2'].'" placeholder="" id="address2" name="address2" class="input-text ">
                            					
                                            </br></br>
                        
                                          Email Address:
                                                <input type="text" value="'.$row['Email'].'" placeholder="" id="email" name="email" class="input-text ">
                                           Password :
                                                <input type="text" value="'.$row['Password'].'" placeholder="" id="password" name="password" class="input-text ">
                                            Phone:
                                                <input type="text" value="'.$row['Phone'].'" placeholder="" id="phone" name="password" class="input-text ">
                                             Postcode :
                                                <input type="text" value="'.$row['PostCode'].'" placeholder="" id="postcode" name="postcode" class="input-text ">
                                                </br></br>
                                            
                                           County:
                                                <input type="text" id="state" name="state" placeholder="State / County" value="'.$row['State'].'" class="input-text ">
											city:
                                                <input type="text" value="'.$row['City'].'" placeholder="Town / City" id="city" name="city" class="input-text ">
                </br>
               <a href="accountEdit.php?uid='.$row['ID'].' "> <h3>Edit</h3></a>
                </br> </br></br> </br>';
                
                
                }
                
            }
            $result->free();
        }
        $mysqli->close();                  
    }


    
           
?>
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
                <div class="col-md-4">
                    <div class="header-right">
                        <ul class="list-unstyled list-inline">
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div> <!-- End header area -->
    
    <div class="site-branding-area">
        <div class="container">
            <div class="row">
                <div class="col-sm-6">
                    <div class="logo">
                        <h1><a href="index.php"><span>Online shopping system  </span></a></h1>
                    </div>
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
                        <li><a href="shop.php?uid=<?php                echo $userID.'&pid='.$productID;?>">Shop page</a></li>
                        <li><a href="contact.php?uid=<?php             echo $userID.'&pid='.$productID;?>">Contact</a></li>
						<li><a href="index.php">LogOut</a></li>
                    </ul>
                </div>  
            </div>
        </div>
    </div> <!-- End mainmenu area -->
    
    <div class="product-big-title-area">
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <div class="product-bit-title text-center">
                        <h2>Account Management</h2>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    
    <div class="single-product-area">
        <div class="zigzag-bottom"></div>
        <div class="container">
            <div class="row">
                
            

	<br/>  <br/><br/>
                                          	<form enctype="multipart/form-data" action="" method="post">
                          
<?php 
    print $msg;

    loadUserInfo();
    $msg = "";
?>	
									
                                            	<input name="enter" class="btn" type="submit" value="Submit" />
                                			</form>


                                    

                                  
                </div>
            </div>
        </div>
    </div>

   
    <!-- Latest jQuery form server -->
    <script src="https://code.jquery.com/jquery.min.js"></script>
    
    <!-- Bootstrap JS form CDN -->
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    
    <!-- jQuery sticky menu -->
    <script src="js/owl.carousel.min.js"></script>
    <script src="js/jquery.sticky.js"></script>
    
    <!-- jQuery easing -->
    <script src="js/jquery.easing.1.3.min.js"></script>
    
    <!-- Main Script -->
    <script src="js/main.js"></script>
  </body>
</html>