import * as functions from 'firebase-functions'
import * as admin from 'firebase-admin'
admin.initializeApp()

export const function2Update =
    functions.firestore.document('city-weather/boston-ma-us')
        .onUpdate(change => {
            const after = change.after.data()
            const payload = {
                data: {
                    temp: String(after?.temp),
                    conditions: after?.conditions
                }
            }
            return admin.messaging().sendToTopic('weather_boston-ma-us', payload)
        })

export const function1Http = functions.https.onRequest((request, response) => {
    admin.firestore().doc('city-weather/boston-ma-us').get()
        .then(snapshot => {
            const data = snapshot.data()
            response.send(data)
        })
        .catch(error => {
            console.error(error)
            response.status(500).send(error)
        })
})



