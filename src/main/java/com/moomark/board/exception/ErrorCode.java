package com.moomark.board.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  DONT_KNOW_ERROR("DOESN'T KNOW ERROR", 100000),
  CANNOT_FIND_BOARD("CANNOT_FIND_BOARDINFO", 100001),
  CANNOT_FIND_CATEGORY("CANNOT_FIND_CATEGORYINFO", 100002);

  protected final String msg;
  protected final int code;
}
