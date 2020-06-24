'''
Created on Jun. 19, 2020

@author: justi
'''
import datetime
import jwt
import requests
import json


secret = """-----BEGIN PRIVATE KEY-----
MIGTAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBHkwdwIBAQQg1zjSooejCIPTHpci8joGxMdczELa1VTjLMsGtJpACbmgCgYIKoZIzj0DAQehRANCAATfDdFeyIFnV8BNBJgjkyJxVKPzm9nPurXIIwrebRocIlztTVZees+AAUvjw2YONnbiTByu43hSvoce3s+HaVba
-----END PRIVATE KEY-----"""
keyId = "JT5J8K27HR"
teamId = "77V6T6PAVC"
alg = 'ES256'


time_now = datetime.datetime.now()
time_expired = time_now + datetime.timedelta(hours=12)

headers = {
    'alg': alg,
    'kid': keyId
}
payload = {
    'iss': teamId,  # issuer
    'iat': int(time_now.strftime('%S')),  # issued at
    'exp': int(time_expired.strftime('%S'))  # expiration time
}


if __name__ == "__main__":
    """Create an auth token"""

    token = jwt.encode(payload, secret, algorithm=alg, headers=headers)
    token_str = token.decode()
    print(token_str)
#     ADAMS PLAYLIST: https://api.music.apple.com/v1/me/library/playlists/p.MoGJB2ktlYPoNB
#     ALBUM: https://api.music.apple.com/v1/catalog/ca/albums/1373516902
    url = "https://api.music.apple.com/v1/me/library/playlists/p.MoGJB2ktlYPoNB"
    # print (f"curl -v -H 'Authorization: Bearer {token_str}' {url}")
    res = requests.get(url, headers={'Authorization': "Bearer " +  token_str})
    if res == None or res == '':
        print("none")
    else:
        result = json.loads(res.text)
        print(str(result).encode("utf-8"))
