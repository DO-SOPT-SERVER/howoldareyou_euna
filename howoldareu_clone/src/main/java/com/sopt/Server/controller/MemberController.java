package com.sopt.Server.controller;

import com.sopt.Server.common.ApiResponse;
import com.sopt.Server.controller.request.MemberPostRequest;
import com.sopt.Server.controller.response.MemberGetResponse;
import com.sopt.Server.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.sopt.Server.exception.Success.CREATE_MEMBER_SUCCESS;

@RestController
@RequiredArgsConstructor
public class MemberController {
    
    private final MemberService memberService;

    @PostMapping("/member")
    public ApiResponse<MemberGetResponse> saveMember(@RequestBody MemberPostRequest request) {
        return ApiResponse.success(CREATE_MEMBER_SUCCESS, memberService.saveMember(request));
    }
}
