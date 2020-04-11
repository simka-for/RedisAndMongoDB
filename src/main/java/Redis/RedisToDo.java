package Redis;


import redis.clients.jedis.Jedis;

import java.util.Scanner;

public class RedisToDo {

    public static void main(String[] args) throws InterruptedException {

        Jedis jedis = new Jedis();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Redis ToDoList ");
        Thread.sleep(500);
        System.out.println( "\nЧтобы добавить новое дело введите 'ADD 'Название дела''\n" +
                "Для удаления введите 'DEL 'Название дела''\n" +
                "Для просмотра всех дел введите ALL \n");
        Thread.sleep(500);
        System.out.println("Result выполнения - \n" +
                "1 - успешно\n" +
                "0 - безуспешно\n" );

        while (true){

            String firstRequest = scanner.nextLine();

            if (firstRequest.equals("ALL")){

                System.out.println(jedis.smembers("todo"));

            }else {

                String[] request = firstRequest.split(" ", 2);
                String todo = request[1];

                switch (request[0]){
                    case "ADD" -> System.out.println("Дело << " + request[1] + " >> добавлено! Result: " + jedis.sadd("todo" , todo));
                    case "DEL" -> System.out.println("Дело << " + request[1] + " >> удалено! Result: " + jedis.srem("todo" , todo));
                    default -> System.out.println("Введена неправильная команда");
                }
            }
        }
    }
}
