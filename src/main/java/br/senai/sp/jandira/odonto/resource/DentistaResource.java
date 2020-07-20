package br.senai.sp.jandira.odonto.resource;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.jandira.odonto.model.Dentista;
import br.senai.sp.jandira.odonto.repository.DentistaRepository;
import br.senai.sp.jandira.odonto.upload.FileUpload;
import br.senai.sp.jandira.odonto.upload.FileUploadUrl;
import br.senai.sp.jandira.odonto.upload.FireBaseStorageService;

import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/odonto")
public class DentistaResource {

    @Autowired
    private DentistaRepository dentistaRepository;

    @Autowired
    private FireBaseStorageService fireBaseStorageService;

    @GetMapping("/dentistas")
    public List<Dentista> getDentistas() {
        return dentistaRepository.findAll();   
    }

    @GetMapping("/dentistas/{id}")
    public ResponseEntity<?> getDentista(@PathVariable Long id) {
        Optional<?> dentista = dentistaRepository.findById(id);

        return dentista.isPresent() ? 
            ResponseEntity.ok(dentista.get()) : 
            ResponseEntity.notFound().build(); 
    }
    
    @PostMapping("/dentistas")
    public Dentista insertDentista(@Valid @RequestBody Dentista dentista) {
        return dentistaRepository.save(dentista);
    }   

    @DeleteMapping("/dentistas/{id}")
    public ResponseEntity<?> deleteDentista(@PathVariable Long id) {
        Optional<Dentista> dentista = dentistaRepository.findById(id);

        if (dentista.isPresent()) {
            if (dentista.get().getUrlImagem() != null) {
                String pieces[] = dentista.get().getUrlImagem().split("/");
                String fileName = pieces[pieces.length - 1];
                
                fireBaseStorageService.delete(fileName);
            }

            dentistaRepository.deleteById(id);

            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value="/dentistas/{id}")
    public Dentista updateDentista(@PathVariable Long id, @Valid @RequestBody Dentista dentista) {
        dentista.setId(id);
        return dentistaRepository.save(dentista);
    }

    @PostMapping(value="/dentistas/upload")
    public ResponseEntity<FileUploadUrl> uploadDentista(@RequestBody FileUpload file) {
        return ResponseEntity.ok(new FileUploadUrl(fireBaseStorageService.upload(file)));
    }
}