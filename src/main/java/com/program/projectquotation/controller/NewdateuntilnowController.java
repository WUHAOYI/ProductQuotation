package com.program.projectquotation.controller;

import com.program.projectquotation.result.Result;
import com.program.projectquotation.service.NewdateuntilnowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by WHY on 2024/11/6.
 * Functions:
 */
@RestController
@RequestMapping("time")
@CrossOrigin
public class NewdateuntilnowController {

    @Autowired
    NewdateuntilnowService newdateuntilnowService;

    @GetMapping()
    public int getNewdateuntilnow(){
        return newdateuntilnowService.getNewdateuntilnow();
    }

    @PutMapping("/{time}")
    public Result updateNewdateuntilnow(@PathVariable("time") int time){
        return newdateuntilnowService.updateNewdateuntilnow(time);
    }
}
