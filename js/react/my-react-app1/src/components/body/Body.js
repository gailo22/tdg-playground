import { useEffect, useState } from 'react'
import axios from 'axios'

function Body() {

    const authCodeURL = 'https://superapp-poc.montree.me/miniapp/api/v1/authcode';
    const userInfoURL = 'https://superapp-poc.montree.me/miniapp/api/v1/userinfo';
    // const authCodeURL = 'http://localhost:9081/api/v1/authcode';
    // const userInfoURL = 'http://localhost:9081/api/v1/userinfo';

    const authCode = "JWNTcmuKDurrwUL";

    function resolveAfter2Seconds() {
        return new Promise((resolve) => {
          setTimeout(() => {
            resolve('resolved');
          }, 2000);
        });
      }
    const [userInfo, setUserInfo] = useState([])
    useEffect(() => {
        (async () => {

            const a = await resolveAfter2Seconds();
            console.log(a);
            const firstRequest = await axios.post(authCodeURL, {
                "code": authCode
                });
            const data1 = firstRequest.data;
        
            const secondRequest = await axios.post(userInfoURL, {
                "accessToken": data1.accessToken,
                "authCode": authCode 
            });
        
            const data2 = secondRequest.data;
            setUserInfo(data2.userInfo);

          })();

        }, []);

    console.log(userInfo);

    return (
        <>
            <div>User Name: {userInfo.userCode}!</div>
            <div>First Name: {userInfo.firstName}!</div>
            <div>Last Name: {userInfo.lastName}!</div>
        </>
    )
}

export default Body;