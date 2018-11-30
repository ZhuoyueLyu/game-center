package group_0617.csc207.gamecentre.viewAndController;

/**
 * Controller to LeaderboardActivity
 */
class LeaderboardActivityController {
    /**
     * Rank the score of a given game (descending)
     * @param leaderboardActivity the leaderboard Activity
     */
    void RankTheScore(LeaderboardActivity leaderboardActivity) {
        int n = leaderboardActivity.leaderBoardData.size();
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (leaderboardActivity.leaderBoardData.get(j - 1).getY() < leaderboardActivity.leaderBoardData.get(j).getY()) {
                    String tmp_x = leaderboardActivity.leaderBoardData.get(j - 1).getX();
                    int tmp_y = leaderboardActivity.leaderBoardData.get(j - 1).getY();
                    leaderboardActivity.leaderBoardData.get(j - 1).setX(leaderboardActivity.leaderBoardData.get(j).getX());
                    leaderboardActivity.leaderBoardData.get(j - 1).setY(leaderboardActivity.leaderBoardData.get(j).getY());
                    leaderboardActivity.leaderBoardData.get(j).setX(tmp_x);
                    leaderboardActivity.leaderBoardData.get(j).setY(tmp_y);
                }
            }
        }
        int len = leaderboardActivity.leaderBoardData.size();
        leaderboardActivity.listItem = new String[Math.min(10,len)];
        for (int i = 0; i < 10; i++) {
            if (i <= len - 1) {
                leaderboardActivity.listItem[i] = (Integer.toString(i + 1) + "__" + leaderboardActivity.leaderBoardData.get(i).getX() + "__" +
                        leaderboardActivity.leaderBoardData.get(i).getY());
            }
        }
    }

    /**
     * get the game data from database
     * @param currentGame the name of the current game
     * @param gameComplexity the complexity of the current game
     * @param leaderboardActivity the leaderboardActivity
     */
    void getDataFromDatabase(String currentGame,int gameComplexity,LeaderboardActivity leaderboardActivity) {
        switch (gameComplexity) {
            case 3:
                leaderboardActivity.leaderBoardData = leaderboardActivity.db.getLeaderboardData(currentGame + "easy");
                break;
            case 4:
                leaderboardActivity.leaderBoardData = leaderboardActivity.db.getLeaderboardData(currentGame + "medium");
                break;
            case 5:
                leaderboardActivity.leaderBoardData = leaderboardActivity.db.getLeaderboardData(currentGame + "hard");
                break;
            default:
                break;
        }
    }
}
