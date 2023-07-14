package com.teammatching.demo.result.exception;

public class NotFoundException extends RuntimeException {
    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public NotFoundException(String message) {
        super(message);
    }

    public static class UserAccount extends NotFoundException{
        public UserAccount(){
            super("유저");
        }
    }

    public static class Post extends NotFoundException{
        public Post() {
            super("게시글");
        }
    }

    public static class Comment extends NotFoundException{
        public Comment() {
            super("댓글");
        }
    }

    public static class Team extends NotFoundException{
        public Team() {
            super("팀");
        }
    }

    public static class Admission extends NotFoundException{
        public Admission() {
            super("가입 신청");
        }
    }
}
