<?php 

require("connection.php");

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $name       = $_POST["name"];
    $email      = $_POST['email'];
    $password   = $_POST['password'];

    if ($email == '' || $password == '' || $name == '') {
        echo 'fill input';
    }
    else {

        $query = "SELECT * FROM user WHERE email_user = '$email'";
        $checkEmail = mysqli_query($conn, $query);
        
        if (mysqli_num_rows($checkEmail) == 1) {
            $response["value"]      = 0;
            $response["message"]    = "Email already used";
            echo json_encode($response);
        }
        else {

            $query = "INSERT INTO user (email_user, name_user, password_user) VALUES ('$email', '$name', '$password')";
            $result = mysqli_query($conn, $query);
    
            $response["value"]      = 1;
            $response["message"]    = "success";
            echo json_encode($response);
            

        }

        mysqli_close($conn);
    }
}
else {
    $response["value"] = 0;
    $response["message"] = "oops! something wrong!";
    echo json_encode($response);
}


?>