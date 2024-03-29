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
            GameCommand.MOVE, this::move,
            GameCommand.START, args -> start(),
            GameCommand.END, args -> end(),
            GameCommand.STATUS, args -> status()
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
        List<Move> moves = chessGameService.getRecentPlayingGameMoves();

        chessGame.start(moves);

        OutputView.printChessBoard(chessGame.getBoard());
    }

    private void move(CommandCondition commandCondition) {
        Position source = Position.convert(commandCondition.getSource());
        Position target = Position.convert(commandCondition.getTarget());

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
