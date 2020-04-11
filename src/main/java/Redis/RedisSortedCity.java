package Redis;

import redis.clients.jedis.Jedis;

import java.util.Scanner;

public class RedisSortedCity {

    public static void main(String[] args) throws InterruptedException {

        Jedis jedis = new Jedis();
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nДобавляем 10 городов в общий список и указываем стоимость пути");
        Thread.sleep(1000);

        jedis.zadd("City", 100, "Moscow");
        jedis.zadd("City", 500, "Novgorod");
        jedis.zadd("City", 700, "Orel");
        jedis.zadd("City", 1500, "Rostov on Don");
        jedis.zadd("City", 2000, "Krasnodar");
        jedis.zadd("City", 4000, "Kazan");
        jedis.zadd("City", 5000, "Saint-P");
        jedis.zadd("City", 8000, "Krasnoyarsk");
        jedis.zadd("City", 10000, "Novosibirsk");
        jedis.zadd("City", 15000, "Vladivostok");

        System.out.println("Выведем общий список:");
        Thread.sleep(1000);

        System.out.println(jedis.zrangeByScore("City", "0", "+inf"));
        Thread.sleep(1000);
        System.out.println("\nТоп-3 самых дешевых путешествий:");
        System.out.println(jedis.zrange("City", 0, 2));

        System.out.println("\nТоп-3 самых дорогих путешествий:");
        System.out.println(jedis.zrevrange("City", 0, 2));

        System.out.println("\nЦена самого дорогого путешествия: " + jedis.zscore("City", "Vladivostok"));
        Thread.sleep(1000);
        System.out.println("\nВведите ваш бюджет: ");
        int budget = Integer.parseInt(scanner.nextLine());

        System.out.println("Количество городов в которые вы можете отправиться - " + jedis.zcount("City", 0, budget));
        Long cityCount = jedis.zcount("City", 0, budget);
        System.out.println(jedis.zrange("City", 0, cityCount -1));
    }


}
