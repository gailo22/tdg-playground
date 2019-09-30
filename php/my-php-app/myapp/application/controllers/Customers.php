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
		$start = microtime(true);
		$customers = $this->CI->customer_model->get_all();
		$time_elapsed_ms = (microtime(true) - $start) * 1000;

		log_message('info', 'Total execution time 123: '.$time_elapsed_ms);
		
		return $customers;
	}

}
