package org.re.location.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.re.common.domain.DomainService;
import org.re.location.domain.LocationHistory;
import org.re.location.domain.LocationHistoryRepository;

@DomainService
@Transactional
@RequiredArgsConstructor
public class LocationHistoryDomainService {
    private final LocationHistoryRepository locationHistoryRepository;

    public void save(LocationHistory locationHistory) {
        locationHistoryRepository.save(locationHistory);
    }
}
