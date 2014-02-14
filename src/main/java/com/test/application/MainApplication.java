package com.test.application;

import com.test.Person;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;


@Configuration
@EnableJpaRepositories
public class MainApplication extends Application {


    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().setType(H2).build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
        lef.setDataSource(dataSource);
        lef.setJpaVendorAdapter(jpaVendorAdapter);
        lef.setPackagesToScan("com.test");
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



















    
    public static String personScreenID = "person";
    public static String personScreenFile = "person.fxml";
    public static String screen2ID = "screen2";
    public static String screen2File = "Screen2.fxml";
    public static String screen3ID = "screen3";
    public static String screen3File = "Screen3.fxml";

    public static String createPersonID = "nouveauPerson";
    public static String createPersonFile = "nouveauPerson.fxml";
    
    
    @Override
    public void start(Stage primaryStage) {

        AbstractApplicationContext context = new AnnotationConfigApplicationContext(MainApplication.class);
        PersonRepository repository = context.getBean(PersonRepository.class);
        repository.save(new Person("faiez", "elleuch","felleuch@gmail.com"));
        repository.save(new Person("faiez2", "elleuch","felleuch@gmail.com"));
        repository.save(new Person("faiez3", "elleuch","felleuch@gmail.com"));
        repository.save(new Person("faiez4", "elleuch","felleuch@gmail.com"));
        repository.save(new Person("faiez5", "elleuch","felleuch@gmail.com"));
        repository.save(new Person("faiez6", "elleuch","felleuch@gmail.com"));
        repository.save(new Person("faiez7", "elleuch","felleuch@gmail.com"));
        repository.save(new Person("faiez8", "elleuch","felleuch@gmail.com"));

        
        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(MainApplication.personScreenID, MainApplication.personScreenFile);
        mainContainer.loadScreen(MainApplication.screen2ID, MainApplication.screen2File);
        mainContainer.loadScreen(MainApplication.screen3ID, MainApplication.screen3File);
        mainContainer.loadScreen("myMenu", "MyMenu.fxml");
        mainContainer.loadScreen(MainApplication.createPersonID, MainApplication.createPersonFile);
        
        mainContainer.setScreen(MainApplication.personScreenID);
        
        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setTitle("Application de gestion");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
