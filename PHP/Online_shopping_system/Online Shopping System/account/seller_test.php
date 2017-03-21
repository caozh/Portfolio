<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html" charset=UTF-8" />
    <title>Seller Account</title>
    <link rel="stylesheet" type="text/css" href="Account.css" />
</head>

<body> 
<?php
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
    //change db to another 
    //$mysqli->select_db("Data base name");
    
    //return name of current default database 
    if ($result = $mysqli->query("SELECT DATABASE()")) {
        $row = $result->fetch_row();
        printf("Default database is %s.\n", $row[0]);
        $result->free();
    }
    
    $sql = "SELECT * FROM Demo";
	if ($result = $mysqli->query($sql)) {
        //fetch associative array
        while ($row = $result->fetch_assoc()){
            printf ("<br>Name: %s Gender: %s Age: %s",$row["Name"],$row["Gender"],$row["Age"]);
        }
        $result->free();
    }
    
    $mysqli->close();
?>
    <div class="banner">
        <h1>Seller Account</h1>
    </div>
    <div class="container">
        <div class="main-content">
            <!--Left Div-->
            <div class="left-content">
                <p>Add Product</p>
                <p>Edit Product</p>
                <p>View Orders</p>
                </ul>
            </div>
            
            <!--Right Div-->
            <div class="right-content">
                <form class  = "form" 
                      method = "POST">
                    <fieldset>
                        <legend>Product Information:</legend>
                            <label for="name">Product Name</label>
                            <input type="text" name="name" id="name"/> <br>
                            
                            <label for="price">Price</label>
                            <input type="text" name="price" id="price"/> <br>
                            
                            <label for="category">Category</label>
                            <input type="text" name="category" id="category"/> <br>
                            
                            <label for="quantity">Quantity</label>
                            <input type="text" name="quantity" id="quantity"/> <br>
                            
                            <label for="description">Description</label>
                            <textarea name="description" rows="4" cols="50"></textarea><br>
                            <input type="submit" value="Submit">
                    </fieldset>
                </form>
            </div>
        </div>
    </div>







    
</body>
</html>