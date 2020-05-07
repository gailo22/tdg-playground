import * as functions from 'firebase-functions';
import { db } from './init';

const request = require('request');

// const runtimeOpts = {
//     timeoutSeconds: 60,
//     memory: '2GB'
// }

async function post_data(requestData) {
    // await Promise.all([
    //     post_data_url('https://cclimit.stm.trueid.net/setssoid.php', requestData),
    //     post_data_url('http://107.178.253.19/setssoid.php', requestData),
    //     post_data_url('https://cclimit-tt2.stm.trueid.net/setssoid.php', requestData)
    // ]).catch(error => {
    //     console.error(error.message);
    // });
    console.log(requestData);
    await Promise.all[
        post_data_url('https://httpbin.org/post', requestData),
        post_data_url('https://httpbin.org/post', requestData),
        post_data_url('https://httpbin.org/post', requestData)
    ];
}

function post_data_url(url, requestData) {
    return request.post(url, {
        json: requestData
    }, (error, res, body) => {
        if (error) {
            console.error(error.message);
            return;
        }
    });
}

export const STMccuCreation = functions
    // .runWith(runtimeOpts)
    .region('us-central1')
    .firestore
    // /concurrency/0/21986030/4D2CD12C-0D66-4106-BC20-05CB62743F13
    // /concurrency/1/26115581/3E073D58-8802-4819-B43E-5AEBFE239F64
    // /collection/d/collection/doc/fields
    .document('concurrency/{endDigit}/{makeId}/{randId}')
    .onCreate(async (snap, context) => {
        // /concurrency/configuration
        const confDoc = await db.doc('concurrency/configuration').get();
        const confDocData = confDoc.data();
        console.log('confDocData: ', confDocData);

        if (!confDoc.exists) {
            console.log('No such document!');
        } else {
            const makeIdRef = snap.ref.parent;
            const makeIdSnap = await makeIdRef.get();

            let ccu = makeIdSnap.size;
            let counter = 1;
            let loc = Math.floor(new Date().getTime() / 1000);
            let myDev: any = [];
            let myAgent: any = [];
            makeIdSnap.forEach(doc => {
                const docData = doc.data();
                const docId = doc.id;
                console.log('docData: ', docData);

                let serv = Math.floor(docData.timestamp.toMillis() / 1000);
                console.log('loc: ', loc);
                console.log('serv: ', serv);
                console.log('loc - serv: ', loc - serv);
                if ((loc - serv) >= confDocData.del_time) {
                    db.doc('concurrency/' + context.params.endDigit + '/' + context.params.makeId + '/' + docId).delete();
                    ccu--;
                } else if (counter <= confDocData.maximum_device) {
                    myDev.push(docId);
                    myAgent.push("|" + docId + "|" + docData.os + "|" + docData.nick_name + "|" + docData.device_model + "|");
                    counter++;
                }
            });

            console.log('ccu: ', ccu);
            console.log('counter: ', counter);

            if (ccu >= confDocData.maximum_device) {
                let myData = { "ssoid": context.params.makeId, "utc": loc, "ttl": confDocData.rd_ttl, "device": myDev, "body": myAgent };
                console.log('Create :: JSON', myData);
                await post_data(myData);
            }
        }

        // .then(doc => {
        //     if (!doc.exists) {
        //         console.log('No such document!');
        //     } else {
        //         db.collection('concurrency').doc('0').collection(context.params.makeId)
        //             .get()
        //             .then(documentSet => {
        //                 let ccu = documentSet.size;
        //                 let counter = 1;
        //                 let loc = Math.floor(new Date().getTime() / 1000);
        //                 let myDev = [];
        //                 let myAgent = [];
        //                 documentSet.forEach(docu => {
        //                     let serv = Math.floor(docu.data().timestamp.toMillis() / 1000);
        //                     if (loc - serv >= doc.data().del_time) {
        //                         db.doc('concurrency/0/' + context.params.makeId + '/' + docu.id).delete();
        //                         ccu--;
        //                     } else if (counter <= doc.data().maximum_device) {
        //                         myDev.push(docu.id);
        //                         myAgent.push("|" + docu.id + "|" + docu.data().os + "|" + docu.data().nick_name + "|" + docu.data().device_model + "|");
        //                         counter++;
        //                     }
        //                 });
        //                 if (ccu >= doc.data().maximum_device) {
        //                     let myData = { "ssoid": context.params.makeId, "utc": loc, "ttl": doc.data().rd_ttl, "device": myDev, "body": myAgent };
        //                     console.log('Create 0 :: JSON', myData);
        //                     post_data(myData);
        //                 }
        //             });
        //     }
    });

// export const STMccuUpdate0 = functions
//     // .runWith(runtimeOpts)
//     .region('us-central1')
//     .firestore
//     .document('concurrency/0/{makeId}/{randId}')
//     .onUpdate(async (change, context) => {
//         const confDoc = await db.doc('concurrency/configuration').get();
//         console.log(confDoc.data());

//         if (!confDoc.exists) {
//             console.log('No such document!');
//         } else {
//             const makeIdRef = change.ref.parent;
//             const makeIdSnap = await makeIdRef.get();
//             makeIdSnap.forEach(documentSet => {
//                 console.log(documentSet.data());
//             });
//         }

//     });


// exports.STMccuUpdate0 = functions
//     .runWith(runtimeOpts)
//     .region('us-central1')
//     .firestore
//     .document('concurrency/0/{makeId}/{randId}')
//     .onUpdate(async (change, context) => {
//         await db.doc('concurrency/configuration').get().then(doc => {
//             if (!doc.exists) {
//                 console.log('No such document!');
//             } else {
//                 db.collection('concurrency').doc('0').collection(context.params.makeId)
//                     .get()
//                     .then(docSet => {
//                         let ccu = docSet.size;
//                         let counter = 1;
//                         let loc = Math.floor(new Date().getTime() / 1000);
//                         let myDev = [];
//                         let myAgent = [];
//                         docSet.forEach(docu => {
//                             let serv = Math.floor(docu.data().timestamp.toMillis() / 1000);
//                             if (loc - serv > doc.data().del_time) {
//                                 db.doc('concurrency/0/' + context.params.makeId + '/' + docu.id).delete();
//                                 ccu--;
//                             } else if (counter <= doc.data().maximum_device) {
//                                 myDev.push(docu.id);
//                                 myAgent.push("|" + docu.id + "|" + docu.data().os + "|" + docu.data().nick_name + "|" + docu.data().device_model + "|");
//                                 counter++;
//                             }
//                         });
//                         if (ccu >= doc.data().maximum_device) {
//                             let myData = { "ssoid": context.params.makeId, "utc": loc, "ttl": doc.data().rd_ttl, "device": myDev, "body": myAgent };
//                             console.log('Update 0 :: JSON', myData);
//                             post_data(myData);
//                         }
//                     });
//             }
//         }).catch(err => {
//             console.log('Error getting document', err);
//         });
//     });
