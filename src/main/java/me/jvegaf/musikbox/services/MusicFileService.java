package me.jvegaf.musikbox.services;

import me.jvegaf.musikbox.models.Track;

import java.io.File;
import java.util.ArrayList;

public class MusicFileService {
  private TagService tagService;

  public MusicFileService(TagService tagService) {
    this.tagService = tagService;
  }

  public ArrayList<Track> processMusicFilesOfPath(File path) {
    File[] fileList = path.listFiles((dir, name) -> name.endsWith(".mp3"));
    if (fileList == null) return new ArrayList<>();
    ArrayList<Track> resultTracks = new ArrayList<>();
    for (File file : fileList) {
      Track t = this.tagService.createTrackFromFile(file);
      resultTracks.add(t);
    }
    return resultTracks;
  }
}
