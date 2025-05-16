package org.re.mdtlog.collector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
    "org.re.mdtlog.collector",
    "org.re.mdtlog.domain"
})
public class MdtCollectorApplication {
    public static void main(String[] args) {
        SpringApplication.run(MdtCollectorApplication.class, args);
    }
}
