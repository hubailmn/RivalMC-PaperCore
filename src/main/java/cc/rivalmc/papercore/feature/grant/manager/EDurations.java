package cc.rivalmc.papercore.feature.grant.manager;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Duration;

@AllArgsConstructor
@Getter
public enum EDurations {

    ONE_DAY("§f1 Day", Duration.ofDays(1)),
    THREE_DAYS("§f3 Days", Duration.ofDays(3)),
    TWO_WEEKS("§f2 Weeks", Duration.ofDays(14)),
    ONE_MONTH("§f1 Months", Duration.ofDays(30)),
    THREE_MONTHS("§f3 Months", Duration.ofDays(90)),
    LIFETIME("§fLIFETIME", Duration.ofDays(10000));

    private final String prefix;
    private final Duration duration;

}
