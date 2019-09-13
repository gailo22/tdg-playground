<?php

if ($this->db->query('select * from customer')) {
    echo "Success!";
} else {
    echo "Query failed!";
}

?>