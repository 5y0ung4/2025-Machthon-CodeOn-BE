package com.codeon.sweet_choice.controller;

import com.codeon.sweet_choice.dto.PostResponseDto;
import com.codeon.sweet_choice.service.InformationPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class InformationPostController {

    private final InformationPostService informationPostService;

     //[GET] /api/posts?initial=가
    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getPostsByInitial(
            @RequestParam("initial") String initial) {

        List<PostResponseDto> posts = informationPostService.getPostsByInitial(initial);

        return ResponseEntity.ok(posts);
    }
}