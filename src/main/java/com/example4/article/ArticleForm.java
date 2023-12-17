package com.example4.article;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class ArticleForm {
    @NotEmpty(message = "제목은 필수 사항입니다.")
    private String title;

    @NotEmpty(message = "내용은 필수 사항입니다.")
    private String content;
}
