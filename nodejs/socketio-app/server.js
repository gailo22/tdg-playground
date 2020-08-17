var express = require('express'),
    app = express(),
    http = require('http'),
    socketIO = require('socket.io'),
    server, io;

app.get('/', (req, res) => {
    res.sendFile(__dirname + '/index.html');
});

server = http.Server(app);
var port = 5000;
server.listen(port, () => {
    console.log(`app listening at http://localhost:${port}`)
  })

io = socketIO(server);

io.on('connection', (socket) => {
    socket.on('message.send', (data) => {
        io.emit('message.sent', data);
    });
});
