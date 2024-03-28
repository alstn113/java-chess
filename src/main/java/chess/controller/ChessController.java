package chess.controller;

import chess.domain.ChessGame;
import chess.domain.ChessGameStatus;
import chess.controller.command.CommandCondition;
import chess.controller.command.CommandExecutor;
import chess.controller.command.GameCommand;
import chess.view.InputView;
import chess.view.OutputView;
import java.util.List;
import java.util.Map;

public class ChessController {
    private final ChessGame chessGame;
    private final Map<GameCommand, CommandExecutor> commands = Map.of(
            GameCommand.MOVE, this::move,
            GameCommand.START, args -> start(),
            GameCommand.END, args -> end(),
            GameCommand.STATUS, args -> status()
    );

    public ChessController(ChessGame chessGame) {
        this.chessGame = chessGame;
    }

    public void run() {
        OutputView.printGameStartMessage();

        while (chessGame.isPlaying()) {
            repeatUntilValidCommand();
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
        chessGame.start();

        OutputView.printChessBoard(chessGame.getBoard());
    }

    private void move(CommandCondition commandCondition) {
        String source = commandCondition.getSource();
        String target = commandCondition.getTarget();
        chessGame.move(source, target);

        OutputView.printChessBoard(chessGame.getBoard());
    }

    private void end() {
        chessGame.end();
    }

    private void status() {
        ChessGameStatus chessGameStatus = chessGame.status();
        OutputView.printChessGameStatus(chessGameStatus);
    }
}
