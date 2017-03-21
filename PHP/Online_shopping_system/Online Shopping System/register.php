<?php 
	require_once "inc/util.php";
	require_once "mail/mail.class.php";

	$msg = "";
 
    if (isset($_POST['enter']))
    {
        //always initialized variables to be used
        $firstName = "";
        $lastName = "";
        $country = "";
        $address1 = "";
        $address2 = "";
        $city = "";
        $state = "";
        $postcode = "";
        $email = "";
        $password = "";
		$password2 = "";
        $membership = "";
        $phone = "";
        $check = true;
        
        //take the information submitted and verify inputs
        $firstName =  trim($_POST['firstname']);
        $lastName = trim($_POST['lastname']);	
        $country = trim($_POST['country']);	
        $address1 = trim($_POST['address1']);	
        $address2 = trim($_POST['address2']);
        $city = trim($_POST['city']);
        $state = trim($_POST['state']);
        $postcode = trim($_POST['postcode']);	
        $email = trim($_POST['email']);
        $password = $_POST['password'];
		$password2 = $_POST['password2'];
        $membership = trim($_POST['membership']);
        $phone = trim($_POST['phone']);
        
        //validation
        if ($firstName == ""){							
            $msg = $msg . '<br/><b>Please enter your first name.</b>';
            $check = false;
        }
		if(strlen($firstName) > 30){
			$msg = $msg . '<br/><b>Your first name is exceeding the max length of 30.</b>';
			$check = false;
		}
		if(strlen($firstName) < 3 and $firstName != ""){
			$msg = $msg . '<br/><b>Your first name is below the min length of 3.</b>';
			$check = false;
		}
		if (!preg_match("/^[a-zA-Z ]*$/",$firstName)) {
            $msg = $msg.'<br/><b>Only letters allowed in the first name.</b>';
            $check = false;
        }
        if ( $lastName == "" ){
            $msg = $msg.'<br/><b>Please enter your last name.</b>';
            $check = false;
        }
		if (!preg_match("/^[a-zA-Z ]*$/",$lastName)) {
            $msg = $msg.'<br/><b>Only letters allowed in the last name.</b>';
            $check = false;
        }
		if(strlen($lastName) > 30){
			$msg = $msg . '<br/><b>Your last name is exceeding the max length of 30.</b>';
			$check = false;
		}
		if(strlen($lastName) < 3 and $lastName != ""){
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
            $msg = $msg.'<br/><b>State: only letters allowed.</b>';
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
		if(strlen($email) > 40){
			$msg = $msg . '<br/><b>Your email is exceeding the max length of 40.</b>';
			$check = false;
		}
        elseif (!spamcheck($email) and $email != "")	{
            $msg = $msg.'<br/><b>Email is not valid.</b>';
            $check = false;
        }
        if ( $password == "" ){
            $msg = $msg.'<br/><b>Please enter the password.</b>';
            $check = false;
        }
        if ( $phone == "" ){
            $msg = $msg.'<br/><b>Please enter your phone number.</b>';
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
		if ( $password2 == "" ){
            $msg = $msg.'<br/><b>Please enter the password in the confirmation field.</b>';
            $check = false;
        }
		if(strlen($password2) > 30){
			$msg = $msg . '<br/><b>Your confirmation password is exceeding the max length of 30.</b>';
			$check = false;
		}
		if(strlen($password2) < 4 and $password2 != ""){
			$msg = $msg . '<br/><b>Your confirmation password is not long enough. It needs to be at least 4 long.</b>';
			$check = false;
		}
		if($password != $password2){
			$msg = $msg . '<br/><b>The password and confirmation passwords field did not match.</b>';
			$check = false;
		}
        if ( $membership == ""){
            $msg = $msg."<br /> Please choose your membership.<br />";
            $check = false;
        }
		if(!$check)
			$msg = $msg."<br/>";
        if ($check) { 
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
            $sql = "SELECT * FROM login WHERE Email = '$email' ";
            if ($result = $mysqli->query($sql)) {
                    $row_cnt = $result->num_rows;
                    if ($row_cnt != 0)
                        $msg = $msg.'<br/><h2>failed: user exists</h2>';
                    else {
                        //$msg = $msg.'<br/><b>Your information is valid</b>';
                        $sql = "INSERT INTO login (Email, Password, FirstName, LastName, Address1, Address2, City, State, PostCode, Country, Membership, Phone)
                                           VALUES ('$email','$password','$firstName','$lastName ','$address1','$address2','$city','$state','$postcode','$country','$membership','$phone')";
                        if ($mysqli->query($sql) === TRUE) {
                            $alert = "Your account is created successfully!";
                            phpAlert($alert);
                        } else {
                            echo "Error: " . $sql . "<br>" . $mysqli->error;
                        } 
                        //insert data into database
                        $mysqli->close();               
                    }
            } //end if ($_POST['enter'])
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
                            <li><a href="index.php"><i class="fa fa-user"></i> Login</a></li>
                        </ul>
                    </div>
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
                        <li><a href="index.php">Home</a></li>
                        <li><a href="contact.php">Contact</a></li>
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
                        <h2>Registration</h2>
                    </div>
                </div>
            </div>
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
								</br>
                                <div id="customer_details" class="col2-set">
                                    <div class="col-1">
                                        <div class="woocommerce-billing-fields">
                                            <p id="billing_country_field" class="form-row form-row-wide address-field update_totals_on_change validate-required woocommerce-validated">
                                                <label class="" for="billing_country">Country <abbr title="required" class="required">*</abbr>
                                                </label>
                                           
                                                <select class="country_to_state country_select" id="country" name="country">
                                                    <option value="">Select a country…</option>
                                                    <option value="AX">Åland Islands</option>
                                                    <option value="AF">Afghanistan</option>
                                                    <option value="AL">Albania</option>
                                                    <option value="DZ">Algeria</option>
                                                    <option value="AD">Andorra</option>
                                                    <option value="AO">Angola</option>
                                                    <option value="AI">Anguilla</option>
                                                    <option value="AQ">Antarctica</option>
                                                    <option value="AG">Antigua and Barbuda</option>
                                                    <option value="AR">Argentina</option>
                                                    <option value="AM">Armenia</option>
                                                    <option value="AW">Aruba</option>
                                                    <option value="AU">Australia</option>
                                                    <option value="AT">Austria</option>
                                                    <option value="AZ">Azerbaijan</option>
                                                    <option value="BS">Bahamas</option>
                                                    <option value="BH">Bahrain</option>
                                                    <option value="BD">Bangladesh</option>
                                                    <option value="BB">Barbados</option>
                                                    <option value="BY">Belarus</option>
                                                    <option value="PW">Belau</option>
                                                    <option value="BE">Belgium</option>
                                                    <option value="BZ">Belize</option>
                                                    <option value="BJ">Benin</option>
                                                    <option value="BM">Bermuda</option>
                                                    <option value="BT">Bhutan</option>
                                                    <option value="BO">Bolivia</option>
                                                    <option value="BQ">Bonaire, Saint Eustatius and Saba</option>
                                                    <option value="BA">Bosnia and Herzegovina</option>
                                                    <option value="BW">Botswana</option>
                                                    <option value="BV">Bouvet Island</option>
                                                    <option value="BR">Brazil</option>
                                                    <option value="IO">British Indian Ocean Territory</option>
                                                    <option value="VG">British Virgin Islands</option>
                                                    <option value="BN">Brunei</option>
                                                    <option value="BG">Bulgaria</option>
                                                    <option value="BF">Burkina Faso</option>
                                                    <option value="BI">Burundi</option>
                                                    <option value="KH">Cambodia</option>
                                                    <option value="CM">Cameroon</option>
                                                    <option value="CA">Canada</option>
                                                    <option value="CV">Cape Verde</option>
                                                    <option value="KY">Cayman Islands</option>
                                                    <option value="CF">Central African Republic</option>
                                                    <option value="TD">Chad</option>
                                                    <option value="CL">Chile</option>
                                                    <option value="CN">China</option>
                                                    <option value="CX">Christmas Island</option>
                                                    <option value="CC">Cocos (Keeling) Islands</option>
                                                    <option value="CO">Colombia</option>
                                                    <option value="KM">Comoros</option>
                                                    <option value="CG">Congo (Brazzaville)</option>
                                                    <option value="CD">Congo (Kinshasa)</option>
                                                    <option value="CK">Cook Islands</option>
                                                    <option value="CR">Costa Rica</option>
                                                    <option value="HR">Croatia</option>
                                                    <option value="CU">Cuba</option>
                                                    <option value="CW">CuraÇao</option>
                                                    <option value="CY">Cyprus</option>
                                                    <option value="CZ">Czech Republic</option>
                                                    <option value="DK">Denmark</option>
                                                    <option value="DJ">Djibouti</option>
                                                    <option value="DM">Dominica</option>
                                                    <option value="DO">Dominican Republic</option>
                                                    <option value="EC">Ecuador</option>
                                                    <option value="EG">Egypt</option>
                                                    <option value="SV">El Salvador</option>
                                                    <option value="GQ">Equatorial Guinea</option>
                                                    <option value="ER">Eritrea</option>
                                                    <option value="EE">Estonia</option>
                                                    <option value="ET">Ethiopia</option>
                                                    <option value="FK">Falkland Islands</option>
                                                    <option value="FO">Faroe Islands</option>
                                                    <option value="FJ">Fiji</option>
                                                    <option value="FI">Finland</option>
                                                    <option value="FR">France</option>
                                                    <option value="GF">French Guiana</option>
                                                    <option value="PF">French Polynesia</option>
                                                    <option value="TF">French Southern Territories</option>
                                                    <option value="GA">Gabon</option>
                                                    <option value="GM">Gambia</option>
                                                    <option value="GE">Georgia</option>
                                                    <option value="DE">Germany</option>
                                                    <option value="GH">Ghana</option>
                                                    <option value="GI">Gibraltar</option>
                                                    <option value="GR">Greece</option>
                                                    <option value="GL">Greenland</option>
                                                    <option value="GD">Grenada</option>
                                                    <option value="GP">Guadeloupe</option>
                                                    <option value="GT">Guatemala</option>
                                                    <option value="GG">Guernsey</option>
                                                    <option value="GN">Guinea</option>
                                                    <option value="GW">Guinea-Bissau</option>
                                                    <option value="GY">Guyana</option>
                                                    <option value="HT">Haiti</option>
                                                    <option value="HM">Heard Island and McDonald Islands</option>
                                                    <option value="HN">Honduras</option>
                                                    <option value="HK">Hong Kong</option>
                                                    <option value="HU">Hungary</option>
                                                    <option value="IS">Iceland</option>
                                                    <option value="IN">India</option>
                                                    <option value="ID">Indonesia</option>
                                                    <option value="IR">Iran</option>
                                                    <option value="IQ">Iraq</option>
                                                    <option value="IM">Isle of Man</option>
                                                    <option value="IL">Israel</option>
                                                    <option value="IT">Italy</option>
                                                    <option value="CI">Ivory Coast</option>
                                                    <option value="JM">Jamaica</option>
                                                    <option value="JP">Japan</option>
                                                    <option value="JE">Jersey</option>
                                                    <option value="JO">Jordan</option>
                                                    <option value="KZ">Kazakhstan</option>
                                                    <option value="KE">Kenya</option>
                                                    <option value="KI">Kiribati</option>
                                                    <option value="KW">Kuwait</option>
                                                    <option value="KG">Kyrgyzstan</option>
                                                    <option value="LA">Laos</option>
                                                    <option value="LV">Latvia</option>
                                                    <option value="LB">Lebanon</option>
                                                    <option value="LS">Lesotho</option>
                                                    <option value="LR">Liberia</option>
                                                    <option value="LY">Libya</option>
                                                    <option value="LI">Liechtenstein</option>
                                                    <option value="LT">Lithuania</option>
                                                    <option value="LU">Luxembourg</option>
                                                    <option value="MO">Macao S.A.R., China</option>
                                                    <option value="MK">Macedonia</option>
                                                    <option value="MG">Madagascar</option>
                                                    <option value="MW">Malawi</option>
                                                    <option value="MY">Malaysia</option>
                                                    <option value="MV">Maldives</option>
                                                    <option value="ML">Mali</option>
                                                    <option value="MT">Malta</option>
                                                    <option value="MH">Marshall Islands</option>
                                                    <option value="MQ">Martinique</option>
                                                    <option value="MR">Mauritania</option>
                                                    <option value="MU">Mauritius</option>
                                                    <option value="YT">Mayotte</option>
                                                    <option value="MX">Mexico</option>
                                                    <option value="FM">Micronesia</option>
                                                    <option value="MD">Moldova</option>
                                                    <option value="MC">Monaco</option>
                                                    <option value="MN">Mongolia</option>
                                                    <option value="ME">Montenegro</option>
                                                    <option value="MS">Montserrat</option>
                                                    <option value="MA">Morocco</option>
                                                    <option value="MZ">Mozambique</option>
                                                    <option value="MM">Myanmar</option>
                                                    <option value="NA">Namibia</option>
                                                    <option value="NR">Nauru</option>
                                                    <option value="NP">Nepal</option>
                                                    <option value="NL">Netherlands</option>
                                                    <option value="AN">Netherlands Antilles</option>
                                                    <option value="NC">New Caledonia</option>
                                                    <option value="NZ">New Zealand</option>
                                                    <option value="NI">Nicaragua</option>
                                                    <option value="NE">Niger</option>
                                                    <option value="NG">Nigeria</option>
                                                    <option value="NU">Niue</option>
                                                    <option value="NF">Norfolk Island</option>
                                                    <option value="KP">North Korea</option>
                                                    <option value="NO">Norway</option>
                                                    <option value="OM">Oman</option>
                                                    <option value="PK">Pakistan</option>
                                                    <option value="PS">Palestinian Territory</option>
                                                    <option value="PA">Panama</option>
                                                    <option value="PG">Papua New Guinea</option>
                                                    <option value="PY">Paraguay</option>
                                                    <option value="PE">Peru</option>
                                                    <option value="PH">Philippines</option>
                                                    <option value="PN">Pitcairn</option>
                                                    <option value="PL">Poland</option>
                                                    <option value="PT">Portugal</option>
                                                    <option value="QA">Qatar</option>
                                                    <option value="IE">Republic of Ireland</option>
                                                    <option value="RE">Reunion</option>
                                                    <option value="RO">Romania</option>
                                                    <option value="RU">Russia</option>
                                                    <option value="RW">Rwanda</option>
                                                    <option value="ST">São Tomé and Príncipe</option>
                                                    <option value="BL">Saint Barthélemy</option>
                                                    <option value="SH">Saint Helena</option>
                                                    <option value="KN">Saint Kitts and Nevis</option>
                                                    <option value="LC">Saint Lucia</option>
                                                    <option value="SX">Saint Martin (Dutch part)</option>
                                                    <option value="MF">Saint Martin (French part)</option>
                                                    <option value="PM">Saint Pierre and Miquelon</option>
                                                    <option value="VC">Saint Vincent and the Grenadines</option>
                                                    <option value="SM">San Marino</option>
                                                    <option value="SA">Saudi Arabia</option>
                                                    <option value="SN">Senegal</option>
                                                    <option value="RS">Serbia</option>
                                                    <option value="SC">Seychelles</option>
                                                    <option value="SL">Sierra Leone</option>
                                                    <option value="SG">Singapore</option>
                                                    <option value="SK">Slovakia</option>
                                                    <option value="SI">Slovenia</option>
                                                    <option value="SB">Solomon Islands</option>
                                                    <option value="SO">Somalia</option>
                                                    <option value="ZA">South Africa</option>
                                                    <option value="GS">South Georgia/Sandwich Islands</option>
                                                    <option value="KR">South Korea</option>
                                                    <option value="SS">South Sudan</option>
                                                    <option value="ES">Spain</option>
                                                    <option value="LK">Sri Lanka</option>
                                                    <option value="SD">Sudan</option>
                                                    <option value="SR">Suriname</option>
                                                    <option value="SJ">Svalbard and Jan Mayen</option>
                                                    <option value="SZ">Swaziland</option>
                                                    <option value="SE">Sweden</option>
                                                    <option value="CH">Switzerland</option>
                                                    <option value="SY">Syria</option>
                                                    <option value="TW">Taiwan</option>
                                                    <option value="TJ">Tajikistan</option>
                                                    <option value="TZ">Tanzania</option>
                                                    <option value="TH">Thailand</option>
                                                    <option value="TL">Timor-Leste</option>
                                                    <option value="TG">Togo</option>
                                                    <option value="TK">Tokelau</option>
                                                    <option value="TO">Tonga</option>
                                                    <option value="TT">Trinidad and Tobago</option>
                                                    <option value="TN">Tunisia</option>
                                                    <option value="TR">Turkey</option>
                                                    <option value="TM">Turkmenistan</option>
                                                    <option value="TC">Turks and Caicos Islands</option>
                                                    <option value="TV">Tuvalu</option>
                                                    <option value="UG">Uganda</option>
                                                    <option value="UA">Ukraine</option>
                                                    <option value="AE">United Arab Emirates</option>
                                                    <option value="GB">United Kingdom (UK)</option>
                                                    <option selected="selected" value="US">United States (US)</option>
                                                    <option value="UY">Uruguay</option>
                                                    <option value="UZ">Uzbekistan</option>
                                                    <option value="VU">Vanuatu</option>
                                                    <option value="VA">Vatican</option>
                                                    <option value="VE">Venezuela</option>
                                                    <option value="VN">Vietnam</option>
                                                    <option value="WF">Wallis and Futuna</option>
                                                    <option value="EH">Western Sahara</option>
                                                    <option value="WS">Western Samoa</option>
                                                    <option value="YE">Yemen</option>
                                                    <option value="ZM">Zambia</option>
                                                    <option value="ZW">Zimbabwe</option>
                                                </select>
                                            </p>

                                            <p id="billing_first_name_field" class="form-row form-row-first validate-required">
                                                <label class="" for="billing_first_name">First Name <abbr title="required" class="required">*</abbr>
                                                </label>
                                                <input type="text" value="" placeholder="" id="firstname" name="firstname" class="input-text ">
                                            </p>

                                            <p id="billing_last_name_field" class="form-row form-row-last validate-required">
                                                <label class="" for="billing_last_name">Last Name <abbr title="required" class="required">*</abbr>
                                                </label>
                                                <input type="text" value="" placeholder="" id="lastname" name="lastname" class="input-text ">
                                            </p>
                                            <div class="clear"></div>

                                            <p id="billing_address_1_field" class="form-row form-row-wide address-field validate-required">
                                                <label class="" for="billing_address_1">Address 1<abbr title="required" class="required">*</abbr>
                                                </label>
                                                <input type="text" value="" placeholder="Street address" id="address1" name="address1" class="input-text ">
                                            </p>

                                            <p id="billing_address_2_field" class="form-row form-row-wide address-field">
                                             <label class="" for="billing_address_1">Address 2</label>
                                                <input type="text" value="" placeholder="Apartment, suite, unit etc. (optional)" id="address2" name="address2" class="input-text ">
                                            </p>

                                            <p id="billing_city_field" class="form-row form-row-wide address-field validate-required" data-o_class="form-row form-row-wide address-field validate-required">
                                                <label class="" for="billing_city">Town / City <abbr title="required" class="required">*</abbr>
                                                </label>
                                                <input type="text" value="" placeholder="Town / City" id="city" name="city" class="input-text ">
                                            </p>

                                            <p id="billing_state_field" class="form-row form-row-first address-field validate-state" data-o_class="form-row form-row-first address-field validate-state">
                                                <label class="" for="billing_state">State</label>
                                                <input type="text" id="state" name="state" placeholder="State" value="" class="input-text ">
                                            </p>
                                            <p id="billing_postcode_field" class="form-row form-row-last address-field validate-required validate-postcode" data-o_class="form-row form-row-last address-field validate-required validate-postcode">
                                                <label class="" for="billing_postcode">Postcode <abbr title="required" class="required">*</abbr>
                                                </label>
                                                <input type="text" value="" placeholder="Postcode / Zip" id="postcode" name="postcode" class="input-text " onkeypress="return isNumberKey(event);" maxlength="10" >
                                            </p>

                                            <p id="billing_phone_field" class="form-row form-row-first validate-required validate-email">
                                                <label class="" for="billing_phone">Phone <abbr title="required" class="required">*</abbr>
                                                </label>
                                                <input type="text" value="" placeholder="" id="phone" name="phone" class="input-text" onkeypress="return isNumberKey(event);"  maxlength="13"  >
                                            </p>

                                            <p id="billing_email_field" class="form-row form-row-first validate-required validate-email">
                                                <label class="" for="billing_email">Email Address <abbr title="required" class="required">*</abbr>
                                                </label>
                                                <input type="email" value="" placeholder="" id="email" name="email" class="input-text" size="54.5"  maxlength="40" >
                                            </p>
											<p id="billing_company_field" class="form-row form-row-wide">
                                                <label class="" for="billing_company">Password <abbr title="required" class="required">*</abbr> 
                                                </label>
                                                <input type="text" value="" placeholder="" id="password" name="password" class="input-text ">
                                            </p>
											<p id="billing_company_field" class="form-row form-row-wide">
                                                <label class="" for="billing_company2">Confirm Password <abbr title="required" class="required">*</abbr> 
                                                </label>
                                                <input type="text" value="" placeholder="" id="password2" name="password2" class="input-text ">
                                            </p>
                                             <p id="billing_company_field" class="form-row form-row-wide">
                                                <label class="" >Buyer </label>
                                                <input type = "radio" name = "membership" value = "buyer" checked = "checked" />
                                            </p>
                                             <p id="billing_company_field" class="form-row form-row-wide">
                                                <label class="" >Seller </label>
                                                <input type = "radio" name = "membership" value = "seller" /> <br />
                                            </p>
											
                                          	<br/>
                                            	<input name="enter" class="btn" type="submit" value="Submit" />


                                        

                                        </div>
                                    </div>


                                </div>

                            
                            </form>

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
    <script type="text/javascript">
        function isNumberKey(evt){
            var charCode = (evt.which) ? evt.which : evt.keyCode
            return !(charCode > 31 && (charCode < 48 || charCode > 57));
        }
    </script>
  </body>
</html>