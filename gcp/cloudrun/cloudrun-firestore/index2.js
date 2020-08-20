const express = require('express');
const app = express();

const admin = require('firebase-admin');

app.get('/', (req, res) => {
  console.log('Hello world received a request.');

  const target = process.env.TARGET || 'World';
  res.send(`Hello ${target}!`);
});

const port = process.env.PORT || 8080;
app.listen(port, () => {
  console.log('Hello world listening on port', port);
});


let db = initializeAppSA()

function initializeAppSA() {
    // [START initialize_app_service_account]
  let serviceAccount = require('./serviceAccountKey.json');
  admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL: "https://ea-poc-webrtc-2.firebaseio.com"
  });

  let db = admin.firestore();
  // [END initialize_app_service_account]
  return db;
}

// firebase document structure
// collection /     document  /sub collection/document  /sub collection/document
// concurrency/<sso end digit>/ssoIds        /<sso id>  /devices       /<device id>
// concurrency/      0        /ssoIds        /0984448330/devices       /453534535


for (i = 0; i <= 9; i++) {
  let collection = db.collection(`concurrency/${i}/ssoIds`);

  observe(collection);
}

function observe(collection) {
  let observer = collection.onSnapshot(querySnapshot => {
    console.log(`Received query snapshot of size ${querySnapshot.size}`);
    
    querySnapshot.docChanges().forEach(change => {
      if (change.type === 'added') {
        console.log('Added: ', change.doc.ref.path);
        // call streaming api
        observe(db.collection(`${change.doc.ref.path}/devices`));
      }
      if (change.type === 'modified') {
        console.log('Modified: ', change.doc.data());
        // call streaming api
      }
      if (change.type === 'removed') {
        console.log('Removed: ', change.doc.data());
        // call streaming api
      }
    });
  });
}

function observeDoc(doc) {
  let observer = doc.onSnapshot(docSnapshot => {
    console.log(`Received doc snapshot: ${docSnapshot}`);
    console.log("data: ", docSnapshot.data());
  }, err => {
    console.log(`Encountered error: ${err}`);
  });
}



