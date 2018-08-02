//package io.spring.helloworld.sec8lec37;
//
//import io.spring.helloworld.sec4lec14.Customer;
//import io.spring.helloworld.sec4lec14.CustomerRowMapper;
//import io.spring.helloworld.sec8lec36.ColumnRangeParitioner;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.core.explore.JobExplorer;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.core.partition.PartitionHandler;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.integration.partition.BeanFactoryStepLocator;
//import org.springframework.batch.integration.partition.MessageChannelPartitionHandler;
//import org.springframework.batch.integration.partition.StepExecutionRequestHandler;
//import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
//import org.springframework.batch.item.database.JdbcBatchItemWriter;
//import org.springframework.batch.item.database.JdbcPagingItemReader;
//import org.springframework.batch.item.database.Order;
//import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.integration.annotation.ServiceActivator;
//import org.springframework.integration.core.MessagingTemplate;
//import org.springframework.integration.scheduling.PollerMetadata;
//import org.springframework.scheduling.support.PeriodicTrigger;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//@Slf4j
//@RequiredArgsConstructor
//public class JobConfiguration implements ApplicationContextAware {
//    private final StepBuilderFactory stepBuilderFactory;
//    private final JobBuilderFactory jobBuilderFactory;
//    private final DataSource dataSource;
//    private final JobExplorer jobExplorer;
//    private final JobRepository jobRepository;
//    private ApplicationContext applicationContext;
//    private static final int GRID_SIZE = 4;
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        this.applicationContext = applicationContext;
//    }
//
//    @Bean
//    public PartitionHandler partitionHandler(MessagingTemplate messagingTemplate) throws Exception {
//        MessageChannelPartitionHandler partitionHandler = new MessageChannelPartitionHandler();
//        partitionHandler.setStepName("slaveStep");
//        partitionHandler.setGridSize(GRID_SIZE);
//        partitionHandler.setMessagingOperations(messagingTemplate);
//        partitionHandler.setPollInterval(5000L);
//        partitionHandler.setJobExplorer(this.jobExplorer);
//        partitionHandler.afterPropertiesSet();
//        return partitionHandler;
//    }
//
//    @Bean
//    public ColumnRangeParitioner partitioner() {
//        ColumnRangeParitioner columnRangePartitioner = new ColumnRangeParitioner();
//
//        columnRangePartitioner.setColumn("id");
//        columnRangePartitioner.setDataSource(this.dataSource);
//        columnRangePartitioner.setTable("customer");
//
//        return columnRangePartitioner;
//    }
//
//    @Bean
//    @Profile("slave")
//    @ServiceActivator(inputChannel = "inboundRequests", outputChannel = "outboundStaging")
//    public StepExecutionRequestHandler stepExecutionRequestHandler(){
//        StepExecutionRequestHandler requestHandler = new StepExecutionRequestHandler();
//
//        BeanFactoryStepLocator stepLocator = new BeanFactoryStepLocator();
//        stepLocator.setBeanFactory(this.applicationContext);
//
//        requestHandler.setStepLocator(stepLocator);
//        requestHandler.setJobExplorer(this.jobExplorer);
//
//        return requestHandler;
//    }
//
//    @Bean(name = PollerMetadata.DEFAULT_POLLER)
//    public PollerMetadata defaultPoller(){
//        PollerMetadata pollerMetadata = new PollerMetadata();
//        pollerMetadata.setTrigger(new PeriodicTrigger(10));
//        return pollerMetadata;
//    }
//
//    @Bean
//    @StepScope
//    public JdbcPagingItemReader<Customer> pagingItemReader(
//            @Value("#{stepExecutionContext['minValue']}")Long minValue,
//            @Value("#{stepExecutionContext['maxValue']}")Long maxValue) {
//        System.out.println("reading " + minValue + " to " + maxValue);
//        JdbcPagingItemReader<Customer> reader = new JdbcPagingItemReader<>();
//
//        reader.setDataSource(this.dataSource);
//        reader.setFetchSize(100);
//        reader.setRowMapper(new CustomerRowMapper());
//
//        MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
//        queryProvider.setSelectClause("id, firstName, lastName, birthdate");
//        queryProvider.setFromClause("from customer");
//        queryProvider.setWhereClause("where id >= " + minValue + " and id <= " + maxValue);
//
//        Map<String, Order> sortKeys = new HashMap<>(1);
//        sortKeys.put("id", Order.ASCENDING);
//        queryProvider.setSortKeys(sortKeys);
//        reader.setQueryProvider(queryProvider);
//
//        return reader;
//    }
//
//    @Bean
//    @StepScope
//    public JdbcBatchItemWriter<Customer> customerItemWriter() {
//        JdbcBatchItemWriter<Customer> itemWriter = new JdbcBatchItemWriter<>();
//
//        itemWriter.setDataSource(this.dataSource);
//        itemWriter.setSql("INSERT INTO CUSTOMER2 VALUES (:id, :firstName, :lastName, :birthdate)");
//        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider());
//        itemWriter.afterPropertiesSet();
//
//        return itemWriter;
//    }
//
//    @Bean
//    public Step step1() throws Exception {
//        return stepBuilderFactory.get("step1")
//                .partitioner(slaveStep().getName(), partitioner())
//                .step(slaveStep())
//                .partitionHandler(partitionHandler(null))
//                .build();
//    }
//
//    @Bean
//    public Step slaveStep() {
//        return stepBuilderFactory.get("slaveStep")
//                .<Customer, Customer>chunk(1000)
//                .reader(pagingItemReader(null, null))
//                .writer(customerItemWriter())
//                .build();
//    }
//
//    @Bean
//    @Profile("dev")
//    public Job job() throws Exception {
//        return jobBuilderFactory.get("job")
//                .incrementer(new RunIdIncrementer())
//                .start(step1())
//                .build();
//    }
//}
