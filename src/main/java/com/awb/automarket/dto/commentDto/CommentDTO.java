package com.awb.automarket.dto.commentDto;

import com.awb.automarket.customvalidation.CustomDtoAnnotation;
import com.awb.automarket.customvalidation.CustomDtoClassAnnotation;
import com.awb.automarket.entity.Brand;
import com.awb.automarket.entity.Comment;

@CustomDtoClassAnnotation(mappsEntity = Comment.class)
public class CommentDTO {

    public int comment_id;

    public int user_id;
    public String userFullName;

    public int advert_id;
    public String advertTitle;

    @CustomDtoAnnotation(mappsField = "comment")
    public String comment;

    public CommentDTO(Comment comm){
        this.comment_id = comm.getId();
        this.comment = comm.getComment();
        this.user_id = comm.getUser().getId();
        this.userFullName = comm.getUser().getFirstName() + " " + comm.getUser().getLastName();
        this.advert_id = comm.getAdvert().getId();
        this.advertTitle = comm.getAdvert().getTitle();
    }

	public int getComment_id() {
		return comment_id;
	}

	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public int getAdvert_id() {
		return advert_id;
	}

	public void setAdvert_id(int advert_id) {
		this.advert_id = advert_id;
	}

	public String getAdvertTitle() {
		return advertTitle;
	}

	public void setAdvertTitle(String advertTitle) {
		this.advertTitle = advertTitle;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}



}
