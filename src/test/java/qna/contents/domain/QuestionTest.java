package qna.contents.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qna.CannotDeleteException;
import qna.history.domain.DeleteHistory;
import qna.user.domain.UserTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class QuestionTest {
    public static final Question Q1 = new Question("title1", "contents1").writeBy(UserTest.JAVAJIGI);
    public static final Question Q2 = new Question("title2", "contents2").writeBy(UserTest.SANJIGI);

    private Question question;
    private Answer answer;

    @BeforeEach
    public void setUp() throws Exception {
        question = new Question(1L, "title1", "contents1").writeBy(UserTest.JAVAJIGI);
        answer = new Answer(1L, UserTest.JAVAJIGI, question, "Answers Contents1");
        question.addAnswer(answer);
    }

    @Test
    void delete_성공() throws Exception {
        assertThat(question.isDeleted()).isFalse();
        question.delete();

        assertThat(question.isDeleted()).isTrue();
    }

    @Test
    public void delete_성공_질문자_답변자_같음() throws Exception {
        final List<DeleteHistory> deleteHistories = question.delete();

        assertThat(question.isDeleted()).isTrue();
        assertThat(answer.isDeleted()).isTrue();
        assertThat(deleteHistories.size()).isEqualTo(2);
    }

    @Test
    public void delete_답변_중_다른_사람이_쓴_글() throws Exception {
        Answer answer2 = new Answer(2L, UserTest.SANJIGI, question, "Answers Contents1");
        question.addAnswer(answer2);

        assertThatThrownBy(() ->  question.delete())
                .isInstanceOf(CannotDeleteException.class);
    }
}
