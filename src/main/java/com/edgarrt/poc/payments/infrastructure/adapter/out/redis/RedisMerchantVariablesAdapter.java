package com.edgarrt.poc.payments.infrastructure.adapter.out.redis;

import com.edgarrt.poc.payments.application.port.out.MerchantVariablesPort;
import com.edgarrt.poc.payments.domain.model.MerchantId;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RedisMerchantVariablesAdapter implements MerchantVariablesPort {
    private final StringRedisTemplate redisTemplate;

    public RedisMerchantVariablesAdapter(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Map<String, String> getVariables(MerchantId merchantId) {
        String key = key(merchantId);
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);
        if (entries.isEmpty()) {
            Map<String, String> defaults = defaultVariables();
            saveVariables(merchantId, defaults);
            return defaults;
        }
        Map<String, String> result = new HashMap<>();
        entries.forEach((k, v) -> result.put(String.valueOf(k), String.valueOf(v)));
        return result;
    }

    @Override
    public void saveVariables(MerchantId merchantId, Map<String, String> variables) {
        if (variables == null || variables.isEmpty()) {
            return;
        }
        redisTemplate.opsForHash().putAll(key(merchantId), new HashMap<>(variables));
    }

    private String key(MerchantId merchantId) {
        return "payment:variables:{" + merchantId.value() + "}";
    }

    private Map<String, String> defaultVariables() {
        return Map.of(
                "pspRoutingKey", "niubiz-primary",
                "threeDsRequired", "true",
                "maxAmountWithoutReview", "500.00",
                "tokenAudience", "demo-card-gateway"
        );
    }
}
