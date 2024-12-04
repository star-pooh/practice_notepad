package org.example.practice_notepad.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.example.practice_notepad.dto.NotepadRequestDto;
import org.example.practice_notepad.dto.NotepadResponseDto;
import org.example.practice_notepad.entity.Notepad;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notepads")
public class NotepadController {

  private final Map<Long, Notepad> notepadList = new HashMap<>();

  @PostMapping
  public ResponseEntity<NotepadResponseDto> createNotepad(@RequestBody NotepadRequestDto dto) {
    // 식별자가 1씩 증가
    Long notepadId = notepadList.isEmpty() ? 1 : Collections.max(notepadList.keySet()) + 1;

    // 요청받은 데이터로 Notepad 객체 생성
    Notepad notepad = new Notepad(notepadId, dto.getTitle(), dto.getContent());

    // In memory DB에 Notepad 저장
    notepadList.put(notepadId, notepad);

    return new ResponseEntity<>(new NotepadResponseDto(notepad), HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<NotepadResponseDto>> findAllNotepads() {
    List<NotepadResponseDto> responseDtoList = new ArrayList<>();

    for (Notepad notepad : notepadList.values()) {
      NotepadResponseDto responseDto = new NotepadResponseDto(notepad);
      responseDtoList.add(responseDto);
    }
    // responseDtoList = notepadList.values().stream().map(NotepadResponseDto::new).toList();

    return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<NotepadResponseDto> findNotepadById(@PathVariable Long id) {
    Notepad notepad = notepadList.get(id);

    if (Objects.isNull(notepad)) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(new NotepadResponseDto(notepad), HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<NotepadResponseDto> updateNotepadById(@PathVariable Long id,
      @RequestBody NotepadRequestDto dto) {
    Notepad notepad = notepadList.get(id);

    if (Objects.isNull(notepad)) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    if (dto.getTitle() == null || dto.getContent() == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    notepad.update(dto);

    return new ResponseEntity<>(new NotepadResponseDto(notepad), HttpStatus.OK);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<NotepadResponseDto> updateTitle(@PathVariable Long id,
      @RequestBody NotepadRequestDto dto) {
    Notepad notepad = notepadList.get(id);

    if (Objects.isNull(notepad)) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    if (dto.getTitle() == null || dto.getContent() != null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    notepad.updateTitle(dto);

    return new ResponseEntity<>(new NotepadResponseDto(notepad), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteNotepadById(@PathVariable Long id) {
    if (notepadList.containsKey(id)) {
      notepadList.remove(id);

      return new ResponseEntity<>(HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}
