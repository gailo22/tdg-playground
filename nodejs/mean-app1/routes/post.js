const express = require("express");
const router = express.Router();

const postControler = require("../controllers/postController")

router.get('/', postControler.index);

module.exports = router;