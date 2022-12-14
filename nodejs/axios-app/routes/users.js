var express = require('express');
var router = express.Router();

const user_controller = require("../controllers/userController");

router.get('/:userId', user_controller.get_user);
router.get('/', user_controller.get_users);

module.exports = router;
