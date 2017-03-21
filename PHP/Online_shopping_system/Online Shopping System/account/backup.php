
<?php 
	require_once "inc/util.php";
	require_once "mail/mail.class.php";

	$msg = "";
?>

<html>
<head>
	<meta charset="UTF-8">
	<title>This is title</title>
	<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
	
	<div id="body">
		<div id="section">
			<ul>
				<li>
					
					<div>
						<?php
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
								if ( $pwd=="" )
								$msg = $msg.'<br/><b>please enter your password.</b>';	
	
								//if you login successfully, your username will be delivered to another page called "My account"
								if ($unameok) { 										
                                    //Header("Location:my account.php?uname=".$uname);
                                    $servername = "localhost";
                                    $username = "admin";
                                    $password = "";
                                    $dbName = "test";
                                    // Create connection
                                    $mysqli = new mysqli($servername, $username, $password, $dbName);
                                    // Check connection
                                    if ($mysqli->connect_error) {
                                        die("Connection failed: " . $mysqli->connect_error);
                                    } else {
                                        echo "Connected successfully<br>";
                                    }
                                    
                                    // return name of current default database 
                                    if ($result = $mysqli->query("SELECT DATABASE()")) {
                                        $row = $result->fetch_row();
                                        printf("Default database is %s.\n", $row[0]);
                                        $result->free();
                                    }
                                    
                                    $sql = "SELECT * FROM login WHERE username = '$uname' AND password = '$pwd'";
                                    
                                    //mysqli_num_rows($result) == 0
                                    if ($result = $mysqli->query($sql)) {
                                        $row_cnt = $result->num_rows;
                                       // $row_cnt = mysqli_num_rows($result);
                                        if ($row_cnt == 1) {
                                            // fetch associative array
                                            while ($row = $result->fetch_assoc()){
                                                printf ("Login successfully");
                                            }
                                            $result->free();
                                            
                                        } else {
                                        echo "Username or Password is wrong!<br>";
                                    }
                                    } 
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
		
								//check the form of email
								if (!spamcheck($uname))							
								$msg = $msg . '<br/><b>Email is not valid.</b>';

								else $unameok = true;
								
								if ($unameok) 
								{										
									$code = randomCodeGenerator(8);
									$subject = "Email Activation";						
									$body = 'Your password is '.$code;
									$mailer = new Mail();
									//if your email is correct, you will receive a email for your new password
									if (($mailer->sendMail($uname, 'customer', $subject, $body))==true)
									$msg = "<b>please check your email for the new password.</b>";
									else $msg = "Email not sent. ";
					
								}

							}
                            
    
						?>
						<form action="" method="post">
							<h3>Login your account</h3>
							<?php 
                                
								print $msg;
								$msg = "";
							?>
							<br />
                            <h1>Username: buyer@qq.com<br>Password: 123456<br></h1>
							Username(email): <input type="text" maxlength = "50" value="" name="userName" id="userName" /> <br />
							Password   : <input type="text" maxlength = "50" value="" name="pwd" id="pwd" /> <br />
	
							<input name="enter" class="btn" type="submit" value="Submit" />
							</br>
							<input name="forget" class="btn" type="submit" value="forget password" /> 
							</br>
							<a href="register.php"> Register an account!</a>

						</form>
				
				</div>
				</li>
			</ul>
		</div>
	</div>
	<div id="footer">
		
		
		
		
	</div>
</body>
</html>

