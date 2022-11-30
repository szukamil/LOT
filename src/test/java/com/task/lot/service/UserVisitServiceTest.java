package com.task.lot.service;

import com.task.lot.model.UserVisitDTO;
import com.task.lot.repository.UserVisitRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ExtendWith(SpringExtension.class)
class UserVisitServiceTest {
    private UserVisitService underTest;
    private Pageable pageable;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserVisitRepository userVisitRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        underTest = new UserVisitService(userVisitRepository, modelMapper);
        pageable = PageRequest.of(0, 8);

    }

    @Test
    @Commit
    void testRegister() {
        //
        String ip = "123.123.003";
        java.sql.Date sqlDate = java.sql.Date.valueOf(LocalDate.of(2022, 11, 29));
        UserVisitDTO userVisitDTO = new UserVisitDTO(sqlDate, ip);
        //
        underTest.register(userVisitDTO);
        //
        int actual = jdbcTemplate.queryForList("SELECT * FROM user_visit").size();
        Assertions.assertEquals(2, actual);
    }

    @Test
    @Sql(scripts = {"file:src/test/java/resources/insert_data.sql"})
    void getStatsForIp() {
        //
        String ip = "212.34.52.003";
        java.sql.Date sqlDate = java.sql.Date.valueOf(LocalDate.of(2022, 11, 29));
        //
        boolean actual = underTest.getStatsForIp(ip, pageable).getContent().stream()
                .anyMatch(s -> s.getDate().equals(sqlDate));
        //
        Assertions.assertTrue(actual);
    }

    @Test
    @Sql(scripts = {"file:src/test/java/resources/insert_data.sql"})
    void getFullStats() {
        String ip = "212.34.52.003";
        //
        boolean actual = underTest.getFullStats(pageable).getContent().stream()
                .anyMatch(s -> s.getIp().equals(ip));
        //
        Assertions.assertTrue(actual);
        //
    }
}