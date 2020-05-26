'''
Created on May 23, 2020

@author: justi
'''

from gmusicapi import Musicmanager
from gmusicapi.clients.mobileclient import Mobileclient


class Song:
    def __init__(self, name, artist, album):
        self.name = name
        self.artist = artist
        self.album = album
        

def authenticateUser():
    mm = Mobileclient()
    mm.perform_oauth()
#     mm.login(mm.perform_oauth())
    print(mm._get_all_songs(True))



def main():
#     print("python main function")
#     authenticateUser()
    mm = Mobileclient()
#     mm.perform_oauth("C:\\Users\\justi\\Playlist Converter\\Python Test\\creds.txt", True)
    mm.oauth_login(Mobileclient.FROM_MAC_ADDRESS, "C:\\Users\\justi\\Playlist Converter\\Python Test\\creds.txt", "en_US")
    
#     lib = mm.get_all_songs()
    lib = mm.get_all_playlists()
#     print(lib.__next__())
    for playlist in lib:
        if playlist['name'] == "Temp":
            targetPlaylist = mm.get_shared_playlist_contents(playlist['shareToken'])
            
    
    songs = []
    for track in targetPlaylist:
#         print(track['track']['artist'])
        songs.append(Song(track['track']['title'], track['track']['artist'], track['track']['album']))
        
    
    for song in songs:
        print(song.name)
        
    

if __name__ == '__main__':
    main()
