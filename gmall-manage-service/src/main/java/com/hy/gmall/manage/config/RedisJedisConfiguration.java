package com.hy.gmall.manage.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 根据配置自适应方式连接redis
 */
@Configuration
public class RedisJedisConfiguration {
    @Value("${spring.redis.cluster.nodes}")
    private String nodes;

    @Value("${spring.single.redis.host}")
    private String singleHost;
    @Value("${spring.single.redis.port}")
    private int singlePort;
    @Value("${redis.type}")
    private String redisType;

    @Bean
    RedisConfiguration redisConfiguration(){
        if ("single".equals(redisType)){
            RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
            configuration.setDatabase(0);
            configuration.setHostName(singleHost);
            configuration.setPort(singlePort);
        }else if ("cluster".equals(redisType)){
            RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
            Set<RedisNode> redisNodes = new HashSet();
            Set<String> hostAndPorts = Arrays.stream(nodes.split(",")).collect(Collectors.toSet());
            for (String hostAndPort : hostAndPorts){
                String[] args = StringUtils.split(hostAndPort, ":");
                RedisNode redisNode = new RedisNode(args[0].trim(), Integer.valueOf(args[1]));
                redisNodes.add(redisNode);
            }
            redisClusterConfiguration.setClusterNodes(redisNodes);
            redisClusterConfiguration.setMaxRedirects(100);
            return redisClusterConfiguration;
        }
        return null;
    }

    @Bean
    JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxWaitMillis(5000);
        config.setMinIdle(0);
        config.setMaxIdle(8);
        config.setMaxTotal(100);
        config.setMinEvictableIdleTimeMillis(180000);
        config.setNumTestsPerEvictionRun(3);
        config.setTimeBetweenEvictionRunsMillis(-1);
        config.setTestOnBorrow(true);
        config.setTestWhileIdle(true);
        return config;
    }
    @Bean
    public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig){
        if ("single".equals(redisType)){
            RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
            configuration.setDatabase(0);
            configuration.setHostName(singleHost);
            configuration.setPort(singlePort);
            return new JedisConnectionFactory(configuration);
        }else if ("cluster".equals(redisType)){
            RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
            Set<RedisNode> redisNodes = new HashSet();
            Set<String> hostAndPorts = Arrays.stream(nodes.split(",")).collect(Collectors.toSet());
            for (String hostAndPort : hostAndPorts){
                String[] args = StringUtils.split(hostAndPort, ":");
                RedisNode redisNode = new RedisNode(args[0].trim(), Integer.valueOf(args[1]));
                redisNodes.add(redisNode);
            }
            redisClusterConfiguration.setClusterNodes(redisNodes);
            redisClusterConfiguration.setMaxRedirects(100);
            return new JedisConnectionFactory(redisClusterConfiguration,jedisPoolConfig);
        }
        return null;
    }

    @Bean
    public RedisTemplate<String,Object> redisTemplate(JedisConnectionFactory factory){
        RedisTemplate<String,Object> template=new RedisTemplate<>();
        template.setConnectionFactory(factory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        //key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        //value采用jackson序列化方式
        template.setValueSerializer(jackson2JsonRedisSerializer);
        //hash的key采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        //hash的value采用String的序列化方式
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
}
