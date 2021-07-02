package subway;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LineRepositoryTest {

    @Autowired
    private StationRepository stations;

    @Autowired
    private LineRepository lines;


    @Test
    public void saveWithLine() {
        final Station station = new Station("잠실역");
        station.setLine(lines.save(new Line("2호선")));
        stations.save(station);
    }

    @Test
    public void findByNameWithLine() {
        final Station actual = stations.findByName("교대역").get();
        assertNotNull(actual);
        assertEquals("3호선", actual.getLine().getName());

    }

    @Test
    public void removeLine() {
        final Optional<Station> actual = stations.findByName("교대역");
        actual.get().setLine(null);
    }

    @Test
    void findById() {
        Line line = lines.findByName("3호선");
        assertEquals(line.getStations().size(), 1);
        assertEquals(line.getStations().get(0).getName(), "교대역");
    }
}