package note.service.service;

import note.service.model.Note;
import note.service.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository repository;

    @Override
    public Note save(Note note)
    {
        return  repository.save(note);
    }

    @Override
    public Optional<Note> update(Long id, Note noteRecord) {
        Optional<Note> noteInfo = repository.findById(id);
        return noteInfo
                .map(entityInf -> {
                            noteRecord.setId(entityInf.getId());
                            return repository.save(noteRecord);
                        }
                );
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Note> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Note> findByAll() {
        return repository.findAllByOrderById();
    }

    @Override
    public List<Note> findByAllContainingText(String text) {
        return repository.findAllByCaptionIsContainingIgnoreCaseOrTextNoteIsContainingIgnoreCase(text,text);
    }
}
