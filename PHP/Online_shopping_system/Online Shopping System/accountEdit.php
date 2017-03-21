<?php
    $msg = "";
    $userID = "";
    $productID = "";
    $amount="0";
    $getAmount="0";
    $productCounter="";
    $firstname = "";
    $lastname = "";
    $address1 = "";
    $address2 = "";
    $city = "";
    $state = "";
    $postcode = "";
    $email = "";
    $phone = "";
    $password = "";
    //run functions


    
    //check if user id and product id are passed to this page

        if(isset($_GET['uid'])) {    
            $userID = $_GET['uid'];
            //echo 'User ID: '.$userID;
        } else {
            Header("Location: http://cs.iupui.edu/~caozh/CSCI_45200/project/Fangbing/index.php");
        }
        
   

    
    //load user information
        global $userID,$msg,$amount;
        
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
        $sql = "SELECT * FROM login WHERE ID = '$userID'";      
        if ($result = $mysqli->query($sql)){
            //check if data is found
            $row_cnt = $result->num_rows;
            if ($row_cnt == 0){
                echo '<br><b>User ID is not registered!</b>';
            } else {
            
                $row = $result->fetch_assoc();
                //echo 'user info:  '.$row['LastName'].' '.$row['FirstName'];
                $firstname = $row['FirstName'];
                $lastname = $row['LastName'];
                $address1 = $row['Address1'];
                $address2 = $row['Address2'];
                $city = $row['City'];
                $state = $row['State'];
           		$postcode = $row['PostCode'];
            	$email = $row['Email'];
             	$phone = $row['Phone'];
             	$password = $row['Password'];
             	
                                    
            }
            $result->free();
        }
        $mysqli->close();             
    	if(isset($_POST['delete']))
    	{
    	        $servername = "localhost";
       			 $usernameDB = "admin";
        		$passwordDB = "";
        		$dbName = "test";
          		$mysqli = new mysqli($servername, $usernameDB, $passwordDB, $dbName);
    	 		$sql = "DELETE FROM login WHERE ID = '$userID'";
           		 $mysqli->query($sql);
           		 	$msg = "You have successfully delete the account";
            	
    	}
		if(isset($_POST['update']))
		{
			 $firstname = trim($_POST['firstname']); 
 			 $lastname = trim($_POST['lastname']); 
 			 $address1 = trim($_POST['address1']); 
 			 $address2 = trim($_POST['address2']); 
 			 $city = trim($_POST['city']);
 			 $state = trim($_POST['state']);
 			 $postcode = trim($_POST['postcode']);
 			 $phone = trim($_POST['phone']);
 			 $password = trim($_POST['password']);
 			 if ($firstname == ""){							
            $msg = $msg . '<br/><b>Please enter your first name.</b>';
            $check = false;
        }
		if(strlen($firstname) > 30){
			$msg = $msg . '<br/><b>Your first name is exceeding the max length of 30.</b>';
			$check = false;
		}
		if(strlen($firstname) < 3 and $firstname != ""){
			$msg = $msg . '<br/><b>Your first name is below the min length of 3.</b>';
			$check = false;
		}
		if (!preg_match("/^[a-zA-Z ]*$/",$firstname)) {
            $msg = $msg.'<br/><b>Only letters allowed in the first name.</b>';
            $check = false;
        }
        if ( $lastname == "" ){
            $msg = $msg.'<br/><b>Please enter your last name.</b>';
            $check = false;
        }
		if (!preg_match("/^[a-zA-Z ]*$/",$lastname)) {
            $msg = $msg.'<br/><b>Only letters allowed in the last name.</b>';
            $check = false;
        }
		if(strlen($lastname) > 30){
			$msg = $msg . '<br/><b>Your last name is exceeding the max length of 30.</b>';
			$check = false;
		}
		if(strlen($lastname) < 3 and $lastname != ""){
			$msg = $msg . '<br/><b>Your last name is below the min length of 3.</b>';
			$check = false;
		}
        if ( $state == "" ){
            $msg = $msg.'<br/><b>Please enter your state.</b>';
            $check = false;
        }
		if(strlen($state) > 20){
			$msg = $msg . '<br/><b>Your state is exceeding the max length of 20.</b>';
			$check = false;
		}
		if (!preg_match("/^[a-zA-Z ]*$/",$state)) {
            $msg = $msg.'<br/><b>Only letters allowed.</b>';
            $check = false;
        }
        if (!preg_match("/^[0-9]*$/",$postcode)) {
            $msg = $msg.'<br/><b>Post : only numbers allowed.</b>';
            $check = false;
        }
        if ( $address1 == "" ){
            $msg = $msg.'<br/><b>Please enter the address.</b>';
            $check = false;
        }
		if(strlen($address1) > 30){
			$msg = $msg . '<br/><b>Your address is exceeding the max length of 30.</b>';
			$check = false;
		}
		if(strlen($address1) < 5  and $address1 != ""){
			$msg = $msg . '<br/><b>Your address is below the min length of 5.</b>';
			$check = false;
		}
		if(strlen($address2) > 30){
			$msg = $msg . '<br/><b>Your second address is exceeding the max length of 30.</b>';
			$check = false;
		}
        if ( $city == "" ){
            $msg = $msg.'<br/><b>Please enter the city you live in.</b>';	
            $check = false;
        }
		if(strlen($city) > 20){
			$msg = $msg . '<br/><b>Your second address is exceeding the max length of 20.</b>';
			$check = false;
		}
        if ( $state == "" ){
            $msg = $msg.'<br/><b>Please enter your the state.</b>';
            $check = false;
        }
		if(strlen($state) > 20){
			$msg = $msg . '<br/><b>Your state name is exceeding the max length of 20.</b>';
			$check = false;
		}
        if ( $postcode == "" ){
            $msg = $msg.'<br/><b>Please enter the post code.</b>';
            $check = false;
        }
		if(strlen($postcode) > 10){
			$msg = $msg . '<br/><b>Your Postal Code is exceeding the max length of 10.</b>';
			$check = false;
		}
		if(strlen($postcode) < 5  and $postcode != ""){
			$msg = $msg . '<br/><b>Your Postal Code is below the min length of 5.</b>';
			$check = false;
		}
        if ( $email == "" ){
            $msg = $msg.'<br/><b>Please enter the email.</b>';
            $check = false;
        }
    
        if ( $password == "" ){
            $msg = $msg.'<br/><b>Please enter the password.</b>';
            $check = false;
        }
		if(strlen($password) > 30){
			$msg = $msg . '<br/><b>Your password is exceeding the max length of 30.</b>';
			$check = false;
		}
		if(strlen($password) < 4 and $password != ""){
			$msg = $msg . '<br/><b>Your password is not long enough. It needs to be at least 4 long.</b>';
			$check = false;
		}
		if(!$check)
			$msg = $msg."<br/>";
        if ($check) {
 			$servername = "localhost";
       		$usernameDB = "admin";
        	$passwordDB = "";
        	$dbName = "test";
          	$mysqli = new mysqli($servername, $usernameDB, $passwordDB, $dbName);
    	 	$sql = "UPDATE login SET Email='$email',FirstName='$firstname',
    	 	LastName='$lastname',Address1='$address1',Address2='$address2',City='$city',State='$state',PostCode='$postcode', Phone='$phone', Password='$password' WHERE ID = '$userID' ";
           	$mysqli->query($sql);
           	$msg = "You have successfully updated your information";
           	}
		}
    
?>
   
<!DOCTYPE html>
<html lang="en">
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
                        <li><a href="http://cs.iupui.edu/~caozh/CSCI_45200/project/Fangbing/adminHome.php?uid=<?php             echo $userID.'&pid='.$productID;?>">Account</a></li>
                        
						<li><a href="index.php">LogOut</a></li>
                    </ul>
                </div>  
            </div>
        </div>
    </div> <!-- End mainmenu area -->
    
    <div class="product-big-title-area">
       
                    <div class="product-bit-title text-center">
                        <h2>Edit Account</h2>
                    </div>
        
    </div>
    
    
    <div class="single-product-area">
        <div class="zigzag-bottom"></div>
        <div class="container">
            <div class="row">
                
                <div class="col-md-8">
                    <div class="product-content-right">


                            <form enctype="multipart/form-data" action="" method="post">
<?php 
    print $msg;
    $msg = "";
?>	
      </br>      </br>
                            <div class="col-1">
                                        <div class="woocommerce-billing-fields">
                                            

                                   				<p>
                                                <label class="" for="billing_first_name">First Name <abbr title="required" class="required">*</abbr>
                                                </label><br>
                                                <input type="text" value="<?php echo $firstname ?>" placeholder="" id="billing_first_name" name="firstname" class="input-text ">
                                            	</p>
												<p>
                                                <label class="" for="billing_last_name">Last Name <abbr title="required" class="required">*</abbr>
                                                </label><br>
                                                <input type="text" value="<?php echo $lastname ?>" placeholder="" id="billing_last_name" name="lastname" class="input-text ">
                                            	</p>
                                            <div class="clear"></div>

												<p>
                                                <label class="" for="billing_address_1">Address  1<abbr title="required" class="required">*</abbr>
                                                </label><br>
                                                <input type="text" value="<?php echo $address1 ?>" placeholder="Street address" id="billing_address_1" name="address1" class="input-text ">
                                        		</p>

                                          		<p>
                                          		<label class="" for="billing_address_1">Address  2<abbr title="required" class="required">*</abbr>
                                                </label><br>
                                                <input type="text" value="<?php echo $address2 ?>"  id="billing_address_2" name="address2" class="input-text ">
                                        		</p>

                                          		<p>
                                                <label class="" for="billing_city">Town / City <abbr title="required" class="required">*</abbr>
                                                </label><br>
                                                <input type="text" value="<?php echo $city ?>" placeholder="Town / City" id="billing_city" name="city" class="input-text ">
                                    			</p>

                                            	<p>
                                                <label class="" for="billing_state">State</label><br>
                                                <input type="text" id="billing_state" name="state" placeholder="State / County" value="<?php echo $state?>" class="input-text ">
                                       			</p>
                                           		<p>
                                                <label class="" for="billing_postcode">Postcode <abbr title="required" class="required">*</abbr>
                                                </label><br>
                                                <input type="text" value="<?php echo $postcode ?>" placeholder="Postcode / Zip" id="billing_postcode" name="postcode" class="input-text ">
                                            	</p>

                                            <div class="clear"></div>
											
                                       		<p>
                                                <label class="" for="billing_phone">Password</label><br>
                                                <input type="text" value="<?php echo $password ?>" placeholder="" id="billing_phone" name="password" class="input-text ">
                                        	</p	>	
                                       		<p>
                                                <label class="" for="billing_phone">Phone</label><br>
                                                <input type="text" value="<?php echo $phone ?>" placeholder="" id="billing_phone" name="phone" class="input-text ">
                                        	</p	>	
                                        	</br></br>
                                        	<input type="submit" name="update" value="Update">
                                        	</br></br>
                                        	<input type="submit" name="delete" value="delete account">
                                            <div class="clear"></div>

                                        </div>
                                    </div>
						
						</form>
                                        </div>

                                    </div>

                                </div>