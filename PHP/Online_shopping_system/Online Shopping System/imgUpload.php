<?php   

   $uploaddir = $_SERVER['DOCUMENT_ROOT']."/image/";   
   $type=array("jpg","gif","bmp","jpeg","png","docx","avi");     
   $patch = "./";
  
      function fileext($filename)   
    {   
        return substr(strrchr($filename, '.'), 1);   
    }   
       
    function random($length)   
    {   
        $hash = 'CR-';   
        $chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz';   
        $max = strlen($chars) - 1;   
        mt_srand((double)microtime() * 1000000);   
            for($i = 0; $i < $length; $i++)   
            {   
                $hash .= $chars[mt_rand(0, $max)];   
            }   
        return $hash;   
    }   
   $a=strtolower(fileext($_FILES['file']['name']));   
  
   // if(!in_array(strtolower(fileext($_FILES['file']['name'])),$type))   
   //   {   
   //      $text=implode(",",$type);   
   //      echo "Allowed file types: ",$text,"<br>";   
   //   }   
      
    $filename=explode(".",$_FILES['file']['name']);   
        do   
        {   
            $filename[0]=random(10);   
            $name=implode(".",$filename);   
            //$name1=$name.".Mcncc";   
            $uploadfile=$uploaddir.$name;   
        }   
   while(file_exists($uploadfile));   
        if (move_uploaded_file($_FILES['file']['tmp_name'],$uploadfile)){   

            echo "Uploaded."; 
        }     
?> 