import spotipy
import spotipy.util as util

PORT_NUMBER = 8080
username = "repthecave"
SPOTIPY_CLIENT_ID = '1f70624ac6c84c93a95998e8d1f5ba98'
SPOTIPY_CLIENT_SECRET = '66194225c1e6478eb181fe569215059d'
SPOTIPY_REDIRECT_URI = 'http://localhost:8080'
SCOPE = 'playlist-modify-private'




def main():
    token = util.prompt_for_user_token(username, SCOPE, SPOTIPY_CLIENT_ID, SPOTIPY_CLIENT_SECRET, SPOTIPY_REDIRECT_URI)
    print(token)
    if token:
        spotipy.Spotify(auth=token)
        #results = sp.current_user_saved_tracks()
        #for item in results['items']:
            #track = item['track']
            #print(track['name'] + ' - ' + track['artists'][0]['name'])
    else:
        print("Can't get token for", username)

main()
