<?php
    $msg = "";
    $userID = "";
    $productID = "";
    $quantity = "";
    $productCounter = "";
    $amount = "0";
    $imagePath = "";
    $category = "";
    $price = "";
    $description = "";
    $productName = "";
    checkPost();
    
    //check if add to cart button be clicked


    //Load cart icon data from database

    
    //check if user id and product id are passed to this page
    function checkPost() {
        global $userID,$productID;
        
        //if(!empty($_GET['uid'])) {
  
        if(isset($_GET['pid'])) {
            $productID = $_GET['pid'];
            //echo 'Product ID: '.$productID;
        } else {
            echo '  You should select a product or append "&pid=1" to header.';
        }    
    }	
    

   
        global $userID,$productID,$msg;
        
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
			
			$imagePath = $row['ImagePath'];
			$category = $row['Category'];
			$price	= $row['Price'];
			$quantity = $row['Quantity'];
			$description = $row['Description'];
			$productName = $row['Name'];
        
           
            }
            $result->free();
        }
        $mysqli->close();        
    
    
    //save cart product into database
    function insertDB () {
        global $userID,$productID,$quantity,$msg;
        
        $servername = "localhost";
        $usernameDB = "admin";
        $passwordDb = "";
        $dbName = "test";
        
        // Create connection
        $mysqli = new mysqli($servername, $usernameDB, $passwordDb, $dbName);
        // Check connection
        if ($mysqli->connect_error) {
            die("Connection failed: " . $mysqli->connect_error);
        } else {
            //echo '<br><b>Database Connected</b>';
        }   
        
        //insert data into database
        $sql = "INSERT INTO CART ( UserID,   ProductID,   Quantity)
                          VALUES ('$userID','$productID','$quantity')";
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
    	if(isset($_POST['delete']))
    	{
    	        $servername = "localhost";
       			 $usernameDB = "admin";
        		$passwordDB = "";
        		$dbName = "test";
          		$mysqli = new mysqli($servername, $usernameDB, $passwordDB, $dbName);
    	 		$sql = "DELETE FROM PRODUCT WHERE ID = '$productID'";
           		 $mysqli->query($sql);
            	
    	}
 		if(isset($_POST['edit']))
    	{
 			 $Tquantity = trim($_POST['quantity']); 
 			 $Tprice = trim($_POST['price']); 
 			 $Tcategory = trim($_POST['category']); 
 			 $Tdescription = trim($_POST['description']); 
 			 $TproductName = trim($_POST['productName']); 

      
    		if($Tcategory != "")
    			$category = $Tcategory;
    		if($Tquantity != "")
    			$quantity = $Tquantity;
    		if($Tprice != "")
    			$price = $Tprice;
    		if($Tdescription != "")
    			$description = $Tdescription;
    		if($TproductName!= "")
    			$productName= $TproductName;
    		if(!empty($_FILES))
    			$imagePath = "image/".$_FILES['file']['name'];
    			
    			
    		$servername = "localhost";
       		$usernameDB = "admin";
        	$passwordDB = "";
        	$dbName = "test";
          	$mysqli = new mysqli($servername, $usernameDB, $passwordDB, $dbName);
    	 	$sql = "UPDATE PRODUCT SET ID='$productID',Name='$productName',
    	 	Owner='$userID',Category='$category',Price='$price',Quantity='$quantity',Description='$description',ImagePath='$imagePath' WHERE ID = '$productID' ";
           	$mysqli->query($sql);
            	
 		
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
                        <h1><a href="index.php">e<span>Online Shopping System</span></a></h1>
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
                        <h2>Edit Product</h2>
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
                          <form enctype="multipart/form-data" action="" method="post">
            <?Php
            	print $msg;
            	$msg;
            ?>
  											 <p id="billing_first_name_field" class="form-row form-row-first validate-required">
                                                <label class="" for="billing_first_name">Product Name <abbr title="required" class="required">*</abbr>
                                                </label>
                                                <input type="text" value="" placeholder="<?php echo $productName ;?>" id="productName" name="productName" class="input-text ">
                                            </p>

                                            <p id="billing_last_name_field" class="form-row form-row-last validate-required">
                                                <label class="" for="billing_last_name">Price <abbr title="required" class="required">*</abbr>
                                                </label>
                                                <input type="text" value="" id="price" placeholder="<?php echo $price ;?>" name="price" class="input-text ">
                                            </p>
                                            <div class="clear"></div>

                                            <p id="billing_address_1_field" class="form-row form-row-wide address-field validate-required">
                                                <label class="" for="billing_address_1">Category<abbr title="required" class="required">*</abbr>
                                                </label>
                                                <input type="text" value="" id="category" placeholder="<?php echo $category ;?>" name="category" class="input-text ">
                                            </p>

                                            <p id="billing_address_2_field" class="form-row form-row-wide address-field">
                                             <label class="" for="billing_address_1">Quantity</label>
                                                <input type="text" value="" id="quantity" placeholder="<?php echo $quantity ;?>" name="quantity" class="input-text ">
                                            </p>

                                            <p id="billing_city_field" class="form-row form-row-wide address-field validate-required" data-o_class="form-row form-row-wide address-field validate-required">
                                                <label class="" for="billing_city">Description 
                                                </label>
                                                 <textarea cols="15" rows="10" id="order_comments" placeholder="<?php echo $description ;?>" class="input-text " name="order_comments"></textarea>
                                            </p>
                                           
    										<?php
    											echo '
                                        			<img src="'.$imagePath.'" alt="">';
    										?>
    										</br></br>
     										<input type="file" name="file" />
    									  </br></br>
    			
    										<input type="submit" name="edit" value="sumbit">
    											  </br></br>
    										 	<input type="submit" name="delete" value="delete product">
				</form>
				</br></br >
              								
									<a href="sellerPage.php?uid=<?php echo $userID?>"> <h1>Back to home</h1></a>
               		
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