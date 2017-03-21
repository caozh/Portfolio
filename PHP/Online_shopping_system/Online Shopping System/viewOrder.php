<?php
    $userID = "";
    $productID = "";
	$msg = "";
	$orderID = "";
	$date = "";
	
	$amount = "";
	$productArray = array();
	$quantityArray = array();
	
    checkPost();
    loadOrder();
	
	
    //check if user id and product id are passed to this page
    function checkPost() {
        global $userID,$productID,$amount;
        
        //if(!empty($_GET['uid'])) {
        if(isset($_GET['uid'])&& ($_GET['uid'])>0) {  
            $userID = $_GET['uid'];
            //echo 'User ID: '.$userID;
        } else {
            //Header("Location: http://cs.iupui.edu/~caozh/CSCI_45200/project/Fangbing/index.php");
        }
        if(isset($_GET['pid'])) {
            $productID = $_GET['pid'];
            //echo 'Product ID: '.$productID;
        } else {
        } 
		if(isset($_GET['amount'])) {
            $amount = $_GET['amount'];
            //echo 'Amount: '.$amount;
        } else {
			//echo 'No amount ';
        }    
    }	
    
    function loadOrder() {
        global $msg,$userID,$orderID,$date,$amount,$productArray,$quantityArray;
        
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
            //echo '<b>Database Connected</b><br>';
        }           
        
        //send a query to database to fetch data
        $sql = "SELECT * FROM ORDERS WHERE BuyerID = '$userID' AND Amount = '$amount'";      
        if ($result = $mysqli->query($sql)){
            //check if data is found
            $row_cnt = $result->num_rows;
            if ($row_cnt == 0){
                $msg = $msg.'<br><b>Nothing in the order!</b>';
            } else {
                //fetch associative array
                while ($row = $result->fetch_assoc()){
                    //load product name from PRODUCT table by product ID
					//echo 'Order ID: '.$row['ID'].'<br>Amount: '.$row['Amount'].'<br>Date: '.$row['Date'].'<br>Product ID: '.$row['ProductID'].'<br>Quantity: '.$row['Quantity'];
					$productArray = explode(',',$row['ProductID']);
					$quantityArray = explode(',',$row['Quantity']);
                    $orderID = $row['ID'];
                    $date = $row['Date'];
                }
            }
            $result->free();
        }
        $mysqli->close();     
		
		
    
	}
	
    //print order
	function printOrder () {
		global $msg,$userID,$orderID,$date,$amount,$productArray,$quantityArray;
		
		$servername = "localhost";
        $usernameDB = "admin";
        $passwordDB = "";
        $dbName = "test";
        
		echo 'Name: '.loadUserInfo();
		echo '<br>Order Number: '.$orderID;
        echo '<br>Date: '.$date;
        echo '<br>Amount: $'.$amount.'<br><br>';
        // Create connection
        $mysqli = new mysqli($servername, $usernameDB, $passwordDB, $dbName);
        // Check connection
        if ($mysqli->connect_error) {
            die("Connection failed: " . $mysqli->connect_error);
        } else {
            //echo '<b>Database Connected</b><br>';
        }           
        
        $counter = count($productArray);
        for ($i = 0; $i < $counter; $i++){
            //echo $i.' '.$productArray[$i].' '.$quantityArray[$i];
            //send a query to database to fetch data
            $sql = "SELECT * FROM PRODUCT WHERE ID = '$productArray[$i]'";      
            if ($result = $mysqli->query($sql)){
                //check if data is found
                $row_cnt = $result->num_rows;
                if ($row_cnt == 0){
                    $msg = $msg.'<br><b>Cannot find this product!</b>';
                } else {
                    //fetch associative array
                    while ($row = $result->fetch_assoc()){
                        //load product name from PRODUCT table by product ID
                        echo 'Product name: '.$row['Name'];
                        echo '<br>Price: '.$row['Price'];
                        echo '&nbsp;&nbsp;&nbsp;Quantity: '.$quantityArray[$i];
                        echo '<br>Subtotal: '.($quantityArray[$i]*$row['Price']);
                        
                        
                        
                        echo '<br><br>';
                        
                    }
                }
                $result->free();
            }
        }
        $mysqli->close();   
	}
    
    //load user information
    function loadUserInfo() {
        global $userID,$msg;
        
        $servername = "localhost";
        $usernameDB = "admin";
        $passwordDB = "";
        $dbName = "test";
        $userName = "";
        
        // Create connection
        $mysqli = new mysqli($servername, $usernameDB, $passwordDB, $dbName);
        // Check connection
        if ($mysqli->connect_error) {
            die("Connection failed: " . $mysqli->connect_error);
        } else {
            //echo '<b>load product Connected</b><br>';
        }           
        
        //send a query to database to fetch data
        $sql = "SELECT * FROM login WHERE ID = '$userID'";      
        if ($result = $mysqli->query($sql)){
            //check if data is found
            $row_cnt = $result->num_rows;
            if ($row_cnt == 0){
                $msg = $msg. '<br><b>User ID is not registered!</b>';
            } else {
                //fetch associative array
                $row = $result->fetch_assoc();
                $userName = $row['FirstName'].' '.$row['LastName'];
                return $userName;
            }
            $result->free();
        }
        $mysqli->close();             
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

  </head>
  
  
  <body>
   
    <div class="header-area">
        <div class="container">
            <div class="row">
                <div class="col-md-8">
                    <div class="user-menu">
                        <ul>
                            <li><a href=""> </a></li>
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
                        <h1><a href="#">online shopping system</a></h1>
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
                        <li><a href="shop.php?uid=<?php echo $userID.'&pid='.$productID;?>">Shop page</a></li>
                        <li><a href="cart.php?uid=<?php                echo $userID.'&pid='.$productID;?>">Cart</a></li>
                        <li><a href="#">Contact</a></li>
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
                        <h2>Order Receipt</h2>

                    </div>
                </div>
            </div>
        </div>
    </div>
    
    
    <div class="single-product-area">
        <div class="zigzag-bottom"></div>
        <div class="container">
            <div class="row">

            </div>
            <div class="row">

                <div class="col-md-12">
                    <div class="product-pagination text-center">
                           <b>
<?php
    echo $msg;
    printOrder();
?>
                            </b>     
                    </div>
                </div>
            </div>
        </div>
    </div>


    <div class="footer-top-area">
        <div class="zigzag-bottom"></div>
        <div class="container">
            <div class="row">
                <div class="col-md-3 col-sm-6">
                    <div class="footer-about-us">
                        <h2>e<span>Electronics</span></h2>
                        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Perferendis sunt id doloribus vero quam laborum quas alias dolores blanditiis iusto consequatur, modi aliquid eveniet eligendi iure eaque ipsam iste, pariatur omnis sint! Suscipit, debitis, quisquam. Laborum commodi veritatis magni at?</p>
                        <div class="footer-social">
                            <a href="#" target="_blank"><i class="fa fa-facebook"></i></a>
                            <a href="#" target="_blank"><i class="fa fa-twitter"></i></a>
                            <a href="#" target="_blank"><i class="fa fa-youtube"></i></a>
                            <a href="#" target="_blank"><i class="fa fa-linkedin"></i></a>
                            <a href="#" target="_blank"><i class="fa fa-pinterest"></i></a>
                        </div>
                    </div>
                </div>
                
                <div class="col-md-3 col-sm-6">
                    <div class="footer-menu">
                        <h2 class="footer-wid-title">User Navigation </h2>
                        <ul>
                            <li><a href="">Vendor contact</a></li>
                            <li><a href="index.php">Front page</a></li>
                        </ul>                        
                    </div>
                </div>
                
                <div class="col-md-3 col-sm-6">
                    <div class="footer-menu">
                        <h2 class="footer-wid-title">Categories</h2>
                        <ul>
                            <li><a href="">Mobile Phone</a></li>
                            <li><a href="">Home accesseries</a></li>
                            <li><a href="">LED TV</a></li>
                            <li><a href="">Computer</a></li>
                            <li><a href="">Gadets</a></li>
                        </ul>                        
                    </div>
                </div>
                
                <div class="col-md-3 col-sm-6">
                    <div class="footer-newsletter">
                        <h2 class="footer-wid-title">Newsletter</h2>
                        <p>Sign up to our newsletter and get exclusive deals you wont find anywhere else straight to your inbox!</p>
                        <div class="newsletter-form">
                            <input type="email" placeholder="Type your email">
                            <input type="submit" value="Subscribe">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="footer-bottom-area">
        <div class="container">
            <div class="row">
                <div class="col-md-8">
                    <div class="copyright">
                        <p>&copy; 2015 eElectronics. All Rights Reserved. Coded with <i class="fa fa-heart"></i> by <a href="http://wpexpand.com" target="_blank">WP Expand</a></p>
                    </div>
                </div>
                
                <div class="col-md-4">
                    <div class="footer-card-icon">
                        <i class="fa fa-cc-discover"></i>
                        <i class="fa fa-cc-mastercard"></i>
                        <i class="fa fa-cc-paypal"></i>
                        <i class="fa fa-cc-visa"></i>
                    </div>
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