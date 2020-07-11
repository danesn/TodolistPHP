<?php 

require("connection.php");

$array = array();

if ($_SERVER['REQUEST_METHOD'] == 'GET') {

    $email  = $_GET["email"];
    $query  = "SELECT * FROM todolist_table WHERE email_user = '$email'";
    $result = mysqli_query($conn, $query);
    

    while ($row = mysqli_fetch_assoc($result)) {
        array_push($array, array(
            'id' => $row['id_todolist'], 
            'titleToDoList' => $row['title_todolist'], 
            'descToDoList' => $row['desc_todolist'], 
            'dateToDoList' => $row['date_todolist']));
    };
}

echo json_encode($array);
mysqli_close($conn);
    

    

?>