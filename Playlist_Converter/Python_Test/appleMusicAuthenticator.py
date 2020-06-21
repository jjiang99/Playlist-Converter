'''
Created on Jun. 19, 2020

@author: justi
'''
from datetime import datetime
import jwt
import requests
import json


secret = """-----BEGIN PRIVATE KEY-----
MIGTAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBHkwdwIBAQQg1zjSooejCIPTHpci8joGxMdczELa1VTjLMsGtJpACbmgCgYIKoZIzj0DAQehRANCAATfDdFeyIFnV8BNBJgjkyJxVKPzm9nPurXIIwrebRocIlztTVZees+AAUvjw2YONnbiTByu43hSvoce3s+HaVba
-----END PRIVATE KEY-----"""
keyId = "JT5J8K27HR"
teamId = "77V6T6PAVC"
alg = 'ES256'


headers = {
    'alg': alg,
    'kid': keyId
}
payload = {
    'iss': teamId,  # issuer
    'iat': int(datetime.now().timestamp()),  # issued at
    'exp': int(datetime.now().timestamp())  # expiration time
}


if __name__ == "__main__":
    """Create an auth token"""

    token = jwt.encode(payload, secret, algorithm=alg, headers=headers)
    token_str = token.decode()
    print(token_str)
    url = "https://api.music.apple.com/v1/catalog/us/playlists/zp6KmqOsmopkRN"
    # print (f"curl -v -H 'Authorization: Bearer {token_str}' {url}")
    res = requests.get(url, headers={'Authorization': "Bearer " +  token_str})
    if res == None or res == '':
        print("none")
    else:
        result = json.loads(res.text)
        print(str(result).encode("utf-8"))
