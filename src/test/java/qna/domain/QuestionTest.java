package qna.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qna.contents.domain.Question;

public class QuestionTest {
    public static final Question Q1 = new Question("title1", "contents1").writeBy(UserTest.JAVAJIGI);
    public static final Question Q2 = new Question("title2", "contents2").writeBy(UserTest.SANJIGI);


    @DisplayName("질문 데이터를 완전히 삭제하는 것이 아니라 삭제 상태로 변경")
    @Test
    void changeDeletedStatusWhenQuestionIsDeleted() throws Exception {
        //given
        final Question q1 = new Question("title1", "contents1").writeBy(UserTest.JAVAJIGI);
        //when
        q1.delete();
        //then
        Assertions.assertTrue(q1.isDeleted());
    }
}
