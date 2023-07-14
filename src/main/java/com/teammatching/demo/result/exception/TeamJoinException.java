package com.teammatching.demo.result.exception;

public class TeamJoinException extends RuntimeException {
    public static class AlreadyApplying extends TeamJoinException {
    }

    public static class AlreadyJoined extends TeamJoinException {
    }
}
