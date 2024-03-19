package chess.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import chess.domain.Board;
import chess.domain.Color;
import chess.domain.File;
import chess.domain.Position;
import chess.domain.Rank;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class QueenTest {
    @DisplayName("생성 테스트")
    @Test
    public void create() {
        assertThatCode(() -> new Queen(Color.BLACK))
                .doesNotThrowAnyException();
    }

    @DisplayName("아군을 만나기 직전까지만 이동 가능하다.")
    @Test
    public void givenQueenMoveWhenMeetTeamMThenStop() {
        Queen queen = new Queen(Color.WHITE);
        Position currentQueenPosition = new Position(File.g, Rank.SEVEN);
        Map<Position, Piece> board = Map.of(
                currentQueenPosition, queen,
                new Position(File.e, Rank.SEVEN), new Pawn(Color.WHITE),
                new Position(File.f, Rank.SIX), new Pawn(Color.WHITE),
                new Position(File.g, Rank.FIVE), new Pawn(Color.WHITE)
        );

        Set<Position> movablePositions = queen.calculateMovablePositions(currentQueenPosition, new Board(board));

        assertThat(movablePositions).isEqualTo(
                Set.of(new Position(File.g, Rank.SIX),
                        new Position(File.g, Rank.EIGHT),
                        new Position(File.h, Rank.SIX),
                        new Position(File.h, Rank.SEVEN),
                        new Position(File.h, Rank.EIGHT),
                        new Position(File.f, Rank.EIGHT),
                        new Position(File.f, Rank.SEVEN)));
    }

    @DisplayName("적군을 만난 위치까지 이동 가능하다.")
    @Test
    public void givenQueenMoveWhenMeetEnemyThenStopAtEnemyPosition() {
        Queen queen = new Queen(Color.WHITE);
        Position currentQueenPosition = new Position(File.g, Rank.SEVEN);
        Map<Position, Piece> board = Map.of(
                currentQueenPosition, queen,
                new Position(File.e, Rank.SEVEN), new Pawn(Color.BLACK),
                new Position(File.f, Rank.SIX), new Pawn(Color.BLACK),
                new Position(File.g, Rank.FIVE), new Pawn(Color.BLACK)
        );

        Set<Position> movablePositions = queen.calculateMovablePositions(currentQueenPosition, new Board(board));

        assertThat(movablePositions).isEqualTo(
                Set.of(new Position(File.g, Rank.SIX),
                        new Position(File.g, Rank.EIGHT),
                        new Position(File.h, Rank.SIX),
                        new Position(File.h, Rank.SEVEN),
                        new Position(File.h, Rank.EIGHT),
                        new Position(File.f, Rank.EIGHT),
                        new Position(File.f, Rank.SEVEN),
                        new Position(File.e, Rank.SEVEN),
                        new Position(File.f, Rank.SIX),
                        new Position(File.g, Rank.FIVE)));
    }
}
