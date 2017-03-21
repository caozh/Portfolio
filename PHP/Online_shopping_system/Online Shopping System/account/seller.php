<?php
    $msg = "";
    
    if(filter_has_var(INPUT_POST,"submitForm")) {
        //initialize variables
        $productName = trim($_POST['productName']);;
        $price = trim($_POST['price']);;
        $category = trim($_POST['category']);;
        $quantity = trim($_POST['quantity']);;
        $description = trim($_POST['description']);;
        $check = true;
        //echo $productName."<br>".$price."<br>".$category."<br>".$quantity."<br>".$description."<br>";
        
        //validation
        if ($check) {
         insertDB();
        }
    }
    
    function insertDB () {
        global $msg,$productName,$price,$category,$quantity,$description;
        
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
        
        //insert data into database
        $sql = "INSERT INTO PRODUCT ( Name,          Category,   Price,      Quantity,    Description)
                             VALUES ('$productName','$category','$price','$quantity','$description')";
        if ($mysqli->query($sql) === TRUE) {
            $msg = $msg.'<br/><b>New record created successfully</b>';
        } else {
            echo "Error: " . $sql . "<br>" . $mysqli->error;
        } 
        $mysqli->close();            
    }
    
    function showProduct() {
        global $msg;
        
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
        
        $sql = "SELECT * FROM PRODUCT";
        if ($result = $mysqli->query($sql)) {
            //fetch associative array
            while ($row = $result->fetch_assoc()){
                printf ("Name: %s <br>Category: %s <br>Price: %s <br>Quantity: %s <br>Description: %s<br><br>",$row["Name"],$row["Category"],$row["Price"],$row["Quantity"],$row["Description"]);
            }
            $result->free();
        }
        
        $mysqli->close();        
        
    }
?>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html" charset=UTF-8" />
    <title>Seller Account</title>
    <link rel="stylesheet" type="text/css" href="Account.css" />
</head>

<body> 
    <div class="banner">
        <h1>Seller Account</h1>
    </div>
    <div class="container">
        <div class="main-content">
            <!--Left Div-->
            <div class="left-content">
                <form action="" method="POST">
                    <input type="submit" value="Add Product" name = "add">
                    <input type="submit" value="Edit Product" name = "edit">
                    <input type="submit" value="View Order" name = "view">
                </form>
            </div>
            
            <!--Right Div-->
            <div class="right-content">
<?php
    print $msg;
    $msg = "";
    
    if(filter_has_var(INPUT_POST,"add")){
        showAdd();
    } elseif(filter_has_var(INPUT_POST,"edit")){
        showEdit();
    } elseif(filter_has_var(INPUT_POST,"view")){
        showView();
    } else {
        showAdd();
    }
    
    function showAdd() {
        print <<< HERE
                <form class  = "form" 
                      method = "POST">
                    <fieldset>
                        <legend>Product Information:</legend>
                            <label for="name">Product Name</label>
                            <input type="text" name="productName" id="name"/> <br>
                            
                            <label for="price">Price</label>
                            <input type="text" name="price" id="price"/> <br>
                            
                            <label for="category">Category</label>
                            <input type="text" name="category" id="category"/> <br>
                            
                            <label for="quantity">Quantity</label>
                            <input type="text" name="quantity" id="quantity"/> <br>
                            
                            <label for="description">Description</label>
                            <textarea name="description" rows="4" cols="50"></textarea><br>
                            <input type="submit" value="Submit" name="submitForm">
                    </fieldset>
                </form>        
HERE;
    }

    function showEdit() {
        print <<< HERE
                <form class  = "form" 
                      method = "POST">
                    <fieldset>
                        <legend>Edit Product:</legend>
HERE;
        //display product table
        showProduct();

        print <<< HERE
                    </fieldset>
                </form>        
HERE;
    }
    
    function showView() {
        print <<< HERE
                <form class  = "form" 
                      method = "POST">
                    <fieldset>
                        <legend>View Order:</legend>
                        <p>Under developing</p>
                    </fieldset>
                </form>        
HERE;
    }    
?>
            </div>
        </div>
    </div>







    
</body>
</html>