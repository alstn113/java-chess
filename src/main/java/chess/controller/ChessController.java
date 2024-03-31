package chess.controller;

import chess.controller.command.CommandCondition;
import chess.controller.command.CommandExecutor;
import chess.controller.command.GameCommand;
import chess.domain.ChessGame;
import chess.domain.ChessGameStatus;
import chess.domain.Move;
import chess.domain.position.Position;
import chess.service.ChessGameService;
import chess.view.InputView;
import chess.view.OutputView;
import java.util.List;
import java.util.Map;

public class ChessController {
    private final ChessGame chessGame;
    private final ChessGameService chessGameService;
    private final Map<GameCommand, CommandExecutor> commands = Map.of(
            GameCommand.START, ignore -> start(),
            GameCommand.STATUS, ignore -> status(),
            GameCommand.END, ignore -> end(),
            GameCommand.MOVE, this::move
    );

    public ChessController(ChessGame chessGame, ChessGameService chessGameService) {
        this.chessGame = chessGame;
        this.chessGameService = chessGameService;
    }

    public void run() {
        OutputView.printGameStartMessage();

        while (chessGame.isPlaying()) {
            repeatUntilValidCommand();
        }

        whenKingIsDead();
    }

    private void whenKingIsDead() {
        if (chessGame.isKingDead()) {
            OutputView.printGameResult(chessGame.getGameResult());
            chessGameService.deleteAllMoves();
        }
    }

    private void executeCommand() {
        List<String> inputCommand = InputView.readGameCommand();
        CommandCondition commandCondition = CommandCondition.of(inputCommand);
        GameCommand gameCommand = commandCondition.gameCommand();

        commands.get(gameCommand).execute(commandCondition);
    }

    private void repeatUntilValidCommand() {
        try {
            executeCommand();
        } catch (RuntimeException e) {
            OutputView.printErrorMessage(e.getMessage());
            repeatUntilValidCommand();
        }
    }

    private void start() {
        List<Move> moveHistories = chessGameService.getMoveHistories();
        chessGame.start(moveHistories);

        printChessBoard();
    }

    private void move(CommandCondition commandCondition) {
        Position source = Position.convert(commandCondition.getSource());
        Position target = Position.convert(commandCondition.getTarget());

        chessGame.move(source, target);
        chessGameService.addMoveHistory(source, target);

        printChessBoard();
    }

    private void end() {
        chessGame.end();
    }

    private void status() {
        ChessGameStatus chessGameStatus = chessGame.status();

        OutputView.printChessGameStatus(chessGameStatus);
    }

    private void printChessBoard() {
        OutputView.printChessBoard(chessGame.getBoard(), chessGame.getCurrentTurn());
    }
}
