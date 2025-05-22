package org.re.hq.admin.service;

import lombok.RequiredArgsConstructor;
import org.re.hq.admin.domain.PlatformAdmin;
import org.re.hq.admin.repository.PlatformAdminRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PlatformAdminService {
    private final PlatformAdminRepository platformAdminRepository;

    private final PasswordEncoder passwordEncoder;

    public PlatformAdmin createAdmin(String username, String rawPassword) {
        var encodedPassword = passwordEncoder.encode(rawPassword);
        var admin = PlatformAdmin.create(username, encodedPassword);
        return platformAdminRepository.save(admin);
    }
}
