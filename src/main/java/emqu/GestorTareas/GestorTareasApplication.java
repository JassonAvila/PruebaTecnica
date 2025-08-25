package emqu.GestorTareas;

import emqu.GestorTareas.Service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GestorTareasApplication {


	String nl = System.lineSeparator();
	private static final Logger logs = LoggerFactory.getLogger(GestorTareasApplication.class);

	public static void main(String[] args) {
		logs.info("*** Aplicacion Iniciada** ");

		SpringApplication.run(GestorTareasApplication.class, args);


	}

}
