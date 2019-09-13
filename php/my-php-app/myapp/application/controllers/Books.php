<?php

class Books extends CI_Controller
{
   use REST_Controller {
    REST_Controller::__construct as private __resTraitConstruct;
  }
  public function index_get()
  {
    // Display all books
  }

  public function index_post()
  {
    // Create a new book
  }
}



?>