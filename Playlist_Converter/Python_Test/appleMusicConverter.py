'''
Created on Jun. 19, 2020

@author: justi
'''
from datetime import datetime
import jwt
import requests
import json
# from test import AppleMusicClient

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

token = jwt.encode(payload, secret, algorithm=alg, headers=headers)
token_str = token.decode()

class Song:
    def __init__(self, name, artist, album):
        self.name = name
        self.artist = artist
        self.album = album
        
    def printHello(self):
        print("hello")
    
    
def authenticateUser(): # Only need to run this once on a given machine, credentials written in text file
    return

def hello():
    print("Hello World!")
    
def getAllSongs():
    return

def createPlaylist(self, name, description=None, track_ids=None, include=None):
    
    params = None
    # https://developer.apple.com/documentation/applemusicapi/libraryplaylistcreationrequest
    # https://developer.apple.com/documentation/applemusicapi/libraryplaylistcreationrequest/attributes
    payload = {'attributes': {'name': name}}
    if description:
        payload['attributes']['description'] = description
    if track_ids:
        # https://developer.apple.com/documentation/applemusicapi/libraryplaylistcreationrequest/relationships
        # https://developer.apple.com/documentation/applemusicapi/libraryplaylistrequesttrack
        tracks = self._build_tracks(track_ids)
        payload['relationships'] = {'tracks': tracks}
    if include:
        params = {'include': include}
    return self._make_request(
        method='POST',
        endpoint='/me/library/playlists',
        params=params,
        payload=payload,
    )
    return
    
def searchSong(name, artist, album):
#     https://api.music.apple.com/v1/catalog/{storefront}/search .encode("utf-8")

    url = "https://api.music.apple.com/v1/catalog/us/search?term=" + name.replace(" ", "+") + "+" + artist.replace(" ", "+") + "&types=songs"
    # print (f"curl -v -H 'Authorization: Bearer {token_str}' {url}")
    res = requests.get(url, headers={'Authorization': "Bearer " +  token_str})
    if res == None or res == '':
        print("none")
        return ""
    else:
        return json.loads(res.text)
    

def findSong(pTitle, pArtist, pAlbum):
    result = searchSong(str(pTitle), str(pArtist), str(pAlbum))
    for item in result['results']['songs']['data']:
        title = item['attributes']['name']
        artist = item['attributes']['artistName']
        songId = item['attributes']['playParams']['id']
        if (pTitle.lower() == title.lower() and pArtist.lower() == artist.lower()):
            return songId
    
def addSongs():
#     https://api.music.apple.com/v1/me/library/songs/
    return





if __name__ == "__main__":
    """Create an auth token"""
    
#     result = searchSong("paranoid", "post malone", "asdas")
#     for item in result['results']['songs']['data']:
#         print(item['attributes']['playParams']['id'])
#         
    print(findSong("paranoid", "post malone", "asdas"))        
#     client = AppleMusicClient(teamId, keyId, secret, token)    
#     client.search("travis scott", limit=None, offset=None, storefront='us', types='songs')

    
    
#     print(token_str)
#     ADAMS PLAYLIST: https://api.music.apple.com/v1/me/library/playlists/p.MoGJB2ktlYPoNB
#     ALBUM: https://api.music.apple.com/v1/catalog/ca/albums/1373516902
#     url = "https://api.music.apple.com/v1/me/library?ids[albums]=1106659171&ids[songs]=1107054256&ids[music-videos]=267079116"
#     # print (f"curl -v -H 'Authorization: Bearer {token_str}' {url}")
#     res = requests.get(url, headers={'Authorization': "Bearer " +  token_str})
#     if res == None or res == '':
#         print("none")
#     else:
#         print(res)
#         result = json.loads(res.text)
#         print(str(result).encode("utf-8"))
