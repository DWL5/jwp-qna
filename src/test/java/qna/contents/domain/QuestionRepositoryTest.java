package qna.contents.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qna.user.domain.User;
import qna.user.domain.UserRepository;
import qna.user.domain.UserTest;

@DataJpaTest
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnswerRepository answerRepository;

    private User savedUser;

    @BeforeEach
    private void setUp() {
        savedUser = userRepository.save(UserTest.JAVAJIGI);
    }

    @DisplayName("질문을 저장한다.")
    @Test
    void createQuestion() throws Exception {
        //when
        final Question saved = questionRepository.save(QuestionTest.Q1.writeBy(savedUser));
        //then
        Assertions.assertSame(questionRepository.findById(saved.getId()).get(), saved);
    }

    @DisplayName("질문에 답변을 등록한다.")
    @Test
    void addAnswer() throws Exception {
        //given
        final Question savedQuestion = questionRepository.save(new Question("title1", "contents1").writeBy(savedUser));
        final Answer answer = new Answer(savedUser, savedQuestion, "Answers Contents1");
        final Answer savedAnswer = answerRepository.save(answer);
        //when
        savedQuestion.addAnswer(savedAnswer);
        //Assertions.assertEquals(1, savedQuestion.getAnswers().size());
        //then
        final Question found = questionRepository.findById(savedQuestion.getId()).get();
        //Assertions.assertEquals(1, found.getAnswers().size());
    }

    @DisplayName("질문을 삭제한다.")
    @Test
    void deleteQuestion() throws Exception {
        //given
        final Question saved = questionRepository.save(new Question("title1", "contents1").writeBy(savedUser));
        //when
        saved.setDeleted(true);
        //then
        final Question found = questionRepository.findById(saved.getId()).get();
        Assertions.assertEquals(found.isDeleted(), true);

    }
}