package chess.controller;

import static chess.domain.command.GameCommand.SOURCE_INDEX;
import static chess.domain.command.GameCommand.TARGET_INDEX;

import chess.domain.ChessGame;
import chess.domain.command.CommandCondition;
import chess.domain.command.CommandExecutor;
import chess.domain.command.GameCommand;
import chess.view.InputView;
import chess.view.OutputView;
import java.util.List;
import java.util.Map;

public class ChessController {
    private final ChessGame chessGame;
    private final Map<GameCommand, CommandExecutor> commands = Map.of(
            GameCommand.MOVE, this::move,
            GameCommand.START, args -> start(),
            GameCommand.END, args -> end()
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

    private void start() {
        chessGame.start();

        OutputView.printChessBoard(chessGame.getBoard());
    }

    private void move(CommandCondition commandCondition) {
        String source = commandCondition.getArg(SOURCE_INDEX);
        String target = commandCondition.getArg(TARGET_INDEX);
        chessGame.movePiece(source, target);

        OutputView.printChessBoard(chessGame.getBoard());
    }

    private void end() {
        chessGame.end();
    }

    private void repeatUntilValidCommand() {
        try {
            executeCommand();
        } catch (RuntimeException e) {
            OutputView.printErrorMessage(e.getMessage());
            executeCommand();
        }
    }
}
