#!/usr/bin/env python

import sys
import struct
import logging
import os
import urllib2
import urllib
import json

LOGFILE = '/var/log/ejabberd/extauth.log'
ERRFILE = '/var/log/ejabberd/extauth.err'
AUTH_URL= "https://www.mocky.io/v2/5185415ba171ea3a00704eed"

def auth_login(token):
    try:
        headers = {'Content-Type': 'application/json'}
        payload = {'token': token }

        url = "{}".format(AUTH_URL) 
        logging.info('Url formatted: %s', url)
        request = urllib2.Request(url, json.dumps(payload), headers)
        response = json.load(urllib2.urlopen(request))
        logging.info('Response: %s', response)
    except urllib2.HTTPError as http_err:
        return False
    except Exception as e:
        logging.exception("Error authenticating user")
        return False

    return response['hello']

def write(flag):
    logging.debug("write(): %s", flag)

def read():
    (pkt_size,) = struct.unpack('>H', sys.stdin.read(2))
    pkt = sys.stdin.read(pkt_size).split(':')
    logging.debug("pkt: %s", pkt)
    cmd = pkt[0]
    args_num = len(pkt) - 1
    if cmd == 'auth' and args_num >= 3:
        logging.debug('User trying to auth')
        is_valid_user = auth_login(pkt[3])

        if is_valid_user:
            logging.debug('Logged User :'+pkt[1]+":"+pkt[2]+":"+pkt[3])
            write(True)
        else:
            logging.info('Error on authenticating user:'+pkt[1]+":"+pkt[2]+":"+pkt[3])
            write(False)
    elif cmd == 'isuser' and args_num == 2:
        logging.debug('isuser received')
        logging.debug(pkt[0]+":"+pkt[1])
        write(False)
    elif cmd == 'setpass' and args_num >= 3:
        logging.debug('setpass received')
        logging.debug(pkt[0]+":"+pkt[1]+":"+pkt[2])
        write(False)
    elif cmd == 'tryregister' and args_num >= 3:
        logging.debug('tryregister received')
        logging.debug(pkt[0]+":"+pkt[1]+":"+pkt[2])
        write(False)
    elif cmd == 'removeuser' and args_num == 2:
        logging.debug('removeuser received')
        logging.debug(pkt[0]+":"+pkt[1])
        write(False)
    elif cmd == 'removeuser3' and args_num >= 3:
        logging.debug('removeuser3 received')
        logging.debug(pkt[0]+":"+pkt[1]+":"+pkt[2])
        write(False)
    else:
        write(False)
    read()

def loop():
    while True:
        try:
            read()
        except KeyboardInterrupt:
            logging.info('Terminating by user input')
            break
        except Exception as e:
            logging.exception('Input error: ')
            break

if __name__ == "__main__":
    PID = str(os.getpid())
    FMT = '[%(asctime)s] ['+PID+'] [%(levelname)s] %(message)s'
    sys.stderr = open(ERRFILE, 'a+')
    logging.basicConfig(level=logging.DEBUG, format=FMT, filename=LOGFILE)
    try:
        loop()
    except struct.error:
        pass