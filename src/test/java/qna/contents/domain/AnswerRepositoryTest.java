package qna.contents.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qna.domain.AnswerTest;
import qna.domain.UserTest;
import qna.user.domain.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AnswerRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AnswerRepository answerRepository;

    @DisplayName("답변을 삭제한다.")
    @Test
    void deleteAnswer() throws Exception {
        //given
        userRepository.save(UserTest.JAVAJIGI);
        final Answer saved = answerRepository.save(AnswerTest.A1);
        //when
        saved.setDeleted(true);
        //then
        final Answer found = answerRepository.findById(saved.getId()).get();
        Assertions.assertEquals(found.isDeleted(), true);
    }
}