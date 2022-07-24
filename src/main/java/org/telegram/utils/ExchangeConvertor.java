package org.telegram.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ExchangeConvertor {

//    private String result;
//
//    private String documentation;
//
//    private String terms_of_use;
//
//    private Integer time_last_update_unix;
//
//    private String time_last_update_utc;
//
//    private Integer time_next_update_unix;
//
//    private String time_next_update_utc;
//
//    private String base_code;
//
//    private Map<String,Double> conversion_rates = new HashMap<>();

    @JsonProperty("result")
    private String result;
    @JsonProperty("documentation")
    private String documentation;
    @JsonProperty("terms_of_use")
    private String termsOfUse;
    @JsonProperty("time_last_update_unix")
    private Integer timeLastUpdateUnix;
    @JsonProperty("time_last_update_utc")
    private String timeLastUpdateUtc;
    @JsonProperty("time_next_update_unix")
    private Integer timeNextUpdateUnix;
    @JsonProperty("time_next_update_utc")
    private String timeNextUpdateUtc;
    @JsonProperty("base_code")
    private String baseCode;
    @JsonProperty("conversion_rates")
    private Map<String,Double> conversionRates = new HashMap<>();
}
