'''
Created on Jun. 20, 2020

@author: justi
'''
import applemusicpy

secret_key = """-----BEGIN PRIVATE KEY-----
MIGTAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBHkwdwIBAQQg1zjSooejCIPTHpci8joGxMdczELa1VTjLMsGtJpACbmgCgYIKoZIzj0DAQehRANCAATfDdFeyIFnV8BNBJgjkyJxVKPzm9nPurXIIwrebRocIlztTVZees+AAUvjw2YONnbiTByu43hSvoce3s+HaVba
-----END PRIVATE KEY-----"""
key_id = 'JT5J8K27HR'
team_id = '77V6T6PAVC'

am = applemusicpy.AppleMusic(secret_key, key_id, team_id)
results = am.search('travis scott', types=['albums'], limit=5)

for item in results['results']['albums']['data']:
    print(item['attributes']['name'])