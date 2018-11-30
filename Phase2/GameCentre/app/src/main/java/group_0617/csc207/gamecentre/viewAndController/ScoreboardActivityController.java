package group_0617.csc207.gamecentre.viewAndController;

import group_0617.csc207.gamecentre.dataBase.DatabaseHelper;

/**
 * The activity controller of scoreboard
 */
class ScoreboardActivityController {

    /**
     * Load game data and build dataString
     * @param slidingTilesData game data for slidingTiles
     * @param twentyFortyEightData game data for 2048
     * @param memoryGameData game data for memory game
     * @param db databasehelper
     * @param scoreboardActivity this scoreboardActivity
     */
    void AddGameData(StringBuilder slidingTilesData,StringBuilder twentyFortyEightData,
                     StringBuilder memoryGameData,DatabaseHelper db,
                     ScoreboardActivity scoreboardActivity) {
        buildDataString(slidingTilesData,
                "Sliding Tiles ", "steasy", "stmedium", "sthard", db);

        //Add the data of 2048 game
        buildDataString(twentyFortyEightData,
                "2048", "tfeasy", "tfmedium", "tfhard", db);

        //Add the data of Memory game
        buildDataString(memoryGameData,
                "Memory", "cardeasy", "cardmedium", "cardhard", db);

        scoreboardActivity.listItem[0] = slidingTilesData.toString();
        scoreboardActivity.listItem[1] = twentyFortyEightData.toString();
        scoreboardActivity.listItem[2] = memoryGameData.toString();
    }


    /**
     * A method which is used to build a string that transfer the data of the specific game
     *  @param stringBuilder a string builder which carry the game data
     * @param game          the name of the game
     * @param easy          the string that represent this game under easy mode
     * @param medium        the string that represent this game under medium mode
     * @param hard          the string that represent this game under hard mode
     */
    private void buildDataString(StringBuilder stringBuilder,String game,String easy,String medium,String hard,DatabaseHelper db) {
        stringBuilder.append(game).append("__");
        stringBuilder.append(Integer.toString(db.getGameData
                (LoginActivity.currentUser, easy))).append("__");
        //Add the score for 4 by 4 sliding tiles game
        stringBuilder.append(Integer.toString(db.getGameData
                (LoginActivity.currentUser, medium))).append("__");
        //Add the score for 5 by 5 sliding tiles game
        stringBuilder.append(Integer.toString(db.getGameData
                (LoginActivity.currentUser, hard)));
    }



}
