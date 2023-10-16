package com.teammatching.demo.global.exception;

public class TeamJoinException extends RuntimeException {
    public static class AlreadyApplying extends TeamJoinException {
    }

    public static class AlreadyJoined extends TeamJoinException {
    }

    public static class FullCapacity extends TeamJoinException {
    }
}
