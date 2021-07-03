package qna.contents.domain;

import qna.CannotDeleteException;
import qna.common.UpdatableEntity;
import qna.history.domain.DeleteHistory;
import qna.user.domain.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Question extends UpdatableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;
    @ManyToOne
    private User writer;

    @Lob
    private String contents;
    @Column(nullable = false)
    private boolean deleted = false;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers;

    protected Question() {
    }

    public Question(String title, String contents) {
        this(null, title, contents);
    }

    public Question(Long id, String title, String contents) {
        this.id = id;
        this.title = title;
        this.contents = contents;
    }

    public Question writeBy(User writer) {
        this.writer = writer;
        return this;
    }

    public boolean isOwner(User writer) {
        return this.writer.getId().equals(writer.getId());
    }

    public void addAnswer(Answer answer) {
        if (answers == null) {
            answers = new ArrayList<>();
        }
        answers.add(answer);
        answer.toQuestion(this);
    }

    public Long getId() {
        return id;
    }

    public User getWriter() {
        return writer;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public List<DeleteHistory> delete() throws CannotDeleteException {
        validateAnswers();
        this.deleted = true;
        List<DeleteHistory> deleteHistories = new ArrayList<>();
        deleteHistories.add(new DeleteHistory(ContentType.QUESTION, id, writer, LocalDateTime.now()));
        
        for (Answer answer : answers) {
            answer.delete();
            deleteHistories.add(new DeleteHistory(ContentType.ANSWER, answer.getId(), answer.getWriter(), LocalDateTime.now()));
        }

        return deleteHistories;
    }

    private void validateAnswers() throws CannotDeleteException {
        for (Answer answer : answers) {
            validateAnswer(answer);
        }
    }

    private void validateAnswer(Answer answer) throws CannotDeleteException {
        if (!answer.isOwner(writer)) {
            throw new CannotDeleteException("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
        }
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", writerId=" + writer.getId() +
                ", deleted=" + deleted +
                '}';
    }
}
