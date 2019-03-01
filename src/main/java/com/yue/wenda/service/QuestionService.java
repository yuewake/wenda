package com.yue.wenda.service;

import com.yue.wenda.dao.QuestionDao;
import com.yue.wenda.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * Created by yue on 2019/2/28
 */
@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public Question getById(int id) {
        return questionDao.getById(id);
    }

    public int addQuestion(Question question) {
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        // TODO 敏感词过滤

        int num = questionDao.addQuestion(question);
        System.out.println("=====================================" + num);

        return num > 0 ? question.getId() : 0;
    }

    public List<Question> getLatestQuestions(int userId, int offset, int limit) {
        return questionDao.selectLatestQuestions(userId, offset, limit);
    }

    public int updateCommentCount(int id, int count) {
        return questionDao.updateCommentCount(id, count);
    }
}
