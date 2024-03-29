import * as functions from 'firebase-functions';

// // Start writing Firebase Functions
// // https://firebase.google.com/docs/functions/typescript
//
// export const helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });

export const helloWorld = functions
    .region('asia-east2')
    .https
    .onRequest((request, response) => {
        response.send("Hello from Firebase!");
    });
