package hexlet.code.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.dto.taskStatuses.TaskStatusUpdateDTO;
import hexlet.code.model.TaskStatus;
import hexlet.code.repositories.TaskRepository;
import hexlet.code.repositories.TaskStatusRepository;
import hexlet.code.repositories.UserRepository;
import hexlet.code.util.ModelGenerator;
import org.instancio.Instancio;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskStatusesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelGenerator modelGenerator;

    private TaskStatus testTaskStatus;

    @BeforeEach
    public void setUp() {
        testTaskStatus = Instancio.of(modelGenerator.getTaskStatusModel()).create();
        taskStatusRepository.save(testTaskStatus);
    }

    @AfterEach
    public void tearDown() {
        taskStatusRepository.deleteAll();
        taskRepository.deleteAll();
        taskStatusRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void showTest() throws Exception {
        var response = mockMvc.perform(get("/api/task_statuses/" + testTaskStatus.getId())
                        .with(jwt().jwt(builder -> builder.subject("hexlet@example.com"))))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        assertThat(response.getContentAsString()).contains(testTaskStatus.getName());
        assertThat(response.getContentAsString()).contains(testTaskStatus.getSlug());
    }

    @Test
    public void indexTest() throws Exception {
        var response = mockMvc.perform(get("/api/task_statuses")
                        .with(jwt().jwt(builder -> builder.subject("hexlet@example.com"))))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        assertThat(response.getContentAsString()).containsIgnoringCase(testTaskStatus.getName());
        assertThat(response.getContentAsString()).containsIgnoringCase(testTaskStatus.getSlug());
    }

    @Test
    public void createTest() throws Exception {
        var newStatus = Instancio.of(modelGenerator.getTaskStatusModel()).create();

        mockMvc.perform(post("/api/task_statuses")
                        .with(jwt().jwt(builder -> builder.subject("hexlet@example.com")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(newStatus)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        var status = taskStatusRepository.findBySlug(newStatus.getSlug()).orElseThrow();
        assertThat(status.getName()).isEqualTo(newStatus.getName());
        assertThat(status.getSlug()).isEqualTo(newStatus.getSlug());
    }

    @Test
    public void updateTest() throws Exception {
        var dto = new TaskStatusUpdateDTO("New Name", "new_slug");
        mockMvc.perform(put("/api/task_statuses/" + testTaskStatus.getId())
                        .with(jwt().jwt(builder -> builder.subject("hexlet@example.com")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        var updatedTaskStatus = taskStatusRepository.findBySlug(dto.getSlug().get()).orElseThrow();

        assertThat(updatedTaskStatus.getSlug()).isEqualTo(dto.getSlug().get());
        assertThat(updatedTaskStatus.getName()).isEqualTo(dto.getName().get());
    }

    @Test
    public void deleteTest() throws Exception {
        mockMvc.perform(delete("/api/task_statuses/" + testTaskStatus.getId())
                        .with(jwt().jwt(builder -> builder.subject("hexlet@example.com"))))
                .andExpect(status().isNoContent())
                .andReturn().getResponse();
        assertThat(taskStatusRepository.findBySlug(testTaskStatus.getSlug())).isNotPresent();
    }

    @Test
    public void unauthorizedTest() throws Exception {
        mockMvc.perform(post("/api/task_statuses")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(put("/api/task_statuses/" + testTaskStatus.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/api/task_statuses"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/api/task_statuses/" + testTaskStatus.getId()))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(delete("/api/task_statuses/" + testTaskStatus.getId()))
                .andExpect(status().isUnauthorized());
    }
}
