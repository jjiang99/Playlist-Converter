'''
Created on Jun. 19, 2020

@author: justi
'''

from datetime import datetime
import jwt
import requests
import json

secret_key = 'MIGTAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBHkwdwIBAQQg1zjSooejCIPTHpci8joGxMdczELa1VTjLMsGtJpACbmgCgYIKoZIzj0DAQehRANCAATfDdFeyIFnV8BNBJgjkyJxVKPzm9nPurXIIwrebRocIlztTVZees+AAUvjw2YONnbiTByu43hSvoce3s+HaVba'

key_id  = 'JT5J8K27HR' # <-- your key id here
team_id = '77V6T6PAVC' # <-- your team id here
alg     = 'ES256'
iat     = int(datetime.utcnow().strftime("%S")) # set issued at to now
exp     = iat + 86400 # add e.g. 24h from now for expiration (24 * 3600secs == 86400)

payload = {
    'iss': team_id,
    'iat': iat,
    'exp': exp
}

headers = {
    'alg': alg,
    'kid': key_id
}

token = jwt.encode(payload, secret_key, algorithm=alg, headers=headers)

token_str = jwt.decode(secret_key)
print(token_str)

# url = "https://api.music.apple.com/v1/storefronts"
# # print (f"curl -v -H 'Authorization: Bearer {token_str}' {url}")
# res = requests.get(url, headers={'Authorization': "Bearer " +  token_str})
# if res == None or res == '':
#     print("none")
# else:
#     result = json.loads(res.text)
#     print(result)