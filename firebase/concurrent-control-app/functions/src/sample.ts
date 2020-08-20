'use strict';

import cbor = require('cbor');

import * as admin from "firebase-admin";
import * as functions from 'firebase-functions';
const iot = require('@google-cloud/iot');
const client = new iot.v1.DeviceManagerClient();

// start cloud function
exports.configUpdate = functions.firestore
  // assumes a document whose ID is the same as the deviceid
  .document('device-configs/{deviceId}')
  .onWrite(async (change: functions.Change<admin.firestore.DocumentSnapshot>, 
    context?: functions.EventContext) => {
    if (context) {
      console.log(context.params.deviceId);
      const request = generateRequest(context.params.deviceId, change.after.data(), false);
      return client.modifyCloudToDeviceConfig(request);
    } else {
      throw(Error("no context from trigger"));
    }
  });
  

// git clone https://github.com/GoogleCloudPlatform/community.git
// cd community/tutorials/cloud-iot-firestore-config