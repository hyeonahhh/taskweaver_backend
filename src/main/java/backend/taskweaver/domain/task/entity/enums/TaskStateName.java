package backend.taskweaver.domain.task.entity.enums;

import backend.taskweaver.domain.task.entity.Task;
import backend.taskweaver.global.code.ErrorCode;
import backend.taskweaver.global.exception.handler.BusinessExceptionHandler;

import java.util.Arrays;

public enum TaskStateName {
    BEFORE(0),
    ON_PROGRESS(1),
    COMPLETE(2);

    private final int value;

    TaskStateName(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static TaskStateName find(int inputValue) {
        return Arrays.stream(TaskStateName.values())
                .filter(it -> it.value == inputValue)
                .findAny()
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.TASK_STATE_NOT_FOUND));
    }
}
