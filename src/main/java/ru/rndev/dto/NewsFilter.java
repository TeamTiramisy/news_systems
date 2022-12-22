package ru.rndev.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class NewsFilter {

    String title;

    String text;

}
