package com.racheal.fundadmin.fundadmin.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.script.DigestUtils;


public class HashUtil {
    public static String hash(Object object) {
        try{
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(object);
            return DigestUtils.sha1DigestAsHex(json);

        } catch (Exception e) {
            throw new RuntimeException("failed to hash object",e);
        }
    }
}
