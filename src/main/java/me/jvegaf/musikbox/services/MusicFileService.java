package me.jvegaf.musikbox.services;

import me.jvegaf.musikbox.tracks.Track;

import java.io.File;
import java.util.ArrayList;

public class MusicFileService {

  public static ArrayList<Track> processMusicFilesOfPath(File path) {
    File[] fileList = path.listFiles((dir, name) -> name.endsWith(".mp3"));
    if (fileList == null) return new ArrayList<>();
    ArrayList<Track> resultTracks = new ArrayList<>();
    for (File file : fileList) {
      Track t = TagService.createTrackFromFile(file);
      resultTracks.add(t);
    }
    return resultTracks;
  }
}
