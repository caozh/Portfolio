<?php
    $msg = "";
    $userID = "";
    $productID = "";
    $quantity = "";
    $productCounter = "";
    $amount = "0";
    $productQuantity = array();
    
    checkPost();
    checkCart();
    cartIcon();
    
    //check if add to cart button be clicked
    function checkCart() {    
        global $quantity;
        if (isset($_GET['quantity'])) { 
            $quantity = $_GET['quantity'];
            insertDB();
        }
    }

    //Load cart icon data from database
    function cartIcon() {
        global $userID,$msg,$amount,$productCounter,$productQuantity;
        
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
            //echo '<b>load product Connected</b><br>';
        }           
        
        //send a query to database to fetch data
        $sql = "SELECT * FROM CART WHERE UserID = '$userID'";      
        if ($result = $mysqli->query($sql)){
            //check if data is found
            $row_cnt = $result->num_rows;
            if ($row_cnt == 0){
                //echo '<br><b>Nothing in the cart!</b>';
            } else {
                //fetch associative array
                
                while ($row = $result->fetch_assoc()){
                    //load product name from PRODUCT table by product ID
                    $productPrice = loadProductPrice($row['ProductID']);
                    array_push($productQuantity,$row['ProductID']);
                    //associative array
                    $productQuantity[$row['ProductID']] = $row['Quantity'];
                    $amount = $amount + $productPrice*$row['Quantity'];
                    $productCounter= $productCounter + $row['Quantity'];
                }
            }
            $result->free();
        }
        $mysqli->close();        
    }    
    
    //check if user id and product id are passed to this page
    function checkPost() {
        global $userID,$productID;
        
        //if(!empty($_GET['uid'])) {
        if(isset($_GET['uid'])&& ($_GET['uid'])>0) {  
            $userID = $_GET['uid'];
            //echo 'User ID: '.$userID;
        } else {
            Header("Location: http://cs.iupui.edu/~caozh/CSCI_45200/project/Fangbing/index.php");
        }
        if(isset($_GET['pid'])) {
            $productID = $_GET['pid'];
            //echo 'Product ID: '.$productID;
        } else {
            Header("Location: http://cs.iupui.edu/~caozh/CSCI_45200/project/Fangbing/shop.php?uid=".$userID);
            //echo '  You should select a product or append "&pid=1" to header.';
        }    
    }	
    
    //Load product data from database
    function loadProduct() {
        global $userID,$productID,$msg,$productCounter,$productQuantity;
        
        $servername = "localhost";
        $usernameDB = "admin";
        $passwordDB = "";
        $dbName = "test";
        
        
        //print_r($productQuantity);
        
        // Create connection
        $mysqli = new mysqli($servername, $usernameDB, $passwordDB, $dbName);
        // Check connection
        if ($mysqli->connect_error) {
            die("Connection failed: " . $mysqli->connect_error);
        } else {
            //echo '<br><b>load product Connected</b>';
        }           
        
        //send a query to database to fetch data
        $sql = "SELECT * FROM PRODUCT WHERE ID = '$productID'";      
        if ($result = $mysqli->query($sql)){
            //check if data is found
            $row_cnt = $result->num_rows;
            if ($row_cnt == 0){
                echo '<br><b>You should select a product!<br>Please go back to Shop Page</b>';
            } else {
            //fetch associative array
            $row = $result->fetch_assoc();
			//echo '<br><b>Find product!</b>'.$row['ImagePath'];
            if (empty($productQuantity[$productID])){
                //echo 'it is empty';
                $productQuantity[$productID] = 0;
            } else {
                //echo 'not empty';
            }
            echo '
                        <div class="product-breadcroumb">
						
                        </div>
                        
                        <div class="row">
                            <div class="col-sm-6">
                                <div class="product-images">
                                    <div class="product-main-img">
                                        <img src="'.$row['ImagePath'].'" alt="">
                                    </div>
                                </div>
                            </div>
                            
                            <div class="col-sm-6">
                                <div class="product-inner">
                                    <h2 class="product-name">'.$row['Name'].'</h2>		
                                    <div class="product-inner-price">
                                        <ins>$'.$row['Price'].'.00</ins> <del>$'.($row['Price']+500).'.00</del>
                                    </div>    
                                    
                                    <form action="" class="cart">
                                        <div class="quantity">
                                            <input type="hidden" name="uid" value="'.$userID.'">
                                            <input type="hidden" name="pid" value="'.$productID.'">
                                            <input type="number" size="4" class="input-text qty text" title="Qty" value="1" name="quantity" min="0" max="'.($row['Quantity']-$productQuantity[$productID]).'" step="1" onkeypress="return isNumberKey(event);"> 
                                        </div>
                                        <button class="add_to_cart_button" type="submit">Add to cart</button>
                                        <b>'.$msg.'</b>
                                    </form>   
                                    
                                    <div class="product-inner-category">
                                        <p>Category: <a href="">Summer</a>. Tags: <a href="">awesome</a>, <a href="">best</a>, <a href="">sale</a>, <a href="">shoes</a>. </p>
                                    </div> 
                                    
                                    <div role="tabpanel">
                                        <ul class="product-tab" role="tablist">
                                            <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">Description</a></li>
                                            <li role="presentation"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">Reviews</a></li>
                                        </ul>
                                        <div class="tab-content">
                                            <div role="tabpanel" class="tab-pane fade in active" id="home">
                                                <h2>Product Description</h2>  
                                                <p>'.$row['Description'].'</p>
                                            </div>';
            }
            $result->free();
        }
        $mysqli->close();        
    }
    
    //save cart product into database
    function insertDB () {
        global $userID,$productID,$quantity,$msg;
        
        $servername = "localhost";
        $usernameDB = "admin";
        $passwordDb = "";
        $dbName = "test";
		$numberOfThisItemAlreadyInCart = 0;
        
        // Create connection
        $mysqli = new mysqli($servername, $usernameDB, $passwordDb, $dbName);
        // Check connection
        if ($mysqli->connect_error) {
            die("Connection failed: " . $mysqli->connect_error);
        } else {
            //echo '<br><b>Database Connected</b>';
        }   
        //send a query to database to fetch data
        $sql = "SELECT * FROM CART WHERE UserID = '$userID'";      
        if ($result = $mysqli->query($sql)){
            //check if data is found
            $row_cnt = $result->num_rows;
            if ($row_cnt == 0){
                //echo '<br><b>Nothing in the cart!</b>';
            } else {
                //fetch associative array
                
                while ($row = $result->fetch_assoc()){
                    //load product name from PRODUCT table by product ID
					if($row['ProductID'] == $productID){
						$numberOfThisItemAlreadyInCart = ($numberOfThisItemAlreadyInCart + $row['Quantity']);
					}
                }
            }
            $result->free();
        }
		if($numberOfThisItemAlreadyInCart > 0){
			$sql = "UPDATE CART 
					SET Quantity = ($numberOfThisItemAlreadyInCart +  $quantity)
                    WHERE UserID = '$userID' AND  ProductID = '$productID'";
		}
		else{
			$sql = "INSERT INTO CART ( UserID,   ProductID,   Quantity)
                          VALUES ('$userID','$productID','$quantity')";
		}
        //insert data into database
        if ($mysqli->query($sql) === TRUE) {
            $msg = '<br><b>Add to cart successfully</b>';
        } else {
            echo "Error: " . $sql . "<br>" . $mysqli->error;
        }  
        $mysqli->close();            
    }

    function loadProductName($productID) {
        
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
            //echo '<b>load product Connected</b><br>';
        }           
        
        //send a query to database to fetch data
        $sql = "SELECT * FROM PRODUCT WHERE ID = '$productID'";      
        if ($result = $mysqli->query($sql)){
            //check if data is found
            $row_cnt = $result->num_rows;
            if ($row_cnt == 0){
                echo '<br><b>Can not find the productID in PRODUCT table!</b>';
            } else {
                //fetch associative array
                $row = $result->fetch_assoc();
                $productName = $row['Name'];
                return $productName;
            }
            $result->free();
        }
        $mysqli->close();     
    }    

    function loadProductPrice($productID) {
        
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
            //echo '<b>load product Connected</b><br>';
        }           
        
        //send a query to database to fetch data
        $sql = "SELECT * FROM PRODUCT WHERE ID = '$productID'";      
        if ($result = $mysqli->query($sql)){
            //check if data is found
            $row_cnt = $result->num_rows;
            if ($row_cnt == 0){
                echo '<br><b>Can not find the productID in PRODUCT table!</b>';
            } else {
                //fetch associative array
                $row = $result->fetch_assoc();
                $productPrice = $row['Price'];
                return $productPrice;
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
                            <li></li>
                        </ul>
                    </div>
                </div>
                
                <div class="col-md-4">
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
        </div>
    </div> <!-- End header area -->
    
    <div class="site-branding-area">
        <div class="container">
            <div class="row">
                <div class="col-sm-6">
                    <div class="logo">
                        <h1><a href="#">e<span>Electronics</span></a></h1>
                    </div>
                </div>
                
                <div class="col-sm-6">
                    <div class="shopping-item">
                        <a href="cart.php?uid=<?php echo $userID.'&pid='.$productID;?>">Cart - <span class="cart-amunt">$<?php echo $amount;?>.00</span> <i class="fa fa-shopping-cart"></i> <span class="product-count"><?php echo $productCounter;?></span></a>
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
                        <li><a href="shop.php?uid=<?php                          echo $userID.'&pid='.$productID;?>">Shop page</a></li>
                        <li><a href="cart.php?uid=<?php                          echo $userID.'&pid='.$productID;?>">Cart</a></li>
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
                        <h2>Shop</h2>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    
    <div class="single-product-area">
        <div class="zigzag-bottom"></div>
        <div class="container">
            <div class="row">
                <div class="col-md-4">
                                       
                    <div class="single-sidebar">
                    </div>
                </div>
                
                <div class="col-md-8">
                    <div class="product-content-right">

<?php
    loadProduct();
?>
                                            <div role="tabpanel" class="tab-pane fade" id="profile">
                                                <h2>Reviews</h2>
                                                <div class="submit-review">
                                                    <p><label for="name">Name</label> <input name="name" type="text"></p>
                                                    <p><label for="email">Email</label> <input name="email" type="email"></p>
                                                    <div class="rating-chooser">
                                                        <p>Your rating</p>

                                                        <div class="rating-wrap-post">
                                                            <i class="fa fa-star"></i>
                                                            <i class="fa fa-star"></i>
                                                            <i class="fa fa-star"></i>
                                                            <i class="fa fa-star"></i>
                                                            <i class="fa fa-star"></i>
                                                        </div>
                                                    </div>
                                                    <p><label for="review">Your review</label> <textarea name="review" id="" cols="30" rows="10"></textarea></p>
                                                    <p><input type="submit" value="Submit"></p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    
                                </div>
                            </div>
                        </div>
                        
                        
                 
                            <h2 class="related-products-title">Related Products</h2>
                            <div class="related-products-carousel">
                            </div>
                        </div>
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
    <script type="text/javascript">
        function isNumberKey(evt){
            var charCode = (evt.which) ? evt.which : evt.keyCode
            return !(charCode > 31 && (charCode < 48 || charCode > 57));
        }
    </script>
  </body>
</html>