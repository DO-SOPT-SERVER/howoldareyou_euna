package com.sopt.Server.controller.response;

public record ResultResponse(String nickname, int resultAge, String title, String content, String imgUrl1, String imgUrl2) {
    public static ResultResponse of(String nickname, int resultAge, String title, String content, String imgUrl1, String imgUrl2) {
        return new ResultResponse(nickname, resultAge, title, content, imgUrl1, imgUrl2);
    }
}
