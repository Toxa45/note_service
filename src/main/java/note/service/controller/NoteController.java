package note.service.controller;

import note.service.exception.ErrorType;
import note.service.exception.SampleException;
import note.service.model.Note;
import note.service.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping
    public ResponseEntity<List<Note>> getAll(@RequestHeader HttpHeaders requestHeader) {
        return ResponseEntity.ok(noteService.findByAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getById(@RequestHeader HttpHeaders requestHeader, @PathVariable Long id) {
        return noteService.findById(id).map(ResponseEntity::ok)
                .orElseThrow(() -> new SampleException(
                        String.format(ErrorType.ENTITY_NOT_FOUND.getDescription(), id)
                ));
    }

    @GetMapping(params = {"text_filter"})
    public  ResponseEntity<List<Note>> getByPageableGymNameLike(@RequestParam( name ="text_filter",defaultValue="", required = false) String  textFilter ) {
        return ResponseEntity.ok(noteService.findByAllContainingText(textFilter));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Note> update(@RequestBody Note noteRecord, @PathVariable Long id)
    {
        return noteService.update(id,noteRecord).map(ResponseEntity::ok)
                .orElseThrow(() -> new SampleException(
                        String.format(ErrorType.ENTITY_NOT_UPDATED.getDescription(), noteRecord.toString())
                ));
    }

    @PostMapping
    public ResponseEntity<Note> add(@RequestBody Note noteNew)
    {
        return ResponseEntity.ok(noteService.save(noteNew));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id)
    {
        noteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
