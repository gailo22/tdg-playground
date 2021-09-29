#!/usr/bin/env python

import sys
from struct import pack, unpack
import os
import logging
import datetime
import requests
import json

LOGFILE = '/var/log/ejabberd/extauth.log'
ERRFILE = '/var/log/ejabberd/extauth.err'

AUTH_URL = "https://www.mocky.io/v2/5185415ba171ea3a00704eed"

def log(string):
    with open(LOGFILE, 'a') as f:
        f.write(str(datetime.datetime.now()) + ': ' + string + '\n')

def from_ejabberd():
    input_length = sys.stdin.read(2)
    (size,) = unpack('>h', input_length)
    input = sys.stdin.read(size)
    return input.split(':')

def to_ejabberd(bool):
    answer = 0
    if bool:
        answer = 1
    token = pack('>hh', 2, answer)
    logging.debug("writing token %s to stdout", str(token))
    sys.stdout.write(token)
    sys.stdout.flush()

def auth(username, server, password):
    rurl = str(AUTH_URL)
    obj = {'jwtToken': password, 'username': username}
    headers = {'Content-Type': 'application/json'}
    r = requests.post(rurl, data=json.dumps(obj), headers=headers)
    logging.debug("response: %s", r)
    if ((r.status_code == 200 or r.status_code == 202) and (json.loads(r.text)['status'] == 200)):
        return True
    else:
        logging.debug("R-text: %s", str(r.text))
        return False
    # return True

def isuser(username, server):
    return True

def check_password(username, server, password):
    return False

def setpass(username, server, password):
    return False

def loop():
    while True:
        data = from_ejabberd()
        log("data: " + data)
        success = False
        if data[0] == "auth":
            success = auth(data[1], data[2], data[3])
        elif data[0] == "isuser":
            success = isuser(data[1], data[2])
        elif data[0] == "setpass":
            success = setpass(data[1], data[2], data[3])
        elif data[0] == "check_password":
            success = auth(data[1], data[2], data[3])

        logging.debug("success: %s" + success)
        to_ejabberd(success)

if __name__ == "__main__":
    PID = str(os.getpid())
    FMT = '[%(asctime)s] ['+PID+'] [%(levelname)s] %(message)s'
    sys.stderr = open(ERRFILE, 'a+')
    logging.basicConfig(level=logging.DEBUG, format=FMT, filename=LOGFILE)
    try:
        loop()
    except struct.error:
        pass
