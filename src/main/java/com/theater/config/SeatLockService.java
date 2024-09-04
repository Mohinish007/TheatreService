package com.theater.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SeatLockService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public boolean lockSeats(Long showId, List<String> seatIds) {
        boolean allSeatsLocked = true;
        for (String seatId : seatIds) {
            String key = generateRedisKey(showId, seatId);
            ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();

            // Try to set the lock with a TTL of 5 minutes
            Boolean success = valueOperations.setIfAbsent(key, "LOCKED", 5, TimeUnit.MINUTES);
            if (!Boolean.TRUE.equals(success)) {
                allSeatsLocked = false;
            }
        }
        return allSeatsLocked;
    }

    public void unlockSeats(Long showId, List<String> seatIds) {
        for (String seatId : seatIds) {
            String key = generateRedisKey(showId, seatId);
            redisTemplate.delete(key);
        }
    }

    public boolean isSeatLocked(Long showId, String seatId) {
        String key = generateRedisKey(showId, seatId);
        return redisTemplate.hasKey(key);
    }

    private String generateRedisKey(Long showId, String seatId) {
        return "show:" + showId + ":seat:" + seatId + ":locked";
    }
}
