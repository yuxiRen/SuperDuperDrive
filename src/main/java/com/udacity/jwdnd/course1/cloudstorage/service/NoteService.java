package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public void addNote(Note note, int userId) {
        System.out.println(note.getNotetitle());
        System.out.println(note.getNotedescription());
        noteMapper.insertNote(note,userId);
    }
    public void deleteNote(int noteId) {
        noteMapper.deleteNote(noteId);
    }
    public void updateNote(Note note) {
        noteMapper.updateNote(note);
    }
    public List<Note> getNote(int userId) {
        return noteMapper.findByUserId(userId);
    }

}
