package com.finalaeonproject.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Response<T> {
  private boolean status;
  private String message;
  private T data;
}
