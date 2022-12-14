const user_service = require('../services/userService');


exports.get_user = async (req, res) => {
    let userId = req.params.userId;
    let data = await user_service.getUser(userId);
    res.send(data);
};

exports.get_users = async (req, res) => {
    let data = await user_service.getUsers();
    res.send(data);
};
