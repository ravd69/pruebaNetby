package com.netby.prueba.controller;

import com.netby.prueba.model.Talk;
import com.netby.prueba.repository.TalkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/")
public class TalkController {

    @Autowired
    private TalkRepository talkRepository;

    @GetMapping(value = "/allTask")
    public List<Talk> getALL(){
        return talkRepository.findAll();
    }

    @GetMapping(value = "/findTask/{id}")
    public Talk find(@PathVariable Long id){
        return talkRepository.findById(id).orElse(null);
    }

    @PutMapping(value = "/updateTask/{id}")
    public Talk update(@RequestBody Talk talk, @PathVariable Long id) {
        Talk talkExistente = talkRepository.findById(id).orElse(null);
        if (talkExistente != null && talk.isValidTitle(talk.getNombre())){
            talkExistente.setNombre(talk.getNombre());
            talkExistente.setDuracionMinutos(talk.getDuracionMinutos());
            return talkRepository.save(talkExistente);
        }else {
         return null;
        }
    }

    @PostMapping(value = "/saveTask")
    public Talk save(@RequestBody Talk talk){
        if (talk.isValidTitle(talk.getNombre())){
            return talkRepository.save(talk);
        }
        return null;
    }

    @GetMapping(value = "/deleteTask/{id}")
    public Talk delete(@PathVariable Long id){
        Talk talkExistente = talkRepository.findById(id).orElse(null);
        if (talkExistente != null){
            talkExistente.marcarComoBorrada();
            return talkRepository.save(talkExistente);
        }else{
            return  null;
        }
    }

}
