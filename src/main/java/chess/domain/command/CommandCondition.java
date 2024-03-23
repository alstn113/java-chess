package chess.domain.command;

import java.util.List;

public record CommandCondition(GameCommand gameCommand, List<String> args) {
    private static final int COMMAND_INDEX = 0;

    public static CommandCondition of(List<String> inputCommand) {
        GameCommand gameCommand = GameCommand.from(inputCommand.get(COMMAND_INDEX));
        List<String> args = inputCommand.subList(COMMAND_INDEX + 1, inputCommand.size());

        validateIsSameArgsCount(gameCommand, args);

        return new CommandCondition(gameCommand, args);
    }

    private static void validateIsSameArgsCount(GameCommand gameCommand, List<String> args) {
        if (!gameCommand.isSameArgsCount(args.size())) {
            throw new IllegalArgumentException("명령어의 인자 개수가 올바르지 않습니다.");
        }
    }

    public String getArg(int index) {
        if (args.size() < index) {
            throw new IllegalArgumentException("현재 명령어에 해당 인덱스의 인자가 존재하지 않습니다.");
        }

        return args.get(index);
    }
}
