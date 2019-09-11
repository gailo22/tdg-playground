// The Cloud Functions for Firebase SDK to create Cloud Functions and setup triggers.
const functions = require('firebase-functions');
const firebase = require("firebase/app");
const {Storage} = require('@google-cloud/storage');
const storage = new Storage();

// Add the Firebase products that you want to use
require("firebase/auth");
require("firebase/firestore");

// The Firebase Admin SDK to access the Firebase Realtime Database.
const admin = require('firebase-admin');
admin.initializeApp();
// const spawn = require('child-process-promise').spawn;
const path = require('path');
const os = require('os');
const fs = require('fs');

var db = admin.firestore();

// const bucket = admin.storage().bucket();

// var storage = firebase.storage();
// var storageRef = storage.ref();
// var receiptRef = storageRef.child('receipt');

// Get a non-default Storage bucket
// var storage = firebase.app().storage("gs://my-custom-bucket");

// Take the text parameter passed to this HTTP endpoint and insert it into the
// Realtime Database under the path /messages/:pushId/original
exports.addMessage = functions.https.onRequest(async (req, res) => {
    // Grab the text parameter.
    const original = req.query.text;
    // Push the new message into the Realtime Database using the Firebase Admin SDK.
    const snapshot = await admin.database().ref('/messages').push({original: original});
    // Redirect with 303 SEE OTHER to the URL of the pushed object in the Firebase console.
    res.redirect(303, snapshot.ref.toString());
});

// Listens for new messages added to /messages/:pushId/original and creates an
// uppercase version of the message to /messages/:pushId/uppercase
exports.makeUppercase = functions.database.ref('/messages/{pushId}/original')
    .onCreate((snapshot, context) => {
      // Grab the current value of what was written to the Realtime Database.
      const original = snapshot.val();
      console.log('Uppercasing', context.params.pushId, original);
      const uppercase = original.toUpperCase();
      // You must return a Promise when performing asynchronous tasks inside a Functions such as
      // writing to the Firebase Realtime Database.
      // Setting an "uppercase" sibling in the Realtime Database returns a Promise.
      return snapshot.ref.parent.child('uppercase').set(uppercase);
    });


// Listen for any change on document `marie` in collection `users`
exports.myFunctionName = functions.firestore
    .document('users/marie')
    .onWrite((change, context) => {
      // ... Your code here
    });

exports.updateUser = functions.firestore
    .document('users/{userId}')
    .onUpdate((change, context) => {
      // Get an object representing the document
      // e.g. {'name': 'Marie', 'age': 66}
      const newValue = change.after.data();

      // ...or the previous value before this update
      const previousValue = change.before.data();

      // access a particular field as you would any JS property
      const name = newValue.name;

      // perform desired operations ...
      
      console.log("newValue: " + newValue);

    });

exports.concurrentUpdate = functions
    .region('us-central1')
    .firestore
    .document('concurrency/{mId}/{makeId}/{randId}')
    .onUpdate(async(change, context) => {
    await db.doc('concurrency/configuration').get().then(doc => {
     if (!doc.exists) {
      console.log('No such document!');
     } else {
      db.collection('concurrency').doc(context.params.mId).collection(context.params.makeId)
       .get()
       .then(docSet => {
        let ccu = docSet.size;
        let counter = 1;
        let loc = Math.floor(new Date().getTime()/1000);
        let myDev = [];
        let myAgent = [];
        docSet.forEach(docu => {
         let serv = Math.floor(docu.data().timestamp.toMillis()/1000);
         if(loc - serv > doc.data().del_time) {
          db.doc('concurrency/'+context.params.mId+'/'+context.params.makeId+'/'+docu.id).delete();
          ccu--;
         }else if(counter <= doc.data().maximum_device){
           myDev.push(docu.id);
           myAgent.push("|"+docu.id+"|"+docu.data().os+"|"+docu.data().nick_name+"|"+docu.data().device_model+"|");
           counter++;
         }
        });
        if(ccu >= doc.data().maximum_device){
           let myData = {"ssoid": context.params.makeId,"utc": loc,"ttl": doc.data().rd_ttl,"device": myDev,"body": myAgent};
           console.log('JSON',myData);
        }
       });
     }
    }).catch(err => {
      console.log('Error getting document', err);
    });
  });

  exports.receiptCreated = functions.storage.object().onFinalize(async (object) => {
    const fileBucket = object.bucket;
    const filePath = object.name;
    const contentType = object.contentType;
    const metageneration = object.metageneration;
    const fileName = path.basename(filePath);
    const bucket = storage.bucket(object.bucket);

    console.log("fileBucket: " + fileBucket);
    console.log("filePath: " + filePath);
    console.log("contentType: " + contentType);
    console.log("metageneration: " + metageneration);
    console.log("fileName: " + fileName);

    if (!fileName.endsWith('.json')) {
      return console.log('Not a json file');
    }

    bucket.file(filePath).download((err, contents) => {
      if (err) {
          console.log('error', err);
          return null
      }

      console.log(contents.toString());
    });

  });