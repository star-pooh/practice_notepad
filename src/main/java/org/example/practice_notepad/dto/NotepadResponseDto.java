package org.example.practice_notepad.dto;

import lombok.Getter;
import org.example.practice_notepad.entity.Notepad;

@Getter
public class NotepadResponseDto {
  private Long id;
  private String title;
  private String content;


  public NotepadResponseDto(Notepad notepad) {
    this.id = notepad.getId();
    this.title = notepad.getTitle();
    this.content = notepad.getContent();
  }
}
