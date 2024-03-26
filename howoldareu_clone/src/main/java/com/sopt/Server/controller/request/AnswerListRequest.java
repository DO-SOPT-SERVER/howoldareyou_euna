package com.sopt.Server.controller.request;

import java.util.List;

public record AnswerListRequest(String nickname, List<AnswerRequest> results) {
}
