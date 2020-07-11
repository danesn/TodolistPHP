<?php 

require("connection.php");

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    
    $email      = $_POST["email"];
    $title      = $_POST["title"];
    $desc       = $_POST["desc"];
    $date       = $_POST["date"];

    if ($email == '' || $title == '' || $desc == '' || $date == '') {
        echo 'fill input';
    }
    else {

        $query = "INSERT INTO todolist_table (id_todolist, email_user, title_todolist, desc_todolist, date_todolist) VALUES (NULL, '$email', '$title', '$desc', '$date')";
        $result = mysqli_query($conn, $query);
    
        $response["value"]      = 1;
        $response["message"]    = "success";
        echo json_encode($response);

    }
}
else {
    $response["value"] = 0;
    $response["message"] = "oops! something wrong!";
    echo json_encode($response);
}


?>