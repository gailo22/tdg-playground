<?php
defined('BASEPATH') OR exit('No direct script access allowed');

require(APPPATH.'/libraries/REST_Controller.php');
use Restserver\Libraries\REST_Controller;

class Api extends REST_Controller
{

       public function __construct() {
            parent::__construct();
            $this->CI = & get_instance();

            $this->CI->load->model('customer_model', '', TRUE);

       }   

       public function customers_get() {
           $r = $this->CI->customer_model->get_all();
           $this->response($r); 
       }

       public function customersrand_get() {
        $r = $this->CI->customer_model->get_random();
        $this->response($r); 
    }


    //    public function user_put(){
    //        $id = $this->uri->segment(3);

    //        $data = array('name' => $this->input->get('name'),
    //        'pass' => $this->input->get('pass'),
    //        'type' => $this->input->get('type')
    //        );

    //         $r = $this->user_model->update($id,$data);
    //            $this->response($r); 
    //    }

    //    public function user_post(){
    //        $data = array('name' => $this->input->post('name'),
    //        'pass' => $this->input->post('pass'),
    //        'type' => $this->input->post('type')
    //        );
    //        $r = $this->user_model->insert($data);
    //        $this->response($r); 
    //    }
    //    public function user_delete(){
    //        $id = $this->uri->segment(3);
    //        $r = $this->user_model->delete($id);
    //        $this->response($r); 
    //    }
    }


