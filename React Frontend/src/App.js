// in src/App.js
import * as React from "react";
import { Admin, Resource } from 'react-admin';
import { SongList, SongEdit, SongCreate } from './songs';

import LibraryMusicIcon from '@material-ui/icons/LibraryMusic';
import Dashboard from "./Dashboard";
import authProvider from "./authProvider";
import AlbumIcon from '@material-ui/icons/Album';
import dataProvider from './dataProvider';
import { AlbumCreate, AlbumEdit, AlbumList } from "./albums";
import { ArtistCreate, ArtistEdit, ArtistList } from "./artists";
import { PlaylistCreate, PlaylistEdit, PlaylistList } from "./playlists";
import PersonIcon from '@material-ui/icons/Person';
import PlaylistPlayIcon from '@material-ui/icons/PlaylistPlay';

const App = () => {

  const isAdmin = localStorage.getItem('role') === 'admin';
  return (
    <Admin dashboard={Dashboard} authProvider={authProvider} dataProvider={dataProvider}>

      <Resource name="songs" list={SongList} edit={isAdmin && SongEdit} create={isAdmin && SongCreate} icon={LibraryMusicIcon} />

      <Resource name="albums" list={AlbumList} edit={isAdmin && AlbumEdit} create={isAdmin && AlbumCreate} icon={AlbumIcon} />

      <Resource name="artists" list={ArtistList} edit={isAdmin && ArtistEdit} create={isAdmin && ArtistCreate} icon={PersonIcon} />

      <Resource name="playlists" list={PlaylistList} edit={PlaylistEdit} create={PlaylistCreate} icon={PlaylistPlayIcon} />

    </Admin>
  );
}

export default App;