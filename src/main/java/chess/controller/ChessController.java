package chess.controller;

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
        chessGame.movePiece(source, target);

        OutputView.printChessBoard(chessGame.getBoard());
    }

    private void end() {
        chessGame.end();
    }

    private void status() {
        // TODO 각 진영의 점수 출력
        // TODO 어느 진영이 이겼는지 결과 출력
    }
}
