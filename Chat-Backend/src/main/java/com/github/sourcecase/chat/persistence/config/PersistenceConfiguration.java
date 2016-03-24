package com.github.sourcecase.chat.persistence.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Component
@Configuration
@EnableJpaRepositories("com.github.sourcecase.chat.persistence.repositories")
public class PersistenceConfiguration {

	// private JdbcTemplate jdbcTemplate;

	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
			JpaVendorAdapter jpaVendorAdapter) {
		LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
		lef.setDataSource(dataSource);
		lef.setJpaVendorAdapter(jpaVendorAdapter);
		lef.setPackagesToScan("com.github.sourcecase.chat.persistence");
		return lef;
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(false);
		hibernateJpaVendorAdapter.setGenerateDdl(true);
		hibernateJpaVendorAdapter.setDatabase(Database.H2);
		return hibernateJpaVendorAdapter;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new JpaTransactionManager();
	}

	// @Bean
	// public JdbcTemplate jdbcTemplate() {
	// JdbcDataSource dataSource = new JdbcDataSource();
	// String url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
	// dataSource.setURL(url);
	//
	// JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	//
	// return jdbcTemplate;
	// }
	//
	// @Autowired
	// public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
	// this.jdbcTemplate = jdbcTemplate;
	// if(jdbcTemplate != null) {
	// this.initDatabaseThroughJdbc();
	// } else {
	// Logger.getLogger(ChatParticipantDTOImpl.class.getName()).log(Level.SEVERE,
	// "jdbcTemplate problem");
	// }
	// }

	// private void initDatabaseThroughJdbc() {
	// Logger.getLogger(ChatParticipantDTOImpl.class.getName()).log(Level.SEVERE,
	// "jdbcTemplate connected");
	//
	// jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
	// jdbcTemplate.execute("CREATE TABLE customers(" +
	// "id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");
	//
	// // Split up the array of whole names into an array of first/last names
	// List<Object[]> splitUpNames = Arrays.asList("John Woo", "Jeff Dean",
	// "Josh Bloch", "Josh Long").stream()
	// .map(name -> name.split(" "))
	// .collect(Collectors.toList());
	//
	// // Use a Java 8 stream to print out each tuple of the list
	// splitUpNames.forEach(name ->
	// Logger.getLogger(ChatParticipantDTOImpl.class.getName()).log(Level.SEVERE,String.format("Inserting
	// customer record for %s %s", name[0], name[1])));
	//
	// // Uses JdbcTemplate's batchUpdate operation to bulk load data
	// jdbcTemplate.batchUpdate("INSERT INTO customers(first_name, last_name)
	// VALUES (?,?)", splitUpNames);
	//
	// Logger.getLogger(ChatParticipantDTOImpl.class.getName()).log(Level.SEVERE,"Querying
	// for customer records where first_name = 'Josh':");
	// jdbcTemplate.query(
	// "SELECT id, first_name, last_name FROM customers WHERE first_name = ?",
	// new Object[] { "Josh" },
	// (rs, rowNum) -> new ChatParticipantDTOImpl(rs.getLong("id"),
	// rs.getString("first_name"))
	// ).forEach(customer ->
	// Logger.getLogger(ChatParticipantDTOImpl.class.getName()).log(Level.SEVERE,customer.toString()));
	// }

}
