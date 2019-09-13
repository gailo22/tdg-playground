<?php

class Customer_model extends CI_Model {

    public function __construct()
    {
        parent::__construct();

        $this->load->helper('rndstring');

    }

    public function get_all() {
        $query = $this->db->get('customer', 10);
        return $query->result_array();
    }

    public function get_random() {
        $keyword = substr(md5(mt_rand()), 0, 7);
        $query = $this->db->select('*')->from('customer')->where("name LIKE '%$keyword%'")->get();
        return $query->result_array();
    }

}
