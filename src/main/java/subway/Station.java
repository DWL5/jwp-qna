package subway;

import javax.persistence.*;

@Entity
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;


    //다대일 관계라는 매핑 정보
    @ManyToOne
    //외래키의 이름을 지정
    @JoinColumn(name = "line_id")

    private Line line;

    public Station() {
    }

    public Station(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public Station(final String name) {
        this(null, name);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(final Line line) {
        this.line = line;
    }
}
