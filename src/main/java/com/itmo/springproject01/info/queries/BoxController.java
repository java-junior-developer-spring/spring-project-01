package com.itmo.springproject01.info.queries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/box")
public class BoxController {
    private final BoxRepository boxRepository;

    @Autowired
    public BoxController(BoxRepository boxRepository) {
        this.boxRepository = boxRepository;
    }

    @GetMapping("/01")
    public void get01(@RequestParam int height){
        List<Box> boxes = boxRepository.findAllByHeight(height);
        System.out.println(boxes);

        List<Integer> widths = boxRepository.widthListByHeightJPQL(height);
        System.out.println(widths);

        int numberOfBoxes = boxRepository.numberOFBoxesByHeightSQL(height);
        System.out.println(numberOfBoxes);
    }

    @GetMapping("/02")
    public void get02(@RequestParam int maxHeight,
                      @RequestParam int maxWidth,
                      @RequestParam int maxLength,
                      @RequestParam int id){
        List<Box> boxes = boxRepository.findAllByHeightLessThanAndWidthLessThanAndLengthLessThan(maxHeight, maxWidth, maxLength);
        System.out.println(boxes);
        int size = boxRepository.sumSizeByIdSQL(id);
        System.out.println(size);
    }

    @PatchMapping("/03")
    public void get03(int maxHeight, int newWidth){
        // boxRepository.findAll(BoxSpecifications.spec01(10, 23, 44));
    }
}
