<?php 

require("connection.php");

if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    $id      = $_GET['id'];

    if ($id == '') {
        echo 'fill input';
    }
    else {
        $query = "DELETE FROM todolist_table WHERE id_todolist = '$id'";
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