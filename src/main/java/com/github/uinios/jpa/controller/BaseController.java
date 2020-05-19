package com.github.uinios.jpa.controller;

import com.github.uinios.jpa.io.Respond;
import com.github.uinios.jpa.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
        return Respond.failureContent("查询{}失败!", content);
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
        return Respond.failureContent("查询{}单条数据出错!", content);
    }

    @PostMapping("save")
    public Respond save(T entity) {
        try {
            T save = baseService.save(entity);
            if (Objects.nonNull(save)) {
                return Respond.successContent("添加{}成功！", content);
            }
        } catch (Exception e) {
            log.error("添加{}失败!{}", content, e.getMessage());
        }
        return Respond.failureContent("添加{}失败！", content);
    }

    @PostMapping("batch/save")
    public Respond save(List<T> entity) {
        try {
            List<T> list = baseService.saveInBatch(entity);
            if (Objects.nonNull(list) && !list.isEmpty()) {
                return Respond.successContent("批量添加{}成功！", content);
            }
        } catch (Exception e) {
            log.error("批量添加{}失败!{}", content, e.getMessage());
        }
        return Respond.failureContent("批量添加{}失败！", content);
    }

    @PutMapping("update")
    public Respond update(T entity, ID id) {
        try {
            T update = baseService.update(entity, id);
            if (Objects.nonNull(update)) {
                return Respond.successContent("修改{}成功！", content);
            }
        } catch (Exception e) {
            log.error("修改{}失败!{}", content, e.getMessage());
        }
        return Respond.failureContent("修改{}失败！", content);
    }

    @DeleteMapping("delete/{id}")
    public Respond delete(@PathVariable ID id) {
        try {
            if (Objects.nonNull(id) && !Objects.equals(id, "")) {
                baseService.deleteById(id);
                return Respond.successContent("删除{}成功！", content);
            }
        } catch (Exception e) {
            log.error("删除{}失败!{}", content, e.getMessage());
        }
        return Respond.failureContent("删除{}失败！", content);
    }

    @DeleteMapping("batch/delete/{ids}")
    public Respond deleteInBatch(@PathVariable ID[] ids) {
        try {
            if (Objects.nonNull(ids)) {
                baseService.deleteInBatch(ids);
                return Respond.successContent("批量删除{}成功！", content);
            }
        } catch (Exception e) {
            log.error("批量删除{}失败!{}", content, e.getMessage());
        }
        return Respond.failureContent("批量删除{}失败！", content);
    }

}
