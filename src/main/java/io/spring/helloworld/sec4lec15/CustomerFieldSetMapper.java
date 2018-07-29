//package io.spring.helloworld.sec4lec15;
//
//import io.spring.helloworld.sec4lec14.Customer;
//import org.springframework.batch.item.database.JdbcBatchItemWriter;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.mapping.DefaultLineMapper;
//import org.springframework.batch.item.file.mapping.FieldSetMapper;
//import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
//import org.springframework.batch.item.file.transform.FieldSet;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.validation.BindException;
//
//public class CustomerFieldSetMapper implements FieldSetMapper<Customer> {
//    @Override
//    public Customer mapFieldSet(FieldSet fieldSet) throws BindException {
//        return new Customer(fieldSet.readLong("id"),
//                fieldSet.readString("firstName"),
//                fieldSet.readString("lastName"),
//                fieldSet.readDate("birthdate", "yyyy-MM-dd HH:mm:ss"));
//    }
//}
