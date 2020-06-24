'''
Created on Jun. 19, 2020

@author: justi
'''
from datetime import datetime
import jwt
import requests
import json
import applemusicpy
from test import AppleMusicClient


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

#     token = jwt.encode(payload, secret, algorithm=alg, headers=headers)
#     token_str = token.decode()
#     print(token_str)
#     url = "https://api.music.apple.com/v1/catalog/us/search?term=james+brown&limit=2&types=artists,albums"
    
#     am = applemusicpy.AppleMusic(secret, keyId, teamId)
#     results = am.albums('1373516902', storefront='us')
#     print(results['data'])
    
    client = AppleMusicClient(teamId, keyId, secret)

    client.search("travis scott", limit=None, offset=None, storefront='us', types='songs')
    
#     am = applemusicpy.AppleMusic(secret, keyId, teamId)
#     results = am.playlist('p.MoGJYM3CYXW09B', storefront='us', l=None, include=None)
#     
#     for item in results['results']['albums']['data']:
#         print(item['attributes']['name'])
