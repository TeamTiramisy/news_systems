package ru.rndev.exception;

import lombok.Getter;
import ru.rndev.util.Constant;

@Getter
public class NotAccessRightsException extends RuntimeException{

    private Integer errorCode;

    public NotAccessRightsException(Integer errorCode) {
        super(Constant.AUTHOR_EXCEPTION);
        this.errorCode = errorCode;
    }

}
