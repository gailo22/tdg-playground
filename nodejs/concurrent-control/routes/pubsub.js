const express = require("express");
const router = express.Router();

const pubsubControler = require("../controllers/pubsubController");
 
router.get("/hello", pubsubControler.getFromPubSub);
router.post("/hello", pubsubControler.postFromPubSub);

module.exports = router;
