package com.example.customerdemo.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.customerdemo.DTO.CustomerDTO;
import com.example.customerdemo.entity.CustomerEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDTO toDTO(CustomerEntity entity);

    CustomerEntity toEntity(CustomerDTO dto);

    List<CustomerDTO> toDTOList(List<CustomerEntity> entities);
}
