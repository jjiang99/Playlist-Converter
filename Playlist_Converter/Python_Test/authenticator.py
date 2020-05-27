import os
from bottle import route, run, request
import spotipy
from spotipy import oauth2
import spotipy.util as util

PORT_NUMBER = 8080
username = "repthecave"
SPOTIPY_CLIENT_ID = '1269535013ee4729bdc1ef093b2cfb54'
SPOTIPY_CLIENT_SECRET = '1d3342d16579456897fb6f11980a9a14'
SPOTIPY_REDIRECT_URI = 'http://localhost:8080'
SCOPE = 'user-library-read'




def main():
    token = util.prompt_for_user_token(username, SCOPE, SPOTIPY_CLIENT_ID, SPOTIPY_CLIENT_SECRET, SPOTIPY_REDIRECT_URI)
    print(token)
    if token:
        sp = spotipy.Spotify(auth=token)
        #results = sp.current_user_saved_tracks()
        #for item in results['items']:
            #track = item['track']
            #print(track['name'] + ' - ' + track['artists'][0]['name'])
    else:
        print("Can't get token for", username)

main()
