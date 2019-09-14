const express = require("express");
const router = express.Router();

const authController = require("../controllers/authController");
const passportJWT = require('../middlewares/passportJWT')();

const {
  isEmail,
  hasName,
  hasPassword
} = require("../validations/validators");

router.post("/login", authController.login);
router.post("/signup", [isEmail, hasName, hasPassword], authController.signup);
router.get("/me", passportJWT.authenticate(), authController.me);

module.exports = router;
