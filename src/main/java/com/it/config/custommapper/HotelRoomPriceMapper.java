package com.it.config.custommapper;

import com.it.dto.HotelRoomPriceDto;
import com.it.model.HotelRoomPrice;
import lombok.Getter;
import org.dozer.DozerConverter;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;

import java.time.LocalDate;

/**
 * Class with programmatic Builder of Dozer mappings for HotelRoomPrice entity
 */
public class HotelRoomPriceMapper {
    @Getter
    private BeanMappingBuilder hotelRoomPriceMapping = new BeanMappingBuilder() {
        @Override
        protected void configure() {
            mapping(HotelRoomPriceDto.class, HotelRoomPrice.class).fields("date", "date", FieldsMappingOptions.customConverter(HotelRoomPriceConverterImpl.class));
        }
    };

    /**
     * Implementation of new Custom Converter (for HotelRoomPrice entity)
     * for value transformation
     */
    public static class HotelRoomPriceConverterImpl extends DozerConverter<LocalDate, String> {

        public HotelRoomPriceConverterImpl() {
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

