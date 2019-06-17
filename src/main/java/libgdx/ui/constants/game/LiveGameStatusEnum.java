package libgdx.ui.constants.game;

public enum LiveGameStatusEnum {
    GAME_PENDING(0),
    WAITING_GAME_START(1),
    GAME_IN_PROGRESS(2),
    GAME_FINISHED(3),
    WANT_MATCH(4),
    REJECT_MATCH(5),;

    private int status;

    LiveGameStatusEnum(int status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}