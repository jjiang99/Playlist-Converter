'''
Created on May 25, 2020

@author: justi
'''

class Song:
    
    def __init__(self, name, artist, album):
        self.name = name
        self.artist = artist
        self.album = album
        
    def getName(self):
        return self.name
    
    def getArtist(self):
        return self.artist
    
    def getAlbum(self):
        return self.album