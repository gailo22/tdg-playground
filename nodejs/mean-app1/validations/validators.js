const { body } = require("express-validator/check");

exports.hasDescription = body("description")
  .isLength({ min: 5 })
  .withMessage("Description is required, Min length is 5 characters");

exports.isEmail = body("email")
  .isEmail()
  .withMessage("Email field must contains a correct email");

exports.hasPassword = body("password")
  .exists()
  .withMessage("Password cannot be empty");

exports.hasName = body("name")
  .isLength({ min: 5 })
  .withMessage("Name is required, Min length is 5 characters");
