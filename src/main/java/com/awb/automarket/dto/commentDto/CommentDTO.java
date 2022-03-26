package com.awb.automarket.dto.commentDto;

import com.awb.automarket.customvalidation.CustomDtoAnnotation;
import com.awb.automarket.customvalidation.CustomDtoClassAnnotation;
import com.awb.automarket.entity.Brand;
import com.awb.automarket.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
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



}
