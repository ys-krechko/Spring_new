package com.it.config.custommapper;

import com.it.dto.CustomerDto;
import com.it.model.Customer;
import lombok.Getter;
import org.dozer.DozerConverter;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;

import java.time.LocalDate;

/**
 * Class with programmatic Builder of Dozer mappings for Customer entity
 */
public class CustomerMapper {
    @Getter
    private BeanMappingBuilder customerMapping = new BeanMappingBuilder() {
        @Override
        protected void configure() {
            mapping(CustomerDto.class, Customer.class).fields("customersContractDateOfSigning", "customersContractDateOfSigning", FieldsMappingOptions.customConverter(CustomerConverterImpl.class));
        }
    };

    /**
     * Implementation of new Custom Converter (for Hotel entity)
     * for value transformation
     */
    public static class CustomerConverterImpl extends DozerConverter<LocalDate, String> {

        public CustomerConverterImpl() {
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
