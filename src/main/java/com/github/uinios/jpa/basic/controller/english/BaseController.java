package com.github.uinios.jpa.basic.controller.english;

import com.github.uinios.jpa.basic.io.Respond;
import com.github.uinios.jpa.basic.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


/**
 * @param <T>  entity
 * @param <ID> Primary key
 * @author Jingle-Cat
 */

@Slf4j
public abstract class BaseController<T, ID> {

    @Autowired
    private BaseService<T, ID> baseService;

    private final String content;


    protected BaseController(String content) {
        this.content = content;
    }

    @GetMapping("search")
    public Respond search() {
        try {
            return Respond.success(baseService.findAll());
        } catch (Exception e) {
            log.error("according to the {} queries error!{}", content, e.getMessage());
        }
        return Respond.failure("according to the {} queries error!", content);
    }

    @GetMapping("findById/{id}")
    public Respond findById(@PathVariable ID id) {
        try {
            final Optional<T> byId = baseService.findById(id);
            if (byId.isPresent()) {
                return Respond.success(byId.get());
            }
        } catch (Exception e) {
            log.error("error queries a single piece of data according to the {}!{}", content, e.getMessage());
        }
        return Respond.failure("error queries a single piece of data according to the {}!", content);
    }

    @PostMapping("save")
    public Respond save(@RequestBody T entity) {
        try {
            T save = baseService.save(entity);
            if (Objects.nonNull(save)) {
                return Respond.success("{} added successfully！", content);
            }
        } catch (Exception e) {
            log.error("{} added failed!{}", content, e.getMessage());
        }
        return Respond.failure("{} added failed!", content);
    }

    @PostMapping("batch/save")
    public Respond save(@RequestBody List<T> entities) {
        try {
            List<T> list = baseService.saveInBatch(entities);
            if (Objects.nonNull(list) && !list.isEmpty()) {
                return Respond.success("added {} in batches successfully!", content);
            }
        } catch (Exception e) {
            log.error("added {} in batches failed!{}", content, e.getMessage());
        }
        return Respond.failure("added {} in batches failed!", content);
    }

    @PutMapping("update")
    public Respond update(@RequestBody T entity, @RequestParam ID id) {
        try {
            T update = baseService.update(entity, id);
            if (Objects.nonNull(update)) {
                return Respond.success("{} modification succeeded!", content);
            }
        } catch (Exception e) {
            log.error("{} modification failed!{}", content, e.getMessage());
        }
        return Respond.failure("{} modification failed!", content);
    }

    @DeleteMapping("delete/{id}")
    public Respond delete(@PathVariable ID id) {
        try {
            if (Objects.nonNull(id) && !Objects.equals(id, "")) {
                baseService.deleteById(id);
                return Respond.success("{} successfully deleted!", content);
            }
        } catch (Exception e) {
            log.error("{} failed deleted!{}", content, e.getMessage());
        }
        return Respond.failure("{} failed deleted!", content);
    }

    @DeleteMapping("batch/delete/{ids}")
    public Respond deleteInBatch(@PathVariable ID[] ids) {
        try {
            if (Objects.nonNull(ids)) {
                baseService.deleteInBatch(ids);
                return Respond.success("successfully delete {} in batch!", content);
            }
        } catch (Exception e) {
            log.error("failed delete {} in batch!{}", content, e.getMessage());
        }
        return Respond.failure("failed delete {} in batch!", content);
    }

}
