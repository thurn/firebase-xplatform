package com.firebase.client;

public class Transaction {
  public static interface Handler {
    Result doTransaction(MutableData currentData);

    void onComplete(FirebaseError error, boolean committed, DataSnapshot currentData);
  }
  public static class Result {
    Object getResultData() {
      if (data == null) {
        return null;
      } else {
        return data.getMutableData();
      }
    }

    private final MutableData data;

    private Result() {
      this.data = null;
    }

    private Result(MutableData result) {
      this.data = result;
    }

    public boolean isSuccess() {
      return data != null;
    }
  }

  public static Result abort() {
    return new Result();
  }

  public static Result success(MutableData resultData) {
    if (resultData == null) {
      throw new IllegalArgumentException("Argument to Transaction.success() was null");
    }
    return new Result(resultData);
  }
}
