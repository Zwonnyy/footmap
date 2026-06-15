package footmap.footmap_spring.service.aiService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import footmap.footmap_spring.dto.teamDto.team;
import footmap.footmap_spring.dto.userDto.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class AiServiceImpl implements AiService {

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${gemini.api-key:${openai.api-key:}}")
    private String apiKey;

    @Value("${gemini.model:gemini-3.1-flash-lite}")
    private String model;

    @Value("${gemini.base-url:https://generativelanguage.googleapis.com/v1beta/models}")
    private String baseUrl;

    @Override
    public String analyzePlayer(User player) {
        if (player == null) {
            return "선수 정보를 찾을 수 없어 분석을 만들 수 없습니다.";
        }
        String fallback = localPlayerAnalysis(player);
        String prompt = String.format(
                "풋살 서비스의 선수 상세 화면에 표시할 짧은 한국어 코멘트를 작성해줘. " +
                        "칭찬만 하지 말고 강점과 다음 성장 포인트를 2문장으로 말해줘. 선수명:%s, 골:%d, 어시스트:%d, 수비:%d",
                player.getU_nick(), player.getU_goal(), player.getU_assi(), player.getU_cut());
        return ask(prompt, fallback);
    }

    @Override
    public String summarizeBoard(String title, String contents) {
        String safeTitle = defaultText(title, "제목 없음");
        String safeContents = defaultText(contents, "");
        String fallback = localSummary(safeTitle, safeContents);
        String prompt = "풋살 커뮤니티 게시글을 한국어 한 문장으로 요약해줘. 제목: "
                + safeTitle + "\n내용: " + safeContents;
        return ask(prompt, fallback);
    }

    @Override
    public String moderateText(String text) {
        String fallback = localModeration(text);
        String prompt = "아래 풋살 커뮤니티 글이 비방, 개인정보 노출, 과도한 광고, 위험한 만남 유도에 해당하는지 " +
                "한국어 한 문장으로 판단해줘. 문제가 없으면 '게시해도 무리가 없습니다.'로 시작해줘.\n" + defaultText(text, "");
        return ask(prompt, fallback);
    }

    @Override
    public String generateGameIntro(String teamName, String fieldName, String people, String date, String time) {
        String fallback = String.format("%s에서 %s %s 경기를 진행합니다. 매너 있는 플레이와 정시 도착이 가능한 팀을 기다립니다.",
                defaultText(fieldName, "선택한 구장"), defaultText(date, "선택한 날짜"), defaultText(time, "선택한 시간"));
        String prompt = String.format(
                "풋살 경기 모집 소개글을 한국어로 2문장 작성해줘. 팀:%s, 구장:%s, 인원:%s, 날짜:%s, 시간:%s. 과장 없이 매너와 준비물을 자연스럽게 포함해줘.",
                defaultText(teamName, "등록팀"), defaultText(fieldName, "구장"), defaultText(people, "인원"),
                defaultText(date, "날짜"), defaultText(time, "시간"));
        return ask(prompt, fallback);
    }

    @Override
    public String generateTeamIntro(String teamName, String stadium) {
        String fallback = String.format("%s은(는) %s 중심으로 활동하는 풋살팀입니다. 즐겁게 뛰면서도 약속과 매너를 중요하게 생각합니다.",
                defaultText(teamName, "우리 팀"), defaultText(stadium, "주 활동 구장"));
        String prompt = String.format(
                "풋살팀 소개글을 한국어 2문장으로 작성해줘. 팀명:%s, 홈구장:%s. 신규 팀원이 팀 분위기를 바로 이해할 수 있게 써줘.",
                defaultText(teamName, "팀"), defaultText(stadium, "홈 구장"));
        return ask(prompt, fallback);
    }

    @Override
    public String recommendMatch(team team) {
        if (team == null) {
            return "팀 정보를 찾을 수 없어 매칭 추천을 만들 수 없습니다.";
        }
        String fallback = localMatchRecommendation(team);
        String prompt = String.format(
                "풋살 매칭 추천 문구를 한국어 2문장으로 작성해줘. 팀:%s, 승:%d, 무:%d, 패:%d, 홈구장:%s. 어떤 상대와 경기하면 좋을지 말해줘.",
                team.getT_name(), team.getT_vic(), team.getT_draw(), team.getT_lose(), defaultText(team.getT_stadium(), "미정"));
        return ask(prompt, fallback);
    }

    @Override
    public String recommendPlayerForTeam(team team) {
        if (team == null) {
            return "팀 정보를 찾을 수 없어 영입 추천을 만들 수 없습니다.";
        }
        String fallback = String.format("%s은(는) 꾸준히 참여하고 수비 가담이 좋은 선수를 우선 찾으면 팀 밸런스를 잡기 좋습니다.",
                defaultText(team.getT_name(), "이 팀"));
        String prompt = String.format(
                "풋살팀 상세 화면에 보여줄 선수 영입 추천을 한국어 2문장으로 작성해줘. 팀:%s, 소개:%s, 승:%d, 무:%d, 패:%d.",
                defaultText(team.getT_name(), "팀"), defaultText(team.getT_intro(), "소개 없음"),
                team.getT_vic(), team.getT_draw(), team.getT_lose());
        return ask(prompt, fallback);
    }

    @Override
    public String generateReview(String homeTeam, String awayTeam, String score, String mvp) {
        String fallback = String.format("%s와 %s의 경기는 %s로 마무리되었습니다. MVP는 %s이며, 다음 경기에서도 좋은 흐름을 기대할 수 있습니다.",
                defaultText(homeTeam, "등록팀"), defaultText(awayTeam, "상대팀"), defaultText(score, "스코어 미입력"), defaultText(mvp, "미정"));
        String prompt = String.format(
                "풋살 경기 후기를 한국어 3문장으로 작성해줘. 등록팀:%s, 상대팀:%s, 스코어:%s, MVP:%s. 과한 표현 없이 커뮤니티 게시글로 쓸 수 있게 작성해줘.",
                defaultText(homeTeam, "등록팀"), defaultText(awayTeam, "상대팀"), defaultText(score, "스코어"), defaultText(mvp, "MVP"));
        return ask(prompt, fallback);
    }

    private String ask(String prompt, String fallback) {
        if (apiKey == null || apiKey.isBlank()) {
            return fallback;
        }
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("x-goog-api-key", apiKey);
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> request = new LinkedHashMap<>();
            request.put("contents", new Object[]{
                    Map.of("parts", new Object[]{
                            Map.of("text", prompt)
                    })
            });
            request.put("generationConfig", Map.of(
                    "maxOutputTokens", 500,
                    "temperature", 0.7
            ));

            String url = String.format("%s/%s:generateContent", baseUrl, model);
            ResponseEntity<String> response = restTemplate.postForEntity(url, new HttpEntity<>(request, headers), String.class);
            return extractText(response.getBody(), fallback);
        } catch (Exception e) {
            log.warn("AI API request failed. fallback text will be used.", e);
            return fallback;
        }
    }

    private String extractText(String body, String fallback) throws JsonProcessingException {
        if (body == null || body.isBlank()) {
            return fallback;
        }
        JsonNode root = objectMapper.readTree(body);
        StringBuilder builder = new StringBuilder();
        for (JsonNode candidate : root.path("candidates")) {
            for (JsonNode part : candidate.path("content").path("parts")) {
                JsonNode text = part.path("text");
                if (!text.asText().isBlank()) {
                    builder.append(text.asText().trim()).append("\n");
                }
            }
        }
        if (builder.length() > 0) {
            return builder.toString().trim();
        }
        return fallback;
    }

    private String localPlayerAnalysis(User player) {
        int goal = player.getU_goal();
        int assist = player.getU_assi();
        int cut = player.getU_cut();
        String type = "밸런스형";
        if (goal >= assist && goal >= cut) {
            type = "득점형";
        } else if (assist >= goal && assist >= cut) {
            type = "연계형";
        } else if (cut >= goal && cut >= assist) {
            type = "수비형";
        }
        return String.format("%s 선수는 현재 %s 플레이 성향이 강합니다. 다음 경기에서는 강점을 살리면서 부족한 지표를 한 가지씩 끌어올리면 종합 점수를 더 빠르게 높일 수 있습니다.",
                player.getU_nick(), type);
    }

    private String localSummary(String title, String contents) {
        String plain = contents.replaceAll("\\s+", " ").trim();
        if (plain.length() > 80) {
            plain = plain.substring(0, 80) + "...";
        }
        if (plain.isBlank()) {
            return "'" + title + "' 게시글입니다.";
        }
        return "'" + title + "' 글은 " + plain + " 내용을 다룹니다.";
    }

    private String localModeration(String text) {
        String value = defaultText(text, "").toLowerCase();
        if (value.contains("죽") || value.contains("꺼져") || value.contains("사기") || value.contains("카톡")) {
            return "표현을 한 번 더 확인하는 것이 좋습니다. 비방, 개인정보, 외부 연락 유도처럼 보일 수 있는 문구가 포함되어 있습니다.";
        }
        return "게시해도 무리가 없습니다. 다만 개인정보와 과도한 연락처 노출은 피하는 것이 좋습니다.";
    }

    private String localMatchRecommendation(team team) {
        int total = team.getT_vic() + team.getT_draw() + team.getT_lose();
        if (total == 0) {
            return String.format("%s은(는) 아직 전적이 적어 비슷한 신규 팀과 첫 매칭을 잡기 좋습니다.", team.getT_name());
        }
        if (team.getT_vic() >= team.getT_lose()) {
            return String.format("%s은(는) 안정적인 흐름이 있어 비슷한 승률의 팀과 경쟁력 있는 매칭을 추천합니다.", team.getT_name());
        }
        return String.format("%s은(는) 전술을 맞춰볼 수 있는 친선 위주의 매칭부터 잡으면 좋습니다.", team.getT_name());
    }

    private String defaultText(String text, String defaultValue) {
        return text == null || text.isBlank() ? defaultValue : text.trim();
    }
}
