package chess.controller.command;

@FunctionalInterface
public interface CommandExecutor {
    void execute(CommandCondition commandCondition);
}
