package src.srccode.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import src.srccode.service.MultiMatrixService;

@RestController
@RequestMapping("/rbpic")
@RequiredArgsConstructor
public class RBPICController {
    private final MultiMatrixService multiMatrixService;

    @GetMapping("/getMultiMatrix")
    public Integer getMultiMatrix(@RequestParam Integer rows , @RequestParam Integer columns) throws InterruptedException {
        System.out.println(multiMatrixService.multiMatrix(rows, columns));
        return multiMatrixService.multiMatrix(rows, columns);
    }
}
