<?php
class Products extends CI_Controller {

    // http://localhost:8000/products/shoes/sandals/123
    public function shoes($sandals, $id)
    {
        echo $sandals;
        echo $id;
    }
}

?>