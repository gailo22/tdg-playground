var admin = require("firebase-admin");

var serviceAccount = require("/Users/montreebungnasang/dev/playground/android/FCM/sender-app/sample-fcm-serviceAccount.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});


// This registration token comes from the client FCM SDKs.
const registrationToken = 'fkFPFeUjR-yyxwop6RW9-y:APA91bFjkjr3roWWK6l11PazKhEa6rXGSd5XHLorblEZ4rDmqHY6DWHYYOaN6fAkqb2wIXLGhvdw5N3HimXO_cck--75MtmUDudtvL-mlbRbx6m9881f9USTHa2HvDfMEQL1yec_azRA';

const message = {
  data: {
    score: '850',
    time: '2:45'
  },
  token: registrationToken
};

// Send a message to the device corresponding to the provided
// registration token.
admin.messaging().send(message)
  .then((response) => {
    // Response is a message ID string.
    console.log('Successfully sent message:', response);
  })
  .catch((error) => {
    console.log('Error sending message:', error);
  });