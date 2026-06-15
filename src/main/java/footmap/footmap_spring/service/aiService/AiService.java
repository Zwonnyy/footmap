package footmap.footmap_spring.service.aiService;

import footmap.footmap_spring.dto.teamDto.team;
import footmap.footmap_spring.dto.userDto.User;

public interface AiService {
    String analyzePlayer(User player);

    String summarizeBoard(String title, String contents);

    String moderateText(String text);

    String generateGameIntro(String teamName, String fieldName, String people, String date, String time);

    String generateTeamIntro(String teamName, String stadium);

    String recommendMatch(team team);

    String recommendPlayerForTeam(team team);

    String generateReview(String homeTeam, String awayTeam, String score, String mvp);
}
