package org.re.mdtlog.collector.app.ignition;

import lombok.RequiredArgsConstructor;
import org.re.mdtlog.collector.app.ignition.dto.MdtIgnitionOnRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ignition")
public class MdtIgnitionController {
    private final MdtIgnitionService mdtIgnitionService;

    @PostMapping(
        value = "/on",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public void ignitionOn(@RequestBody MdtIgnitionOnRequest dto) {
        mdtIgnitionService.ignitionOn(dto);
    }
}
