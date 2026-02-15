package com.haiilo.kata.backend.config;

import com.haiilo.kata.backend.model.mapper.DateMapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@MapperConfig(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = DateMapper.class
)
public interface CentralMapperConfig {
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void ignoreAuditFields(Object dto, @MappingTarget Object entity);
}
