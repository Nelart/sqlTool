package org.sqlTool.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.sqlTool.service.PrepareDataService;

import javax.annotation.Resource;

/**
 * @author Gunnel
 * @Date: 2021/6/24
 * @Time: 10:59 PM
 * @Description:
 */
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/data")
public class PrepareDataController {

    @Resource
    private PrepareDataService prepareDataService;

    @GetMapping("/create")
    public String create() throws Exception {
        long count = prepareDataService.createTestData();
        return "created " + count + " records!";
    }
}
