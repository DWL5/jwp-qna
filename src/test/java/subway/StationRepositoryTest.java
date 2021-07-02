package subway;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class StationRepositoryTest {
    @Autowired
    private StationRepository stations;

    @Test
    void save () throws Exception {
        //given
        final Station station = new Station("잠실역");
        //when
        final Station actual = stations.save(station);
        //then
        Assertions.assertNotNull(actual.getId());
        Assertions.assertEquals("잠실역", actual.getName());

    }

    @Test
    void findByName() throws Exception {
        //given
        final Station station = new Station("잠실역");
        stations.save(station);
        //when

        final Optional<Station> actual = stations.findByName("잠실역");

        //then
        Assertions.assertNotNull(actual.get().getId());
        Assertions.assertEquals("잠실역", actual.get().getName());
    }

    @Test
    void update() throws Exception {
        //given
        final Station station = stations.save(new Station("잠실역"));
        //when
        station.changeName("잠실나루역");
        final Optional<Station> acutal = stations.findByName("잠실나루역");
        //then
        Assertions.assertEquals("잠실나루역", acutal.get().getName());
    }
}
