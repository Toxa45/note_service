package note.service.service;

import note.service.model.Note;

import java.util.List;
import java.util.Optional;

public interface NoteService {
    public Note save(Note note);

    public Optional<Note> update(Long id, Note noteRecord);

    public void delete(Long id);

    public Optional<Note> findById(Long id);
    public List<Note> findByAll();
    public List<Note> findByAllContainingText(String text);
}
