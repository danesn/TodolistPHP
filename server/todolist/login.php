<?php 

require("connection.php");

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $email      = $_POST['email'];
    $password   = $_POST['password'];

    if ($email == '' || $password == '') {
        echo 'fill input';
    }
    else {
        $query = "SELECT * FROM user WHERE email_user = '$email' AND password_user = '$password'";
        $result = mysqli_query($conn, $query);

        if (mysqli_num_rows($result) == 1) {
            $response["value"] = 1;
            while ($row = mysqli_fetch_assoc($result)) {
                $name = $row['name_user'];
            }
            
            $response["message"] = "$name";
            echo json_encode($response);
        }
        else {
            $response["value"] = 0;
            $response["message"] = "wrong email or password";
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