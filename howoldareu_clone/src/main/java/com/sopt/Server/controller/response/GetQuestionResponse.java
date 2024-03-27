package com.sopt.Server.controller.response;

public record GetQuestionResponse(Long questionId, String questionContent) {
    static public GetQuestionResponse of(Long questionId, String questionContent) {
        return new GetQuestionResponse(questionId, questionContent);
    }
}
