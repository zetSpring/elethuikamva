package elethu.ikamva.mappers;

import elethu.ikamva.domain.Payment;
import elethu.ikamva.dto.PaymentDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    @Mapping(source = "investmentId", target = "investId")
    PaymentDto modelToDto(Payment payment);

    List<PaymentDto> modelsDtToDtos(List<Payment> payments);

    List<Payment> dtosToModels(List<PaymentDto> paymentDtos);

    @InheritInverseConfiguration
    Payment dtoToModel(PaymentDto paymentDto);
}
