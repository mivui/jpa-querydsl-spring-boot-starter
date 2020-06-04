package com.github.uinios.jpa.basic.controller;

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
            log.error("查询{}出错!{}", content, e.getMessage());
        }
        return Respond.failure("查询{}失败!", content);
    }

    @GetMapping("findById/{id}")
    public Respond findById(@PathVariable ID id) {
        try {
            final Optional<T> byId = baseService.findById(id);
            if (byId.isPresent()) {
                return Respond.success(byId.get());
            }
        } catch (Exception e) {
            log.error("查询{}单条数据出错!{}", content, e.getMessage());
        }
        return Respond.failure("查询{}单条数据出错!", content);
    }

    @PostMapping("save")
    public Respond save(@RequestBody T entity) {
        try {
            T save = baseService.save(entity);
            if (Objects.nonNull(save)) {
                return Respond.success("添加{}成功！", content);
            }
        } catch (Exception e) {
            log.error("添加{}失败!{}", content, e.getMessage());
        }
        return Respond.failure("添加{}失败！", content);
    }

    @PostMapping("batch/save")
    public Respond save(@RequestBody List<T> entities) {
        try {
            List<T> list = baseService.saveInBatch(entities);
            if (Objects.nonNull(list) && !list.isEmpty()) {
                return Respond.success("批量添加{}成功！", content);
            }
        } catch (Exception e) {
            log.error("批量添加{}失败!{}", content, e.getMessage());
        }
        return Respond.failure("批量添加{}失败！", content);
    }

    @PutMapping("update")
    public Respond update(@RequestBody T entity, @RequestParam ID id) {
        try {
            T update = baseService.update(entity, id);
            if (Objects.nonNull(update)) {
                return Respond.success("修改{}成功！", content);
            }
        } catch (Exception e) {
            log.error("修改{}失败!{}", content, e.getMessage());
        }
        return Respond.failure("修改{}失败！", content);
    }

    @DeleteMapping("delete/{id}")
    public Respond delete(@PathVariable ID id) {
        try {
            if (Objects.nonNull(id) && !Objects.equals(id, "")) {
                baseService.deleteById(id);
                return Respond.success("删除{}成功！", content);
            }
        } catch (Exception e) {
            log.error("删除{}失败!{}", content, e.getMessage());
        }
        return Respond.failure("删除{}失败！", content);
    }

    @DeleteMapping("batch/delete/{ids}")
    public Respond deleteInBatch(@PathVariable ID[] ids) {
        try {
            if (Objects.nonNull(ids)) {
                baseService.deleteInBatch(ids);
                return Respond.success("批量删除{}成功！", content);
            }
        } catch (Exception e) {
            log.error("批量删除{}失败!{}", content, e.getMessage());
        }
        return Respond.failure("批量删除{}失败！", content);
    }

}
