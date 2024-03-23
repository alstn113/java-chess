package chess.controller;

import static chess.domain.command.GameCommand.SOURCE_INDEX;
import static chess.domain.command.GameCommand.TARGET_INDEX;

import chess.domain.ChessGame;
import chess.domain.command.CommandCondition;
import chess.domain.command.CommandExecutor;
import chess.domain.command.GameCommand;
import chess.view.InputView;
import chess.view.OutputView;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ChessController {
    private final Map<GameCommand, CommandExecutor> commands;
    private final ChessGame chessGame;

    public ChessController(ChessGame chessGame) {
        this.commands = new EnumMap<>(GameCommand.class);
        this.chessGame = chessGame;
    }

    public void run() {
        registerCommands();
        playChess();
    }

    private void registerCommands() {
        commands.put(GameCommand.MOVE, this::move);
        commands.put(GameCommand.START, args -> start());
        commands.put(GameCommand.END, args -> end());
    }

    private void playChess() {
        OutputView.printGameStartMessage();

        while (chessGame.isPlaying()) {
            retryOnException(this::executeCommand);
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

    private void retryOnException(Runnable runnable) {
        try {
            runnable.run();
        } catch (RuntimeException e) {
            OutputView.printErrorMessage(e.getMessage());
            retryOnException(runnable);
        }
    }
}
