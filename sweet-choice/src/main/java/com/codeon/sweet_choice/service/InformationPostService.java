package com.codeon.sweet_choice.service;


import com.codeon.sweet_choice.dto.PostResponseDto;
import com.codeon.sweet_choice.entity.InformationPost;
import com.codeon.sweet_choice.repository.InformationPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InformationPostService {

    private final InformationPostRepository informationPostRepository;

    // 한글 초성 범위 Map
    private static final Map<String, String[]> INITIAL_RANGES = new HashMap<>();

    static {
        //'가' ~ '하' 까지의 범위 정의
        INITIAL_RANGES.put("가", new String[]{"가", "나"});
        INITIAL_RANGES.put("나", new String[]{"나", "다"});
        INITIAL_RANGES.put("다", new String[]{"다", "라"});
        INITIAL_RANGES.put("라", new String[]{"라", "마"});
        INITIAL_RANGES.put("마", new String[]{"마", "바"});
        INITIAL_RANGES.put("바", new String[]{"바", "사"});
        INITIAL_RANGES.put("사", new String[]{"사", "아"});
        INITIAL_RANGES.put("아", new String[]{"아", "자"});
        INITIAL_RANGES.put("자", new String[]{"자", "차"});
        INITIAL_RANGES.put("차", new String[]{"차", "카"});
        INITIAL_RANGES.put("카", new String[]{"카", "타"});
        INITIAL_RANGES.put("타", new String[]{"타", "파"});
        INITIAL_RANGES.put("파", new String[]{"파", "하"});
        INITIAL_RANGES.put("하", new String[]{"하", "힣"});
    }


     // 초성으로 당 정보 포스트 목록 조회
    public List<PostResponseDto> getPostsByInitial(String initial) {
        String[] range = INITIAL_RANGES.get(initial);
        if (range == null) {
            // "기타" 또는 빈 리스트 반환
            return List.of();
        }

        String start = range[0];
        String end = range[1];

        List<InformationPost> posts;

        if ("하".equals(initial)) {
            posts = informationPostRepository.findPostsBySugarNameRangeLast(start, end);
        } else {
            posts = informationPostRepository.findPostsBySugarNameRange(start, end);
        }

        // Entity 리스트를 DTO 리스트로 변환하여 반환
        return posts.stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }
}