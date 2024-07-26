package ttfe;

import java.util.Arrays;
import java.util.Random;

public class ComputerPlayer implements PlayerInterface {
    
    private static final int DEPTH_LIMIT = 4;

    @Override
    public MoveDirection getPlayerMove(SimulatorInterface game, UserInterface ui) {
        MoveDirection[] directions = MoveDirection.values();
        double maxScore = Double.NEGATIVE_INFINITY;
        MoveDirection bestMove = null;

        for (MoveDirection direction : directions) {
            if (game.isMovePossible(direction)) {
                SimulatorInterface copy = copySimulator(game);
                copy.performMove(direction);
                double score = expectimax(copy, DEPTH_LIMIT, false);
                if (score > maxScore) {
                    maxScore = score;
                    bestMove = direction;
                }
            }
        }

        return bestMove != null ? bestMove : directions[0];
    }

    private double expectimax(SimulatorInterface game, int depth, boolean isPlayerTurn) {
        if (depth == 0 || !game.isMovePossible()) {
            return evaluateBoard(game);
        }

        if (isPlayerTurn) {
            double maxScore = Double.NEGATIVE_INFINITY;
            for (MoveDirection direction : MoveDirection.values()) {
                if (game.isMovePossible(direction)) {
                    SimulatorInterface copy = copySimulator(game);
                    copy.performMove(direction);
                    double score = expectimax(copy, depth - 1, false);
                    maxScore = Math.max(maxScore, score);
                }
            }
            return maxScore;
        } else {
            double totalScore = 0;
            int emptyCount = 0;
            for (int x = 0; x < game.getBoardWidth(); x++) {
                for (int y = 0; y < game.getBoardHeight(); y++) {
                    if (game.getPieceAt(x, y) == 0) {
                        emptyCount++;
                        SimulatorInterface copy = copySimulator(game);
                        copy.setPieceAt(x, y, 2);
                        totalScore += 0.9 * expectimax(copy, depth - 1, true);
                        copy.setPieceAt(x, y, 4);
                        totalScore += 0.1 * expectimax(copy, depth - 1, true);
                    }
                }
            }
            return emptyCount == 0 ? evaluateBoard(game) : totalScore / emptyCount;
        }
    }

    private double evaluateBoard(SimulatorInterface game) {
        double score = 0;
        int emptyCount = 0;
        for (int x = 0; x < game.getBoardWidth(); x++) {
            for (int y = 0; y < game.getBoardHeight(); y++) {
                int value = game.getPieceAt(x, y);
                if (value == 0) {
                    emptyCount++;
                } else {
                    score += value * Math.log(value) / Math.log(2);
                    if (x == 0 || x == game.getBoardWidth() - 1 || y == 0 || y == game.getBoardHeight() - 1) {
                        score += value; // Prefer tiles at edges
                    }
                }
            }
        }
        score += emptyCount * 100;
        return score;
    }

    private SimulatorInterface copySimulator(SimulatorInterface original) {
        SimulatorInterface copy = TTFEFactory.createSimulator(original.getBoardWidth(), original.getBoardHeight(), new Random());
        for (int x = 0; x < original.getBoardWidth(); x++) {
            for (int y = 0; y < original.getBoardHeight(); y++) {
                copy.setPieceAt(x, y, original.getPieceAt(x, y));
            }
        }
        return copy;
    }
}