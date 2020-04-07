package note.service.repository;

import note.service.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
@Transactional
public interface NoteRepository extends JpaRepository<Note, Long> {

    public List<Note> findAllByOrderById();
    public List<Note> findAllByCaptionIsContainingIgnoreCaseOrTextNoteIsContainingIgnoreCase(String caption,String textNote);
}
