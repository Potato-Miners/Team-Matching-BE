package com.teammatching.demo.result;

import lombok.Getter;

@Getter
public class ResponseMessage {
    public static final String SUCCESS = "요청 성공";

    //Post
    public static final String SUCCESS_GET_SIMPLE_POSTS = "게시글 리스트 간단 조회 요청 성공";
    public static final String SUCCESS_GET_POST_BY_ID = "게시글 상세 조회(댓글 함께) 요청 성공";
    public static final String SUCCESS_CREATE_POST = "게시글 작성 요청 성공";
    public static final String SUCCESS_UPDATE_POST = "게시글 수정 요청 성공";
    public static final String SUCCESS_DELETE_POST = "게시글 삭제 요청 성공";

    //Comment
    public static final String SUCCESS_CREATE_COMMENT = "댓글 작성 요청 성공";
    public static final String SUCCESS_UPDATE_COMMENT = "댓글 수정 요청 성공";
    public static final String SUCCESS_DELETE_COMMENT = "댓글 삭제 요청 성공";

    //MyPage
    public static final String SUCCESS_GET_MY_PAGE = "유저 상세 정보 조회 요청 성공";
    public static final String SUCCESS_UPDATE_ACCOUNT = "유저 정보 수정 요청 성공";
    public static final String SUCCESS_GET_MY_POSTS = "해당 유저가 작성한 게시글 리스트 조회 요청 성공";
    public static final String SUCCESS_GET_MY_COMMENTS = "해당 유저가 작성한 댓글 리스트 조회 요청 성공";
    public static final String SUCCESS_GET_MY_TEAMS = "해당 유저가 속한 팀 리스트 조회 요청 성공";

    //Team
    public static final String SUCCESS_GET_SIMPLE_TEAMS = "팀 리스트 간단 조회 요청 성공";
    public static final String SUCCESS_GET_TEAM_BY_ID = "팀 상세 조회 요청 성공";
    public static final String SUCCESS_CREATE_TEAM = "팀 생성 요청 성공";
    public static final String SUCCESS_UPDATE_TEAM = "팀 수정 요청 성공";
    public static final String SUCCESS_DELETE_TEAM = "팀 삭제 요청 성공";

    //UserAccount
    public static final String SUCCESS_SIGN_UP = "회원 가입 요청 성공";

    //Admission
    public static final String SUCCESS_GET_SIMPLE_ADMISSION = "가입 신청자 리스트 조회 요청 성공";
    public static final String SUCCESS_GET_ADMISSION_BY_USER_ID = "가입 신청 내용 상세 조회 요청 성공";
    public static final String SUCCESS_APPLY_TEAM = "가입 신청 요청 성공";
}
