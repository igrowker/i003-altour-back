package com.igrowker.altour.persistence.Redis;

import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPooled;

import java.util.HashMap;
import java.util.Map;

@Repository
public class NotificationsRepository implements INotificationsRepository {


    @Override
    public UserLocationAndPopulation getUserLocationAndPopulation(String userId) {

        JedisPool pool = new JedisPool(System.getenv("REDIS_HOST"), 6379);

        UserLocationAndPopulation userLocationAndPopulation = new UserLocationAndPopulation();


        try (Jedis jedis = pool.getResource()) {
            if (jedis.exists("user " + userId)) {
                userLocationAndPopulation.setLat(Long.parseLong(jedis.hget("user " + userId, "lat")));
                userLocationAndPopulation.setLng(Long.parseLong(jedis.hget("user " + userId, "lng")));
                userLocationAndPopulation.setPopulation(Long.parseLong(jedis.hget("user " + userId, "population")));
            }
        } catch (Exception e) {
                e.printStackTrace();
                return null;
        }
        
        return userLocationAndPopulation;
    }

    @Override
    public void setUserLocationAndPopulation(String userId, Map<String, String> userLocationAndPopulation) {

        JedisPool pool = new JedisPool(System.getenv("REDIS_HOST"), 6379);

        try (Jedis jedis = pool.getResource()) {
        jedis.hset("user " + userId, userLocationAndPopulation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
