package chess.domain;

import chess.domain.position.Position;

public record Move(Position source, Position target) {
}
