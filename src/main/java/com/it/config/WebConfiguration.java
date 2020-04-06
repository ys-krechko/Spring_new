package com.it.config;

import com.it.config.custommapper.CustomerMapper;
import com.it.config.custommapper.HotelRoomPriceMapper;
import com.it.config.custommapper.OrderListMapper;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * Configuration class of web components. Imports the Spring MVC configuration.
 */
@EnableWebMvc
public class WebConfiguration {

    /**
     * Bean for programmatic Builder of Dozer mappings for HotelRoomPrice entity
     *
     * @return - HotelRoomPriceMapper instance
     */
    @Bean
    public static HotelRoomPriceMapper hotelRoomPriceMap() {
        return new HotelRoomPriceMapper();
    }

    /**
     * Bean for programmatic Builder of Dozer mappings for Customer entity
     *
     * @return - CustomerMapper instance
     */
    @Bean
    public static CustomerMapper customerMap() {
        return new CustomerMapper();
    }

    /**
     * Bean for programmatic Builder of Dozer mappings for OrderList entity
     *
     * @return - OrderListMapper instance
     */
    @Bean
    public static OrderListMapper orderListMap() {
        return new OrderListMapper();
    }

    /**
     * Create Bean from Mapper interface for performing Dozer mappings with custom converters
     * for HotelRoomPrice, Customer entities
     * from application code
     *
     * @return - DozerBeanMapper instance
     */
    @Bean
    public Mapper mapper() {
        DozerBeanMapper mapper = new DozerBeanMapper();
        mapper.addMapping(hotelRoomPriceMap().getHotelRoomPriceMapping());
        mapper.addMapping(customerMap().getCustomerMapping());
        mapper.addMapping(orderListMap().getOrderListMappingRequestDto());
        mapper.addMapping(orderListMap().getOrderListMappingResponseDto());
        return mapper;
    }

    /**
     * Create Bean from service interface for encoding passwords
     *
     * @return - BCryptPasswordEncoder.
     * Implementation of PasswordEncoder that uses the BCrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Creates Bean that generates views by setted parameters
     *
     * @return - InternalResourceViewResolver instance
     */
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
}
