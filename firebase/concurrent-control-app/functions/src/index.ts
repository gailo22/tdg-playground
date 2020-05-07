import * as functions from 'firebase-functions';
import {db} from './init';


import * as express from "express";
const cors = require('cors');

const app = express();

app.use(cors({origin:true}));


app.get('/courses', async (request, response) => {

    const snaps = await db.collection('courses').get();

    const courses:any[] = [];

    snaps.forEach(snap => courses.push(snap.data()));

    response.status(200).json({courses});

});

app.get('/configs', async (request, response) => {

    const docRef = await db.doc('concurrency/configuration').get();

    response.status(200).json(docRef.data());

});


export const api = functions.https.onRequest(app);

export {onAddLesson, onDeleteLesson} from './lessons-counter';

export {STMccuCreation} from './stm-ccu';