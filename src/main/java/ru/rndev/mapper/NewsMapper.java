package ru.rndev.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.rndev.dto.NewsCreateDto;
import ru.rndev.dto.NewsDto;
import ru.rndev.dto.NewsFilter;
import ru.rndev.entity.News;

@Mapper
public interface NewsMapper {

    NewsDto mapToDto(News news);

    News mapToEntity(NewsCreateDto newsCreateDto);

    News mapFromFilter(NewsFilter newsFilter);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    News mapToUpdate(@MappingTarget News news, NewsCreateDto newsCreateDto);

}
