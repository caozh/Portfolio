<?php
    $msg = "";
    $userID = "";
    $productID = "";
    
    if(isset($_GET['uid'])) {    
            $userID = $_GET['uid'];
            //echo 'User ID: '.$userID;
        } else {
            Header("Location: http://cs.iupui.edu/~caozh/CSCI_45200/project/Fangbing/index.php");
        }
	if(isset($_POST['submit']))
	{
        $productName = trim($_POST['productName']);
        $price = trim($_POST['price']);
        $category = trim($_POST['category']);
        $quantity = $_POST['quantity'];
        $description = trim($_POST['description']);
        $check = true;
        if(!empty($_FILES)){
     	   if($_FILES["file"]["error"] == 0){ 
        	    move_uploaded_file($_FILES["file"]["tmp_name"],"image/".$_FILES["file"]["name"]);
            	echo $_FILES['file']['name'].' upload success'; 
            	$imagePath = "image/".$_FILES["file"]["name"];
        	}
        	else
        	{
        		$msg = $msg . '<br/><b>Please upload a image for product.</b>';
    			$check = false;
    		}
    	}

    	
    	
    	
    	if ($productName == ""){							
            $msg = $msg . '<br/><b>Please enter product name.</b>';
            $check = false;
        }
		if($price == ""){
			$msg = $msg . '<br/><b>Please enter price.</b>';
			$check = false;
		}
		if (!preg_match("/^[0-9]*$/",$price)) {
            $msg = $msg.'<br/><b>Price : only number allowed.</b>';
            $check = false;
        }
		if($category == ""){
			$msg = $msg . '<br/><b>Please enter category.</b>';
			$check = false;
		}
		if ($quantity < 1) {
            $msg = $msg.'<br/><b>Please choose at least one product</b>';
            $check = false;
        }
        

        if(empty($_FILES)){
        	$msg = $msg.'<br/><b>Only letters allowed in the first name.</b>';
        	$check = false;
        }
		
		if($check == true)
		{
        $servername = "localhost";
        $usernameDB = "admin";
        $passwordDB = "";
        $dbName = "test";
        
        // Create connection
        $mysqli = new mysqli($servername, $usernameDB, $passwordDB, $dbName);
        // Check connection
        if ($mysqli->connect_error) {
            die("Connection failed: " . $mysqli->connect_error);
        } 
        $sql = "SELECT * FROM PRODUCT WHERE Owner = '$userID' AND Name = '$productName'";
         if ($result = $mysqli->query($sql)) {
                $row_cnt = $result->num_rows;
                if ($row_cnt != 0)
                    $msg = $msg.'<br/><h2>failed: product exists!</h2>';
                else {
                            $sql = "INSERT INTO PRODUCT ( Name,  Owner,      Category,   Price,      Quantity,    Description, imagePath)
                             VALUES ('$productName','$userID', '$category','$price','$quantity','$description', '$imagePath')";
        if ($mysqli->query($sql) == TRUE) {
            $msg = $msg.'<br/><b>New record created successfully</b>';
        } else {
            echo "Error: " . $sql . "<br>" . $mysqli->error;
        } 
                    } 
                
           
  
        //insert data into database

        $mysqli->close();   
        }         
   

}
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
                    </di               
              
            </div>
        </div>
    </div> <!-- End header area -->
    
    <div class="site-branding-area">
        <div class="container">
            <div class="row">
                <div class="col-sm-6">
                    <div class="logo">
                        <h1><a href="index.php">online shopping system</a></h1>
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
                        <li><a href="http://cs.iupui.edu/~caozh/CSCI_45200/project/Fangbing/sellerPage.php?uid=<?php             echo $userID.'&pid='.$productID;?>">Account</a></li>
                        
						<li><a href="index.php">LogOut</a></li>
                    </ul>
                </div>  
            </div>
        </div>
    </div> <!-- End mainmenu area -->
    <div class="banner">
        <h1>Add a product</h1>
    </div>
    <div class="container">
        <div class="main-content">
            <!--Left Div-->
            <div class="left-content">
            </div>
            
            <!--Right Div-->
            <div class="right-content">
             <form enctype="multipart/form-data" action="" method="post">
            <?Php
            	print $msg;
            	$msg;
            ?>
  											 <p id="billing_first_name_field" class="form-row form-row-first validate-required">
                                                <label class="" for="billing_first_name">Product Name <abbr title="required" class="required">*</abbr>
                                                </label><br>
                                                <input type="text" value="" placeholder="" id="productName" name="productName" class="input-text ">
                                            </p>

                                            <p id="billing_last_name_field" class="form-row form-row-last validate-required">
                                                <label class="" for="billing_last_name">Price <abbr title="required" class="required">*</abbr>
                                                </label><br>
                                                <input type="text" value="" id="price" name="price" class="input-text ">
                                            </p>
                                            <div class="clear"></div>

                                            <p id="billing_address_1_field" class="form-row form-row-wide address-field validate-required">
                                                <label class="" for="billing_address_1">Category<abbr title="required" class="required">*</abbr>
                                                </label><br>
                                                <input type="text" value="" id="category" name="category" class="input-text ">
                                            </p>

                                            <p id="billing_address_2_field" class="form-row form-row-wide address-field">
                                            <label class="" for="billing_address_1">Quantity</label><br>
											<td class="product-quantity">
                                					<input type="number" size="6" class="input-text qty text" title="qty" name = "quantity" min="0" step="1"> 	
                        					</td>
                        					
                        					</p>
                                            <p id="billing_city_field" class="form-row form-row-wide address-field validate-required" data-o_class="form-row form-row-wide address-field validate-required">
                                                <label class="" for="billing_city">Description 
                                                </label><br>
                                          	<textarea  cols="20" rows="5" class="input-text qty text" title="Description" name = "description" > </textarea>	
                                
                                  
                                            </p>
                                            <input type="file" name="file" />
                                            </br></br>
    										<input type="submit" name="submit" value="sumbit">
    
				</form>

            </div>
        </div>
    </div>







    
</body>
</html>