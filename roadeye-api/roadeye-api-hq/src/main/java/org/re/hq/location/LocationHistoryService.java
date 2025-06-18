package org.re.hq.location;

import lombok.RequiredArgsConstructor;
import org.re.driving.domain.DrivingHistory;
import org.re.driving.service.DrivingHistoryDomainService;
import org.re.location.domain.LocationHistory;
import org.re.location.service.LocationHistoryDomainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LocationHistoryService {
    private final LocationHistoryDomainService locationHistoryDomainService;
    private final DrivingHistoryDomainService drivingHistoryDomainService;

    public List<LocationHistory> getLatestLocationHistories() {
        List<DrivingHistory> drivingHistoryList = drivingHistoryDomainService.getActiveDrivingHistory();
        List<Long> drivingHistoryIds = drivingHistoryList.stream().map(DrivingHistory::getId).toList();
        return locationHistoryDomainService.findLatestLocationHistory(drivingHistoryIds);
    }
}
