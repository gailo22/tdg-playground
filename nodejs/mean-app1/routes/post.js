const express = require("express");
const router = express.Router();

const uploadImage = require('../middlewares/multer');
const postControler = require("../controllers/postController");
const { hasDescription } = require("../validations/validators");

router.get("/", postControler.index);
router.get("/:id", postControler.show);
router.post("/", uploadImage('posts').single("image"), hasDescription, postControler.store);
router.patch("/:id", hasDescription, postControler.update);
router.delete("/:id", postControler.delete);

module.exports = router;
