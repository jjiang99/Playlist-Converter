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

userToken = "AgPPAtfi1bMzzM28q0E2668Gc3Qt0M7V1olkRFE2TSFxqXsCC2Ws6/Y3OWO6r0rwdqyUzmrTzx8fvmg/EqBUPdD9lsHDjZOPQ4D9iqixaCWyq1Ii7SYeq+JoFAEHHS8dHhVfZ2iagVv9KqsciRU44A7mtHXoiuZqzjHy2f+BgAZny3/lKpeFdPBiQzfWJ2eFJlgzoh/0nnIY06BMpk1JFndNPh8PVnmt1PmI9K1oaKG1AZghZA=="

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


playlistId = ''




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
    
def getAllSongs(link):
    playlistId = link.split("/")[-1]
#     print(playlistId)
    url = "https://api.music.apple.com/v1/catalog/us/playlists/" + playlistId
    res = requests.get(url, headers={'Authorization': "Bearer " +  token_str})
    
#     print(json.loads(res.text))
    result = json.loads(res.text)
    
#     print(result['data'][0]['relationships']['tracks']['data'][0]['attributes']['name'])
    songs = []
    for track in result['data'][0]['relationships']['tracks']['data']:
#         print(track['attributes']['albumName'])
#         if (track['attributes']['name'] != None):
        title = str(track['attributes']['name'])
        if ("feat." in title):
            title = title.split("feat.")[0][0:-2]
        songs.append(Song(title, str(track['attributes']['artistName']).split(" &")[0], track['attributes']['albumName']))
        

    for song in songs:
        print(song.name + ", " + song.artist)
    return "DONE"

def createPlaylist(playlistName, description=None):
    url = "https://api.music.apple.com/v1/me/library/playlists"
    res = requests.post(url, headers={'Authorization': "Bearer " +  token_str, 'Music-User-Token': userToken}, json={'attributes': {'name': playlistName, 'description': description}})
    result = json.loads(res.text)

    playlistId = result['data'][0]['id']
    return "https://music.apple.com/library/playlist/" + playlistId
    
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
#     print(result)
    for item in result['results']['songs']['data']:
        title = item['attributes']['name']
        artist = item['attributes']['artistName']
        songId = item['attributes']['playParams']['id']
        if (pTitle.lower() == title.lower() and pArtist.lower() == artist.lower()):
            return songId
    
def addSongs(songIds):
    url = "https://api.music.apple.com/v1/me/library/playlists/" + playlistId + "/tracks"
    tracks = []
    for id in songIds:
        tracks.append({'id': id, 'type': 'songs'})
    
    requests.post(url, headers={'Authorization': "Bearer " +  token_str, 'Music-User-Token': userToken}, json={'data': tracks})
    return


def putAllSongs():
    return


# if __name__ == "__main__":
    
# print(findSong("come back to me (feat. Shaylen)", "Chantel Jeffries", "asdas"))        
#     songs = ['1373516907', '1373516907', '1373516907', '1373516907']
     
#     addSongs(songs)
#     getAllSongs("https://music.apple.com/ca/playlist/summer-2019/pl.u-PDb40YATLAz4XN")
getAllSongs("https://music.apple.com/ca/playlist/summer-2020/pl.u-b3b8RX7syNGrgR")



