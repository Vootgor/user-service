package com.bikebuilder.userservice.Integration;

import com.bikebuilder.userservice.adapter.in.web.dto.UserCreateRequest;
import com.bikebuilder.userservice.adapter.in.web.dto.UserResponse;
import com.bikebuilder.userservice.adapter.in.web.dto.UserUpdateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.web.servlet.MockMvc;

import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@EmbeddedKafka(partitions = 1, topics = {"user-deleted"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafka;

    @Autowired
    private ObjectMapper objectMapper;

    private UserResponse createdUser;

    @BeforeEach
    void setUp() throws Exception {
        String uniqueEmail = "user" + UUID.randomUUID() + "@gmail.com";
        UserCreateRequest request = new UserCreateRequest(uniqueEmail, "password");

        String response = mockMvc.perform(post("/api/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getContentAsString();
        createdUser = objectMapper.readValue(response, UserResponse.class);
    }

    private Map<String, Object> getKafkaProps() {
        return Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, embeddedKafka.getBrokersAsString(),
                ConsumerConfig.GROUP_ID_CONFIG, "test-group",
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest",
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class
        );
    }


    @Test
    void createUser_happyPath() throws Exception {
        UserCreateRequest request = new UserCreateRequest(
            "mail@example.com",
            "password123"
        );

        mockMvc.perform(post("/api/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.email").value("mail@example.com"))
            .andExpect(jsonPath("$.id").isNotEmpty());
    }


    @Test
    void getUser_happyPath() throws Exception {
        mockMvc.perform(get("/api/users/" + createdUser.id()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value(createdUser.email()))
            .andExpect(jsonPath("$.id").value(createdUser.id().toString()));
    }


    @Test
    void updateUser_happyPath() throws Exception {
        UserUpdateRequest updateRequest = new UserUpdateRequest(
            "vanya666@mail.com",
            null,
            "Ivan",
            "ivanov",
            "8-800-2000-600");

        mockMvc.perform(put("/api/users/" + createdUser.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value(updateRequest.email()))
            .andExpect(jsonPath("$.name").value(updateRequest.name()))
            .andExpect(jsonPath("$.lastName").value(updateRequest.lastName()))
            .andExpect(jsonPath("$.phoneNumber").value(updateRequest.phoneNumber()));
    }


    @Test
    void deleteUserWithKafka_happyPath() throws Exception {
        mockMvc.perform(delete("/api/users/" + createdUser.id()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value(createdUser.email()));

        try (Consumer<String, String> consumer = new KafkaConsumer<>(getKafkaProps())) {
            consumer.subscribe(List.of("user-deleted"));

            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(5));
            assertThat(records.count()).isGreaterThan(0);

            ConsumerRecord<String, String> record = records.iterator().next();
            assertThat(record.value()).contains(createdUser.id().toString());
        }
    }
}
