package Redis;

import redis.clients.jedis.Jedis;

import java.util.Map;

public class RedisStudentWork {

    public static void main(String[] args) throws InterruptedException {

        Jedis jedis = new Jedis();

        System.out.println("\nДобавляем студенту Иванову И.И два курса и устанавливаем " +
                "количество выполненых домашних заданий\n");

        jedis.hset("Ivanov I.I", "Web-Developer", "1");
        jedis.hset("Ivanov I.I", "Data Science", "4");

        Thread.sleep(2000);
        System.out.println("Все курсы и количество выполненых домашних заданий:");

        Map<String, String> student = jedis.hgetAll("Ivanov I.I");
        student.forEach((key, value) -> System.out.println(key + " " + value));

        Thread.sleep(2000);
        System.out.println("\nДобавляем одно выполненое задание к курсу Data Science");

        jedis.hset("Ivanov I.I", "Data Science", "5");

        Thread.sleep(2000);
        System.out.println("Количество выполненых заданий по Data Science :");

        String dataSc = jedis.hget("Ivanov I.I", "Data Science");
        System.out.println(dataSc);
        

    }
}
