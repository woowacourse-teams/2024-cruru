package com.cruru.dashboard.domain.repository;

import static com.cruru.util.fixture.DashboardFixture.createBackendDashboard;
import static com.cruru.util.fixture.DashboardFixture.createFrontendDashboard;
import static org.assertj.core.api.Assertions.assertThat;

import com.cruru.dashboard.domain.Dashboard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DisplayName("대시보드 레포지토리 테스트")
@DataJpaTest
class DashboardRepositoryTest {

    @Autowired
    private DashboardRepository dashboardRepository;

    @BeforeEach
    void setUp() {
        dashboardRepository.deleteAllInBatch();
    }

    @DisplayName("ID가 없는 대시보드를 저장하면, ID를 순차적으로 부여하여 저장한다.")
    @Test
    void saveNoId() {
        //given
        Dashboard dashboard1 = createBackendDashboard();
        Dashboard dashboard2 = createFrontendDashboard();

        //when
        Dashboard savedDashboard1 = dashboardRepository.save(dashboard1);
        Dashboard savedDashboard2 = dashboardRepository.save(dashboard2);

        //then
        assertThat(savedDashboard1.getId() + 1).isEqualTo(savedDashboard2.getId());
    }
}
