const express = require("express");
const router = express.Router();

const concurrentControler = require("../controllers/concurrentController");

// router.get("/", concurrentControler.index);
router.get("/:id/:deviceId", concurrentControler.get);
// router.patch("/:id", concurrentControler.update);
// router.delete("/:id", concurrentControler.delete);

module.exports = router;
