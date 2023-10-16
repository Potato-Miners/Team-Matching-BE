package com.teammatching.demo.global.exception;

public class NotEqualsException extends RuntimeException {

    public static class UserAccount extends NotEqualsException {
    }

    public static class TeamAdmin extends NotEqualsException {
    }

    public static class Password extends NotEqualsException {
    }

    public static class PasswordValid extends NotEqualsException {
    }

}
