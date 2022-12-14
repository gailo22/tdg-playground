const axios = require('axios');
const axiosRetry = require('axios-retry');
const https = require('https');

module.exports  = {

    getUser: async function (userId) {
        const client = axios.create();

        axiosRetry(client, {
            retries: 3,
            retryDelay: (retryCount) => {
              console.log("retry attempt:",retryCount);
              return retryCount * 100;
            },
            retryCondition: (error) => {
              console.error("error code:",error.response.statusText);
              return error.code == 'ERR_BAD_RESPONSE';
              //return error.response.statusText === "Request Timeout" || error.code === "ETIMEDOUT";
            },
        });

        let URL = "https://mock.codes/500";
		var res = client({
			url: URL,
			method: 'GET',
			headers: { 
                'Content-Type': 'application/json'
            },
			responseType: 'json',
			httpsAgent: new https.Agent({ rejectUnauthorized: false })
		}).then(function (response) {
			if (response && response.status == 200) {
				return response.data;
			} else {
                return null;				
			}
		}).catch(function (error) {
            return null;
        });

        return res;
    },

    getUsers: async function () {

        let URL = "https://mock.codes/400";
		var res = axios({
			url: URL,
			method: 'GET',
			headers: { 
                'Content-Type': 'application/json'
            },
			responseType: 'json',
			httpsAgent: new https.Agent({ rejectUnauthorized: false })
		}).then(function (response) {
			if (response && response.status == 200) {
				return response.data;
			} else {
                return null;				
			}
		}).catch(function (error) {
            return null;
        });

        return res;
    }

};

