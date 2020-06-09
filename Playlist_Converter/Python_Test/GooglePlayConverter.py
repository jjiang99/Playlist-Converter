'''
Created on May 23, 2020

@author: justi
'''
# import sys
# import os
# 
# print (os.path.dirname(os.path.abspath(__file__)), __file__)


from gmusicapi import Musicmanager
from gmusicapi.clients.mobileclient import Mobileclient


class Song:
    def __init__(self, name, artist, album):
        self.name = name
        self.artist = artist
        self.album = album
        
    def printHello(self):
        print("hello")
    
        
    
def authenticateUser(): # Only need to run this once on a given machine, credentials written in text file
    mm = Mobileclient()
    mm.perform_oauth()

def hello():
    print("Hello World!")
    
def getAllSongs():
    mm = Mobileclient()
    mm.oauth_login(Mobileclient.FROM_MAC_ADDRESS, "C:\\Users\\justi\\Documents\\GitHub\\Project\\Playlist-Converter\\Playlist_Converter\\Python_Test\\creds.txt", "en_US")
    
    lib = mm.get_all_playlists()
    
    for playlist in lib:
        if playlist['name'] == "Temp":
            targetPlaylist = mm.get_shared_playlist_contents(playlist['shareToken'])
             
     
    songs = []
    for track in targetPlaylist:
#         print(track['track']['artist'])
        songs.append(Song(track['track']['title'], track['track']['artist'], track['track']['album']))
         
     
    for song in songs:
        print(song.name + ", " + song.artist)
        
    return "DONE"

def createPlaylist():
    mm = Mobileclient()
    mm.oauth_login(Mobileclient.FROM_MAC_ADDRESS, "C:\\Users\\justi\\Documents\\GitHub\\Project\\Playlist-Converter\\Playlist_Converter\\Python_Test\\creds.txt", "en_US")
    playlist = mm.create_playlist("added playlist", "", False)
    print(playlist)
    
def searchSong(name, artist, album):
    mm = Mobileclient()
    mm.oauth_login(Mobileclient.FROM_MAC_ADDRESS, "C:\\Users\\justi\\Documents\\GitHub\\Project\\Playlist-Converter\\Playlist_Converter\\Python_Test\\creds.txt", "en_US")
    query = str(name) + str(artist)
    return mm.search(query)

def addSong(name, artist, album):
    mm = Mobileclient()
    mm.oauth_login(Mobileclient.FROM_MAC_ADDRESS, "C:\\Users\\justi\\Documents\\GitHub\\Project\\Playlist-Converter\\Playlist_Converter\\Python_Test\\creds.txt", "en_US")
#     mm.add_songs_to_playlist(playlist_id, song_ids)
    
    response = searchSong(name, artist, album)
    print(str(response).encode("utf-8"))
    for searchResult in response:
#         if searchResult['title'].lower() == name.lower() & searchResult['artist'].lower() == artist.lower():
            print("same")
#             targetPlaylist = mm.get_shared_playlist_contents(playlist['shareToken'])
    
def main():
    print("MAIN")
#     createPlaylist()
#     print(searchSong("hello", "adele", ""))
    addSong("hello", "adele", "")
#     getAllSongs()

if __name__ == '__main__':
    main()
