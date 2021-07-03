package qna.contents.domain;

import qna.common.UpdatableEntity;

import javax.persistence.*;

@MappedSuperclass
public abstract class ContentsEntity extends UpdatableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Lob
    protected String contents;
    @Column(nullable = false)
    protected boolean deleted = false;
}
