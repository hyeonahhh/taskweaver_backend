package backend.taskweaver.global.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    // Redis Connector를 만들어주어야 하는데, Redis Connector Factory로 만든다.
    // Redis Connector에는 대표적으로 Lettuce와 Jedis가 있는데 이 중 Lettuce를 사용. Lettuce가 비동기 지원, jedis는 동기만 가능
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration();
        redisConfiguration.setHostName(host);
        redisConfiguration.setPort(port);
        return new LettuceConnectionFactory(redisConfiguration);
    }


    // RedisTemplate은 Redis 데이터 액세스 코드를 간소화하는 스프링 데이터 리디스의 핵심 클래스
    // Spring boot 2.0부터 RedisTemplate와 StringTemplate를 자동생성 되어서 따로 빈에 등록안해도 되지만, Bean으로 등록하면서 믾은 세부적인 것들을 설정할 수 있어서, Bean으로 등록하는 것이 좋다.
    // 기본적으로 RedisTemplate는 Java 객체와 Redis 저장소 사이의 직렬화(serialization)와 역직렬화(deserialization)를 담당. 그래서 RedisTemplate에서 serializer를 설정해준다.
    // RedisTemplate에 직렬화 방법(serializer)를 별도로 설정하지 않으면, 기본적으로 JDK 직렬화 방식을 사용. JDK의 기본 직렬화는 데이터를 바이너리 형태로 저장하기 때문에, 직렬화되지 않은 형태로 데이터를 저장하거나 조회할 때 확인하기 어렵다.
    // 따라서, 사용자 친화적인 형태로 사용하려면, JSON 형식 등을 직렬화/역직렬화 할 수 있는 직렬화 방식을 명시적으로 설정. 예를 들어, key와 value를 모두 문자열로 다루고자 할 때는 StringRedisTemplate를 사용하거나, Jackson2JsonRedisSerializer, StringRedisSerializer 등을 RedisTemplate에 설정하여 사용하는 것이 좋다.
    @Primary
    @Bean
    public RedisTemplate<String, String> redisTemplate() {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());

        // 일반적인 key:value의 경우 시리얼라이저
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        // 모든 경우
        redisTemplate.setDefaultSerializer(new StringRedisSerializer());

        return redisTemplate;
    }
}
