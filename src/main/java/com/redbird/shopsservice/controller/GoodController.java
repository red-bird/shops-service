package com.redbird.shopsservice.controller;

import com.redbird.shopsservice.model.Good;
import com.redbird.shopsservice.model.GoodDTO;
import com.redbird.shopsservice.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/goods")
public class GoodController {

    @Autowired
    private GoodService goodService;

    @GetMapping
    public List<Good> findAll() {
        return goodService.findAll();
    }

    @GetMapping("/{id}")
    public Good findById(@PathVariable("id") Long id) {
        return goodService.findById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Good saveGood(@RequestBody Good good) {
        return goodService.saveGood(good);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable("id") Long id) {
        goodService.deleteById(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Good updateGood(@PathVariable("id") Long id, @RequestBody Map<String, Object> fields) {
        return goodService.updateById(id, fields);
    }
}
