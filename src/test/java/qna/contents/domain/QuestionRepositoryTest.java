package qna.contents.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qna.domain.AnswerTest;
import qna.domain.QuestionTest;
import qna.domain.UserTest;
import qna.user.domain.UserRepository;

@DataJpaTest
class QuestionRepositoryTest {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AnswerRepository answerRepository;

    @DisplayName("질문을 저장한다.")
    @Test
    void createQuestion() throws Exception {
        //given
        userRepository.save(UserTest.JAVAJIGI);
        //when
        final Question saved = questionRepository.save(QuestionTest.Q1);
        //then
        Assertions.assertSame(questionRepository.findById(saved.getId()).get(), saved);
    }

    @DisplayName("질문에 답변을 등록한다.")
    @Test
    void addAnswer() throws Exception {
        //given
        userRepository.save(UserTest.JAVAJIGI);
        final Question savedQuestion = questionRepository.save(QuestionTest.Q1);
        final Answer savedAnswer = answerRepository.save(AnswerTest.A1);
        //when
        savedQuestion.addAnswer(savedAnswer);
        //then
        final Question found = questionRepository.findById(savedQuestion.getId()).get();
        Assertions.assertEquals(1, found.getAnswers().size());
    }

    @DisplayName("질문을 삭제한다.")
    @Test
    void deleteQuestion() throws Exception {
        //given
        userRepository.save(UserTest.JAVAJIGI);
        final Question saved = questionRepository.save(QuestionTest.Q1);
        //when
        saved.setDeleted(true);
        //then
        final Question found = questionRepository.findById(saved.getId()).get();
        Assertions.assertEquals(found.isDeleted(), true);

    }
}