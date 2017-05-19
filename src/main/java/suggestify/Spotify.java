package suggestify;

import com.wrapper.spotify.Api;
import com.wrapper.spotify.methods.AddTrackToPlaylistRequest;
import com.wrapper.spotify.methods.ArtistSearchRequest;
import com.wrapper.spotify.methods.CurrentUserRequest;
import com.wrapper.spotify.methods.PlaylistCreationRequest;
import com.wrapper.spotify.methods.TopTracksRequest;
import com.wrapper.spotify.methods.TrackSearchRequest;
import com.wrapper.spotify.models.*;
import java.util.Arrays;
import java.util.List;
import java.lang.System;
import java.util.ArrayList;
import java.util.LinkedList;
import static suggestify.PlaylistViewController.numOfTracksPerArtist;

public class Spotify {
  Authorization authorizer;
  boolean debug;
  public String userID;
  final String state = "someExpectedStateString";
  final String clientId = "b1a1f4d65ed9403f9036be6ad48ba4b3";
  final String clientSecret = "1a1cd75834e04397b23849cc0fa86adf";
  final String redirectUri = "http://www.movemypaper.com/images/sucess.png";
  final Api api = Api.builder()
  .clientId(clientId)
  .clientSecret(clientSecret)
  .redirectURI(redirectUri)
  .build();
  final List<String> scopes = Arrays.asList(
                                            "playlist-read-private",
                                            "playlist-read-collaborative",
                                            "playlist-modify-public",
                                            "playlist-modify-private",
                                            "user-follow-modify",
                                            "user-library-read",
                                            "user-follow-modify",
                                            "user-read-email"
                                            );
  public Spotify (boolean mode, String userID) {
    debug = false;
    this.userID = userID;
    authorizer = new Authorization();
  }
  public void setUserID()  {
    try {
      userID = api.getMe().build().get().getId();
    }
    catch(Exception e) {
    }
  }
  public void displayUserInfo() {
    final CurrentUserRequest request = api.getMe().build();
    try {
      final User user = request.get();
      System.out.println("Display name: " + user.getDisplayName());
      System.out.println("ID: " + user.getId());
      System.out.println("Email: " + user.getEmail());

      System.out.println("Images:");
      for (Image image : user.getImages()) {
        System.out.println(image.getUrl());
      }
      System.out.println("This account is a " + user.getProduct() + " account");
    } catch (Exception e) {
      System.out.println("Something went wrong!" + e.getMessage());
    }
  }
  String createAndAddToPlaylist(ArrayList<String> artists, String playlistTitle) {
    api.refreshAccessToken();
    Playlist playlist = createPlaylist(playlistTitle);
    if(debug) {
      System.out.println("Created Playlist:  " + playlist.getId());
    }
    for(String artist : artists) {
      if(debug) {
        System.out.println("artist  " + artist);
      }
      addToPlaylist(playlist.getId(), artist, numOfTracksPerArtist);
    }
    if(debug) {
      System.out.println("Finished creation");
    }
    return playlist.getExternalUrls().get("spotify");

  }

  public Playlist createPlaylist (String title){
    PlaylistCreationRequest request = api.createPlaylist(userID, title)
    .publicAccess(true)
    .build();
    Playlist playlist = null;
    try {
      playlist = request.get();
      if(debug) {
        System.out.println("You just created this playlist!");
        System.out.println("Its title is " + playlist.getName());
      }
      return playlist;
    } catch (Exception e) {
      System.out.println("Something went wrong!" + e.getMessage());
    }
    return playlist;
  }
  private void addTopSearchResultsToPlaylists(String playlistID, String artist, int top){
        List<Track> tracks = TrackSearchQuery(artist);
        List<String> tracksToAdd = new LinkedList<String>();
        int sz = Math.min(top, tracks.size());
        for(int i = 0; i < sz; i++) {
            tracksToAdd.add(tracks.get(i).getUri());
        }
        final int insertIndex = 0;
        final AddTrackToPlaylistRequest request = api.addTracksToPlaylist(userID, playlistID, tracksToAdd)
                .position(insertIndex)
                .build();
        try {
            request.get();
        } catch (Exception e) {
            if(debug) {
                System.out.println("Something went wrong in Adding to PlayList!" + e.getMessage());
            }
        }
    }
    public void addToPlaylist(String playlistID, String artist, int top) {
        final ArtistSearchRequest request = api.searchArtists(artist).market("US").limit(10).build();
        try {
            final Page<Artist> artistSearchResult = request.get();
            final List<Artist> artists = artistSearchResult.getItems();

            //System.out.println("I've found " + artistSearchResult.getTotal() + " artists!");
            if(artistSearchResult.getTotal() < 1)
            {
                addTopSearchResultsToPlaylists(playlistID,artist,top);
                return;
            }

            final TopTracksRequest req = api.getTopTracksForArtist(artists.get(0).getId(), "US").build();
            try{
                final List<Track> tracks = req.get();
                List<String> tracksToAdd = new LinkedList<>();
                int sz = Math.min(top, tracks.size());
                for(int i = 0; i < sz; i++) {
                    tracksToAdd.add(tracks.get(i).getUri());
                }
                final int insertIndex = 0;
                final AddTrackToPlaylistRequest reqq = api.addTracksToPlaylist(userID, playlistID, tracksToAdd)
                        .position(insertIndex)
                        .build();
                try {
                    reqq.get();
                } catch (Exception e) {
                    if(debug) {
                        System.out.println("Something went wrong in Adding to PlayList!" + e.getMessage());
                    }
                }
            } catch (Exception e){
                if(debug){
                    System.out.println("Something went wrong requesting top tracks!" + e.getMessage());
                }

            }
        } catch (Exception e) {
            System.out.println("Something went wrong!" + e.getMessage());
        }
    }

  public List<Track> TrackSearchQuery(String query) {
    final TrackSearchRequest request;
    request = api.searchTracks(query).market("US").build();
    List<Track> tracks = null;
    try {
      final Page<Track> trackSearchResult = request.get();
      tracks = trackSearchResult.getItems();
      if(debug) {
        System.out.println("I got " + trackSearchResult.getTotal() + " results!");
        System.out.println(tracks.size());
        for(Track cur : tracks) {
          System.out.println(cur.getName() + " LINK " + cur.getExternalUrls().get("spotify"));
        }

      }
    } catch (Exception e) {
      System.out.println("Something went wrong!" + e.getMessage());
    }
    return tracks;
  }
  class Authorization  {
    public String permissionURL;
    public String code = "";
    public Authorization() {
      //super(new Builder());
      permissionURL =  userPermission();
      if(debug) {
        System.out.println("Permission " + permissionURL);
      }
      System.out.println();
    }
    public String getPermissionURL() {
      return permissionURL;
    }
    public boolean isRedirectAddress (String url) {
      return url.contains("code&") == false;
    }
    public boolean isDeny(String targetAddress) {
      return targetAddress.contains("error=access_denied") == true;
    }
    public String userPermission() {
      String authorizeURL = api.createAuthorizeURL(scopes, state);
      if(debug) {
        System.out.println("Authorize " + authorizeURL);
      }
      return authorizeURL;
    }
    public void retrieveCode(String url) {
      int start = url.indexOf("=") + 1;
      while(url.charAt(start) != '&') {
        code += url.charAt(start++);
      }
      if(debug) {
        System.out.println("Code in RetrieveCode " + code);
      }
    }
    public void obtainCredentials (String targetAddress) {
      retrieveCode(targetAddress);
      AuthorizationCodeCredentials authorizationCodeCredentials = null;
      try {
        authorizationCodeCredentials = api.authorizationCodeGrant(code).build().get();
      }
      catch (Exception e) {
        if(debug) {
          System.out.println("Code " + code);
          System.out.println("Something went wrong while Authorizing  " + e.getMessage());
          //System.out.println();
        }

      }
      api.setAccessToken(authorizationCodeCredentials.getAccessToken());
      api.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
      setUserID();
    }
  }
}
