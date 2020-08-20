const express = require('express');
const app = express();

const admin = require('firebase-admin');

app.get('/', (req, res) => {
  console.log('Hello world received a request.');

  const target = process.env.TARGET || 'World';
  res.send(`Hello ${target}!`);
});

app.get('/0/3333333330', (req, res) => {
  console.log('add collection');

  let collection = db.collection('concurrency').doc('0').collection('3333333330');
  collection.add({
      os: 'ios'
  });
  
  observe(collection);

  res.send(`ok`);
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
// collection /     document     /sub collection/document/
// concurrency/<mobile end digit>/<mobile no>   /<docId>/
// concurrency/      0           /08383837370   /qkBJZxlyurcfH7NQVWr0/

let doc = db.collection('concurrency').doc('0');

doc.listCollections().then(collections => {
  collections.forEach(collection => {
    console.log('Found subcollection with id:', collection.id);

    observe(collection);

  });
});


function observe(collection) {
  let observer = collection.onSnapshot(querySnapshot => {
    console.log(`Received query snapshot of size ${querySnapshot.size}`);
    querySnapshot.docChanges().forEach(change => {
      if (change.type === 'added') {
        console.log('Added: ', change.doc.data());
      }
      if (change.type === 'modified') {
        console.log('Modified: ', change.doc.data());
      }
      if (change.type === 'removed') {
        console.log('Removed: ', change.doc.data());
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
