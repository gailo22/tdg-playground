<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Customers extends CI_Controller {

	public function __construct()
    {
		parent::__construct();

		$this->CI = & get_instance();

        $this->CI->load->model('customer_model', '', TRUE);
	}

	public function index() 
	{
		$customers = $this->CI->customer_model->get_all();

		echo $customers;
		
		return $customers;
	}

}
