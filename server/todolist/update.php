<?php 

require("connection.php");

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $id     = $_POST['id'];
    $email  = $_POST['email'];
    $title  = $_POST['title'];
    $desc   = $_POST['desc'];
    $date   = $_POST['date'];

    if ($id == '' || $email == '' || $title == '' || $desc == '' || $date == '') {
        echo 'fill input';
    }
    else {
        $query = "UPDATE todolist_table SET email_user = '$email', title_todolist = '$title', desc_todolist = '$desc', date_todolist = '$date' WHERE id_todolist = '$id'";
        
        if (mysqli_query($conn, $query)) {
            $response["value"]      = 1;
            $response["message"]    = "success";
        } 
        else {
            $response["value"]      = 0;
            $response["message"]    = "fail";
        }

        echo json_encode($response);
    }
}

else {
    $response["value"] = 0;
    $response["message"] = "oops! something wrong!";
    echo json_encode($response);
}

?>