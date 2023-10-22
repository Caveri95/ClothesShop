package ru.skypro.courseWork;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * Главный класс приложения Clothes Shop.
 */
@SpringBootApplication
@Slf4j
public class HomeworkApplication {

  /**
   * Главный метод, запускающий приложение.
   *
   * @param args Аргументы командной строки.
   */
  public static void main(String[] args) {
    SpringApplication.run(HomeworkApplication.class, args);
    log.info("Application is running");
  }
}
