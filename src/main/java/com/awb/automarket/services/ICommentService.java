package com.awb.automarket.services;

import com.awb.automarket.dto.ServiceResponseModel;
import com.awb.automarket.dto.commentDto.CommentDTO;

public interface ICommentService {
    ServiceResponseModel findById(Integer id);
    ServiceResponseModel findAllUserComments(Integer userId);
    ServiceResponseModel deleteById(Integer id);
    ServiceResponseModel save(CommentDTO comment);
    ServiceResponseModel update(CommentDTO comment);
}
