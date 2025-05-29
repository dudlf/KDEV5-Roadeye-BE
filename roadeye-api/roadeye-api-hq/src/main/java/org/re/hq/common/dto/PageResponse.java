package org.re.hq.common.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

public class PageResponse<T> extends SuccessResponse<List<T>> {
    PageInfo pageInfo;

    private PageResponse(List<T> data, PageInfo pageInfo) {
        super(data);
        this.pageInfo = pageInfo;
    }

    public static <T> PageResponse<T> of(Page<T> page) {
        return new PageResponse<>(page.getContent(), PageInfo.of(page));
    }

    @Getter
    public static class PageInfo {
        private final int page;
        private final int size;
        private final long total;

        private PageInfo(int page, int size, long total) {
            this.page = page;
            this.size = size;
            this.total = total;
        }

        public static PageInfo of(Page<?> page) {
            return new PageInfo(page.getNumber(), page.getSize(), page.getTotalElements());
        }
    }
}
