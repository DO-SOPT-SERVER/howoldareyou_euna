package com.sopt.Server.service;

import com.sopt.Server.controller.response.GetQuestionResponse;
import com.sopt.Server.domain.Question;
import com.sopt.Server.repository.QuestionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionService {
    private final QuestionJpaRepository questionJpaRepository;

    public List<GetQuestionResponse> getQuestionResponseDTOList() {
        List<Question> questionList = questionJpaRepository.findAll();
        List<GetQuestionResponse> getQuestionResponseList = new ArrayList<>();
        for(Question question : questionList){
            getQuestionResponseList.add(GetQuestionResponse.of(question.getQuestionId(),question.getQuestionContent()));
        }
        return getQuestionResponseList;
    }

}
