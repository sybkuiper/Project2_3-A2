package Model;

import Controller.ViewController;

import java.util.*;

//https://www.baeldung.com/java-copy-hashmap

public class Reversi extends Game {

    ArrayList<String> debugmoves = new ArrayList<>();
    int[] boardWeight = {64, -8, 8, 8, 8, 8, -8, 64,
                         -8, -8, 0, 0, 0, 0, -8, -8,
                          8, 0, 4, 0, 0, 4, 0, 8,
                          8, 0, 0, 1, 1, 0, 0, 8,
                          8, 0, 0, 1, 1, 0, 0, 8,
                          8, 0, 4, 0, 0, 4, 0, 8,
                         -8, -8, 0, 0, 0, 0, -8, -8,
                         64, -8, 8, 8, 8, 8, -8, 64};


    public Reversi(int rows, int columns, String playerOne, ViewController controller, boolean online){
        super(rows, columns, playerOne, controller, online);
        controller.setGame(this);
        System.out.println("game initialized");
    }

    public Reversi(int rows, int columns, String playerOne, String playerTwo, ViewController controller, boolean online){
        super(rows, columns, playerOne, playerTwo, controller, online);
        controller.setGame(this);

        //Todo: initialize scores based on players turn
    }

    @Override
    public Integer think(Map<Integer,String> gameBoard){
        Integer bestMove = 0;
        int bestScore = -1000;
        Map<Integer,String> cloneBoard = new HashMap<>(gameBoard);
        if(getController().playerName.equals(playerOne)) {
            for (Integer key : getLegalMoves(cloneBoard, "B")) {
                //try all empty spots and make them X and do minimax on the gamboard
                if (getGameBoard().get(key).equals("E")) {
                    updateBoard(cloneBoard, key, "B"); // replace "B" by playersTurn
                    int score = minimax(cloneBoard, 0, false);
                    gameBoard.replace(key, "E");
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = key;
                    }
                }
            }
            return bestMove;
        } else {
            for (Integer key : getLegalMoves(cloneBoard, "W")) {
                //try all empty spots and make them X and do minimax on the gamboard
                if (getGameBoard().get(key).equals("E")) {
                    updateBoard(cloneBoard, key, "W"); // replace "B" by playersTurn
                    int score = minimax(cloneBoard, 0, false);
                    gameBoard.replace(key, "E");
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = key;
                    }
                }
            }
            return bestMove;
        }
    }

    public void updateGameBoard(Integer move, String player){
        int key = move;
        if(!online) {
            if (player.equals("AI")) {//playerOne)) {
                updateBoard(key, "B");
                //getController().showPlayer(key);
            } else {
                updateBoard(key, "W");
            }
        } else {
            if (player.equals(playerOne)) {//playerOne)) {
                updateBoard(key, "B");
            //    getController().showPlayer(key);
            } else {
                updateBoard(key, "W");
            }
        }
        System.out.println(player + " has placed move: " + move);
        debugmoves.add(player + " : " +move);
        System.out.println(debugmoves);
        printGameState();
    }

    @Override
    public int minimax(Map<Integer,String> gameBoard,int steps, boolean isMaximizing) {
        String result = checkWinner(getGameBoard());
        if(result != null){
            int score = 0;
            if(result.equals("TIE")){score = 0;}
            if(result.equals("W")){score = -1;}
            if(result.equals("B")){score = 1;}
            return score;
        }

        int bestScore;
        //if its the maximizing turn, the AI's turn, check all outcomes
        if(isMaximizing){
            bestScore = -1000;
            if(getController().playerName.equals(playerOne)) {
                for (Integer key : getLegalMoves(gameBoard, "B")) {
                    Object value = gameBoard.get(key);
                    if (value.equals("E")) {
                        minimax(updateBoard(gameBoard, key, "B"), steps + 1, false);
                        int score = evaluate_board(gameBoard, "B");
                        if (score > bestScore) {
                            bestScore = score;
                        }
                    }
                }
                return bestScore;
                //if its the minimizing turn, the other player, check all outcomes
            } else {
                for (Integer key : getLegalMoves(gameBoard, "W")) {
                    Object value = gameBoard.get(key);
                    if (value.equals("E")) {
                        minimax(updateBoard(gameBoard, key, "W"), steps + 1, false);
                        int score = evaluate_board(gameBoard, "W");
                        if (score > bestScore) {
                            bestScore = score;
                        }
                    }
                }
                return bestScore;
                //if its the minimizing turn, the other player, check all outcomes
            }
        }else {
            if (getController().playerName.equals(playerOne)) {
                bestScore = 1000;
                for (Integer key : getLegalMoves(gameBoard, "W")) {
                    Object value = gameBoard.get(key);
                    if (value.equals("E")) {
                        minimax(updateBoard(gameBoard, key, "W"), steps + 1, true);
                        int score = evaluate_board(gameBoard, "W");
                        if (score < bestScore) {
                            bestScore = score;
                        }
                    }
                }
                return bestScore;
            } else {
            bestScore = 1000;
            for (Integer key : getLegalMoves(gameBoard, "B")) {
                Object value = gameBoard.get(key);
                if (value.equals("E")) {
                    minimax(updateBoard(gameBoard, key, "B"), steps + 1, true);
                    int score = evaluate_board(gameBoard, "B");
                    if (score < bestScore) {
                        bestScore = score;
                    }
                }
            }
            return bestScore;
        }
        }
    }

    public Integer evaluate_board(Map<Integer, String> gameBoard, String player){
        int playerOneScore = 0;
        int playerTwoScore = 0;
        for(Integer key : gameBoard.keySet()){
            if(gameBoard.get(key).equals("B")){
                playerOneScore = playerOneScore + boardWeight[key];
            } else if (gameBoard.get(key).equals("W")){
                playerTwoScore = playerTwoScore + boardWeight[key];
            }
        }
        if(player.equals("B")){
            return playerOneScore;
        } else {
            return playerTwoScore;
        }
    }

    @Override
    public String checkWinner(Map<Integer,String> gameBoard) {
        if((getLegalMoves(gameBoard,"B").size() + (getLegalMoves(gameBoard,"W").size()) == 0)){
            int amountOfBlackTiles = 0;
            int amountOfWhiteTiles = 0;
            for(Integer key : gameBoard.keySet()){
                if(gameBoard.get(key).equals("B")){
                    amountOfBlackTiles = amountOfBlackTiles +1;
                } else if (gameBoard.get(key).equals("W")){
                    amountOfWhiteTiles = amountOfWhiteTiles+1;
                }
            }
           if((amountOfBlackTiles - amountOfWhiteTiles) == 0){
               return "TIE";
           }
           if(amountOfBlackTiles > amountOfWhiteTiles){
               return "B";
           }
           if(amountOfBlackTiles < amountOfWhiteTiles){
               return "W";
          }
        }
        return "Error in victor selection";
    }

    //==================================================
    // Jaspers code

    @Override
    public LinkedHashSet<Integer> getLegalMoves(Map<Integer, String> gameBoard, String color){
        LinkedHashSet<Integer> legalMoves = new LinkedHashSet<>();
        List<Map<Integer,String>> lines = getAllLines(gameBoard);
        for (Map<Integer, String> line : lines) {
            if (color.equals(playerOne)) {
                LinkedHashSet<Integer> moveList = checkLineForMoves(line, "B");
                legalMoves.addAll(moveList);
            } else if (color.equals(playerTwo)) {
                LinkedHashSet<Integer> moveList = checkLineForMoves(line, "W");
                legalMoves.addAll(moveList);
            } else {
                LinkedHashSet<Integer> moveList = checkLineForMoves(line, color);
                legalMoves.addAll(moveList);
            }
        }
        System.out.println(legalMoves);
        return legalMoves;
    }


    private List<Map<Integer,String>> getAllLines(Map<Integer, String> gameBoard){
        List<Map<Integer,String>> lines= new ArrayList<>();

        Map<Integer,String> horizontal = new TreeMap<>();
        Map<Integer,String> vertical = new TreeMap<>();
        Map<Integer,String> leftDiagonal = new TreeMap<>();
        Map<Integer,String> rightDiagonal = new TreeMap<>();
        int position = 7;

        //all horizontal lines
        for (Map.Entry<Integer, String> entry : gameBoard.entrySet()) {
            Integer key = entry.getKey();
            String value = entry.getValue();
            horizontal.put(key,value);
            if(key == position && position < 64){
                lines.add(horizontal);
                horizontal = new TreeMap<>();
                position += 8;
            }
        }

        //all vertical lines
        for(int i=0; i<8; i++){
            int position2 = i;
            for(int x=0; x<7; x++){
                vertical.put(position2,gameBoard.get(position2));
                position2 += 8;
            }
            vertical.put(position2,gameBoard.get(position2));
            lines.add(vertical);
            vertical = new TreeMap<>();
        }

        //all diagonal lines
        for(int i=0; i<8; i++){
            int position3 = i;
            for(int x=0; x < i+1; x++){
                leftDiagonal.put(position3, gameBoard.get(position3));
                position3 += 7;
            }
            position3 = i;
            for(int x=0; x < 8-i; x++){
                rightDiagonal.put(position3, gameBoard.get(position3));
                position3 += 9;
            }
            lines.add(leftDiagonal);
            lines.add(rightDiagonal);
            leftDiagonal = new TreeMap<>();
            rightDiagonal = new TreeMap<>();
        }

        for(int i=56; i<64; i++){
            int position3 = i;
            for(int x=56; x < i+1; x++){
                leftDiagonal.put(position3, gameBoard.get(position3));
                position3 -= 9;
            }
            position3 = i;
            for(int x=0; x < 64-i; x++){
                rightDiagonal.put(position3, gameBoard.get(position3));
                position3 -= 7;
            }
            lines.add(leftDiagonal);
            lines.add(rightDiagonal);
            leftDiagonal = new TreeMap<>();
            rightDiagonal = new TreeMap<>();
        }
        return lines;
    }

    private LinkedHashSet<Integer> checkLineForMoves(Map<Integer, String> line, String color){
        LinkedHashSet<Integer> legalMoves= new LinkedHashSet<>();
        String player = color;
        String enemy;
        if(player.equals("B")){ enemy = "W"; }
        else{ enemy = "B"; }

        boolean playerFound = false;
        boolean enemyFound = false;
        boolean emptyFound = false;
        int emptySpot = -1;

        //look from left to right
        for (Map.Entry<Integer, String> entry : line.entrySet()) {
            Integer key = entry.getKey();
            String value = entry.getValue();
            if(value.equals(player)){
                playerFound = true;
                enemyFound = false;
            }if(value.equals(enemy)){
                enemyFound = true;
            }if (value.equals("E")&& playerFound && enemyFound){
                playerFound = false;
                enemyFound = false;
                legalMoves.add(key);
            } if(value.equals("E") && playerFound && !enemyFound){
                playerFound=false;
                enemyFound=false;
            }
        }

        //look from right to left
        for (Map.Entry<Integer, String> entry : line.entrySet()) {
            Integer key = entry.getKey();
            String value = entry.getValue();
            if(value.equals("E")){
                emptyFound = true;
                enemyFound = false;
                emptySpot = key;
            }if(value.equals(enemy)){
                enemyFound = true;
            }if (value.equals(player)&& emptyFound && enemyFound){
                legalMoves.add(emptySpot);
                emptyFound = false;
                enemyFound = false;
            }if(value.equals(player)&& emptyFound){
                emptyFound = false;
                enemyFound = false;
            }
        }
        return legalMoves;
    }

    public  Map<Integer, String> updateBoard(Map<Integer, String> gameBoard, int madeMove, String color){
        List<Map<Integer,String>> lines = getAllLines(gameBoard);
        String enemy;

        //TODO: make move madeMove

        gameBoard.replace(madeMove, color);

        if(color.equals("B")){ enemy = "W";
        }else{enemy = "B";}

        LinkedHashSet<Integer> needToBeFlippedTotal= new LinkedHashSet<>();


        //check every line on the board if pieces need to be flipped
        for (Map<Integer, String> line : lines) {

            boolean moveFound = false;
            boolean ownPieceFound = false;
            ArrayList<Integer> needToBeFlippedLR = new ArrayList<>();
            ArrayList<Integer> needToBeFlippedRL = new ArrayList<>();

            //left to right
            if (line.containsKey(madeMove)){
                for (Map.Entry<Integer, String> entry : line.entrySet()) {

                    Integer key = entry.getKey();
                    String value = entry.getValue();
                    if(key == madeMove){ moveFound = true; }
                    if(moveFound && value.equals(enemy)){
                        needToBeFlippedLR.add(key);
                    }
                    if(moveFound && !needToBeFlippedLR.isEmpty() && value.equals(color)){
                        needToBeFlippedTotal.addAll(needToBeFlippedLR);
                        break;
                    }
                    if (moveFound && needToBeFlippedLR.isEmpty() && value.equals(color) && key != madeMove){
                        break;
                    }
                    if(moveFound && value.equals("E") && key != madeMove){
                        //System.out.println("hij komt hier");
                        break;
                    }
                }
            }

            //right to left
            if (line.containsKey(madeMove)){
                for (Map.Entry<Integer, String> entry : line.entrySet()) {
                    Integer key = entry.getKey();
                    String value = entry.getValue();
                    if(value.equals(color)){
                        ownPieceFound =  true;
                        needToBeFlippedRL = new ArrayList<>();
                    }
                    if(ownPieceFound && value.equals(enemy)){
                        needToBeFlippedRL.add(key);
                    }
                    if(ownPieceFound && !needToBeFlippedRL.isEmpty() && key == madeMove){
                        needToBeFlippedTotal.addAll(needToBeFlippedRL);
                        break;
                    }
                    if (value.equals("E")){
                        ownPieceFound = false;
                        needToBeFlippedRL = new ArrayList<>();
                    }
                }
            }

        }

        for(Integer key : needToBeFlippedTotal){
            gameBoard.replace(key, color);
        }

        //TODO: flip all in needToBeFlippedTotal
        System.out.println(needToBeFlippedTotal);
        return gameBoard;
    }

    // voor gebruik buiten minimax, dus voor het updaten van zetten op het bord, vervanging van updateGameBoard
    public void updateBoard(int madeMove, String color){
        List<Map<Integer,String>> lines = getAllLines(gameBoard);
        String enemy;

        //TODO: make move madeMove

        gameBoard.replace(madeMove, color);
        getController().updateGrid(madeMove,color);

        if(color.equals("B")){ enemy = "W";
        }else{enemy = "B";}

        LinkedHashSet<Integer> needToBeFlippedTotal= new LinkedHashSet<>();


        //check every line on the board if pieces need to be flipped
        for (Map<Integer, String> line : lines) {

            boolean moveFound = false;
            boolean ownPieceFound = false;
            ArrayList<Integer> needToBeFlippedLR = new ArrayList<>();
            ArrayList<Integer> needToBeFlippedRL = new ArrayList<>();

            //left to right
            if (line.containsKey(madeMove)){
                for (Map.Entry<Integer, String> entry : line.entrySet()) {

                    Integer key = entry.getKey();
                    String value = entry.getValue();
                    if(key == madeMove){ moveFound = true; }
                    if(moveFound && value.equals(enemy)){
                        needToBeFlippedLR.add(key);
                    }
                    if(moveFound && !needToBeFlippedLR.isEmpty() && value.equals(color)){
                        needToBeFlippedTotal.addAll(needToBeFlippedLR);
                        break;
                    }
                    if (moveFound && needToBeFlippedLR.isEmpty() && value.equals(color) && key != madeMove){
                        break;
                    }
                    if(moveFound && value.equals("E") && key != madeMove){
                        //System.out.println("hij komt hier");
                        break;
                    }
                }
            }

            //right to left
            if (line.containsKey(madeMove)){
                for (Map.Entry<Integer, String> entry : line.entrySet()) {
                    Integer key = entry.getKey();
                    String value = entry.getValue();
                    if(value.equals(color)){
                        ownPieceFound =  true;
                    }
                    if(ownPieceFound && value.equals(enemy)){
                        needToBeFlippedRL.add(key);
                    }
                    if(ownPieceFound && !needToBeFlippedRL.isEmpty() && key == madeMove){
                        needToBeFlippedTotal.addAll(needToBeFlippedRL);
                        break;
                    }
                    if (value.equals("E")){
                        ownPieceFound = false;
                        needToBeFlippedRL = new ArrayList<>();
                    }
                }
            }

        }

        for(Integer key : needToBeFlippedTotal){
            gameBoard.replace(key, color);
            getController().updateGrid(key,color);
        }

        //TODO: flip all in needToBeFlippedTotal
        System.out.println(needToBeFlippedTotal);
    }

}
