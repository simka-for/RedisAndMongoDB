package Redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.Tuple;

import java.util.Date;
import java.util.List;

public class RedisSiteUsers {

    List<Tuple> result;
    Jedis jedis = new Jedis();

    public static void main(String[] args) {

        RedisSiteUsers redisSiteUsers = new RedisSiteUsers();
        redisSiteUsers.start();

    }
    private void start(){
        init();
        print();
    }

    private void init() {

        for (int i = 1; i <= 20; i++) {
            jedis.zadd("Users", new Date().getTime(), String.valueOf(i));
        }

        ScanResult<Tuple> users = jedis.zscan("Users", "0");
        result = users.getResult();
    }

    private void print() {

        for (Tuple user : result) {
            System.out.println("На главной странице показываем пользователя " + user.getElement());

            try {
                Thread.sleep(1000);
                if (Math.random() < 0.10) {
                    bill();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        print();
    }

    private void bill() throws InterruptedException {

        int i = (int) (0 + result.size() * Math.random());
        String element = result.get(i).getElement();
        System.out.println("Пользователь " + element + " оплатил платную услугу");
        System.out.println("На главной странице показываем пользователя " + element);
        Thread.sleep(1000);
    }
}
