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


# hello world asdasd
def main():
    mm = Mobileclient()
#     mm.perform_oauth("credentials.txt", True)

#    put in your local path to where the 'creds.txt' document is
    mm.oauth_login(Mobileclient.FROM_MAC_ADDRESS, "C:\\Users\\justi\\Documents\\GitHub\\Project\\Playlist-Converter\\Playlist_Converter\\Python_Test\\creds.txt", "en_US")
    
#     lib = mm.get_all_songs()
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
         
    

if __name__ == '__main__':
    main()
