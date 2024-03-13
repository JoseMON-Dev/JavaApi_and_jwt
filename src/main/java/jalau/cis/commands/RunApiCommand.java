package jalau.cis.commands;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

@SpringBootApplication
@ComponentScan(basePackages = {"jalau.cis.commands", "jalau.cis.api"})
@Command(name = "-api", description = "Start the API")
public class RunApiCommand implements Callable<Integer> {

  @Option(names = {"-p", "--port"}, description = "Port for the API")
  private int port = 8080;

  @Override
  public Integer call() throws Exception {
    try {
      System.setProperty("server.port", String.valueOf(port));
      SpringApplication.run(RunApiCommand.class, new String[0]);
      return 0;
    } catch (Exception e) {
      System.out.printf("Cannot start the API. %s%n", e.getMessage());
      throw new Exception(e.getMessage());
    }
  }
}
