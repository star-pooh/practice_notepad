package org.example.practice_notepad.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.practice_notepad.dto.NotepadRequestDto;

@Getter
@AllArgsConstructor
public class Notepad {

  private Long id;
  private String title;
  private String content;

  public void update(NotepadRequestDto dto) {
    this.title = dto.getTitle();
    this.content = dto.getContent();
  }

  public void updateTitle(NotepadRequestDto dto) {
    this.title = dto.getTitle();
  }
}
