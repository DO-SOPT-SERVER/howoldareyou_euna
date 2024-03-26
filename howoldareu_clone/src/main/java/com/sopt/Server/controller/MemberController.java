package com.sopt.Server.controller;

import com.sopt.Server.common.ApiResponse;
import com.sopt.Server.controller.request.MemberPostRequest;
import com.sopt.Server.controller.response.MemberGetResponse;
import com.sopt.Server.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static com.sopt.Server.exception.Success.CREATE_MEMBER_SUCCESS;

@RestController
@RequiredArgsConstructor
public class MemberController {
    
    private final MemberService memberService;

    @PostMapping("/member")
    public ResponseEntity<ApiResponse<MemberGetResponse>> saveMember(@RequestBody MemberPostRequest request) {
        MemberGetResponse response = memberService.saveMember(request);
        URI uri = URI.create("/member"+response.memberId());
        return ResponseEntity.created(uri).body(ApiResponse.success(CREATE_MEMBER_SUCCESS, response));
    }
}
