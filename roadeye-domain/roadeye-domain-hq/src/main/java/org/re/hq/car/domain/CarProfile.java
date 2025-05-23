package org.re.hq.car.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

    @Embeddable
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public class CarProfile {

        @Column(nullable = false, length = 30)
        private String name;

        @Column(nullable = false, length = 8)
        private String number;

        @Column(length = 512)
        private String imageUrl;

        public CarProfile(String name, String imageUrl, String number) {
            this.name = name;
            this.number = number;
            this.imageUrl = imageUrl;
        }
    }
