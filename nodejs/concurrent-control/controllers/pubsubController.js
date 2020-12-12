exports.postFromPubSub = async (req, res, next) => {
  try {
    const message = Buffer.from(req.body.message.data, "base64").toString(
      "utf-8"
    );
    console.log(message);
    const messageJson = JSON.parse(message);

    console.log(`Hello, ${messageJson.name}!`);
    console.log(`I can call you, ${messageJson.display_name}!`);

    res.send(`hello post\n`);
  } catch (err) {
    next(err);
  }
};

//{"name": "John", "display_name": "John Wick", "avatar": "https://trueid.net/1232/34324.png"}

exports.getFromPubSub = async (req, res, next) => {
  const name = req.data
    ? Buffer.from(message.data, "base64").toString()
    : "World";

  console.log(`Hello, ${name}!`);
  res.send(`hello get\n`);
};


// app.post('/pubsub/authenticated-push', jsonBodyParser, async (req, res) => {
//     // Verify that the request originates from the application.
//     if (req.query.token !== PUBSUB_VERIFICATION_TOKEN) {
//       res.status(400).send('Invalid request');
//       return;
//     }
  
//     // Verify that the push request originates from Cloud Pub/Sub.
//     try {
//       // Get the Cloud Pub/Sub-generated JWT in the "Authorization" header.
//       const bearer = req.header('Authorization');
//       const [, token] = bearer.match(/Bearer (.*)/);
//       tokens.push(token);
  
//       // Verify and decode the JWT.
//       // Note: For high volume push requests, it would save some network
//       // overhead if you verify the tokens offline by decoding them using
//       // Google's Public Cert; caching already seen tokens works best when
//       // a large volume of messages have prompted a single push server to
//       // handle them, in which case they would all share the same token for
//       // a limited time window.
//       const ticket = await authClient.verifyIdToken({
//         idToken: token,
//         audience: 'example.com',
//       });
  
//       const claim = ticket.getPayload();
//       claims.push(claim);
//     } catch (e) {
//       res.status(400).send('Invalid token');
//       return;
//     }
  
//     // The message is a unicode string encoded in base64.
//     const message = Buffer.from(req.body.message.data, 'base64').toString(
//       'utf-8'
//     );
  
//     messages.push(message);
  
//     res.status(200).send();
//   });
