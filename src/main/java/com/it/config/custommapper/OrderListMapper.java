package com.it.config.custommapper;

import com.it.dto.request.OrderListRequestDto;
import com.it.dto.response.OrderListResponseDto;
import com.it.model.OrderList;
import lombok.Getter;
import org.dozer.DozerConverter;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;

import java.time.LocalDate;

/**
 * Class with programmatic Builder of Dozer mappings for OrderList entity
 */
public class OrderListMapper {
    @Getter
    private BeanMappingBuilder orderListMappingResponseDto = new BeanMappingBuilder() {
        @Override
        protected void configure() {
            mapping(OrderListResponseDto.class, OrderList.class).fields("beginningDateOfTour", "beginningDateOfTour", FieldsMappingOptions.customConverter(OrderListConverterImpl.class));
        }
    };

    @Getter
    private BeanMappingBuilder orderListMappingRequestDto = new BeanMappingBuilder() {
        @Override
        protected void configure() {
            mapping(OrderListRequestDto.class, OrderList.class).fields("beginningDateOfTour", "beginningDateOfTour", FieldsMappingOptions.customConverter(OrderListConverterImpl.class));
        }
    };

    /**
     * Implementation of new Custom Converter (for OrderList entity)
     * for value transformation (LocalDate - String)
     */
    public static class OrderListConverterImpl extends DozerConverter<LocalDate, String> {

        public OrderListConverterImpl() {
            super(LocalDate.class, String.class);
        }

        @Override
        public String convertTo(LocalDate source, String destination) {
            return String.valueOf(source);
        }

        @Override
        public LocalDate convertFrom(String source, LocalDate destination) {
            return LocalDate.parse(source);
        }
    }
}

