package covidtracker.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Hello world!
 *
 */
@SpringBootApplication
//Same as @EnableAutoConfiguration,@Configuration,@ComponentScan
//this above statement does the job of all three Annotations mentioned above but are used in case we want specific things in our application.
//Even if we don't annotate the class then also 
public class App 
{
    public static void main( String[] args )
    {
    	
    	SpringApplication.run(App.class, args);
       // Returns Application Context
       //Inside this static method the main class that we want to run is specified 
       
    }
}
