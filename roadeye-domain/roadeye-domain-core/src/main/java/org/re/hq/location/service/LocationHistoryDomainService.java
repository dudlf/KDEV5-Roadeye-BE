package org.re.hq.location.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.re.hq.domain.common.DomainService;
import org.re.hq.location.domain.LocationHistory;
import org.re.hq.location.domain.LocationHistoryRepository;

@DomainService
@Transactional
@RequiredArgsConstructor
public class LocationHistoryDomainService {
    private final LocationHistoryRepository locationHistoryRepository;

    public void save(LocationHistory locationHistory) {
        locationHistoryRepository.save(locationHistory);
    }
}
